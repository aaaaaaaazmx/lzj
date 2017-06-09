package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.respbean.GetJbListDataJson;
import com.example.lcsrq.bean.respbean.GetlistjfpData;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/8.
 * 记分项目Adapter
 */

public class GetlistjfpAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<GetlistjfpData> datas = new ArrayList<>();

    public ArrayList<GetlistjfpData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<GetlistjfpData> datas) {
        this.datas = datas;
    }

    public GetlistjfpAdapter (Activity activity){
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
            convertView = View.inflate(activity, R.layout.activity_jfxt_kfxx,null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_kfxm = (TextView) convertView.findViewById(R.id.tv_kfxm);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_kfxm.setText("- " + datas.get(position).getTitle1() + "");
        holder.tv_title.setText(datas.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_kfxm;
    }
}
