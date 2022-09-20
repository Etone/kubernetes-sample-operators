package nvtc.io.conferenceoperator.crd.dataservice;

public class DataServiceStatus {

    private int currentReplicas;
    private String currentVersion;
    private String dataId;

    @Override
    public String toString() {
        return "DataServiceStatus{" +
                "currentReplicas=" + currentReplicas +
                ", currentVersion='" + currentVersion + '\'' +
                ", dataId='" + dataId + '\'' +
                '}';
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getCurrentReplicas() {
        return currentReplicas;
    }

    public void setCurrentReplicas(int currentReplicas) {
        this.currentReplicas = currentReplicas;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
