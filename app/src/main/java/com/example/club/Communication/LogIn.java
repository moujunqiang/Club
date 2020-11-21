package com.example.club.Communication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.club.Service.LogInImplement;
import com.example.club.R;

public class LogIn extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView logInButton;
    private TextView register;
    private boolean mbDisplayFlg = false;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    //需要的权限数组 读/写/相机
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA };


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sprfMain = getSharedPreferences("config",0);
        editorMain = sprfMain.edit();

        if(sprfMain.getBoolean("LogIn",false)){
            Intent intent=new Intent(LogIn.this, aHomePage.class);
            startActivity(intent);
            LogIn.this.finish();
        }

        setContentView(R.layout.activity_log_in);

        //检查是否已经获得相机的权限
        if(verifyPermissions(this,PERMISSIONS_STORAGE[2]) == 0){
            Log.e("msg","提示是否要授权");
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 3);
        }else{
            //已经有权限

        }


        logInButton = findViewById(R.id.btn_login);
        username = findViewById(R.id.et_login_user);
        password = findViewById(R.id.et_login_pwd);

        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uName = username.getText().toString().trim();
                String uPassword = password.getText().toString().trim();



                if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPassword)){
                    Toast.makeText(LogIn.this,"Input empty",Toast.LENGTH_SHORT).show();

                } else {
                    LogInImplement lg = new LogInImplement(LogIn.this);
                    String get = lg.getResult(uName, uPassword);

                    // usertype = 1 -> user; 2 -> club; 3 -> system administrator
                    String type = get.substring(0,1);
                    String result = get.substring(1);

                    switch (result){
                        case "Log in successfully.":
                            if (type.equals("0")) {
                                Intent intent = new Intent(LogIn.this, aHomePage.class);

                                editorMain.putString("username", uName);
                                editorMain.putBoolean("LogIn",true);
                                editorMain.putInt("type", 0);
                                editorMain.commit();

                                startActivity(intent);
                                finish();//Destroy this Activity

                            } else if (type.equals("1")) {

                                Intent intent = new Intent(LogIn.this, aHomePage.class);

                                editorMain.putString("username", uName);
                                editorMain.putBoolean("LogIn",true);
                                editorMain.putInt("type",1);
                                editorMain.commit();

                                startActivity(intent);
                                finish();//Destroy this Activity

                            } else {

                                Intent intent = new Intent(LogIn.this, aHomePage.class);

                                editorMain.putString("username", uName);
                                editorMain.putBoolean("LogIn",true);
                                editorMain.putInt("type", 2);
                                editorMain.commit();

                                startActivity(intent);
                                finish();//Destroy this Activity

                            }

                            break;

                        case "User does not exist.":
                            Toast.makeText(LogIn.this,"Account doesn't exit!",Toast.LENGTH_SHORT).show();
                            break;

                        case "Wrong password.":
                            Toast.makeText(LogIn.this,"Wrong password!",Toast.LENGTH_SHORT).show();
                            break;
                    }


                }

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

        register = findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogIn.this, registerUser.class);
                startActivity(intent);
                finish();//Destroy this Activity

            }
        });
    }

    public int verifyPermissions(Activity activity, java.lang.String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity,permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            Log.e("msg","已经同意权限");
            return 1;
        }else{
            Log.e("msg","没有同意权限");
            return 0;
        }
    }

}
