package com.example.club.Communication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.club.R;

public class messageList extends AppCompatActivity {
    private Button mBtnconfirm4,mBtnCancel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp_message_list);

        //below
        mBtnCancel4=(Button)findViewById(R.id.radio_button_home);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(messageList.this, aHomePage.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_profile);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(messageList.this, UserAccount.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_discovery);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(messageList.this, activity.class);
                startActivity(intent);

            }
        });


    }
}

