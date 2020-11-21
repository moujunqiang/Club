package com.example.club.Communication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.club.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtDate;
    private TextView txtTime;
    private Button btnDate;
    private Button btnTime;
    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    private Button insert;
    private EditText number;

    private String Date;
    private String time;

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    private EditText Name;
    private EditText Location;
    private EditText Capacity;
    private EditText Description;
    private ImageButton Upload_Image;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    private static final int PICK_IMAGE = 100;
    private com.example.club.Objects.Activity activity =
            new com.example.club.Objects.Activity("",0,
                    "","","","","",
                    "","",0,null,0,"");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        sprfMain = getSharedPreferences("config",0);

        spinner = (Spinner) findViewById(R.id.Type);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("Lecture");
        data_list.add("Game");
        data_list.add("Match");
        data_list.add("Party");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        Name = findViewById(R.id.Activity);
        Location = findViewById(R.id.Location);
        Capacity = findViewById(R.id.Capcaity);
        Description = findViewById(R.id.Description);
        Upload_Image = findViewById(R.id.Upload_Image);

        btnDate= (Button) findViewById(R.id.btn_Date);
        btnTime= (Button) findViewById(R.id.btn_Time);
        txtDate= (TextView) findViewById(R.id.txtDate);
        txtTime= (TextView) findViewById(R.id.txtTime);
        Button submit = findViewById(R.id.submit);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = Name.getText().toString();
                String location = Location.getText().toString();
                int capacity = Integer.parseInt(Capacity.getText().toString());
                String description = Description.getText().toString();
                String type = spinner.getSelectedItem().toString();

                String username = sprfMain.getString("username","");
                int userType = sprfMain.getInt("type",0);

                activity.setActivityID(getId());
                activity.setActivityName(activityName);
                activity.setLocation(location);
                activity.setCapacity(capacity);
                activity.setDescription(description);
                activity.setType(type);
                activity.setDate(Date);
                activity.setTime(time);

                activity.setPostByType(userType);
                activity.setPostUserID(username);

            }
        });

        Upload_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));

                startActivityForResult(intent, PICK_IMAGE);
            }
        });

    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date = year + "/" + monthOfYear + "/" + dayOfMonth;

                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText("You choose：" + year + "Year" + (monthOfYear + 1) + "Month" + dayOfMonth + "Day");
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + ":" + minute;

                        tv.setText("You choose：" + hourOfDay + "Hour" + minute  + "Minute");
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Date:
                showDatePickerDialog(this,  4, txtDate, calendar);;
                break;
            case R.id.btn_Time:
                showTimePickerDialog(this,  4, txtTime, calendar);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == PICK_IMAGE){
            Uri uri = data.getData();
            String x = getPath(uri);

            if (readImage(x)){
                Toast.makeText(PostActivity.this,"Upload successful",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PostActivity.this,"Upload unsuccessful",Toast.LENGTH_SHORT).show();
            }

        }
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

    private boolean readImage(String x)  {

        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];

            fs.read(imgbyte);
            fs.close();

            Bitmap image =  BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
            activity.setImage(image);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private String getId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}