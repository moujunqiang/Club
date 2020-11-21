package com.example.club.Service;


public interface Register {
    public String registerUser(String username, String password, int type, String emailAddress, int year, String gender);
    public String registerClub(String clubID, String password, String emailAddress, String type);
}
