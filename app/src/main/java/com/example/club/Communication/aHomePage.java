package com.example.club.Communication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.club.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class aHomePage extends AppCompatActivity {

    private Button mBtnconfirm4,mBtnCancel4;
    private ImageButton mBtnPop;
    private ImageButton mBtnPop1;
    private PopupWindow mPop;
    private PopupWindow mPop1;
    private Button mBtnDialog;
    private Button mBtnDialog1;


    //end
    private RadioGroup mRadioGroup;
    private Fragment[]mFragments;
    private RadioButton mRadioButtonHome;
    //scroll
    private ScrollView mScro;
    private LinearLayout mLinear;
    private TextView mTv;

    private HorizontalScrollView hscroll;
    private LinearLayout hlinear;
    private ImageButton htv;

    //list
    private String[] names = new String[]{"food club", "dance club", "music club"};
    private String[] says = new String[]{"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"};
    private int[] imgIds = new int[]{R.drawable.food, R.drawable.dance, R.drawable.music};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_wl);


        //below
        mBtnCancel4=(Button)findViewById(R.id.radio_button_profile);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aHomePage.this, UserAccount.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_discovery);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aHomePage.this, activity.class);
                startActivity(intent);

            }
        });
        mBtnCancel4=(Button)findViewById(R.id.radio_button_attention);
        mBtnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aHomePage.this, messageList.class);
                startActivity(intent);

            }
        });

        //list
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        ListView listView= (ListView) findViewById(R.id.hh);
        listView.setAdapter(myAdapter);

        //pop
        mBtnPop=(ImageButton)findViewById(R.id.btn_pop);
        mBtnPop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                View view=getLayoutInflater().inflate(R.layout.layout_pop,null);
                mBtnDialog=(Button)view.findViewById(R.id.tv_sports);
                mBtnDialog.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
                mBtnDialog=(Button)view.findViewById(R.id.tv_arts);
                mBtnDialog.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
                mPop=new PopupWindow(view,200, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPop.setOutsideTouchable(true);
                mPop.setFocusable(true);
                mPop.showAsDropDown(mBtnPop);
            }
        });



        //pop1
        mBtnPop1=(ImageButton)findViewById(R.id.btn_pop1);
        mBtnPop1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                View view=getLayoutInflater().inflate(R.layout.layout_pop1,null);
                mBtnDialog1=(Button)view.findViewById(R.id.tv_time);
                mBtnDialog1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
                mBtnDialog1=(Button)view.findViewById(R.id.tv_hot);
                mBtnDialog1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
                mPop1=new PopupWindow(view,250, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPop1.setOutsideTouchable(true);
                mPop1.setFocusable(true);
                mPop1.showAsDropDown(mBtnPop1);
            }
        });


    }

}

