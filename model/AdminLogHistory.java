package model;

public class AdminLogHistory {
    private int logId;
    private int adminId;
    private String action;
    private String timeStamp;

    // Constructor
    public AdminLogHistory(int logId, int adminId, String action, String timeStamp) {
        this.logId = logId;
        this.adminId = adminId;
        this.action = action;
        this.timeStamp = timeStamp;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "AdminLogHistory [LogID=" + logId + ", AdminID=" + adminId + ", Action=" + action 
               + ", TimeStamp=" + timeStamp + "]";
    }
}
