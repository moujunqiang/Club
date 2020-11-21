package com.example.club.Communication.Noticification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.club.R;

public class register_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user2);

        TextView name = findViewById(R.id.username);
        TextView key = findViewById(R.id.password);
        TextView mail = findViewById(R.id.email);
        TextView year = findViewById(R.id.year);
        TextView gender = findViewById(R.id.gender);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String email = getIntent().getStringExtra("email");
        String user_year = getIntent().getStringExtra("year");
        String user_gender = getIntent().getStringExtra("gender");

        name.setText(username);
        key.setText(password);
        mail.setText(email);
        gender.setText(user_gender);
        year.setText(user_year);


    }
}