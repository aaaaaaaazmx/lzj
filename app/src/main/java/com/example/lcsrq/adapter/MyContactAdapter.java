package com.example.lcsrq.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.bean.resq.MyContentRespData;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;
import java.util.Random;

import bolts.Bolts;

/**
 * Created by 苏毅 on 2017/5/3.
 */

public class MyContactAdapter extends BaseAdapter {

    private ArrayList<MyContentRespData> datas = new ArrayList<>();
    private Activity activity;
    private Random random;
    private String yanse;
    private OnAddOrdelClick onAddOrdelClick;

    public interface OnAddOrdelClick {
        public void onCcClick(int position);
        public void onTextClick(int position);
    }

    public MyContactAdapter(Activity activity,OnAddOrdelClick onAddOrdelClick) {
        this.activity = activity;

        this.onAddOrdelClick = onAddOrdelClick;
    }

    public ArrayList<MyContentRespData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<MyContentRespData> datas) {
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

    private Boolean isFirst = true;;

    private boolean First = true;
    private int isPosition = 0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.my_contact_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.yuann_view = (CircleTextImageView) convertView.findViewById(R.id.yuann_view);
            holder.iv_sj = (ImageView) convertView.findViewById(R.id.iv_sj);
            holder.tv_dw = (TextView) convertView.findViewById(R.id.tv_dw);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(datas.get(position).getM_nickname());
        holder.tv_phone.setText(datas.get(position).getM_account());

         isPosition ++;

        // 当更新完成
        if (First == true) {
            if (isPosition != datas.size()) {
                for (int i = 0; i < datas.size(); i++) {
                    random = new Random();
                    int tmp = Math.abs(random.nextInt());
                    yanse = tmp % (999999 - 100000 + 1) + 100000 + "";
                    holder.yuann_view.setFillColor(Color.parseColor("#" + yanse));
                }
            }else {
                First = false;
            }
        }

            String m_nickname = datas.get(position).getM_nickname();
            String name = m_nickname.substring(0, 1);
            holder.yuann_view.setText(name);
            holder.iv_sj.setImageResource(R.mipmap.icon_dhhm);

            holder.tv_dw.setText("("  + datas.get(position).getM_datajson().getDw() + ")" + "");

        holder.iv_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onCcClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_sj;
        TextView tv_name;
        TextView tv_dw;
        TextView tv_danwei;
        TextView tv_phone;
        CircleTextImageView yuann_view;
    }
}
