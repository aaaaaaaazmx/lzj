package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.My.MyZgActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcActivity;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class MyAllHdhAdapter extends BaseAdapter {

    private Activity activity;
    private LinearLayout ll_chachuqingkuang;
    private boolean isFirst = true;
    private LinearLayoutManager linearLayoutManager;
    List<String[]> urlList = new ArrayList<>();
    private RecycleAdapter recycleAdapter;
    public ArrayList<HdhcRespData> getList() {
        return list;
    }

    public void setList(ArrayList<HdhcRespData> list) {
        this.list = list;
    }

    private ArrayList<HdhcRespData> list = new ArrayList<>();

    public MyAllHdhAdapter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.list_item_hdhc, null);
            holder = new ViewHolder();

            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_jbcs = (TextView) convertView.findViewById(R.id.tv_jbcs);
            holder.tv_cp = (TextView) convertView.findViewById(R.id.tv_cp);
            holder.tv_yjfzr = (TextView) convertView.findViewById(R.id.tv_yjfzr);
            holder.tv_ejfzr = (TextView) convertView.findViewById(R.id.tv_ejfzr);
            holder.tv_content1 = (TextView) convertView.findViewById(R.id.tv_content1);
//            holder.tv_content2 = (TextView) convertView.findViewById(R.id.tv_contnet2);
            holder.tv_ccqk = (TextView) convertView.findViewById(R.id.tv_ccqk);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
//            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.recyclerView);

            //让其横向滑动
//            holder.easyRecyclerView = (RecyclerView) convertView.findViewById(R.id.easyRV);
//            linearLayoutManager = new LinearLayoutManager(activity);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            holder.easyRecyclerView.setLayoutManager(linearLayoutManager);

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


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //图片
        String imgUrlStr = list.get(position).getUpload_json();
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

        holder.tv_address.setText("地址 : " + list.get(position).getAddress()); // 地址
        holder.tv_creat.setText("时间 : " + list.get(position).getCreat_at()); //  时间
        //  举报次数
        if (list.get(position).getCclist().size() == 0){
            holder.tv_jbcs.setText("举报次数 : " + 0 + "次");
        }else {
            holder.tv_jbcs.setText("举报次数 : " + list.get(position).getCclist().size() + "次");
        }

        holder.tv_cp.setText("车牌号码 : " + list.get(position).getCart_number());  //  车牌号码

        holder.tv_content1.setText(list.get(position).getContent());  // 举报内容

//        if (list.get(position).getCclist().size() != 0) {
//            holder.tv_dw.setText("查处单位 : " + list.get(position).getCclist().get(position).getDw()); //  查处单位
//            holder.tv_erjifzr.setText("二级负责人 : " + list.get(position).getCclist().get(position).getMan());  // 一级负责人
//            holder.tv_yijifzr.setText("一级负责人 : " + list.get(position).getCclist().get(position).getM_nickname());  // 二级级负责人
//        }else {
//            holder.tv_dw.setText("查处单位 : " + "null"); //  查处单位
//            holder.tv_erjifzr.setText("一级负责人 : "  + "null");  // 一级负责人
//            holder.tv_yijifzr.setText("二级负责人 : " + "null");  // 二级级负责人


        // 不注释, 滑动会直接闪退
//        if (list.get(position).getCclist().size()!=0){
//            holder.tv_yjfzr.setText("一级负责人 : " +list.get(position).getCclist().get(position).getMan());
//            holder.tv_ejfzr.setText("二级负责人 : " + list.get(position).getCclist().get(position).getM_nickname());
//        }else {
//            holder.tv_yjfzr.setText("一级负责人 : "  + "null");  // 一级负责人
//            holder.tv_ejfzr.setText("二级负责人 : " + "null");  // 二级负责人
//        }

        holder.tv_ccqk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"dianjile",Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }



    static class ViewHolder {
        //        RecyclerView recyclerView;
        TextView tv_address; // 黑车地址
        TextView tv_jbcs;       // 举报次数
        TextView tv_cp;     // 车牌
        TextView tv_yjfzr;  // 一级负责人
        TextView tv_ejfzr;  // 二级负责人
        TextView tv_content1; // 内容1
        TextView tv_content2;  //内容2
        TextView tv_ccqk;  // 查看情况
        TextView tv_creat;  // 时间
        RecyclerView easyRecyclerView;

        public LinearLayout morePicLayout;
        public SimpleDraweeView oneImgIv;
        public SimpleDraweeView[] imgs = new SimpleDraweeView[9];
    }

    private class Radapter extends RecyclerView.Adapter {

        private String[] strings;
        private Context context;

        public Radapter(String[] strings, Context context) {
            this.strings = strings;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_r, null);
            Radapter.ViewHolder viewHolder = new Radapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            //图片加载
//            Glide.with(activity)
//                    .load(strings[position])
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.mipmap.mrt_zgt)
//                    .into(((Radapter.ViewHolder) holder).imageView);

            ((Radapter.ViewHolder) holder).imageView.setImageURI(Uri.parse(strings[position]));

            ((Radapter.ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiTool.showPic(context, strings[position]);
                }
            });

        }

        @Override
        public int getItemCount() {
            return strings.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private SimpleDraweeView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (SimpleDraweeView) itemView.findViewById(R.id.iv_image);
            }
        }

    }
}
