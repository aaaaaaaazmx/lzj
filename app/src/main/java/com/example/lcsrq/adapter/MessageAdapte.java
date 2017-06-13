package com.example.lcsrq.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.utils.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class MessageAdapte extends BaseAdapter {
    private ArrayList<ContentRespData> datas = new ArrayList<>();
    private Activity activity;

    public MessageAdapte() {

    }

    public ArrayList<ContentRespData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ContentRespData> datas) {
        this.datas = datas;
    }

    public MessageAdapte(Activity activity) {
    this.activity =activity;
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
        ViewHodler hodler;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = View.inflate(activity, R.layout.messager_item, null);
            hodler.iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
            hodler.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hodler.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            hodler.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        DensityUtil.lzj(hodler.iv_pic,datas.get(position).getUpload_path());
        hodler.tv_data.setText(datas.get(position).getRemark());
        hodler.tv_title.setText(datas.get(position).getTitle());
        String creat_at = datas.get(position).getCreat_at();
        String substring = creat_at.substring(0, 10);
        hodler.tv_content.setText(substring);

        return convertView;
    }

    static class ViewHodler{
        SimpleDraweeView iv_pic;
        TextView tv_title;
        TextView tv_data;
        TextView tv_content;

    }

}
