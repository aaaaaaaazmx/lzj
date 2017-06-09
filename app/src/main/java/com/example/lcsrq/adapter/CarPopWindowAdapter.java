package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class CarPopWindowAdapter extends BaseAdapter {

    String[] stin = {"不限制", "湖南苦哦软件公司", "百单网络公司"};
    private Activity activity;
    private  ArrayList<ContentComPanyRespData> comPanyRespDatas;
    public CarPopWindowAdapter(Activity carMangerActivity, ArrayList<ContentComPanyRespData> comPanyRespDatas) {
        activity = carMangerActivity;
        this.comPanyRespDatas =comPanyRespDatas;
    }

    @Override
    public int getCount() {
        return comPanyRespDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return comPanyRespDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        TextView tv_comp = (TextView) inflate.findViewById(R.id.tv_comp);
//        tv_comp.setText(stin[position]);

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView =  View.inflate(activity, R.layout.list_item_popwindow, null);
            holder.tv_comp = (TextView) convertView.findViewById(R.id.tv_comp);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
         holder.tv_comp.setText(comPanyRespDatas.get(position).getTitle());
        return convertView;
    }


    static class ViewHolder {
        TextView tv_comp;
    }
}
