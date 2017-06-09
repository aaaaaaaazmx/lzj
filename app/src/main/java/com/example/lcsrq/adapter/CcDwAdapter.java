package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 * 查处人Adapter
 */

public class CcDwAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> arrayList = new ArrayList<>();
    private CcDwAdapter.OnAddOrdelClick onAddOrdelClick;

    public CcDwAdapter(Context context, CcDwAdapter.OnAddOrdelClick onAddOrdelClick) {
        this.mContext = context;
        this.onAddOrdelClick = onAddOrdelClick;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ccdw_item, null);
            holder = new ViewHolder();
            holder.tv_ccdw = (TextView) convertView.findViewById(R.id.tv_ccdw);
            holder.tv_ccdwname = (TextView) convertView.findViewById(R.id.tv_ccdwname);
            holder.iv_tj = (ImageView) convertView.findViewById(R.id.iv_tj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onCcClick(position);
            }
        });

        holder.tv_ccdwname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onTextClick(position);
            }
        });

        holder.tv_ccdw.setText("查处单位:");
        holder.tv_ccdwname.setText(arrayList.get(position));
        holder.iv_tj.setImageResource(R.mipmap.icon_tj);

        return convertView;
    }

    static class ViewHolder {
        TextView tv_ccdw;
        TextView tv_ccdwname;
        ImageView iv_tj;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public interface OnAddOrdelClick {
        public void onCcClick(int position);
        public void onTextClick(int position);
    }
}
