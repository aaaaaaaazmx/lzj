package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.respbean.GetlistjftData;
import com.example.lcsrq.value.Global;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/3.
 */

public class ScoringAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<GetlistjftData>  datas = new ArrayList<>();

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<GetlistjftData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<GetlistjftData> datas) {
        this.datas = datas;
    }

    public ScoringAdapter(Activity activity){
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
        if (convertView == null){
           convertView = View.inflate(activity, R.layout.scroing_list, null);
            holder = new ViewHolder();
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_dt = (TextView) convertView.findViewById(R.id.tv_dt);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
            holder.iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_creat.setText(datas.get(position).getCreat_at());
        holder.tv_title.setText("记分对象: " + datas.get(position).getOid_name());
        holder.tv_dt.setText(datas.get(position).getCompany_name());
        holder.tv_data.setText(datas.get(position).getOid_value() + "分");
        switch (datas.get(position).getStatus()){
            case "1" :
                holder.iv_pic.setImageResource(R.mipmap.icon_dqs);
                break;
            case  "2" :
                holder.iv_pic.setImageResource(R.mipmap.icon_yqs);
                break;
            case  "3" :
                holder.iv_pic.setImageResource(R.mipmap.icon_fyz);
                break;
            case  "4" :
                holder.iv_pic.setImageResource(R.mipmap.icon_ysx);
                break;
            case  "5" :
                holder.iv_pic.setImageResource(R.mipmap.icon_ybh);
                break;
        }

        return convertView;
    }
    class ViewHolder{
        SimpleDraweeView iv_pic;
        TextView tv_title;
        TextView tv_dt;
        TextView tv_data;
        TextView tv_creat;
    }
}
