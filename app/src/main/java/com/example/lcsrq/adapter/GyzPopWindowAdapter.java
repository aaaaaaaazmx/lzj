package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class GyzPopWindowAdapter extends BaseAdapter {
    String[] stin = {"不限制", "湖南苦哦软件公司", "百单网络公司","长沙长沙长沙长沙"};
    private Activity activity;

    public GyzPopWindowAdapter(Activity carMangerActivity) {
        activity = carMangerActivity;
    }

    @Override
    public int getCount() {
        return stin.length;
    }

    @Override
    public Object getItem(int position) {
        return stin[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View inflate = View.inflate(activity, R.layout.list_item_popwindow, null);
        TextView tv_comp = (TextView) inflate.findViewById(R.id.tv_comp);
        tv_comp.setText(stin[position]);
        return inflate;
    }
}
