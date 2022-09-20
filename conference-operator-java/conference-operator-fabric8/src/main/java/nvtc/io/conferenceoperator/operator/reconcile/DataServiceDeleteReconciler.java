package nvtc.io.conferenceoperator.operator.reconcile;

import io.fabric8.kubernetes.client.KubernetesClient;
import nvtc.io.conferenceoperator.crd.dataservice.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataServiceDeleteReconciler {

    private final KubernetesClient client;
    private Logger logger;


    public DataServiceDeleteReconciler(KubernetesClient kubernetesClient) {
        this.client = kubernetesClient;
        this.logger = LoggerFactory.getLogger(DataServiceDeleteReconciler.class);

    }

    public void reconcile(DataService toDelete) {
        logger.info("Deleting DataService deployment with name {}", "dataservice-" + toDelete.getSpec().getDataId());
        client.apps().deployments().inNamespace("operators").withName("dataservice-" + toDelete.getSpec().getDataId()).delete();
        logger.info("Deleted DataService deployment");
    }
}
