package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.cxjl.CxInfoActivity;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.bean.resq.ContentRespData1;
import com.lidroid.xutils.BitmapUtils;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class FirstAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<ContentRespData1> list = new ArrayList<>();
    private ProgressActivity type_page_progress;
    private OnAddOrdelClick onAddOrdelClick;
    public FirstAdapter(Context context,OnAddOrdelClick onAddOrdelClick) {
        this.mContext = context;
        this.onAddOrdelClick =onAddOrdelClick;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.firstfragment_ada_item, null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            type_page_progress = (ProgressActivity) convertView.findViewById(R.id.type_page_progress);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.size() == 0){
            type_page_progress.showError(mContext.getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddOrdelClick.onCcClick(position);
                }
            });;
        }

        holder.tv_content.setText(list.get(position).getRemark());
        holder.tv_title.setText(list.get(position).getTitle());

        BitmapUtils utils = new BitmapUtils(mContext);

        // 如果为0 或者为空，就设置错误图片
        if (list.get(position).getUpload_path().equals("0") || TextUtils.isEmpty(list.get(position).getUpload_path())){
            holder.iv_img.setImageResource(R.mipmap.mrt_zgt);
        }
        utils.display(holder.iv_img,list.get(position).getUpload_path());
        holder.tv_data.setText(list.get(position).getCreat_at());

        return convertView;
    }

    public ArrayList<ContentRespData1> getList() {
        return list;
    }
    public void setList(ArrayList<ContentRespData1> list) {
        this.list = list;
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_data;
        TextView tv_content;
        ImageView iv_img;
    }

    // 提供向外的接口
    public interface OnAddOrdelClick {
        public void onCcClick(int position);
    }
}
