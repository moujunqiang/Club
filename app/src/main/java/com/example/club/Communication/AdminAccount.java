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

public class AdminAccount extends AppCompatActivity {
    private ImageView profile;      // Profile of user
    private Button mBtnconfirm4,mBtnCancel4;
    private SharedPreferences sprfMain;
    private SharedPreferences.Editor editorMain;
    private Button logOut;

    private Bitmap head = null;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp_me_adm);

        db = new DatabaseHelper(AdminAccount.this);

        sprfMain = getSharedPreferences("config",0);

        String username = sprfMain.getString("username","");
        TextView user_adm_username = findViewById(R.id.adm_username);
        user_adm_username.setText(username);

        profile = findViewById(R.id.adm_portrait);
        head = db.getImage(username);
        profile.setImageBitmap(head);

        logOut = findViewById(R.id.adm_me_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSprfMain();
                Intent intent = new Intent(AdminAccount.this, LogIn.class);
                startActivity(intent);
                AdminAccount.this.finish();
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, manageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button post = findViewById(R.id.admin_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, PostActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button upload = findViewById(R.id.editProfile);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, EditProfile.class);
                startActivity(intent);
                finish();
            }
        });



        //below
        mBtnCancel4=(Button)findViewById(R.id.radio_button_home);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, aHomePage.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_discovery);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, activity.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_attention);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccount.this, messageList.class);
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