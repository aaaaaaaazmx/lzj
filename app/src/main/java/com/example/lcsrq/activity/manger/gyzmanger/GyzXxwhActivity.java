package com.example.lcsrq.activity.manger.gyzmanger;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.activity.manger.map.BaiduMapActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GyzXxwhReqData;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.TijiaoPicRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
import com.example.lcsrq.http.ModelHttp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.http.ProcessListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.StringTool;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.BaseGridView;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by 苏毅 on 2017/3/31.
 * 提交信息维护
 */

public class GyzXxwhActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp {
    private RelativeLayout commonRightBtn;
    private TextView titleTv, commonRightText, locationTv, tv_dizhi;
    private LoginModel loginModel;
    private LinearLayout commonLeftBtn;

    private EditText postedContentEt;
    private LinearLayout locationLayout;
    // 显示图片
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;
    private BaseGridView gridView;
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
    private ImageView ivGo;
    private int BD = 50;
    private String mLatitude;  //纬度
    private String mLongtitude;//经度
    private String weizhi;
    private String title;
    private TextView tv_name,tv_zhandian;
    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_xxwh);
        loginModel = new LoginModel();
        Intent intent = getIntent();
        weizhi = intent.getStringExtra("dizhi"); //  位置
        title = intent.getStringExtra("title"); //  供应站名字
        tv_dizhi.setText(weizhi + "");
        tv_zhandian.setText(title + "");
        tv_name.setText(Global.usernName + "");
    }

    @Override
    protected void addAction() {


        // 提交修改
        btn_submit.setOnClickListener(this);

        commonLeftBtn.setOnClickListener(this);
        commonRightBtn.setOnClickListener(this);

        postedContentEt.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(GyzXxwhActivity.this);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(GyzXxwhActivity.this, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(GyzXxwhActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);
        ivGo.setOnClickListener(this);
    }

    @Override
    protected void findViews() {

        // 提交修改
        btn_submit = (Button) findViewById(R.id.btn_submit);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        ImageGridAdapter.mSelectedImage.clear();
        titleTv = (TextView) findViewById(R.id.commonTitleTv);
        titleTv.setText("信息维护");

        tv_dizhi = (TextView) findViewById(R.id.tv_dizhi);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_zhandian = (TextView) findViewById(R.id.tv_zhandian);

        ImageGridAdapter.mSelectedImage.clear();
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("确定");
        commonRightText.setBackgroundResource(R.drawable.corner_toumingbg_greenborder_2dp);
        commonRightText.setVisibility(View.GONE); // 隐藏右上角


        postedContentEt = (EditText) findViewById(R.id.postedContentEt);
        locationTv = (TextView) findViewById(R.id.locationTv);

        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindCameraLayout(this);

        //图片列表
        gridView = (BaseGridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter(this, bitmaps, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
        ivGo = (ImageView) findViewById(R.id.iv_1);
    }

    private String dizhi;

    @Override
    public void onClick(View view) {
        // 提交修改
        if (view.getId() == R.id.btn_submit){

            dizhi = tv_dizhi.getText().toString();
            showLoading("正在加载");
            sendPicPost(); // 上传照片,并返回地址

        }else  if (view.getId() == R.id.commonRightBtn) {
            dizhi = tv_dizhi.getText().toString();
            showLoading("正在加载");
            sendPicPost(); // 上传照片,并返回地址

        } else if (view.getId() == R.id.commonLeftBtn) {
            finish();
        } else   // p拍照
            if (view.getId() == R.id.item_popupwindows_one) {
                // 手机被点击
                if (ContextCompat.checkSelfPermission(GyzXxwhActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GyzXxwhActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_XIANGJI);
                } else {
                    XiangJi();
                }
                // 从相册选择
            } else if (view.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent1, IMAGE_CODE);
//                Intent intent = new Intent(this, ChoicePicActivity.class);
//                startActivityForResult(intent, IMAGE_CODE);
//                choicePhotoDialog.dismiss();
                XiangCe();
                // 取消
            } else if (view.getId() == R.id.parent || view.getId() == R.id.item_popupwindows_cancel) {
                choicePhotoDialog.dismiss();
            } else if (view.getId() == R.id.iv_1) {
                 // 跳转到百度地图
                if(isAvilible(this,"com.baidu.BaiduMap")){
                    // 跳转百度地图
                    Intent intent = new Intent(GyzXxwhActivity.this, DingWeiActivity.class);
                    startActivityForResult(intent, BD);
                    choicePhotoDialog.dismiss();

//                    Intent intent = new Intent(GyzXxwhActivity.this, BaiduMapActivity.class);
//                    startActivityForResult(intent, BD);
//                    choicePhotoDialog.dismiss();


                }else {
                    showDialogs();
                    choicePhotoDialog.dismiss();
                }


            }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GyzXxwhActivity.this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //market为路径，id为包名
                //显示手机上所有的market商店
                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                Intent intent = new Intent("android.intent.action.MAIN");
//                intent.addCategory("android.intent.category.APP_MARKET");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent,"请选择要查看的市场"));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    private ArrayList<String> urlList = new ArrayList<String>();
    private String urlStrs = "";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 20) {
//                Toast.makeText(GyzXxwhActivity.this,urlStrs + "",Toast.LENGTH_SHORT).show();
                sendPost(urlStrs); // 提交
            }
        }
    };

    // 获取图片地址
    private void sendPicPost() {

        if (filenames.size() <= 0) {
            urlStrs = "";
            Message message = new Message();
            message.arg1 = 20;
            handler.sendMessage(message);
            return;
        }
        for (int i = 0; i < filenames.size(); i++) {
            ModelHttp.postPicHttpClient(GyzXxwhActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
                @Override
                public boolean onDone(String key, String str) {
                    System.out.println(str);
                    BaseRespData tijiaoPicRespData = JSON.parseObject(str, BaseRespData.class);
                    TijiaoPicRespData tijiaoPicRespData1 = JSON.parseObject(tijiaoPicRespData.getData(), TijiaoPicRespData.class);

                    urlList.add(tijiaoPicRespData1.getFull_url());

                    if (urlList.size() == filenames.size()) {
                        for (String urlStr : urlList) {
                            urlStrs += urlStr + ",";
                        }
                        urlStrs = urlStrs.substring(0, urlStrs.length() - 1);
                        Message message = new Message();
                        message.arg1 = 20;
                        handler.sendMessage(message);
                    }

                    return false;
                }
            });

        }


    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    // 提交
    private void sendPost(String urlStrs) {

        GyzXxwhReqData gyzXxwhReqData = new GyzXxwhReqData();
        //如果经纬度都是空
        if (mLatitude != null && mLongtitude != null && urlStrs != null) {
            gyzXxwhReqData.setLat(Double.parseDouble(mLatitude));
            gyzXxwhReqData.setLng(Double.parseDouble(mLongtitude));
            gyzXxwhReqData.setUploads(urlStrs + "");
            gyzXxwhReqData.setSupply_id(Integer.parseInt(Global.supply_id));  // 供应站ID
        } else if (mLatitude == null && mLongtitude == null &&urlStrs != null){
            gyzXxwhReqData.setSupply_id(Integer.parseInt(Global.supply_id));  // 供应站ID
            gyzXxwhReqData.setUploads(urlStrs + "");
        }else if (mLatitude !=null && mLongtitude!=null && urlStrs == null){
            gyzXxwhReqData.setLat(Double.parseDouble(mLatitude));
            gyzXxwhReqData.setLng(Double.parseDouble(mLongtitude));
            gyzXxwhReqData.setSupply_id(Integer.parseInt(Global.supply_id));  // 供应站ID
        }else if (mLatitude == null && mLongtitude == null && urlStrs == null){
            gyzXxwhReqData.setSupply_id(Integer.parseInt(Global.supply_id));  // 供应站ID
        }

        loginModel.getGyzXxwh(GyzXxwhActivity.this, gyzXxwhReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                Toast.makeText(GyzXxwhActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                closeDialog();
                finish();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(GyzXxwhActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
            }
        });
    }

    // 调用相机
    private void XiangJi() {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_XIANGJI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                XiangJi();
            } else {
                Toast.makeText(GyzXxwhActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private int MY_PERMISSIONS_REQUEST_XIANGJI = 1;

    private void XiangCe() {
//        Intent intent = new Intent(this, ChoicePicActivity.class);
//        startActivityForResult(intent, IMAGE_CODE);
//        choicePhotoDialog.dismiss();

        PhotoPicker.builder()
                .setPhotoCount(9)//多选数量 单选就填1
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
        choicePhotoDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                        bitmapsChoice.clear();
                filenamesChoice.clear();
                for (String path : photos) {
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
                //                    filenames.clear();
                filenames.addAll(filenamesTake);
                filenames.addAll(filenamesChoice);
                return;
            }
        }else {
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
                    for (String path : images) {
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

            case 50:
                Bundle bundleExtra = data.getBundleExtra("bundle");
                mLatitude = bundleExtra.getString("lat");
                mLongtitude = bundleExtra.getString("long");
//                Toast.makeText(GyzXxwhActivity.this,mLatitude + ","  + mLatitude + "",Toast.LENGTH_SHORT).show();
                break;
        }

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
        bitmapsChoice.remove(position); //  移除保存的图片
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();
    }
}
