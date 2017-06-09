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
import com.example.lcsrq.bean.respbean.TiJiaoBean;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 * 查处人Adapter
 */

public class TiJiaoAdapte extends BaseAdapter {
    private Context mContext;
    private ArrayList<TiJiaoBean> arrayList = new ArrayList<>();
    private OnAddOrdelClick onAddOrdelClick;
    public TiJiaoAdapte(Context context, OnAddOrdelClick onAddOrdelClick) {
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

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ccr_item, null);
            holder = new ViewHolder();
            holder.tv_cc = (TextView) convertView.findViewById(R.id.tv_cc);
            holder.tv_ccr = (TextView) convertView.findViewById(R.id.tv_ccr);
            holder.iv_tj = (ImageView) convertView.findViewById(R.id.iv_tj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.iv_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onCcClick(position,holder.iv_tj);
                holder.iv_tj.setImageResource(R.mipmap.icom_jh);
                notifyDataSetChanged();
            }
        });

        holder.tv_ccr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onTextClick(position);
//
            }
        });

        holder.tv_cc.setText("查处人:");
        holder.tv_ccr.setText(arrayList.get(position).getUserName());
        holder.iv_tj.setImageResource(R.mipmap.icon_tj);

        if (position >= 1){
            holder.iv_tj.setImageResource(R.mipmap.icom_jh);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_cc;
        TextView tv_ccr;
        ImageView iv_tj;
    }

    public ArrayList<TiJiaoBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<TiJiaoBean> arrayList) {
        this.arrayList = arrayList;
    }

    public interface OnAddOrdelClick {
        public void onCcClick(int position,ImageView imageView);
        public void onTextClick(int position);
    }
}
