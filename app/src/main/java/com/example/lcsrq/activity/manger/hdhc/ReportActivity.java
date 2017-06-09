package com.example.lcsrq.activity.manger.hdhc;

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
import android.media.DrmInitData;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzXxwhActivity;
import com.example.lcsrq.activity.manger.gyzmanger.MapActivity;
import com.example.lcsrq.activity.manger.report.MyJuabaoMap;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.ContentSumbitHdhcReqData;
import com.example.lcsrq.bean.req.TijiaoPicReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.ContentGyzRegionRespData;
import com.example.lcsrq.bean.resq.TijiaoPicRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
import com.example.lcsrq.crame.PostedNewActivity;
import com.example.lcsrq.crame.StringTool;
import com.example.lcsrq.http.ModelHttp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.http.ProcessListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.BaseGridView;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;

import java.io.File;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * 黑点黑车举报页面
 * Created by 苏毅 on 2017/4/1.
 */

public class ReportActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp {
    private RelativeLayout commonRightBtn;
    private EditText postedContentEt;
    private LinearLayout locationLayout;
    private Button btn_submit;
    private TextView titleTv, commonRightText, locationTv;
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
    private ArrayList<String> urlList = new ArrayList<String>();
    private String imgurl = "";
    private int imageUploadCount = 0;
    private LinearLayout commonLeftBtn;
    private LoginModel loginModel;
    private EditText et_address, et_chr, et_dizhi;

    private RelativeLayout rl_address, rl_qxjd,rl_car;
    private Intent intent;
    private int type = -1; // 判断是黑车还是黑点
    private EditText et_carnum;
    private  String urlStrs;
    private ImageView iv_dingwei;
    private String dingweiDZ;
    private TextView et_leixing,et_diqu;
    private ImageView iv_xiala;
    private ArrayList<ContentGyzRegionRespData> reportDatas;
    private OptionsPickerView optionsPopupWindow;
    private OptionsPickerView pvOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdhc_report);
        loginModel = new LoginModel();
        intent = getIntent();
        type = intent.getIntExtra("11", -1);

        // 参数已经传过来
        if (type == 1) {  // 黑车
            rl_car.setVisibility(View.VISIBLE);
        }else {  // 黑点
            rl_car.setVisibility(View.GONE);
        }

        // 参数已经传过来
        if (type == 1) {  // 黑车
            et_leixing.setText("黑车举报");
        }else {  // 黑点
            et_leixing.setText("黑点举报");
        }

        // 进来就加载
        initData();
    }

    private void initData() {
        // 加载地区

        // 供应站地区列表
        ContentGyzRegionReqData contentGyzRegionReqData = new ContentGyzRegionReqData();
        contentGyzRegionReqData.setLevel(1);  //  返回2级列表
        loginModel.getListOfGyzRegion(ReportActivity.this, contentGyzRegionReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                reportDatas = (ArrayList<ContentGyzRegionRespData>) msg;


                // 接受成功
                Message message = new Message();
                message.arg2 = 20;
                handler.sendMessage(message);
                closeDialog();
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(ReportActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
//        commonRightBtn.setOnClickListener(this);
//        commonRightBtn.setOnClickListener(this);
        postedContentEt.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(ReportActivity.this);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(ReportActivity.this, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(ReportActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);

        iv_dingwei.setOnClickListener(this);
        iv_xiala.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        rl_address = (RelativeLayout) findViewById(R.id.rl_address); //  地址
        rl_qxjd = (RelativeLayout) findViewById(R.id.rl_qxjd); // 曲线街道
        rl_car = (RelativeLayout) findViewById(R.id.rl_car); // 车牌号
        et_carnum = (EditText) findViewById(R.id.et_carnum);  // 车牌
        et_address = (EditText) findViewById(R.id.et_address);  // 输入的区县街道
        et_dizhi = (EditText) findViewById(R.id.et_dizhi);  // 输入的区县街道
        et_chr = (EditText) findViewById(R.id.et_chr);  // 查处人

        et_leixing = (TextView) findViewById(R.id.et_leixing); //  举报类型
        et_diqu = (TextView) findViewById(R.id.et_diqu); // 举报地区
        iv_xiala = (ImageView) findViewById(R.id.iv_xiala);

        ImageGridAdapter.mSelectedImage.clear();  //清楚所有选择的图片
        titleTv = (TextView) findViewById(R.id.commonTitleTv);
        titleTv.setText("我要举报");

        commonRightText = (TextView) findViewById(R.id.commonRightText);
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

        iv_dingwei = (ImageView) findViewById(R.id.iv_dingwei);

    }
    ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
    private ArrayList<ContentGyzRegionRespData>  options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 20){
                // 提交黑车举报
//                System.out.println("lzlzlzlzlzllzllzlzl :" + urlStrs.toString());

                sendPost(type,chepaihao,address,dizhi,content,urlStrs);
            }else if (msg.arg1 == 30){
                // 提交黑点举报
//                System.out.println("lzlzlzlzlzllzllzlzl :" + urlStrs.toString());
                sendPost(type,chepaihao,address,dizhi,content,urlStrs);

            }else if (msg.arg2 == 20){
                //  条件选择器
                 options1Items  = reportDatas;
                for (int i=0;i<reportDatas.size();i++) {//遍历省份
                    ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    for (int c = 0; c < reportDatas.get(i).getChild().size(); c++) {
                        //遍历该省份的所有城市
                        String CityName = reportDatas.get(i).getChild().get(c).getName();
                        CityList.add(CityName);//添加城市
//                        Toast.makeText(ReportActivity.this,CityList.toString(),Toast.LENGTH_LONG).show();
                    }
                    options2Items.add(CityList);
                }


                //条件选择器
                pvOptions = new  OptionsPickerView.Builder(ReportActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                         String s =  options1Items.get(options1).getPickerViewText()+options2Items.get(options1).get(option2).toString();
//                        String s =  options1Items.get(options1).toString();
                        et_diqu.setText(s+"");
                        String id1 = reportDatas.get(options1).getId(); //  区域ID
                        String id2= reportDatas.get(options1).getChild().get(option2).getId();
                        areas = id1 + "," + id2;
                    }
                }).build();
                pvOptions.setPicker(options1Items,options2Items);
            }
        }


    };
    private String areas;
    private int MY_PERMISSIONS_REQUEST_XIANGJI = 1;
    private String ccr,chepaihao,content,address,dizhi;
    // 相机
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
    //相册
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_XIANGJI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                XiangJi();
            } else {
                Toast.makeText(ReportActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.commonLeftBtn) {
            finish();
        } else {
            if (view.getId() == R.id.btn_submit) {
                showLoading("正在提交");
                if (type == 1) { // 表示黑车
                    ccr = et_chr.getText().toString(); //查处人
                    chepaihao = et_carnum.getText().toString(); // 车牌号
                    content = postedContentEt.getText().toString(); // 查询内容
                    address = et_address.getText().toString();
                    dizhi = et_dizhi.getText().toString();
                    // 提交黑点黑车举报
                    // 提交图片地址
                    sendPicPost(type);
                } else {  // 表示黑点

                    // 提交黑点黑车举报
                    // 提交图片地址

                    content = postedContentEt.getText().toString(); // 查询内容
                    address = et_address.getText().toString();
                    dizhi = et_dizhi.getText().toString();
                    sendPicPost(type);
                }
            }
            // p拍照
            else if (view.getId() == R.id.item_popupwindows_one) {
                // 手机被点击
                if (ContextCompat.checkSelfPermission(ReportActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_XIANGJI);
                } else {
                    XiangJi();
                }
                // 从相册选择
            } else if (view.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//           REQUEST_CODE
                XiangCe();
//                Intent intent = new Intent(this, ChoicePicActivity.class);
//                startActivityForResult(intent, IMAGE_CODE);
//                choicePhotoDialog.dismiss();
                // 取消
            }
            else if (view.getId() == R.id.parent || view.getId() == R.id.item_popupwindows_cancel) {
                choicePhotoDialog.dismiss();
            }

            else if (view.getId() == R.id.iv_dingwei){
                //  跳转定位
//                Toast.makeText(ReportActivity.this,"dian",Toast.LENGTH_LONG).show();
                // 现在没有供应站经纬度
                // 如果安装了
                if(isAvilible(this,"com.baidu.BaiduMap")){
                    Intent intent = new Intent(ReportActivity.this, MyJuabaoMap.class);
                    startActivityForResult(intent,100);
                }else {
                    showDialog();
                    choicePhotoDialog.dismiss();
                }
            }else if (view.getId() == R.id.iv_xiala){
                //加载地区
                pvOptions.show();
            }
        }
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
    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
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
    private String imagUrl;
    // 提交图片上传
    private void sendPicPost(final int type) {

        // 如果添加了新的照片,就清除之前的集合  表示添加了图片或者删除了图片
        if (filenames.size() != urlList.size()){
            urlList.clear();
            urlStrs="";
        }

        if (filenames.size() <=0){
            urlStrs = "";
            Message message = new Message();
            message.arg1 = 20;
            handler.sendMessage(message);
            return;
        }

        if (!TextUtils.isEmpty(imagUrl)  && urlList.size() == filenames.size()) {
            if (imagUrl.equals(urlStrs)) {
                if (type == 1) {
                    Message message = new Message();
                    message.arg1 = 20;
                    handler.sendMessage(message);
                    return;
                } else {
                    Message message = new Message();
                    message.arg1 = 30;
                    handler.sendMessage(message);
                    return;
                }
            }
        }else {
            for (int i = 0; i < filenames.size(); i++) {
                ModelHttp.postPicHttpClient(ReportActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
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
                            imagUrl = urlStrs;
                            if (type == 1) {
                                Message message = new Message();
                                message.arg1 = 20;
                                handler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.arg1 = 30;
                                handler.sendMessage(message);
                            }
                        }
                        return true;
                    }
                });
            }
        }
//                for (int i = 0; i < filenames.size(); i++) {
//                    ModelHttp.postPicHttpClient(ReportActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
//                        @Override
//                        public boolean onDone(String key, String str) {
//                            System.out.println(str);
//                            BaseRespData tijiaoPicRespData = JSON.parseObject(str, BaseRespData.class);
//                            TijiaoPicRespData tijiaoPicRespData1 = JSON.parseObject(tijiaoPicRespData.getData(), TijiaoPicRespData.class);
//                            urlList.add(tijiaoPicRespData1.getFull_url());
//                            if (urlList.size() == filenames.size()) {
//                                for (String urlStr : urlList) {
//                                    urlStrs += urlStr + ",";
//                                }
//                                urlStrs = urlStrs.substring(0, urlStrs.length() - 1);
//                                imagUrl = urlStrs;
//                                if (type == 1) {
//                                    Message message = new Message();
//                                    message.arg1 = 20;
//                                    handler.sendMessage(message);
//                                } else {
//                                    Message message = new Message();
//                                    message.arg1 = 30;
//                                    handler.sendMessage(message);
//                                }
//                            }
//
//                            return true;
//                        }
//                    });
//                }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
//                    bitmapsChoice.clear();
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
//                    filenames.clear();
                        filenames.addAll(filenamesTake);
                        filenames.addAll(filenamesChoice);
                    }

                    break;
                case LOCATION_CODE:
                    if (resultCode == this.RESULT_OK && data != null) {
                        location = data.getStringExtra("location");
                        if (com.example.lcsrq.utils.StringTool.isNotNull(location)) {
                            locationTv.setText(location);
                        } else {
                            locationTv.setText("所在的位置");
                        }
                    }
                    break;
            }
        }

        if (resultCode == 100){
            Bundle bundleExtra = data.getBundleExtra("bundle");
            dingweiDZ = bundleExtra.getString("address");
            String substring = dingweiDZ.substring(2, dingweiDZ.length());
            et_dizhi.setText(substring + "");
        }
    }

    @Override
    public void onResume() {
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
        bitmapsChoice.remove(position);
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();

    }

    // 黑车举报

    public void sendPost(int type, String et_carnum, String address, String dizhi, String content, String urlStrs) {
        if (type == 1){
            // 黑车举报
            ContentSumbitHdhcReqData contentSumbitHdhcReqData = new ContentSumbitHdhcReqData();
            contentSumbitHdhcReqData.setUid(Integer.parseInt(Global.uid));
            contentSumbitHdhcReqData.setType(type); // 类型
            contentSumbitHdhcReqData.setCart_number(et_carnum); //  车牌号码
            contentSumbitHdhcReqData.setAreas(et_diqu.getText().toString()); // 区县
            contentSumbitHdhcReqData.setAddress(et_dizhi.getText().toString()); //  地址
            contentSumbitHdhcReqData.setContent(content); // 内容
            contentSumbitHdhcReqData.setUploads(urlStrs); //  图片地址
            contentSumbitHdhcReqData.setAreasid(areas); //  区域ID
            loginModel.getSubmitHdhc(ReportActivity.this, contentSumbitHdhcReqData, new OnLoadComBackListener() {
                        @Override
                        public void onSuccess(Object msg) {
                            Toast.makeText(ReportActivity.this, "黑车提交成功", Toast.LENGTH_LONG).show();
                            closeDialog();
                            finish();
                        }
                        @Override
                        public void onError(String msg) {
                            Toast.makeText(ReportActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                            closeDialog();

                        }
                    }
            );
        }else {
            //  黑点举报
            ContentSumbitHdhcReqData contentSumbitHdhcReqData = new ContentSumbitHdhcReqData();
            contentSumbitHdhcReqData.setUid(Integer.parseInt(Global.uid)); // 用户ID
            contentSumbitHdhcReqData.setType(type);// 类型
            contentSumbitHdhcReqData.setAreas(et_diqu.getText().toString()); // 区县
            contentSumbitHdhcReqData.setAddress(et_dizhi.getText().toString()); //  地址
            contentSumbitHdhcReqData.setContent(content);// 内容
            contentSumbitHdhcReqData.setUploads(urlStrs);// 图片地址
            contentSumbitHdhcReqData.setAreasid(areas); //  区域ID
            loginModel.getSubmitHdhc(ReportActivity.this, contentSumbitHdhcReqData, new OnLoadComBackListener() {
                        @Override
                        public void onSuccess(Object msg) {
                            Toast.makeText(ReportActivity.this, "黑点提交成功", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        @Override
                        public void onError(String msg) {
                            Toast.makeText(ReportActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }


    }
}
