package nvtc.io.conferenceoperator.crd.dataservice;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1alpha1")
@Group("java.nvtc.io")
public class DataService extends CustomResource<DataServiceSpec, DataServiceStatus> implements Namespaced {}
