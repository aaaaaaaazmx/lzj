package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.XxCx_CarResp;
import com.example.lcsrq.bean.resq.XxCx_peopleResp;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/9.
 */

public class JfdxForZdAdater extends BaseAdapter {
    private Activity activity;
    private ArrayList<XxCx_CarResp> datas = new ArrayList<>();

    public ArrayList<XxCx_CarResp> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<XxCx_CarResp> datas) {
        this.datas = datas;
    }

    public JfdxForZdAdater(Activity activity){
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
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
            convertView = View.inflate(activity, R.layout.jfdx_item,null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(datas.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
    }
    
}
