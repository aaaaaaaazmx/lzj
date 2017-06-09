package com.example.lcsrq.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.HdhcActivity;
import com.example.lcsrq.bean.resq.HdhcRespData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class HdhAdapter extends BaseAdapter {

    private Activity activity;
    private RecycleAdapter recycleAdapter;

    public ArrayList<HdhcRespData> getList() {
        return list;
    }

    public void setList(ArrayList<HdhcRespData> list) {
        this.list = list;
    }

    private ArrayList<HdhcRespData> list  = new ArrayList<>();

    public HdhAdapter(Activity activity) {
        this.activity = activity;
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
            convertView = View.inflate(activity, R.layout.list_item_hdhc, null);
            holder = new ViewHolder();
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_jbcs = (TextView) convertView.findViewById(R.id.tv_jbcs);
            holder.tv_cp = (TextView) convertView.findViewById(R.id.tv_cp);
            holder.tv_yjfzr = (TextView) convertView.findViewById(R.id.tv_yjfzr);
            holder.tv_ejfzr = (TextView) convertView.findViewById(R.id.tv_ejfzr);
            holder.tv_content1 = (TextView) convertView.findViewById(R.id.tv_content1);
//            holder.tv_content2 = (TextView) convertView.findViewById(R.id.tv_contnet2);
            holder.tv_ccqk = (TextView) convertView.findViewById(R.id.tv_ccqk);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
//            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.recyclerView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        HdhcRespData hdhcRespData = list.get(position);
//        holder.tv_creat.setText(hdhcRespData.getCreat_at());
//        holder.tv_cp.setText(hdhcRespData.getCart_number());
//        holder.tv_address.setText(hdhcRespData.getAddress());
//        holder.tv_content1.setText(hdhcRespData.getContent());
//        holder.tv_content2.setText(hdhcRespData.getContent());
//
//        holder.tv_ccqk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(activity,"点击了查处情况",Toast.LENGTH_LONG).show();
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
//        RecyclerView recyclerView;
        TextView tv_address; // 黑车地址
        TextView tv_jbcs;       // 举报次数
        TextView tv_cp;     // 车牌
        TextView tv_yjfzr;  // 一级负责人
        TextView tv_ejfzr;  // 二级负责人
        TextView tv_content1; // 内容1
        TextView tv_content2;  //内容2
        TextView tv_ccqk;  // 查看情况
        TextView tv_creat;  // 时间
    }
}
