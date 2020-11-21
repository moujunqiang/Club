package com.example.club.Communication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class registerClub extends AppCompatActivity {
    private TextView registerUser;
    private TextView register;

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    protected ImageButton registerReturn;
    private boolean mbDisplayFlg = false;
    private EditText type;
    private EditText emailAddress;
    private EditText verificationCode;
    private Button send;
    private int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_club);
        username = findViewById(R.id.et_club_user);
        type = findViewById(R.id.et_club_type);
        password = findViewById(R.id.et_club_pwd);
        confirmPassword = findViewById(R.id.et_club_confirm_pwd);
        send = findViewById(R.id.bt_send);
        emailAddress = findViewById(R.id.et_club_mail);
        verificationCode=findViewById(R.id.et_code);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        registerUser = findViewById(R.id.registerUser1);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerClub.this, registerUser.class);
                startActivity(intent);
                registerClub.this.finish();
            }
        });

        register = findViewById(R.id.register1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vCode = verificationCode.getText().toString().trim();
                if (TextUtils.isEmpty(vCode)) {
                    Toast.makeText(registerClub.this, "Input empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(verificationCode.getText().toString().trim().equals(code+"")){
                    String clubName = username.getText().toString().trim();
                    String clubType = type.getText().toString().trim();
                    String clubPassword = password.getText().toString().trim();
                    String clubEmail = emailAddress.getText().toString().trim();

                    RegisterImplement obj = new RegisterImplement(registerClub.this);
                    String result = obj.registerClub(clubName, clubPassword, clubEmail, type.getText().toString());

                    switch (result) {
                        case "Club already exist":
                            Toast.makeText(registerClub.this, "Club already exist!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registerClub.this, LogIn.class);
                            startActivity(intent);
                            finish();
                            break;

                        case "Club insert failed":
                            Toast.makeText(registerClub.this, "Club insert failed!", Toast.LENGTH_SHORT).show();
                            break;

                        case "Club insert successfully":
                            Intent success = new Intent(registerClub.this, LogIn.class);
                            startActivity(success);
                            finish();

                            DatabaseHelper ob = new DatabaseHelper(registerClub.this);
                            byte[] imgbyte = imgToByte(R.drawable.defaultmale);
                            ob.insertDefultImage(clubName, imgbyte);

                            break;
                    }

                }else {
                    Toast.makeText(registerClub.this, "Incorrect verification code", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // return to login
        registerReturn = findViewById(R.id.ib_return);
        registerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerClub.this, LogIn.class);
                startActivity(intent);
                registerClub.this.finish();

            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailAddress = (EditText) findViewById(R.id.et_club_mail);
                String sendtoEmail = emailAddress.getText()+"";
                System.out.println(sendtoEmail);
                //此处填写点击按钮后，要执行的代码
                sendEmail(sendtoEmail);
                Toast.makeText(getApplicationContext(),"Sent Successfully",Toast.LENGTH_SHORT).show();
            }


        });



        List<String> list = new ArrayList<String>();
        list.add("Functional Organizations");
        list.add("Academic Clubs");
        list.add("Community Service Clubs");
        list.add("Recreational Clubs");
        list.add("Art Organizations & Clubs");
        list.add("Recreational Clubs");
        list.add("Sports Clubs");
        list.add("Varsity");
        list.add("other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        Spinner sp = (Spinner) findViewById(R.id.Spinner02);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tvResult = (TextView) findViewById(R.id.et_club_type);
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                tvResult.setText(adapter.getItem(position));
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void sendEmail(String emailAddress) {


        //verification code
        code = (int)(Math.random()*(9999-1000+1))+1000;
        // 收件人电子邮箱
        String to = emailAddress;
        // 发件人电子邮箱
        String from = "374887020@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("374887020@qq.com", "zatvfhrzyjilbjid"); //发件人邮件用户名、密码
            }
            //zatvfhrzyjilbjid
            //imrryojespzqbiic
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("WELCOME TO OCOC!");

            // 设置消息体
            message.setText("You have signed in successfully.\n \n The verification code is: " + code);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from OCOC");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
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


}
