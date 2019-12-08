package Tracker;

public class LoggedEmployee {
    private String userName;
    private Boolean isaccess;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getManagement() {
        return isaccess;
    }

    public void setManagement(Boolean management) {
        isaccess = management;
    }

    LoggedEmployee(String userName, Boolean isaccess) {
        this.userName = userName;
        this.isaccess = isaccess;
    }
}
