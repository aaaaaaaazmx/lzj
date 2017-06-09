package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.DfzwActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.bean.respbean.Data_json;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.bean.resq.JuBaoBean;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/10.
 * 打非治违Activity
 */

public class DfzwAdapter extends BaseAdapter {
    private ArrayList<JuBaoBean> list = new ArrayList<>();
    private Activity activity;

    public DfzwAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<JuBaoBean> getList() {
        return list;
    }

    public void setList(ArrayList<JuBaoBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        DfzwAdapter.ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(activity, R.layout.list_item_dfzw,null);
            holder = new DfzwAdapter.ViewHolder();
            holder.tv_areas = (TextView) convertView.findViewById(R.id.tv_areas);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_creat_at = (TextView) convertView.findViewById(R.id.tv_creat_at);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_fzr = (TextView) convertView.findViewById(R.id.tv_fzr);
            holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
            holder.tv_go = (TextView) convertView.findViewById(R.id.tv_go);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.img_dw = (ImageView) convertView.findViewById(R.id.img_dw);
            holder.tv_dw = (TextView) convertView.findViewById(R.id.tv_dw);
            holder.tv_yijifzr = (TextView) convertView.findViewById(R.id.tv_yijifzr);
            holder.tv_erjifzr = (TextView) convertView.findViewById(R.id.tv_erjifzr);
            convertView.setTag(holder);
        }else {
            holder = (DfzwAdapter.ViewHolder) convertView.getTag();
        }

        if (list.get(position).getFzrs().size() != 0) {
            holder.tv_erjifzr.setText("二级负责人 : " + list.get(position).getFzrs().get(0).getDw() + " (" + list.get(position).getFzrs().get(0).getUname() + ")");  // 一级负责人
            holder.tv_yijifzr.setText("一级负责人 : " +  list.get(position).getFzrs().get(1).getDw() +"("  +  list.get(position).getFzrs().get(1).getUname() +")");  // 二级级负责人
        }else {
            holder.tv_erjifzr.setText("一级负责人 : "  + "null");  // 一级负责人
            holder.tv_yijifzr.setText("二级负责人 : " + "null");  // 二级级负责人
        }
        //查处单位
        if (list.get(position).getCclist().size() != 0){
            holder.tv_dw.setText("查处单位 : " + list.get(position).getFzrs().get(0).getDw()); //  查处单位
        }else {
            holder.tv_dw.setText("查处单位 : " + "null"); //  查处单位
        }

        holder.tv_content.setText(list.get(position).getContent());  // 内容

        holder.tv_address.setText("地址 : " + list.get(position).getAddress());  //  地址
//        holder.tv_areas.setText(list.get(position).getAreas());

        holder.tv_creat_at.setText("受理时间 : " + list.get(position).getCreat_at());

        if (list.get(position).getStatus().equals("1")){
            //表示待查处
//            holder.tv_go.setText("待查处");
            holder.img_dw.setImageResource(R.mipmap.icom_dcc);
        }else if (list.get(position).getStatus().equals("2")){
            // 表示查处中
//            holder.tv_go.setText("查处中");
            holder.img_dw.setImageResource(R.mipmap.icom_ccz);
        }else {
            // 表示已查处
//            holder.tv_go.setText("已查处");
            holder.img_dw.setImageResource(R.mipmap.icom_ycc);
        }
        // 点击事件取消
//        holder.img_dw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                  if (list.get(position).getStatus().equals("1")){
//                    //表示待查处
//                    //  跳传过去,并传过去JB_ID
//                    Intent intent = new Intent(activity, HdhcCheckActivity.class);
//                    intent.putExtra("jb_id",list.get(position).getId());
//                      Toast.makeText(activity,list.get(position).getId(),Toast.LENGTH_SHORT).show();
//                    activity.startActivity(intent);
//                    return;
//                }else if (list.get(position).getStatus().equals("2")){
//                    // 表示查处中
//                      Intent intent = new Intent(activity, HdhcCheckActivity.class);
//                      intent.putExtra("jb_id",list.get(position).getId());
//                      Toast.makeText(activity,list.get(position).getId(),Toast.LENGTH_SHORT).show();
//                      activity.startActivity(intent);
//                    return;
//                }else {
//                    // 表示已查处
//                    return;
//                }
//            }
//        });
        return convertView;
    }

    static class  ViewHolder {
        TextView tv_content;
        TextView tv_yijifzr;
        TextView tv_erjifzr;
        TextView tv_address;
        TextView tv_areas;
        TextView tv_creat_at;
        TextView tv_state;
        TextView tv_fzr;
        TextView tv_danwei;
        TextView tv_go;
        ImageView img_dw;
        TextView tv_dw;
    }
}
