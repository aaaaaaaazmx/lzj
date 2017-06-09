package com.example.lcsrq.crame;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布
 */
public class  PostedNewActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp {
    private RelativeLayout commonRightBtn;
    private EditText postedContentEt;
    private LinearLayout locationLayout;
    private TextView titleTv, commonRightText, locationTv;
        // 显示图片
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;
    private GridView gridView;
    private MyPostGridAdapter adapter;

    private String fileName;
    //弹窗
    private CustomDialog choicePhotoDialog;

//    private FirstModel firstModel = new FirstModel();
    private String location;
    private ArrayList<File> filenames = new ArrayList<File>();
    private ArrayList<File> filenamesTake = new ArrayList<File>();
    private ArrayList<File> filenamesChoice = new ArrayList<File>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsTake = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsChoice = new ArrayList<Bitmap>();
    private String imgurl = "";
    private int imageUploadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_send);
    }

    @Override
    public void findViews() {
        ImageGridAdapter.mSelectedImage.clear();
        titleTv = (TextView) findViewById(R.id.commonTitleTv);
        titleTv.setText("发布");
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("确定");
        commonRightText.setBackgroundResource(R.drawable.corner_toumingbg_greenborder_2dp);

        postedContentEt = (EditText) findViewById(R.id.postedContentEt);
        locationTv = (TextView) findViewById(R.id.locationTv);

        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindCameraLayout(this);


        //图片列表
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter(this, bitmaps, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
    }

    @Override
    public void addAction() {
        commonRightBtn.setOnClickListener(this);
        postedContentEt.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(PostedNewActivity.this);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(PostedNewActivity.this,"最多能上传9张图片",Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(PostedNewActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);
    }


    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }


    @Override
    public void onClick(View view) {
        // p拍照
        if (view.getId() == R.id.item_popupwindows_one) {
            //先验证手机是否有sdcard
            String status = Environment.getExternalStorageState();
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    fileName = String.valueOf(System.currentTimeMillis() + ".JPEG");
                    File dir = new File(Environment.getExternalStorageDirectory() + "/temp");
                    if (!dir.exists()) dir.mkdirs();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(dir, fileName);//localTempImgDir和localTempImageFileName是自己定义的名字
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                    startActivityForResult(intent, TAKE_PICTURE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "没有找到储存目录", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "没有储存卡", Toast.LENGTH_LONG).show();
            }
            choicePhotoDialog.dismiss();

            // 从相册选择
        } else if (view.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent1, IMAGE_CODE);
                Intent intent = new Intent(this, ChoicePicActivity.class);
                startActivityForResult(intent, IMAGE_CODE);
                choicePhotoDialog.dismiss();
            // 取消
        }
        else if (view.getId() == R.id.parent || view.getId() == R.id.item_popupwindows_cancel) {
            choicePhotoDialog.dismiss();
        }
//        else if (view.getId() == R.id.commonRightBtn) {
//            sendPost();
//        } else if (view.getId() == R.id.locationLayout) {
//            Intent intent = new Intent(this, LocationActivity.class);
//            startActivityForResult(intent, LOCATION_CODE);
//        }
    }

    /**
     * 发贴
     */
//    public void sendPost() {
//
//
//        final String content = postedContentEt.getText().toString();
//
//        if (!StringTool.isNotNull(content)) {
//            UiTool.showToast(this, "内容不能为空");
//            return;
//        }
////        if (content.length() >= 3999) {
////            UiTool.showToast(this, "内容不可超过3999个字符");
////            return;
////        }
//        showLoading("正在提交");
//        imgurl = "";
//        imageUploadCount = 0;
//        if (filenames.size() > 0) {
//            for (int i = 0; i < filenames.size(); i++) {
//                ModelHttp.shangChuanPic(this, filenames.get(i), new ProcessListener() {
//                    @Override
//                    public boolean onDone(String key, String str) {
//                        imgurl += (str + ",");
//                        imageUploadCount++;
//                        if (imageUploadCount == filenames.size()) {
//                            String s = imgurl.substring(0, imgurl.length() - 1);
//                            UiTool.showLog("上传完成=" + s);
//                            commit(content, s);
//                        }
//                        return false;
//                    }
//                });
//            }
//        } else {
//            commit(content, "");
//        }
//
//    }

//    public void commit(String content, String imgurl) {
//
//        PutBBSActionReqData reqData = new PutBBSActionReqData();
//        reqData.setInfo(content);
//        reqData.setImg_url(imgurl);
//        reqData.setPulish_site(location);
//        commonRightBtn.setClickable(false);
//
//        firstModel.putBBSAction(this, reqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                showToastText("发布成功");
//                commonRightBtn.setClickable(true);
//                closeDialog();
//                setResult(RESULT_OK);
//                finish();
//
//            }
//
//            @Override
//            public void onError(String msg) {
//                commonRightBtn.setClickable(true);
//                showLoading(msg);
//                closeDialog();
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == this.RESULT_OK) {
                    String path = null;
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/temp" + "/" + fileName);
                    try {
                        Uri uri1 =
                                Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                        f.getAbsolutePath(), null, null));
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //获取包含所需数据的Cursor对象
                        Cursor cursor = this.managedQuery(uri1, proj, null, null, null);
                        if (cursor != null) {
                            //获取索引
                            int photocolumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            //将光标一直开头
                            cursor.moveToFirst();
                            //根据索引值获取图片路径
                            path = cursor.getString(photocolumn);
                        } else {
                            // 如果游标为空说明获取的已经是绝对路径了
                            path = uri1.getPath();
                        }
                        try {
                            Bitmap bm = UiTool.revitionImageSize(path);
                            bitmapsTake.add(bm);
                            bitmaps.clear();
                            bitmaps.addAll(bitmapsTake);
                            bitmaps.addAll(bitmapsChoice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        filenamesTake.add(new File(path));
                        filenames.clear();
                        filenames.addAll(filenamesTake);
                        filenames.addAll(filenamesChoice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case IMAGE_CODE:
                if (resultCode == this.RESULT_OK && data != null) {
                    List<String> images = (List<String>) data.getSerializableExtra("imageUrls");
                    bitmapsChoice.clear();
                    filenamesChoice.clear();
                    for ( String path : images) {
                        try {
                            Bitmap bm = UiTool.loadBitmap(path);

                            bitmapsChoice.add(bm);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        filenamesChoice.add(new File(path));
                    }
                    bitmaps.clear();
                    bitmaps.addAll(bitmapsTake);
                    bitmaps.addAll(bitmapsChoice);
                    filenames.clear();
                    filenames.addAll(filenamesTake);
                    filenames.addAll(filenamesChoice);
                }
//                if (resultCode == this.RESULT_OK && data != null) {
//                    Uri uri1 = data.getData();
//
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    //获取包含所需数据的Cursor对象
//                    Cursor cursor = this.managedQuery(data.getData(), proj, null, null, null);
//                    if (cursor != null) {
//                        //获取索引
//                        int photocolumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        //将光标一直开头
//                        cursor.moveToFirst();
//                        //根据索引值获取图片路径
//                        path = cursor.getString(photocolumn);
//                    } else {
//                        // 如果游标为空说明获取的已经是绝对路径了
//                        path = uri1.getPath();
//                    }
//                    try {
//                        Bitmap bm = UiTool.loadBitmap(path);
//                        bitmaps.add(bm);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    tempFile = new File(path);
//                    filenames.add(tempFile);
//                }
                break;
            case LOCATION_CODE:
                if (resultCode == this.RESULT_OK && data != null) {
                    location = data.getStringExtra("location");
                    if (StringTool.isNotNull(location)) {
                        locationTv.setText(location);
                    } else {
                        locationTv.setText("所在的位置");
                    }
                }
                break;
        }


    }


    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap pic = bitmaps.get(i);
            if (pic != null && !pic.isRecycled()) {
                pic.recycle();
            }
        }
        bitmaps.clear();
    }

    @Override
    public void deletePic(int position) {
        Bitmap pic = bitmaps.get(position);
        pic.recycle();
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();
    }


}
