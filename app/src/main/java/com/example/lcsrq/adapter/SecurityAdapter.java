package com.example.lcsrq.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.resq.GyzListResppData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParsePosition;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/3.
 * // 安全隐患
 */

public class SecurityAdapter extends BaseAdapter {

    private ArrayList<GyzListResppData> datas = new ArrayList<>();
    private Context mContext;

    public SecurityAdapter(FragmentActivity activity) {
        mContext = activity;
    }

    public ArrayList<GyzListResppData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<GyzListResppData> datas) {
        this.datas = datas;
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
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.scroing_list, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_dt = (TextView) convertView.findViewById(R.id.tv_dt);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
            holder.iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
            holder.tv_data= (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_oid_name= (TextView) convertView.findViewById(R.id.tv_oid_name);
            // 去安全隐患不需要记分
            holder.tv_data.setVisibility(View.INVISIBLE);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText("存在问题 : " +datas.get(position).getContent());
        holder.tv_creat.setText(datas.get(position).getCreat_at());
        holder.iv_pic .setImageResource(R.mipmap.icon_anqyh);
//        holder.tv_dt.setText("检查公司 : " + datas.get(position).getCompany_name());

        holder.tv_dt.setText("检查人员 : " + datas.get(position).getM_nickname());

        holder.tv_oid_name.setText("检查站点 : " + datas.get(position).getSupply_name());
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_dt;
        TextView tv_data;
        TextView tv_creat;
        TextView tv_oid_name;
        SimpleDraweeView iv_pic;
    }
}
