package com.example.lcsrq.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.xxcx.People_info;
import com.example.lcsrq.bean.resq.XxCx_peopleResp;
import com.lidroid.xutils.BitmapUtils;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class PeoPleAdapter extends BaseAdapter {
    private boolean First = true;
    private int isPosition = 0;
    private Activity activity;
    private ArrayList<XxCx_peopleResp> data;
    private OnAddOrdelClick onAddOrdelClick;
    private String yanse;
    private  Random random;
    public PeoPleAdapter(Activity activity, ArrayList<XxCx_peopleResp> data,OnAddOrdelClick onAddOrdelClick) {
            this.data =data;
        this.activity =activity;
        this.onAddOrdelClick = onAddOrdelClick;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHodler hodler;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = View.inflate(activity, R.layout.list_item_people, null);
            hodler.iv_sj = (ImageView) convertView.findViewById(R.id.iv_sj);
            hodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hodler.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            hodler.yuann_view = (CircleTextImageView) convertView.findViewById(R.id.yuann_view);
//            hodler.tv_fl = (TextView) convertView.findViewById(R.id.tv_fl);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.iv_sj.setImageResource(R.mipmap.icon_dhhm);
        hodler.tv_name.setText(data.get(position).getM_nickname());
        hodler.tv_phone.setText(data.get(position).getM_account());
//        hodler.tv_fl.setText(data.get(position).getM_account());

        String m_nickname = data.get(position).getM_nickname();
        String name = m_nickname.substring(0, 1);
        hodler.yuann_view.setText(name);
        isPosition ++;
        // 当更新完成才加载
        if (First == true) {
            if (isPosition != data.size()) {
                for (int i = 0; i < data.size(); i++) {
                    random = new Random();
                    int tmp = Math.abs(random.nextInt());
                    yanse = tmp % (999999 - 100000 + 1) + 100000 + "";
                    hodler.yuann_view.setFillColor(Color.parseColor("#" + yanse));
                }
            }else {
                First = false;
            }
        }



        hodler.iv_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onCcClick(position);
            }
        });

        return convertView;
    }

    static class ViewHodler{
        ImageView iv_sj;
        TextView tv_name;
//        TextView tv_fl;
        TextView tv_phone;
        CircleTextImageView yuann_view;
    }
    // 提供向外的接口
    public interface OnAddOrdelClick {
        public void onCcClick(int position);
    }



}
