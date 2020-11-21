package com.example.club.Objects;

public class Club {
    private String clubID;
    private String password;
    private String emailAddress;
    private String type;

    public Club(String clubID, String password, String emailAddress,
                String type){
        this.clubID = clubID;
        this.password = password;
        this.emailAddress = emailAddress;
        this.type = type;
    }

    public String getClubID() {
        return clubID;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setType(String type) {
        this.type = type;
    }
}
