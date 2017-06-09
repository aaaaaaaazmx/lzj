package com.example.lcsrq.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.lawfg.LawActivity;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.utils.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URL;
import java.util.ArrayList;

//import static com.example.lcsrq.R.id.view;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class LawFlfgAdapter extends BaseAdapter {

    private Activity activity;
    private  ArrayList<ContentRespData> flfgList = new ArrayList<>();

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<ContentRespData> getFlfgList() {
        return flfgList;
    }

    public void setFlfgList(ArrayList<ContentRespData> flfgList) {
        this.flfgList = flfgList;
    }

    public LawFlfgAdapter(Activity activity) {
        this.activity =activity;
    }

    public int getCount() {
        return flfgList.size();
    }

    @Override
    public Object getItem(int position) {
        return flfgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
//        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//        TextView tv_data = (TextView) view.findViewById(R.id.tv_data);
//        TextView tv_dt = (TextView) view.findViewById(R.id.tv_fl);
//
//        iv_pic.setImageResource(imger[position]);
//        tv_dt.setText("送气人员定期帮助客户检查清理、维护等,送气人员定期帮助客户检查清理、维护等送气人员定期帮助客户检查清理、" +
//                "维护等送气人员定期帮助客户检查清理、维护等送气人员定期帮助客户检查清理、维护等");

        ViewHodler hodler;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = View.inflate(activity, R.layout.list_item_law_flfg, null);
            hodler.iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
            hodler.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hodler.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }
        DensityUtil.lzj(hodler.iv_pic,flfgList.get(position).getUpload_path());
        hodler.tv_data.setText(flfgList.get(position).getCreat_at());
        hodler.tv_title.setText(flfgList.get(position).getTitle());

        return convertView;
    }

    static class ViewHodler{
        SimpleDraweeView iv_pic;
        TextView tv_title;
        TextView tv_data;
    }

}
