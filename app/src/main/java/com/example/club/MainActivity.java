package com.example.club;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.club.Communication.LogIn;
import com.example.club.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private static String CALENDER_URL = "content://com.android.calendar/calendars"; //日历用户的URL
    private static String CALENDER_EVENT_URL = "content://com.android.calendar/events";//事件的URL
    private static String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders"; //事件提醒URL

    private static int SPLASH_SCREEN=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}