package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.xxcx.CarInfo;
import com.example.lcsrq.bean.resq.XxCx_CarResp;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class CarAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<XxCx_CarResp> data = new ArrayList<>();
    public CarAdapter(Activity activity) {
            this.activity =activity;
    }

    public ArrayList<XxCx_CarResp> getData() {
        return data;
    }

    public void setData(ArrayList<XxCx_CarResp> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView= View.inflate(activity, R.layout.car_info_item, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_tel = (TextView) convertView.findViewById(R.id.tv_tel);
            holder.tv_go = (TextView) convertView.findViewById(R.id.tv_go);
            convertView.setTag(holder);
        }else {
                holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_tel.setText(data.get(position).getTel());
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_tel;
        TextView tv_go;
    }

}
