package com.example.lcsrq.xiangce;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.lcsrq.R;

import java.util.LinkedList;
import java.util.List;

public class ImageGridAdapter extends ImageChoiceAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new LinkedList<String>();
    private ChoiceImageIF choiceImageIF;
    /**
     * 文件夹路径
     */
    private String mDirPath;
    private Context context;

    public ImageGridAdapter(Context context, List<String> mDatas, int itemLayoutId, String dirPath, ChoiceImageIF choiceImageIF) {
        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        this.choiceImageIF = choiceImageIF;
        this.context=context;
        mSelectedImage.clear();
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {

        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.ic_pic_fail);
        //设置no_selected
        helper.setImageResource(R.id.id_item_select,  R.mipmap.ic_checkbox_uncheck);
        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);

        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                // 已经选择过该图片
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.mipmap.ic_checkbox_uncheck);
                    choiceImageIF.choiceNum(mSelectedImage.size());
                    mImageView.setColorFilter(null);
                } else
                // 未选择该图片
                {
                    if (mSelectedImage.size() < 9) {
                        mSelectedImage.add(mDirPath + "/" + item);
                        choiceImageIF.choiceNum(mSelectedImage.size());
                        mSelect.setImageResource(R.mipmap.ic_checkbox_check);
                        mImageView.setColorFilter(Color.parseColor("#77000000"));
                    }else{
                        Toast.makeText(context,"至多选择9张图片",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.mipmap.ic_checkbox_check);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }

    }


    public interface ChoiceImageIF {
        void choiceNum(int num);
    }
}
