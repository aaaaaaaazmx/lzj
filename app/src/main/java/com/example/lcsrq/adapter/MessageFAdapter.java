package com.example.lcsrq.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class MessageFAdapter extends BaseAdapter {

    private Context activity;

    public MessageFAdapter(Context activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.activity_firstment_countydetail, null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_data;
        TextView tv_content;
        ImageView iv_img;
    }
}
