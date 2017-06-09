package com.example.lcsrq.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.car.CarMangerActivity;
import com.example.lcsrq.activity.manger.gyzmanger.MapActivity;
import com.example.lcsrq.bean.resq.ContentGyzRespData;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class GyzMangerAdapter extends BaseAdapter {

    private Activity carMangerActivity;
    private OnAddOrdelClick onAddOrdelClick;
    private ArrayList<ContentGyzRespData> datas = new ArrayList<>();
    public GyzMangerAdapter(Activity carMangerActivity, OnAddOrdelClick onAddOrdelClick) {
        this.carMangerActivity = carMangerActivity;
        this.onAddOrdelClick = onAddOrdelClick;
    }
    public ArrayList<ContentGyzRespData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ContentGyzRespData> datas) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        View view = View.inflate(carMangerActivity, R.layout.list_item_carmanger, null);
//        ImageView img_dw = (ImageView) view.findViewById(R.id.img_dw);
//        img_dw.setBackgroundResource(R.mipmap.icon_dw);

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(carMangerActivity, R.layout.list_item_gyzmanger, null);
            holder.tv_gs = (TextView) convertView.findViewById(R.id.tv_gs);
            holder.tv_go = (TextView) convertView.findViewById(R.id.tv_go);
            holder.tv_car_cp = (TextView) convertView.findViewById(R.id.tv_car_cp);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ContentGyzRespData contentGyzRespData = datas.get(position);
        holder.tv_gs.setText(contentGyzRespData.getAddress());
        holder.tv_car_cp.setText(contentGyzRespData.getTitle());
        holder.tv_phone.setText(contentGyzRespData.getTel());

        holder.tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // 现在没有供应站经纬度
//                Intent i1 = new Intent();
//                i1.setData(Uri.parse("baidumap://map/marker?location=" + contentGyzRespData.getLat() + "," + contentGyzRespData.getLng() + "&title=" + contentGyzRespData.getTitle() + "&content=makeamarker&traffic=on"));
//                try {
//                    carMangerActivity.startActivity(i1);
//                } catch (BaiduMapAppNotSupportNaviException e) {
//                    e.printStackTrace();
//                    showDialog();
//                }
                onAddOrdelClick.onCcClick(position);

            }
        });
        return convertView;
    }

    public interface OnAddOrdelClick {
        public void onCcClick(int position);
        public void onTextClick(int position);
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(carMangerActivity);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(carMangerActivity);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    static class ViewHolder {
        TextView tv_car_cp;
        TextView tv_go;
        TextView tv_phone;
        TextView tv_gs;
    }
}
