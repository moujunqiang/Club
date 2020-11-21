package com.example.club.Communication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.club.Communication.Adapter.ActivityAdapter;
import com.example.club.Communication.Adapter.MyAdapter;
import com.example.club.Database.DatabaseHelper;
import com.example.club.Objects.Activity;
import com.example.club.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class manageActivity extends AppCompatActivity {
    public static Context CONTEXT;
    public static List<Activity> activityList = new ArrayList<>();

    public DatabaseHelper db;

    public static ListView listView;
    public static boolean isMultipleSelectionMode;//判断进入多选模式
    public static ArrayList<HashMap<String, Object>> AdapterList = new ArrayList<>();  //数据
    static MyAdapter listItemAdapter;//适配器

    TextView counttext;
    LinearLayout ItemToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        db = new DatabaseHelper(manageActivity.this);

//        counttext = this.findViewById(R.id.counttext);//选中时更改的textview
//        ItemToolBar = this.findViewById(R.id.ItemToolBar);//多选模式的工具栏

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listItemAdapter = new MyAdapter(AdapterList); //新建并配置ArrayAapeter
        listView.setAdapter(listItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p, View v, int index,
                                    long arg3) {
                if (isMultipleSelectionMode) {
                    setCountChange();

                }
                listItemAdapter.notifyDataSetChanged();
            }


        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long itemId) {

//                if (isMultipleSelectionMode) {
//                    ItemToolBar.setVisibility(View.GONE);
//                    isMultipleSelectionMode = false;
//
//                    list.clearChoices();//取消选中状态
//                    Toast.makeText(CONTEXT, "退出多选模式", Toast.LENGTH_LONG).show();
//                } else {
//                    ItemToolBar.setVisibility(View.VISIBLE);
//                    isMultipleSelectionMode =true;
//                    listItemAdapter.notifyDataSetChanged();
//                    //多选模式
//                    Toast.makeText(CONTEXT, "进入多选模式", Toast.LENGTH_LONG).show();
//                    for(int i = 0;i < listItemAdapter.balistItem.size();i++){
//                        Log.d("del","Item" + i + "的状态：" + list.isItemChecked(i));
//
//                    }
//
//
//                    return true;
//
//                }

                listItemAdapter.notifyDataSetChanged();
                setCountChange();

                return false;
            }
        });
        //设置按钮单机事件绑定
//        setButtonClick();

//        initial();

    }

    public void setCountChange(){
//        counttext.setText("选中了" + list.getCheckedItemCount() +"项");
    }

//    public void setButtonClick(){
//        Button all = this.findViewById(R.id.all);
//        Button unall = this.findViewById(R.id.unall);
//        Button del = this.findViewById(R.id.del);
//
//        all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i = 0;i < listItemAdapter.balistItem.size();i++){
//                    listView.setItemChecked(i,true);
//                }
//                setCountChange();
//            }
//        });
//
//        unall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i = 0;i < listItemAdapter.balistItem.size();i++){
//                    if(listView.isItemChecked(i)){
//                        listView.setItemChecked(i,false);
//                        listView.setItemChecked(i,false);
//                    }else {
//                        listView.setItemChecked(i,true);
//                    }
//                }
//                setCountChange();
//            }
//        });
//
//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for(int i = 0;i < listItemAdapter.balistItem.size();i++){
//                    if(listView.isItemChecked(i)){
//                        listItemAdapter.balistItem.remove(i);
//                        listItemAdapter.notifyDataSetChanged();
//                    }
//                }
//                listView.clearChoices();
//                setCountChange();
//            }
//        });
//
//
//
//    }
//
//    private void initial() {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getBaseContext().getResources(), R.drawable.yoga);
//
//
//        Activity activity1 = new Activity("001",1,"Yuqi.Guo17","Lecture", "CB","2020-10-30","19:00","Lecture","Solo",30,bitmap,0,"");
//        activityList.add(activity1);
//
//        Activity activity2 = new Activity("002",1,"Yuqi.Guo17","Lecture", "CB","2020-10-30","19:00","Lecture","Solo",30,bitmap,0,"");
//        activityList.add(activity2);
//
//        Activity activity3 = new Activity("003",1,"Yuqi.Guo17","Lecture", "CB","2020-10-30","19:00","Lecture","Solo",30,bitmap,0,"");
//        activityList.add(activity3);
//    }
}