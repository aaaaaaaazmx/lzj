package com.example.lcsrq.adapter;

/**
 * 横向列表适配
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Poi;
import com.example.lcsrq.R;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter {

    private String[] strings;
    private Context context;
    ArrayList<String[]> urlList = new ArrayList<>();

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public RecycleAdapter( Context context) {
//        this.strings = strings;
        this.context = context;
        //upload_json: "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163744_22727.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_84893.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_95078.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_71457.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_86577.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_81087.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_68853.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163746_46968.jpg,
        // http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163746_31739.jpg",
//        urlList.add(new String[]{
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163744_22727.jpg",
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_84893.jpg",
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_95078.jpg",
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_71457.jpg",
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163746_31739.jpg",
//                "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170507/20170507163745_86577.jpg"});


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_r, null);
        RecycleAdapter.ViewHolder viewHolder = new RecycleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //图片加载
//            Glide.with(activity)
//                    .load(strings[position])
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.mipmap.mrt_zgt)
//                    .into(((RecycleAdapter.ViewHolder) holder).imageView);

//        ImageLoader.getInstance().displayImage(strings[position],((RecycleAdapter.ViewHolder) holder).imageView);
        ((RecycleAdapter.ViewHolder) holder).imageView.setImageURI(Uri.parse(strings[position]));
        ((RecycleAdapter.ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiTool.showPic(context, strings[position]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return strings.length;
//        return urlList.get(0).length;
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.iv_image);
        }
    }


}
