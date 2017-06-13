package com.example.lcsrq.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.ContentGyzDetailRespData;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class GyzLskfAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    //设置组视图的图片
    int[] logos = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    //设置组视图的显示文字
//            private String[] category = new String[]{"僵尸  ", "魔法植物", "远程植物"};
    private String[] category = new String[]{"历史扣分记录  "};

    //子视图显示文字
    private String[][] subcategory = new String[][]{
            {"张三、李四", "sdasd"},
//                    {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
//            {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
//                    {"满天星", "风车植物", "带刺植物", "贪睡植物", "双子植物", "胆怯蘑菇"}

    };

    private String[][] kfxm = new String[][]{{"煤气罐发生爆","煤气罐GG"}};

    private String[][] kf = new String[][]{{"300", "400"}};

    private String [][] sj = new String[][]{{"2017-3-27(16:30)","2017-3-27(16:40)"}};

    //子视图图片
    public int[][] sublogos = new int[][]{
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher}};

    public GyzLskfAdapter(Activity ac) {
        this.activity = ac;
    }

    private ContentGyzDetailRespData data;

    public ContentGyzDetailRespData getData() {
        return data;
    }

    public void setData(ContentGyzDetailRespData data) {
        this.data = data;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View inflate = View.inflate(activity, R.layout.list_item, null);
        TextView tv_name = (TextView) inflate.findViewById(R.id.textView);
        tv_name.setText(category[groupPosition]);
        return inflate;
    }

    //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //取得分组数
    @Override
    public int getGroupCount() {
        return category.length;
    }

    //取得与给定分组关联的数据
    @Override
    public Object getGroup(int groupPosition) {
        return category[groupPosition];
    }

    //取得指定分组的子元素数.
    @Override
    public int getChildrenCount(int groupPosition) {
//        return subcategory[groupPosition].length;
        if (data.getJftloglist().size() == 0){
            return 0;
        }else {
            return data.getJftloglist().size();
        }
    }


    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //定义一个LinearLayout用于存放ImageView、TextView
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
             convertView = View.inflate(activity, R.layout.gyz_lskf_child, null);
            holder.tv_kfxm = (TextView) convertView.findViewById(R.id.tv_kfxm);
            holder.tv_kfs = (TextView) convertView.findViewById(R.id.tv_kfs);
            holder.tv_kfry = (TextView) convertView.findViewById(R.id.tv_kfry);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder. tv_time.setText(data.getJftloglist().get(childPosition).getCreat_at());
        holder. tv_kfs.setText(data.getJftloglist().get(childPosition).getOid_value() + "分");
        holder. tv_kfry.setText(data.getJftloglist().get(childPosition).getUname());
        holder. tv_kfxm.setText(data.getJftloglist().get(childPosition).getPname());
//        iv_pic.setImageResource(sublogos[groupPosition][childPosition]);
//        tv_name.setText(subcategory[groupPosition][childPosition]);
        return convertView;
    }

    //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subcategory[groupPosition][childPosition];
    }

    class  ViewHolder {
        TextView tv_kfxm;
        TextView tv_kfs;
        TextView tv_kfry;
        TextView tv_time;
    }
}
