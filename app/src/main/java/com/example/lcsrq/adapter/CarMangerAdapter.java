package com.example.lcsrq.adapter;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.ContentCarRespData;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class CarMangerAdapter extends BaseAdapter {

    private Activity carMangerActivity;
    private ArrayList<ContentCarRespData> datas = new ArrayList<>();

    public Activity getCarMangerActivity() {
        return carMangerActivity;
    }

    public void setCarMangerActivity(Activity carMangerActivity) {
        this.carMangerActivity = carMangerActivity;
    }

    public ArrayList<ContentCarRespData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ContentCarRespData> datas) {
        this.datas = datas;
    }

    public CarMangerAdapter(Activity carMangerActivity) {
        this.carMangerActivity = carMangerActivity;
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
//        ImageView img_dw = (ImageView) view.findViewById(R.id.img_dw);
//        img_dw.setBackgroundResource(R.mipmap.icon_dw);
        ViewHolder holder;

        if (convertView == null){
           convertView = View.inflate(carMangerActivity, R.layout.list_item_carmanger, null);
            holder = new ViewHolder();
            holder.tv_car_cp = (TextView) convertView.findViewById(R.id.tv_car_cp);
            holder.tv_gs = (TextView) convertView.findViewById(R.id.tv_gs);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * id='18', title='湘A88888', tel='8888888', company_id='20', company='测试'
         */
        ContentCarRespData data = datas.get(position);
        holder.tv_car_cp.setText(data.getTitle());
        holder.tv_gs.setText(data.getCompany());



        return convertView;
    }

 static  class  ViewHolder{
     TextView tv_car_cp;
     TextView tv_gs;
    }

}
