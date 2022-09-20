package nvtc.io.conferenceoperator.operator.reconcile;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import nvtc.io.conferenceoperator.crd.dataservice.DataService;
import nvtc.io.conferenceoperator.crd.dataservice.DataServiceSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class DataServiceAddReconciler {

    private KubernetesClient client;
    private Logger logger;

    public DataServiceAddReconciler(KubernetesClient client) {
        this.client = client;
        this.logger = LoggerFactory.getLogger(DataServiceAddReconciler.class);
    }

    public void reconcile(DataService toAdd) {
        logger.info("Creating deployment for DataId {}", toAdd.getSpec().getDataId());
        client.apps().deployments().createOrReplace(dataServiceDeployment(toAdd.getSpec()));
        logger.info("Created deployment");
    }

    private Deployment dataServiceDeployment(DataServiceSpec toAdd) {
        return new DeploymentBuilder()
                .withNewMetadata()
                    .withName("dataservice-" + toAdd.getDataId())
                    .withLabels(Map.of(
                            "app", "dataservice-" + toAdd.getDataId(),
                            "version", toAdd.getVersion()
                    ))
                .and()
                .withNewSpec()
                    .withReplicas(toAdd.getReplicas())
                    .withNewTemplate()
                        .withNewMetadata()
                            .withLabels(Collections.singletonMap("app", "dataservice-" + toAdd.getDataId()))
                        .and()
                        .withNewSpec()
                            .addNewContainer()
                                .withName("app-container")
                                .withImage("busybox")
                                .withCommand("/bin/sh", "-c", "--")
                                .withArgs("while true; do sleep 30; done;")
                            .and()
                        .and()
                    .and()
                    .withNewSelector()
                        .addToMatchLabels("app", "dataservice-" + toAdd.getDataId())
                    .and()
                .and()
                .build();
    }
}
