package com.example.lcsrq.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.xxcx.Zhandian_info;
import com.example.lcsrq.bean.resq.XxCx_ZhandianResp;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class ZhanAdapter extends BaseAdapter {

    private Zhandian_info zhandian_info;
    private ArrayList<XxCx_ZhandianResp> data;
    public ZhanAdapter(Zhandian_info zhandian_info, ArrayList<XxCx_ZhandianResp> data) {
        this.zhandian_info =zhandian_info;
        this.data =data;
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
        View inflate = View.inflate(zhandian_info, R.layout.zhandian_item, null);
        TextView tv_tel = (TextView) inflate.findViewById(R.id.tv_tel);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);

        tv_tel.setText(data.get(position).getTel());
        tv_title.setText(data.get(position).getTitle());
        return inflate;
    }
}
