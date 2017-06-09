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
    public DfzwDetailAdapter(Activity activity) {
        this.activity = activity;
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
            View tv_beichachuren = convertView.findViewById(R.id.tv_beichachuren);
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
                        int lineImg = imgurls.length / 3;
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


        // 有点问题
//        if (dfzwList.get(position).getData_json() != null){
//            //   查处内容
//            holder.cc_content.setText(dfzwList.get(position).getData_json().getData_remark() + "");
//            //  被查出人
//            holder.tv_beichachuren.setText("被查出人 : " + dfzwList.get(position).getData_json().getData_man());
//            //  电话号码
//            holder.tv_phone.setText(dfzwList.get(position).getData_json().getData_tel());
//            // 身份证
//            holder.tv_shenfenzheng.setText("身份证 : " + dfzwList.get(position).getData_json().getData_code());
//            // 气源
//            holder.tv_qiyuan.setText("气源 : " + dfzwList.get(position).getData_json().getData_qy());
//            // 空瓶
//            holder.tv_kp.setText("没收空瓶 : " + dfzwList.get(position).getData_json().getData_kp());
//            // 实瓶
//            holder.tv_kp.setText("没收实瓶 : " + dfzwList.get(position).getData_json().getData_sp());
//        }



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
        // 没收实瓶
        TextView tv_sp;
        // 没收空瓶
        TextView tv_kp;
    }
}
