package com.example.club.Communication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.club.Objects.Activity;
import com.example.club.R;

import java.util.List;

public class ActivityAdapter extends ArrayAdapter<Activity> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public ActivityAdapter(Context context, int textViewResourceId, List<Activity> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Activity activity = getItem(position); //获取当前项的Fruit实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.activityImage = view.findViewById(R.id.activityImage);
            viewHolder.activityName = view.findViewById(R.id.activityName);
            viewHolder.date = view.findViewById(R.id.Date);


            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view = convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.activityImage.setImageBitmap(activity.getImage());
        viewHolder.activityName.setText(activity.getActivityName());
        viewHolder.date.setText(activity.getDate());
//        viewHolder.time.setText(activity.getTime());
//        viewHolder.club.setText(activity.getPostUserID());
//        viewHolder.location.setText(activity.getLocation());

        return view;
    }


    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        ImageView activityImage;
        TextView activityName;
        TextView date;
        TextView time;
        TextView club;
        TextView location;
    }

}
