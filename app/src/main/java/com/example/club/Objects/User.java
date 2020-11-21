package com.example.club.Objects;


public class User {
    private String username;
    private String password;
    private int type;
    private String emailAddress;
    private int year;
    private String gender;


    public User(String username, String password, int type, String emailAddress, int year, String gender){
        this.username = username;
        this.password = password;
        this.type = type;
        this.emailAddress = emailAddress;
        this.year = year;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public int getYear() {
        return year;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
