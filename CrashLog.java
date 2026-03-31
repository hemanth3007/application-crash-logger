
public class CrashLog {

    private String crashId;
    private String crashType;
    private String details;

    //Constructor - Called when creating a new CrashLog Object
    public CrashLog(String cId, String cType, String details) {
        this.crashId = cId;
        this.crashType = cType;
        this.details = details;
    }

    //Getters and Setters for controlled access of private variables
    public String getCrashId() {
        return crashId;
    }

    public String getCrashType() {
        return crashType;
    }

    public String getDetails() {
        return details;
    }

    public void setCrashId(String crashId) {
        this.crashId = crashId;
    }

    public void setCrashType(String crashType) {
        this.crashType = crashType;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    //Overriding toString to reperesent the Collection of Objects. If not overriden, prints ObjectName@HashCiode
    @Override
    public String toString() {
        return "CrashLog { \n\t"
                + "crashId = " + crashId
                + " | Type = " + crashType
                + " | Details = " + details + "\n}";
    }
}
