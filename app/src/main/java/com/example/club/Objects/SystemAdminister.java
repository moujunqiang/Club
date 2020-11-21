package com.example.club.Objects;

public class SystemAdminister {
    private String adminID;
    private String password;
    private String userID;
    private String emailAddress;

    public SystemAdminister(String adminID, String password, String userID, String emailAddress) {
        this.adminID = adminID;
        this.password = password;
        this.userID = userID;
        this.emailAddress = emailAddress;
    }

    public String getAdminID() {
        return adminID;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
