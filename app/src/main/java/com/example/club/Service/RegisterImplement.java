package com.example.club.Service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.club.Objects.Club;
import com.example.club.Objects.User;

public class RegisterImplement implements Register{
    private Context context;

    public RegisterImplement(Context context){
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String registerUser(String username, String password, int type, String emailAddress, int year, String gender) {
        String result = "";
        com.example.club.Database.RegisterImplement register = new com.example.club.Database.RegisterImplement(context);
        User user = register.queryRegistered(username);
        if(!user.getUsername().equals("")){
            result = "User already exist";
        }
        else{
            User newuser = new User(username, password, type, emailAddress, year, gender);
            boolean insert_success = register.insertUser(newuser);
            if(!insert_success){
                result = "User insert failed";
            }
            else result = "User insert successfully";
        }
        return result;
    }

    @Override
    public String registerClub(String clubID, String password, String emailAddress, String type) {
        String result = "";
        com.example.club.Database.RegisterImplement register = new com.example.club.Database.RegisterImplement(context);
        User club = register.queryRegistered(clubID);

        if (!club.getUsername().equals("")) {
            result = "Club already exist";
        } else {
            Club newclub = new Club(clubID, password, emailAddress, type);
            User newuser = new User(clubID, password, 1, emailAddress, 0, "");
            boolean insert_success_user = register.insertUser(newuser);
            boolean insert_success_club = register.insertClub(newclub);
            if (!insert_success_user || !insert_success_club) {
                result = "Club insert failed";
            } else {
                result = "Club insert successfully";
            }

        }
        return result;
    }

}
