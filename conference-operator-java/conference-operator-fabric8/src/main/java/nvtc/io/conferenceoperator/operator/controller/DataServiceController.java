package nvtc.io.conferenceoperator.operator.controller;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import nvtc.io.conferenceoperator.crd.dataservice.DataService;
import nvtc.io.conferenceoperator.operator.reconcile.DataServiceAddReconciler;
import nvtc.io.conferenceoperator.operator.reconcile.DataServiceDeleteReconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DataServiceController {

    private final KubernetesClient kubernetesClient;
    private final Logger logger;

    private final DataServiceAddReconciler addReconciler;
    private final DataServiceDeleteReconciler deleteReconciler;


    public DataServiceController(KubernetesClient kubernetesClient, DataServiceAddReconciler addReconciler, DataServiceDeleteReconciler deleteReconciler) {
        this.kubernetesClient = kubernetesClient;
        this.addReconciler = addReconciler;
        this.deleteReconciler = deleteReconciler;
        this.logger = LoggerFactory.getLogger(DataServiceController.class);
    }

    public void run() {

        logger.info("Registering CRD from generated sources");
        // apply CRD if not present
        var crd = kubernetesClient.apiextensions().v1().customResourceDefinitions().load("META-INF/fabric8/dataservices.java.nvtc.io-v1.yml").get();
        kubernetesClient.apiextensions().v1().customResourceDefinitions().resource(crd).createOrReplace();
        logger.info("CRD registered");

        // register SharedIndexInformers to get notified when CR is created, edited or deleted
        try (var informer = kubernetesClient.informers().inNamespace("operators").sharedIndexInformerFor(DataService.class, 10 * 1000L)) {
            logger.info("Registering event handlers for DataService CRs");
            informer.addEventHandler(new DataServiceResourceEventHandler());
            logger.info("Starting SharedIndexInformer for DataServices");
            informer.run();

            // Thread needs to stay alive, else EventHandler are unregistered
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("interrupted", e);
        }
    }

    private class DataServiceResourceEventHandler implements ResourceEventHandler<DataService> {

        @Override
        public void onAdd(DataService dataService) {
            logger.info("Added DataService for DataId {}", dataService.getSpec().getDataId());
            addReconciler.reconcile(dataService);
        }

        @Override
        public void onUpdate(DataService old, DataService updated) {
            // NYI for this operator
        }

        @Override
        public void onDelete(DataService dataService, boolean b) {
            logger.info("Deleted DataService {}", dataService.getMetadata().getName());
            deleteReconciler.reconcile(dataService);
        }
    }
}
