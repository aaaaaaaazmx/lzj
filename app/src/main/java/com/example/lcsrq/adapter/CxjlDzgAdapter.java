package com.example.lcsrq.adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.cxjl.CxjlActivity;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class CxjlDzgAdapter extends BaseAdapter {

    private int[] imger = {R.mipmap.cxtp, R.mipmap.cxtp2, R.mipmap.tpd,};
    private Activity activity;
    private ArrayList<ContentRespData> datas;

    public CxjlDzgAdapter(Activity activity, ArrayList<ContentRespData> datas) {
        this.activity = activity;
        this.datas= datas;
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
            holder  = new ViewHolder();
            convertView = View.inflate(activity, R.layout.list_item_cxjl_dzg, null);
            holder.iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_dt = (TextView) convertView.findViewById(R.id.tv_dt);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_data.setText(datas.get(position).getCreat_at());
        holder.tv_title.setText(datas.get(position).getTitle());

//        BitmapUtils utils = new BitmapUtils(activity);
//        utils.display(holder.iv_pic,datas.get(position).getUpload_path());

        holder.iv_pic.setImageURI(Uri.parse(datas.get(position).getUpload_path()));
        holder.tv_dt.setText(datas.get(position).getRemark());
        return convertView;
    }

    public class ViewHolder{
      public SimpleDraweeView iv_pic;
      public TextView tv_title;
      public TextView tv_dt;
      public TextView tv_data;
    }

}
