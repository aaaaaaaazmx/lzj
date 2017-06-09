package com.example.lcsrq.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.CountBean;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.bean.resq.PicRespData;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by 苏毅 on 2017/3/29.
 */

public class PostListAdapter extends BaseAdapter {

    private Activity activity;
    private List<ContentRespData> list;

    public PostListAdapter(Activity activity, List<ContentRespData> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_list, null);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_dt = (TextView) convertView.findViewById(R.id.tv_dt);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_pic;
        TextView tv_title;
        TextView tv_dt;
        TextView tv_data;
    }
}
