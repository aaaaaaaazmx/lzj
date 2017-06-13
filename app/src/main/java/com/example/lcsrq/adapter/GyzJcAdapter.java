package com.example.lcsrq.adapter;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.ContentGyzDetailRespData;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 苏毅 on 2017/5/10.
 */

public class GyzJcAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    //设置组视图的图片
    int[] logos = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    //设置组视图的显示文字
//            private String[] category = new String[]{"僵尸  ", "魔法植物", "远程植物"};
    private String[] category = new String[]{"检查记录  "};

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

    private ContentGyzDetailRespData data;
    private LinearLayout morePicLayout;

    public ContentGyzDetailRespData getData() {
        return data;
    }

    public void setData(ContentGyzDetailRespData data) {
        this.data = data;
    }

    public GyzJcAdapter(Activity ac) {
        this.activity = ac;
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
        if (data.getCklogtimeslist().size() == 0){
            return 0;
        }
        return data.getCklogtimeslist().size();
    }


    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view ==null){
            viewHolder = new ViewHolder();
            view = View.inflate(activity,R.layout.gyz_jcwt_child,null);
            viewHolder.morePicLayout = (LinearLayout) view.findViewById(R.id.morePicLayout);
            // 动态
            viewHolder.oneImgIv = (SimpleDraweeView) view.findViewById(R.id.oneImgIv);
            viewHolder.imgs[0] = (SimpleDraweeView) view.findViewById(R.id.imgIv1);
            viewHolder.imgs[1] = (SimpleDraweeView) view.findViewById(R.id.imgIv2);
            viewHolder.imgs[2] = (SimpleDraweeView) view.findViewById(R.id.imgIv3);
            viewHolder.imgs[3] = (SimpleDraweeView) view.findViewById(R.id.imgIv4);
            viewHolder.imgs[4] = (SimpleDraweeView) view.findViewById(R.id.imgIv5);
            viewHolder.imgs[5] = (SimpleDraweeView) view.findViewById(R.id.imgIv6);
            viewHolder.imgs[6] = (SimpleDraweeView) view.findViewById(R.id.imgIv7);
            viewHolder.imgs[7] = (SimpleDraweeView) view.findViewById(R.id.imgIv8);
            viewHolder.imgs[8] = (SimpleDraweeView) view.findViewById(R.id.imgIv9);


            //  文字
            viewHolder.tv_jcr = (TextView) view.findViewById(R.id.tv_jcr);
            viewHolder.tv_bh = (TextView) view.findViewById(R.id.tv_bh);
            viewHolder.tv_creat_at = (TextView) view.findViewById(R.id.tv_creat_at);
            viewHolder.tv_danwei = (TextView) view.findViewById(R.id.tv_danwei);
            // 图片
//            String imgUrlStr = data.getCkloglist().get(childPosition).getUpload_json();
//            if (!TextUtils.isEmpty(imgUrlStr)) {
//                final String[] imgurls = imgUrlStr.split(",");
//                if (imgurls.length == 1) {
//                    viewHolder.oneImgIv.setVisibility(View.VISIBLE);
//                    viewHolder.morePicLayout.setVisibility(View.GONE);
//                    viewHolder.oneImgIv.setImageURI(Uri.parse(imgurls[0]));
//                    viewHolder.oneImgIv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            UiTool.showPic(activity, imgurls[0]);
//                        }
//                    });
//                } else if (imgurls.length > 1) {
//                    viewHolder.oneImgIv.setVisibility(View.GONE);
//                    viewHolder.morePicLayout.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < viewHolder.imgs.length; i++) {
//                        if (i < imgurls.length) {
//                            viewHolder.imgs[i].setImageURI(Uri.parse(imgurls[i]));
//                            final int index = i;
//                            viewHolder.imgs[i].setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    UiTool.showPic(activity, imgurls[index]);
//                                }
//                            });
//                        } else {
//                            int lineImg = imgurls.length / 3;
//                            int lineNoImg = i / 3;
//                            if (lineNoImg > lineImg) {
//                                viewHolder.imgs[i].setVisibility(View.GONE);
//                            } else {
//                                viewHolder.imgs[i].setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    }
//                }
//            } else {
//                viewHolder.morePicLayout.setVisibility(View.GONE);
//                viewHolder.oneImgIv.setVisibility(View.GONE);
//            }
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_jcr.setText("检查人 : " + data.getCklogtimeslist().get(childPosition).getM_nickname());
//        viewHolder.tv_bh.setText("编号 : " + data.getCklogtimeslist().get(childPosition).getLogid() );
        viewHolder.tv_creat_at.setText("检查时间 : " + data.getCklogtimeslist().get(childPosition).getTimes_at());
        viewHolder.tv_danwei.setText("检查单位 : " + data.getCklogtimeslist().get(childPosition).getCheck_dw());

        return view;
}

        //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
        @Override
        public long getChildId ( int groupPosition, int childPosition){
            return childPosition;
        }

        @Override
        public Object getChild ( int groupPosition, int childPosition){
            return data.getCklogtimeslist().get(childPosition);
        }

        public  class ViewHolder {
            public TextView tv_jcr;
            public TextView tv_bh;
            public TextView tv_danwei;
            public TextView tv_creat_at;
            public LinearLayout morePicLayout;
            public SimpleDraweeView oneImgIv;
            public SimpleDraweeView[] imgs = new SimpleDraweeView[9];
        }

    }
