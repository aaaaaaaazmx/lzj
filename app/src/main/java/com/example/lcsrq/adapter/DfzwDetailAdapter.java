package com.example.lcsrq.adapter;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.hdhc.DfzwDetaiActivity;
import com.example.lcsrq.bean.resq.AllCclistRespData;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/25.
 */

public class DfzwDetailAdapter extends BaseAdapter {

    private ArrayList<AllCclistRespData> dfzwList = new ArrayList<>();
    private Activity activity;
    private String ccuser;
    public DfzwDetailAdapter(Activity activity) {
        this.activity = activity;
    }

    public String getCcuser() {
        return ccuser;
    }

    public void setCcuser(String ccuser) {
        this.ccuser = ccuser;
    }

    public ArrayList<AllCclistRespData> getDfzwList() {
        return dfzwList;
    }

    public void setDfzwList(ArrayList<AllCclistRespData> dfzwList) {
        this.dfzwList = dfzwList;
    }

    @Override
    public int getCount() {
        return dfzwList.size();
    }

    @Override
    public Object getItem(int position) {
        return dfzwList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.dfzwdetail_item, null);
            holder.morePicLayout = (LinearLayout) convertView.findViewById(R.id.morePicLayout);
            // 动态
            holder.oneImgIv = (SimpleDraweeView) convertView.findViewById(R.id.oneImgIv);
            holder.imgs[0] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv1);
            holder.imgs[1] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv2);
            holder.imgs[2] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv3);
            holder.imgs[3] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv4);
            holder.imgs[4] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv5);
            holder.imgs[5] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv6);
            holder.imgs[6] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv7);
            holder.imgs[7] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv8);
            holder.imgs[8] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv9);

            //  查处内容
            holder.cc_content = (TextView) convertView.findViewById(R.id.cc_content);
            //  被查出人
            holder. tv_beichachuren = (TextView) convertView.findViewById(R.id.tv_beichachuren);
            // 电话好嘛
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            // 身份证
           holder.tv_shenfenzheng = (TextView) convertView.findViewById(R.id.tv_shenfenzheng);
            // 气源
           holder.tv_qiyuan = (TextView) convertView.findViewById(R.id.tv_qiyuan);
            // 空瓶
            holder.tv_kp = (TextView) convertView.findViewById(R.id.tv_kp);
            // 实瓶
            holder.tv_sp = (TextView) convertView.findViewById(R.id.tv_sp);
            //  查处内容
            holder.cc_content = (TextView) convertView.findViewById(R.id.cc_content);
            //  查处对象
            holder.tv_ccdx  = (TextView) convertView.findViewById(R.id.tv_ccdx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //图片
        String imgUrlStr = dfzwList.get(position).getUpload_json();
        if (!TextUtils.isEmpty(imgUrlStr)) {
            final String[] imgurls = imgUrlStr.split(",");
            if (imgurls.length == 1) {
                holder.oneImgIv.setVisibility(View.VISIBLE);
                holder.morePicLayout.setVisibility(View.GONE);
                holder.oneImgIv.setImageURI(Uri.parse(imgurls[0]));
                holder.oneImgIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UiTool.showPic(activity, imgurls[0]);
                    }
                });
            } else if (imgurls.length > 1) {
                holder.oneImgIv.setVisibility(View.GONE);
                holder.morePicLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < holder.imgs.length; i++) {
                    if (i < imgurls.length) {
                        holder.imgs[i].setImageURI(Uri.parse(imgurls[i]));
                        final int index = i;
                        holder.imgs[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UiTool.showPic(activity, imgurls[index]);
                            }
                        });
                    } else {
                        int lineImg = imgurls.length / 4;  //  图片显示不全, 就得除以4
                        int lineNoImg = i / 3;
                        if (lineNoImg > lineImg) {
                            holder.imgs[i].setVisibility(View.GONE);
                        } else {
                            holder.imgs[i].setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        } else {
            holder.morePicLayout.setVisibility(View.GONE);
            holder.oneImgIv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_remark())){
            //   查处内容
            holder.cc_content.setText( "备注 : " + dfzwList.get(position).getData_json().getData_remark() + "");
        }else {
            //   查处内容
            holder.cc_content.setText("");
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_man()) && !TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_tel()) ){
            //  被查出人
            holder.tv_ccdx.setText("查处对象 : " + dfzwList.get(position).getData_json().getData_man() + "(" + dfzwList.get(position).getData_json().getData_tel() + ")");
        }else {
            holder.tv_ccdx.setText("查处对象 : " + " ");
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_tel())){
            //  电话号码
            holder.tv_phone.setText(dfzwList.get(position).getData_json().getData_tel());
        }else {
            //  电话号码
            holder.tv_phone.setText("");
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_code())){
            // 身份证
            holder.tv_shenfenzheng.setText("身份证 : " + dfzwList.get(position).getData_json().getData_code());
        }else {
            // 身份证
            holder.tv_shenfenzheng.setText("身份证 : " + "" );
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_qy())){
            // 气源
            holder.tv_qiyuan.setText("气源 : " + dfzwList.get(position).getData_json().getData_qy());
        }else {
            // 气源
            holder.tv_qiyuan.setText("气源 : " + "");
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_kp())){
            // 空瓶
            holder.tv_kp.setText("暂扣空瓶 : " + dfzwList.get(position).getData_json().getData_kp());
        }else {
            // 空瓶
            holder.tv_kp.setText("暂扣空瓶 : " + "");
        }

        if (!TextUtils.isEmpty(dfzwList.get(position).getData_json().getData_sp())){
            // 实瓶
            holder.tv_sp.setText("暂扣实瓶 : " + dfzwList.get(position).getData_json().getData_sp());
        }else {
            // 实瓶
            holder.tv_sp.setText("暂扣实瓶 : " + "");
        }

        // 查处人
//        if (!TextUtils.isEmpty(dfzwList.get(position).getM_nickname()) && !TextUtils.isEmpty(dfzwList.get(position).getDw())){
//            holder.tv_beichachuren.setText("查处人 : " + dfzwList.get(position).getM_nickname() + "(" +dfzwList.get(position).getDw() + ")" );
//        }else {
//            holder.tv_beichachuren.setText("查处人 : " + "null" );
//        }

            // 查处人
            holder.tv_beichachuren.setText("查处人 : " + ccuser );


        return convertView;
    }

    public class ViewHolder {
        public LinearLayout morePicLayout;
        public SimpleDraweeView oneImgIv;
        public SimpleDraweeView[] imgs = new SimpleDraweeView[9];

        // 被查出人
        TextView tv_beichachuren;
        // 查出内容
        public TextView cc_content;
        // 身份证
        TextView tv_shenfenzheng;
        // 电话号码
        TextView tv_phone;
        // 气源
        TextView tv_qiyuan;
        // 暂扣实瓶
        TextView tv_sp;
        // 暂扣空瓶
        TextView tv_kp;
        //  查处对象
        TextView tv_ccdx;
    }
}
