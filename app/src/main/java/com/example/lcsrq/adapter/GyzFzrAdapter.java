package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.PeopleDetail;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.bean.respbean.Data_fzr;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class GyzFzrAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    //设置组视图的图片
    int[] logos = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    //  父Item的标签
    private String[] category = new String[]{"供应站负责人  "};

    //子视图显示文字
    private String[][] subcategory = new String[][]{
            {"杨某某", "sdasd"},
//                    {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
            {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
//                    {"满天星", "风车植物", "带刺植物", "贪睡植物", "双子植物", "胆怯蘑菇"}

    };

    private String[][] bun = new String[][]{{"123", "455"}, {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"}};
    //子视图图片
    public int[][] sublogos = new int[][]{
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher}};


    private OnAddOrdelClick onAddOrdelClick;
    private  ArrayList<Data_fzr> data_fzr  = new ArrayList<>();
    private final Random random;

    public ArrayList<Data_fzr> getData_fzr() {
        return data_fzr;
    }

    public void setData_fzr(ArrayList<Data_fzr> data_fzr) {
        this.data_fzr = data_fzr;
    }

    public GyzFzrAdapter(Activity ac,OnAddOrdelClick onAddOrdelClick) {
        this.activity = ac;
        this.onAddOrdelClick = onAddOrdelClick;
        random = new Random();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
//        //定义一个LinearLayout用于存放ImageView、TextView
//        LinearLayout ll = new LinearLayout(activity);
////                //设置子控件的显示方式为水平
////                ll.setOrientation(0);
//        //定义一个ImageView用于显示列表图片
//        ImageView logo = new ImageView(activity);
//        logo.setPadding(50, 0, 0, 0);
//        //设置logo的大小(50（padding）+46=96)
//        AbsListView.LayoutParams lparParams = new AbsListView.LayoutParams(96, 46);
//        logo.setLayoutParams(lparParams);
//        logo.setImageResource(logos[groupPosition]);
//        ll.addView(logo);
//        TextView textView = getTextView();
//        textView.setTextSize(20);
//        textView.setText(category[groupPosition]);
//        ll.addView(textView);
        View inflate = View.inflate(activity, R.layout.list_item, null);
        TextView tv_name = (TextView) inflate.findViewById(R.id.textView);
        tv_name.setText(category[groupPosition]);
        return inflate;
    }

    //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    //取得分组数
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return category.length;
    }

    //取得与给定分组关联的数据
    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return category[groupPosition];
    }

    //取得指定分组的子元素数.
    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
//        return subcategory[groupPosition].length;
        if (data_fzr.size() ==0){
            return 0;
        }
        return data_fzr.size();
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        //定义一个LinearLayout用于存放ImageView、TextView
//        View inflate = View.inflate(activity, R.layout.gyz_fzr_child, null);
//        ImageView iv_pic = (ImageView) inflate.findViewById(R.id.iv_pic);
//        TextView tv_name = (TextView) inflate.findViewById(R.id.tv_name);
//        TextView tv_phone = (TextView) inflate.findViewById(R.id.tv_phone);
////        iv_pic.setImageResource(sublogos[groupPosition][childPosition]);
//        tv_name.setText(data_fzr.get(childPosition).getM_nickname());
//        tv_phone.setText(data_fzr.get(childPosition).getTel());

        final GyzCyrAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new GyzCyrAdapter.ViewHolder();
            convertView = View.inflate(activity, R.layout.gyz_fzr_child, null);
            holder.iv_sj = (ImageView) convertView.findViewById(R.id.iv_sj);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.yuann_view = (CircleTextImageView) convertView.findViewById(R.id.yuann_view);
            convertView.setTag(holder);
        } else {
            holder = (GyzCyrAdapter.ViewHolder) convertView.getTag();
        }
        holder.iv_sj.setImageResource(R.mipmap.icon_dhhm);
        holder.tv_name.setText(data_fzr.get(childPosition).getM_nickname());
        holder.tv_phone.setText(data_fzr.get(childPosition).getTel());

        int tmp = Math.abs(random.nextInt());
        String yanse = tmp % (999999 - 100000 + 1) + 100000 + "";
        String m_nickname = data_fzr.get(childPosition).getM_nickname();
        String name = m_nickname.substring(0, 1);
        holder.yuann_view.setText(name);
        holder.yuann_view.setFillColor(Color.parseColor("#" + yanse));

        // 点击的是内置的子项
        holder.iv_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddOrdelClick.onCcClick(childPosition);
            }
        });

        // 跳转详情页面
        holder.yuann_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = data_fzr.get(childPosition).getUid();

                Intent intent = new Intent(activity, PeopleDetail.class);
                intent.putExtra("UID",uid);
                activity.startActivity(intent);
            }
        });


        return convertView;
    }

    //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
//        return subcategory[groupPosition][childPosition];
        return data_fzr.get(childPosition);
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
        ImageView iv_sj;
        CircleTextImageView yuann_view;
    }

    // 提供向外的接口
    public interface OnAddOrdelClick {
        public void onCcClick(int position);
    }
};
