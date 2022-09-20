package nvtc.io.conferenceoperator.crd.dataservice;

public class DataServiceSpec {
    private String version;
    private int replicas;
    private String dataId;

    @Override
    public String toString() {
        return "DataServiceSpec{" +
                "version='" + version + '\'' +
                ", replicas=" + replicas +
                ", dataId='" + dataId + '\'' +
                '}';
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
