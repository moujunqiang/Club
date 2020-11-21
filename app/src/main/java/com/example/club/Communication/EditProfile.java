package com.example.club.Communication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.club.Database.DatabaseHelper;
import com.example.club.R;
import com.example.club.view.CustomDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtool.RxFileTool;
import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.view.RxToast;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_BY_CAMERA;
import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_FROM_PHONE;

public class EditProfile extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private Button image;
    private ImageView profile;
    private DatabaseHelper db;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    private CustomDialog photo_dialog;
    private RxPermissions rxPermissions;
    private String photo_path01 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        
        rxPermissions = new RxPermissions(this);
        sprfMain = getSharedPreferences("config",0);
        db = new DatabaseHelper(EditProfile.this);

        image = findViewById(R.id.Image);
        profile = findViewById(R.id.Profile);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));

                startActivityForResult(intent, PICK_IMAGE);

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });

        Button back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userType = sprfMain.getInt("type",0);

                switch (userType){
                    //Normal user
                    case 0:
                        break;

                        // Club user
                    case 1:
                        break;

                        // System admin
                    case 2:
                        break;
                }
            }
        });

    }

    void showPhotoDialog() {
        if (null == photo_dialog) {
            photo_dialog = new CustomDialog(EditProfile.this, R.layout.photo_dialog, new int[]{R.id.tv_camera, R.id.tv_photo, R.id.tv_cancel}, false, Gravity.BOTTOM);
            photo_dialog.setOnDialogItemClickListener(new CustomDialog.OnCustomDialogItemClickListener() {
                @Override
                public void OnCustomDialogItemClick(CustomDialog dialog, View view) {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            photo_dialog.dismiss();
                            break;
                        case R.id.tv_camera:
                            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                                if (aBoolean) {
                                    RxPhotoTool.openCameraImage(EditProfile.this);
                                    photo_dialog.dismiss();
                                }
                            });
                            break;

                        case R.id.tv_photo:
                            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                                if (aBoolean) {
                                    RxPhotoTool.openLocalImage(EditProfile.this);
                                    photo_dialog.dismiss();
                                }
                            });
                            break;
                    }
                }
            });
        }
        photo_dialog.show();
    }

    private String getPath(Uri uri) {
        if (uri==null) return null;

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null, null);

        if (cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == PICK_IMAGE){
            Uri uri = data.getData();
            String x = getPath(uri);

            String id = sprfMain.getString("username","");
            System.out.println(id);
            if (db.amendImage(x, id)){
                Toast.makeText(EditProfile.this,"Successful",Toast.LENGTH_SHORT).show();

                profile.setImageBitmap(db.getImage(sprfMain.getString("username","")));
            } else {
                Toast.makeText(EditProfile.this,"Failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (resultCode != 0) {

            switch (requestCode) {

                case GET_IMAGE_BY_CAMERA:

                    String dz = RxPhotoTool.getRealFilePath(EditProfile.this, RxPhotoTool.imageUriFromCamera);

                    Luban.with(EditProfile.this).load(dz).ignoreBy(100).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {

                            if (file.exists()) {
                                photo_path01 = file.getPath();
                                Glide.with(EditProfile.this).load(photo_path01).thumbnail(0.5f).into(profile);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            RxToast.error(e.getMessage());
                        }
                    }).launch();

                    break;

                case GET_IMAGE_FROM_PHONE://图库

                    if (null != data.getData()) {

                        File files = new File(RxPhotoTool.getImageAbsolutePath(EditProfile.this, data.getData()));

                        Luban.with(EditProfile.this).load(files).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {

                                if (file.exists()) {
                                    photo_path01 = file.getPath();
                                    Glide.with(EditProfile.this).load(photo_path01).thumbnail(0.5f).into(profile);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                RxToast.error(e.getMessage());
                            }
                        }).launch();
                    }
                    break;

            }

        }
    }
}