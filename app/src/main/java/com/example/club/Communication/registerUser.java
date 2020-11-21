package com.example.club.Communication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.club.Communication.Noticification.*;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.club.Database.DatabaseHelper;
import com.example.club.R;
import com.example.club.Service.LogInImplement;
import com.example.club.Service.RegisterImplement;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class registerUser extends AppCompatActivity {
    private TextView registerClub;
    private TextView register;

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton registerReturn;
    private boolean mbDisplayFlg = false;
    private EditText emailAddress;

    private String gender;
    private TextView year;

    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;

    int isCan = 0;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannels();

        registerClub = findViewById(R.id.registerClub2);
        registerClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerUser.this, registerClub.class);
                startActivity(intent);
                registerUser.this.finish();

            }
        });

        // return to log in
        registerReturn = findViewById(R.id.ib_return);
        registerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerUser.this, LogIn.class);
                startActivity(intent);
                registerUser.this.finish();

            }
        });


        username = findViewById(R.id.et_register_user);
        password = findViewById(R.id.et_register_pwd);
        register = findViewById(R.id.register2);
        confirmPassword = findViewById(R.id.et_register_confirm_pwd);
        year = findViewById(R.id.tv_year);
        emailAddress = findViewById(R.id.et_user_mail);
        final RadioGroup radioGroup = findViewById(R.id.radio_gender);
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String uName = username.getText().toString().trim();
                String uPassword = password.getText().toString().trim();
                String cPassword = confirmPassword.getText().toString().trim();
                String uEmail = emailAddress.getText().toString().trim();


                if (TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPassword) || TextUtils.isEmpty(cPassword) || TextUtils.isEmpty(uEmail)) {
                    Toast.makeText(registerUser.this, "Input empty", Toast.LENGTH_SHORT).show();

                } else if (uPassword.equals(cPassword) == false) {
                    Toast.makeText(registerUser.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                } else {
                    String userName = username.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();
                    String userEmail = emailAddress.getText().toString().trim();
                    int useryear = Integer.parseInt(year.getText().toString());
                    RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    gender = rb.getText().toString();

                    RegisterImplement obj = new RegisterImplement(registerUser.this);
                    String result = obj.registerUser(userName, userPassword,0,userEmail,useryear,gender);

                    switch (result){
                        case "User already exist":
                            Toast.makeText(registerUser.this, "User already exist!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registerUser.this, LogIn.class);
                            startActivity(intent);
                            finish();
                            break;

                        case "User insert failed":
                            Toast.makeText(registerUser.this, "User insert failed!", Toast.LENGTH_SHORT).show();
                            break;

                        case "User insert successfully":
                            sendOnChannel1(userName,userPassword,userEmail,useryear,gender);

                            Intent success = new Intent(registerUser.this, LogIn.class);
                            startActivity(success);
                            finish();

                            DatabaseHelper ob = new DatabaseHelper(registerUser.this);
                            byte[] imgbyte = imgToByte(R.drawable.defaultmale);
                            ob.insertDefultImage(userName, imgbyte);

                            break;
                    }
                }


                // gender

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                        gender = rb.getText().toString();
                    }
                });

                //hide password
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password, display "."
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg = !mbDisplayFlg;
                password.postInvalidate();


            }

        });


        List<Integer> list = new ArrayList<Integer>();
        list.add(2017);
        list.add(2018);
        list.add(2019);
        list.add(2020);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        Spinner sp = (Spinner) findViewById(R.id.Spinner01);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//获取Spinner控件的适配器
                ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) parent.getAdapter();
                year.setText(adapter.getItem(position) + "");
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });


    }

    public byte [] imgToByte ( int id ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
        bitmap.compress(Bitmap. CompressFormat.PNG, 100 , baos);
        return baos.toByteArray();
    }

    public void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        try {
            File desFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(desFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel1.setDescription("This is Channel 1");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    public void sendOnChannel1(String name,String password,String email,int year,String gender){
        String title = "Welcome to OCOC!";
        String message = "You have registered successfully. Click to view details.";

        Intent resultIntent = new Intent(this, register_user.class);

        resultIntent.putExtra("username",name);
        resultIntent.putExtra("password",password);
        resultIntent.putExtra("email",email);
        resultIntent.putExtra("year",year);
        resultIntent.putExtra("gender",gender);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification  = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.register_user)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(1,notification);
    }

}