package com.example.club.Communication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.club.Database.DatabaseHelper;
import com.example.club.R;

public class ClubAccount extends AppCompatActivity {
    ///////
    private ImageView profile;// Profile of user
    private Button logOut;
    private SharedPreferences sprfMain;
    private SharedPreferences.Editor editorMain;
    private Button mBtnconfirm4,mBtnCancel4;

    private Bitmap head = null;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hg_me_club);

        sprfMain= getSharedPreferences("config",0);

        String username = sprfMain.getString("username","");
        TextView club_account_username = findViewById(R.id.club_username);
        db = new DatabaseHelper(ClubAccount.this);

        profile = findViewById(R.id.club_portrait);
        head = db.getImage(username);
        profile.setImageBitmap(head);

        club_account_username.setText(username);

        // return to lohin
        logOut = findViewById(R.id.btn_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSprfMain();
                Intent intent = new Intent(ClubAccount.this, LogIn.class);
                startActivity(intent);
                ClubAccount.this.finish();
            }
        });

        //below
        mBtnCancel4=(Button)findViewById(R.id.radio_button_home);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubAccount.this, aHomePage.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_discovery);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubAccount.this, activity.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_attention);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubAccount.this, messageList.class);
                startActivity(intent);

            }
        });




    }

    public void resetSprfMain(){
        sprfMain= getSharedPreferences("config",0);
        editorMain=sprfMain.edit();
        editorMain.clear();
        editorMain.commit();
    }
}