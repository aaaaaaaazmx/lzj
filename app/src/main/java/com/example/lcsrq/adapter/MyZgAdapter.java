package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyZgActivity;
import com.example.lcsrq.bean.resq.MyCheckRespData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.value.Global;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/3.
 */

public class MyZgAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<MyCheckRespData> list = new ArrayList<>();

    public MyZgAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MyCheckRespData> getList() {
        return list;
    }

    public void setList(ArrayList<MyCheckRespData> list) {
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
        if (convertView == null){
            holder =  new ViewHolder();
            convertView = View.inflate(activity, R.layout.myzg_item,null);
            holder.tv_jiancharen = (TextView) convertView.findViewById(R.id.tv_jiancharen);
            holder.tv_jianchazhandian = (TextView) convertView.findViewById(R.id.tv_jianchazhandian);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_jianchazhandian.setText("检查站点 : " + list.get(position).getSupply_name() + "");
        holder.tv_jiancharen.setText("检查人 : " + Global.usernName);
        holder.tv_creat.setText("检查时间 : " + list.get(position).getCreat_at()+ "");
        return convertView;

    }

    class  ViewHolder {
        TextView tv_jiancharen;
        TextView tv_jianchazhandian;
        TextView tv_creat;
    }
}
