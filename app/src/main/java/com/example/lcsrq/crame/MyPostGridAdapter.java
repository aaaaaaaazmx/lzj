package com.example.lcsrq.crame;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * 发帖图片适配
 */
public class MyPostGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bitmap> urls;
    DeletePicImp deletePicImp;

    public MyPostGridAdapter(Context context, ArrayList<Bitmap> urls, DeletePicImp deletePicImp) {
        super();
        this.context = context;
        this.urls = urls;
        this.deletePicImp = deletePicImp;
    }

    @Override
    public int getCount() {
        return urls.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.griditem_newpost, null);
            holder.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            holder.closeIv = (ImageView) convertView.findViewById(R.id.closeIv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == urls.size()) {
            holder.img.setImageResource(R.mipmap.ic_xiangji);
            holder.closeIv.setVisibility(View.GONE);
        } else {
            holder.closeIv.setVisibility(View.VISIBLE);
            if (urls.get(position) != null) {
                holder.img.setScaleType(SimpleDraweeView.ScaleType.CENTER_CROP);
                holder.img.setImageBitmap(urls.get(position));
            }
        }

        holder.closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletePicImp.deletePic(position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public SimpleDraweeView img;
        public ImageView closeIv;

    }

    public interface DeletePicImp {
        public void deletePic(int position);
    }

}
