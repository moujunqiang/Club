package com.example.club.Communication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.club.Objects.Activity;
import com.example.club.R;
import com.example.club.Communication.manageActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, Object>> balistItem = new ArrayList<HashMap<String, Object>>();

    public MyAdapter(List<? extends Map<String, ?>> data) {
        this.balistItem = (ArrayList<HashMap<String, Object>>) data;
    }

    @Override
    public int getCount() {
        return balistItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int p1) {
        return p1;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(manageActivity.CONTEXT, R.layout.manage_activity, null);

            viewHolder.activityImage = convertView.findViewById(R.id.activity_image);
            viewHolder.activityName = convertView.findViewById(R.id.activity_name);
            viewHolder.location = convertView.findViewById(R.id.location);
            viewHolder.club = convertView.findViewById(R.id.club);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.activityName.setText(balistItem.get(position).get("标题").toString());
        viewHolder.location.setText(balistItem.get(position).get("内容").toString());

        //判断position位置是否被选中，改变颜色
        if (manageActivity.listView.isItemChecked(position) && manageActivity.isMultipleSelectionMode) {
            convertView.setBackgroundColor(0xffff521d);
        } else {
            convertView.setBackgroundColor(0xff1E90FF);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView activityImage;
        TextView activityName;
        TextView location;
        TextView club;
        CheckBox select;


        public static ViewHolder newsInstance(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();

            if (holder == null) {
                holder = new ViewHolder();
//                holder.title = convertView.findViewById(R.id.lv_text1);
//                holder.text = convertView.findViewById(R.id.lv_text2);
                convertView.setTag(holder);
            }

            return holder;
        }

    }
}
