package com.example.lcsrq.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.bean.resq.ContentGyzDetailRespData;
import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class GyzDzgAdapter extends BaseExpandableListAdapter {

    private ContentGyzDetailRespData data;
    private Activity activity;

    public ContentGyzDetailRespData getData() {
        return data;
    }

    public void setData(ContentGyzDetailRespData data) {
        this.data = data;
    }

    public GyzDzgAdapter(Activity activity) {
        this.activity = activity;
    }

    //设置组视图的图片
    int[] logos = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    //设置组视图的显示文字
//            private String[] category = new String[]{"僵尸  ", "魔法植物", "远程植物"};
    private String[] category = new String[]{"存在问题  "};

    //子视图显示文字
    private String[][] subcategory = new String[][]{
            {"因为一级只有一个TextView和一个ImageView，比较简单。因为子视图又是一个ExpandableListView，所以这里设置了监听，保证每次只展开一个分组，如果没有这个需求当然可以不设置，另外就是通过判断增加了指示箭头。因为一级只有一个TextView和一个ImageView，比较简单。因为子视图又是一个ExpandableListView，所以这里设置了监听，" +
                    "保证每次只展开一个分组，如果没有这个需求当然可以不设置，另外就是通过判断增加了指示箭头。", "因为一级只有一个TextView和一个ImageView，比较简单。因为子视图又是一个ExpandableListView，所以这里设置了监听，保证每次只展开一个分组，如果没有这个需求当然可以不设置，另外就是通过判断增加了指示箭头。因为一级只有一个TextView和一个ImageView，比较简单。因为子视图又是一个ExpandableListView，所以这里设置了监听，" +
                    "保证每次只展开一个分组，如果没有这个需求当然可以不设置，另外就是通过判断增加了指示梦否否"},
    };

    //子视图图片
    private int[][] img = new int[][]{{R.mipmap.cxtp, R.mipmap.cxtp, R.mipmap.cxtp}, {R.mipmap.ic_launcher_round, R.mipmap.icom_xlx}
    };
    private int[][] img2 = new int[][]{{R.mipmap.cxtp, R.mipmap.cxtp, R.mipmap.cxtp}, {R.mipmap.ic_launcher_round, R.mipmap.icom_xlx}
    };
    private int[][] img3 = new int[][]{{R.mipmap.cxtp, R.mipmap.cxtp, R.mipmap.icon_dlsy}, {R.mipmap.ic_launcher_round, R.mipmap.icom_xlx}
    };


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
        // 返回0 表示没有数据
        if (data.getCkloglist().size() == 0){
            return 0;
        }else {
            return data.getCkloglist().size();
        }
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //定义一个LinearLayout用于存放ImageView、TextView
        ViewHolder holder;
        if (convertView == null){
           convertView = View.inflate(activity, R.layout.gyz_dzg_child, null);
            holder = new ViewHolder();
            holder.dzg_tv = (TextView) convertView.findViewById(R.id.dzg_tv);
            holder.tv_creat = (TextView) convertView.findViewById(R.id.tv_creat);
            holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //  时间和站点
//        holder.dzg_tv.setText("检查站点 : " + data.getCkloglist().get(childPosition).getSupply_name());
        holder.dzg_tv.setText(data.getCkloglist().get(childPosition).getContent());
        holder.tv_creat.setText("检查时间 : " +data.getCkloglist().get(childPosition).getCreat_at());
        holder.tv_danwei.setText("检查单位 : " + data.getCkloglist().get(childPosition).getCheck_dw());

//        ImageView iv_1 = (ImageView) inflate.findViewById(R.id.iv_1);
//        ImageView iv_2 = (ImageView) inflate.findViewById(R.id.iv_2);
//        ImageView iv_3 = (ImageView) inflate.findViewById(R.id.iv_3);
//
//        TextView dzg_tv = (TextView) inflate.findViewById(R.id.dzg_tv);
//        dzg_tv.setText(subcategory[groupPosition][childPosition]);
//
//        iv_1.setImageResource(img[groupPosition][childPosition]);
//        iv_2.setImageResource(img2[groupPosition][childPosition]);
//        iv_3.setImageResource(img3[groupPosition][childPosition]);

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
        return childPosition;
    }

    class ViewHolder {
        TextView dzg_tv;
        TextView tv_creat;
        TextView tv_danwei;
    }
}
