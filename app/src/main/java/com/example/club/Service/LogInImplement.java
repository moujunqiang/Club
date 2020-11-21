package com.example.club.Service;

import android.content.Context;
import com.example.club.Objects.User;

public class LogInImplement implements LogInService{
    private Context context;

    public LogInImplement(Context context){
        this.context = context;
    }

    @Override
    public String getResult(String username, String password) {
        String result = "";

        com.example.club.Database.LogInImplement login = new com.example.club.Database.LogInImplement(context);
        User user = login.getUser(username);

        if(user.getUsername().equals("")){

            result =  "0User does not exist.";
        }
        else{
            int usertype = user.getType(); // usertype = 0 -> user; 1 -> club; 2 -> system administrator

           if (user.getPassword().equals(password)){
                result = usertype + "Log in successfully.";

           } else {

               result = "0Wrong password.";
           }
        }

        return result;
    }
}
