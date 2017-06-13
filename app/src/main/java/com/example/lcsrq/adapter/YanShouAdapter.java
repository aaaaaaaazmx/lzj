package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzYanshouActivity;
import com.example.lcsrq.bean.req.TiJiaoZgstate;
import com.example.lcsrq.bean.respbean.Data_ckloglist;
import com.example.lcsrq.bean.resq.GyzCheckRespData;
import com.example.lcsrq.bean.resq.GyzCheckZgJlRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DensityUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.BitmapUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/13.
 */

public class YanShouAdapter extends BaseAdapter {
    private   ArrayList<GyzCheckZgJlRespData> datas = new ArrayList<>();
    private ArrayList<String >lists = new ArrayList<String>();
    private Activity activity;
    private Button btn_zg;

    public YanShouAdapter(Activity activity) {
        this.activity = activity;
    }

    public  ArrayList<GyzCheckZgJlRespData> getDatas() {
        return datas;
    }

    public void setDatas( ArrayList<GyzCheckZgJlRespData> datas) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder= new ViewHolder();
//            convertView = View.inflate(activity, R.layout.yanshou_item,null);
            convertView = View.inflate(activity, R.layout.myrectif_item,null);
            /**
             *    TextView tv_time;
             TextView tv_gongyinghzan;
             TextView tv_danwei;
             TextView tv_detail;
             TextView tv_jcr;
             */

            holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei); //  检查单位
            holder.tv_gongyinghzan = (TextView) convertView.findViewById(R.id.tv_gongyinghzan); //  检查站点
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);  //  检查时间
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail); //  检查详情
            holder.tv_jcr = (TextView) convertView.findViewById(R.id.tv_jcr);  //检查人
            holder.tv_riqi = (TextView) convertView.findViewById(R.id.tv_riqi);
            // 日期隐藏
            holder.tv_riqi.setVisibility(View.GONE);
            // 动态图片
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

//            btn_zg = (Button) convertView.findViewById(R.id.btn_zg);
//            btn_zg.setVisibility(View.INVISIBLE);
             holder.btn_zg = (Button) convertView.findViewById(R.id.btn_zg);
             holder.btn_wzg = (Button) convertView.findViewById(R.id.btn_wzg);
            holder.btn_wzg.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //隐藏图片
        holder.oneImgIv.setVisibility(View.GONE);
        // 图片
//        String imgUrlStr = datas.get(position).getUpload_json();
//        if (!TextUtils.isEmpty(imgUrlStr) && imgUrlStr != "0") {
//            final String[] imgurls = imgUrlStr.split(",");
//            if (imgurls.length == 1) {
//                holder.oneImgIv.setVisibility(View.VISIBLE);
//                holder.morePicLayout.setVisibility(View.GONE);
//                DensityUtil.lzj(holder.oneImgIv, imgurls[0]);
//                holder.oneImgIv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        UiTool.showPic(activity, imgurls[0]);
//                    }
//                });
//            } else if (imgurls.length > 1) {
//                holder.oneImgIv.setVisibility(View.GONE);
//                holder.morePicLayout.setVisibility(View.VISIBLE);
//                for (int i = 0; i < holder.imgs.length; i++) {
//                    if (i < imgurls.length) {
//                        DensityUtil.lzj(holder.imgs[i], imgurls[i]);
//                        final int index = i;
//                        holder.imgs[i].setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                UiTool.showPic(activity, imgurls[index]);
//                            }
//                        });
//                    } else {
//                        int lineImg = imgurls.length / 3;
//                        int lineNoImg = i / 3;
//                        if (lineNoImg > lineImg) {
//                            holder.imgs[i].setVisibility(View.GONE);
//                        } else {
//                            holder.imgs[i].setVisibility(View.INVISIBLE);
//                        }
//                    }
//                }
//            }
//        } else {
//            holder.morePicLayout.setVisibility(View.GONE);
//            holder.oneImgIv.setVisibility(View.GONE);
//        }

        holder.tv_danwei.setText("检查单位 : " +datas.get(position).getCheck_dw());
        holder.tv_detail.setText(datas.get(position).getContent());
        holder.tv_gongyinghzan.setText("检查站点 : " + datas.get(position).getSupply_name());
        holder.tv_jcr.setText("检查人 : " + datas.get(position).getM_nickname());
        holder.tv_time.setText("检查时间 : " + datas.get(position).getCreat_at());

        switch (datas.get(position).getStatus()) {
            case "1":
                holder.btn_zg.setText("待整改");
                break;
            case "2":
                holder.btn_zg.setText("已整改");
                break;
            case "3":
                holder.btn_zg.setText("已验收");
                break;
            case "4":
                holder.btn_zg.setText("合格");
                break;
        }
        // 未验收
        holder.btn_wzg.setText("不合格");
        holder.btn_wzg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lists.add(datas.get(position).getCheck_id());
                datas.remove(position);   //  提交成功直接移除
                notifyDataSetChanged();
                // 没有了,直接跳转
                if (datas.size() == 0){
                    Intent intent = new Intent(activity, GyzCheckActivity.class);
                    intent.putStringArrayListExtra("check_id",lists);
//                    intent.putExtra("check_id","2");
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });

        // 整改点击事件  提交整改状态
        holder.btn_zg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //状态码
                String status = datas.get(position).getStatus();
                //检查码
                String check_id = datas.get(position).getLogid();
                // 用户UID
                String uid = Global.uid;
                // 设置请求参数
                TiJiaoZgstate tiJiaoZgstate = new TiJiaoZgstate();
                tiJiaoZgstate.setDid(Integer.parseInt(check_id));
                tiJiaoZgstate.setStatus_uid(Integer.parseInt(uid));
                    // 不管是几 反正都变成3
                    status = "3";

                tiJiaoZgstate.setStatus(Integer.parseInt(status));
                LoginModel  loginModel = new LoginModel();
                loginModel.TijiaZgstate(activity, tiJiaoZgstate, new OnLoadComBackListener() {
                    @Override
                    public void onSuccess(Object msg) {
                        Toast.makeText(activity,"提交成功", Toast.LENGTH_SHORT).show();
                        lists.add(datas.get(position).getCheck_id());
                        datas.remove(position);   //  提交成功直接移除
                        notifyDataSetChanged();
//                        lists.add(datas.get(position).getCheck_id());
                        // 没有了,直接跳转
                        if (datas.size() == 0){
                            Intent intent = new Intent(activity, GyzCheckActivity.class);
                            intent.putStringArrayListExtra("check_id",lists);
//                            intent.putExtra("check_id","2");
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        Toast.makeText(activity, msg.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return convertView;
    }

    public class ViewHolder {
        Button btn_zg;
        Button btn_wzg;
        TextView tv_time;
        TextView tv_gongyinghzan;
        TextView tv_danwei;
        TextView tv_detail;
        TextView tv_jcr;
        TextView tv_riqi;
        //        RecyclerView easyRecyclerView;
        public LinearLayout morePicLayout;
        public SimpleDraweeView oneImgIv;
        public SimpleDraweeView[] imgs = new SimpleDraweeView[9];
    }
}
