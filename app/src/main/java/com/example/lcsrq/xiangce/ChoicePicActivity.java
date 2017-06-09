package com.example.lcsrq.xiangce;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.base.BaseActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ChoicePicActivity extends BaseActivity implements ImageGridAdapter.ChoiceImageIF, ListImageDirPopupWindow.OnImageDirSelected {
    private ProgressDialog mProgressDialog;
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;
    private GridView mGirdView;
    private ImageGridAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private int mScreenHeight;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private Handler mHandler;

    private TextView titleTv, commonRightText;
    private RelativeLayout commonRightBtn;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pic);
        getImages();
    }

    private void init(){
        findViews();
        addAction();
        getImages();
    }


    @Override
    public void findViews() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        titleTv = (TextView) findViewById(R.id.commonTitleTv);
        titleTv.setText("选择图片");
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("确定");
        commonRightText.setBackgroundResource(R.drawable.corner_toumingbg_greenborder_2dp);

        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);

        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                mProgressDialog.dismiss();
                // 为View绑定数据
                data2View();
                // 初始化展示文件夹的popupWindw
                initListDirPopupWindw();
            }
        };

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {

//        if (ContextCompat.checkSelfPermission(ChoicePicActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(ChoicePicActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE1);
//        } else {
//            getImages();
//        }

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ChoicePicActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();



    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }


    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new ImageGridAdapter(getApplicationContext(), mImgs, R.layout.grid_item, mImgDir.getAbsolutePath(),this);
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");

    }

    ;


    @Override
    public void selected(ImageFloder floder) {

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new ImageGridAdapter(getApplicationContext(), mImgs,
                R.layout.grid_item, mImgDir.getAbsolutePath(),this);
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }

    @Override
    public void addAction() {
        mBottomLy.setOnClickListener(this);
        commonRightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_bottom_ly) {
            mListImageDirPopupWindow .setAnimationStyle(R.style.anim_popup_dir);
            mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
            // 设置背景颜色变暗
            WindowManager.LayoutParams lp = getWindow().getAttributes();
//            lp.alpha = .3f;
            getWindow().setAttributes(lp);
        } else if (v.getId() == R.id.commonRightBtn) {
            Intent intent = new Intent();
            intent.putExtra("imageUrls", (Serializable) mAdapter.mSelectedImage);
            setResult(RESULT_OK, intent);
            finish();
        }
    }




    @Override
    public void choiceNum(int num) {
        if (num == 0) {
            commonRightText.setText("确定");
        } else {
            commonRightText.setText("确定(" + num + "/9)");

        }
    }

}
