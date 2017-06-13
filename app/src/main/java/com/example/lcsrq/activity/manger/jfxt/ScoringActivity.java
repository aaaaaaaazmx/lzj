package com.example.lcsrq.activity.manger.jfxt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.SubmitjftReqData;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.TijiaoPicRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
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
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * 记分系统
 */

public class ScoringActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp{

    private RelativeLayout jflx, kfxx, jfdx;
    private LinearLayout commonLeftBtn;
    private TextView tv_zfry;
    private LinearLayout ll_ren,ll_car,ll_gyz;
    private TextView tv_jflx;
    private EditText postedContentEt;
    private TextView locationTv;
    private CustomDialog choicePhotoDialog;
    private BaseGridView gridView;
    private MyPostGridAdapter adapter;
    private LinearLayout locationLayout;


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
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;
    private String fileName;
    private AlertDialog builder;
    private TextView tv_jfxm,tv_jfdx;
    private Button btn_sure;
    private String jfid;
    private String dxid;
    private LoginModel loginModel;
    private String choise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jfxt_jf);
        loginModel = new LoginModel();

        // 选择的类型
        choise = getIntent().getStringExtra("choise");
        type = Integer.parseInt(choise);
        if(type == 1){
            tv_jflx.setText("人员");
        }
        if(type == 2){
            tv_jflx.setText("站点");
        }
        if(type == 3){
            tv_jflx.setText("车辆");
        }
    }

    @Override
    protected void addAction() {
        jflx.setOnClickListener(this);
        kfxx.setOnClickListener(this);
        jfdx.setOnClickListener(this);
        btn_sure.setOnClickListener(this);

        commonLeftBtn.setOnClickListener(this);

        postedContentEt.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(ScoringActivity.this);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(ScoringActivity.this, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(ScoringActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);
    }
    // 图片
    private int MY_PERMISSIONS_REQUEST_XIANGJI = 1;
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
                Toast.makeText(ScoringActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void findViews() {
        jflx = (RelativeLayout) findViewById(R.id.jflx);
        kfxx = (RelativeLayout) findViewById(R.id.kfxx);
        jfdx = (RelativeLayout) findViewById(R.id.jfdx);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("计分系统");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        // 执法人员
        tv_zfry = (TextView) findViewById(R.id.tv_zfry);
        tv_zfry.setText(Global.usernName + "");

        // 记分类型
        tv_jflx = (TextView) findViewById(R.id.tv_jflx);
        //  记分项目
        tv_jfxm = (TextView) findViewById(R.id.tv_jfxm);
        // 记分对象
        tv_jfdx = (TextView) findViewById(R.id.tv_jfdx);
        // 确定扣分
        btn_sure = (Button) findViewById(R.id.btn_sure);

        //  图片
        ImageGridAdapter.mSelectedImage.clear();  //清楚所有选择的图片
        postedContentEt = (EditText) findViewById(R.id.postedContentEt);
        locationTv = (TextView) findViewById(R.id.locationTv);

        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindCameraLayout(this);

        //图片列表
        gridView = (BaseGridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter(ScoringActivity.this, bitmaps, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
    }
    // 记分类型
    private  int type =  -1;

    @Override
    public void onClick(View v) {
        // 计分类型s
        if (v.getId() == R.id.jflx) {
            LayoutInflater inflaterDl = LayoutInflater.from(this);
            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.scro_jflx_dialog, null);
            builder = new AlertDialog.Builder(ScoringActivity.this).create();
            builder.show();
            builder.getWindow().setContentView(layout);
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); //设置宽度
            lp.height = (int) display.getHeight() / 3;
            builder.getWindow().setAttributes(lp);

            ll_ren = (LinearLayout) layout.findViewById(R.id.ll_ren);
            ll_car = (LinearLayout) layout.findViewById(R.id.ll_car);
            ll_gyz = (LinearLayout) layout.findViewById(R.id.ll_gyz);

            // 人员查询
            ll_ren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = 1;
                    tv_jflx.setText("人员");
                    tv_jfxm.setText("");
                    jfid = "";
                    tv_jfdx.setText("");
                    dxid = "";
                    builder.dismiss();
                }
            });
            //  车辆查询
            ll_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = 3;
                    tv_jflx.setText("车辆");
                    tv_jfxm.setText("");
                    jfid = "";
                    tv_jfdx.setText("");
                    dxid = "";
                    builder.dismiss();
                }
            });

            // 供应站查询
            ll_gyz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = 2;
                    tv_jflx.setText("供应站");
                    tv_jfxm.setText("");
                    jfid = "";
                    tv_jfdx.setText("");
                    dxid = "";
                    builder.dismiss();
                }
            });

        }
        // 扣分选项
        else if (v.getId() == R.id.kfxx) {
            if (type == -1){
                Toast.makeText(ScoringActivity.this,"请先选择记分类型",Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intent = new Intent(this, ScroingKfxmActivity.class);
                intent.putExtra("type", type);
                startActivityForResult(intent, 100);
            }
            // 计分对象
        } else if (v.getId() == R.id.jfdx) {
            if (type == -1){
             Toast.makeText(ScoringActivity.this,"请先选择记分类型",Toast.LENGTH_SHORT).show();
             return;
            }else{
            Intent intent = new Intent(this, ScroingJfdx.class);
            intent.putExtra("type",type);
            startActivityForResult(intent, 200);
            }
        }

        // 返回按钮
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
        // p拍照
        else if (v.getId() == R.id.item_popupwindows_one) {
            // 手机被点击
            if (ContextCompat.checkSelfPermission(ScoringActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ScoringActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_XIANGJI);
            } else {
                XiangJi();
            }
            // 从相册选择
        } else if (v.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//           REQUEST_CODE
             //相册
                XiangCe();
//            Intent intent = new Intent(this, ChoicePicActivity.class);
//            startActivityForResult(intent, IMAGE_CODE);
//            choicePhotoDialog.dismiss();

        }
        // 取消
        else if (v.getId() == R.id.parent || v.getId() == R.id.item_popupwindows_cancel) {
            choicePhotoDialog.dismiss();
        }
        // 提交记分
        else if (v.getId() == R.id.btn_sure){
            showLoading("正在提交");
            sendPicPost();
        }
    }

    private  String urlStrs;
    private String imagUrl;
    Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 20){

                if (TextUtils.isEmpty(Global.uid)){
                    Toast.makeText(ScoringActivity.this,"UID必须",Toast.LENGTH_SHORT).show();
                    closeDialog();
                    return;
                }
                if (type == -1){
                    Toast.makeText(ScoringActivity.this,"记分类型必选",Toast.LENGTH_SHORT).show();
                    closeDialog();
                    return;
                }
                if (TextUtils.isEmpty(jfid)){
                    Toast.makeText(ScoringActivity.this,"记分项目必选",Toast.LENGTH_SHORT).show();
                    closeDialog();
                    return;
                }
                if (TextUtils.isEmpty(dxid)){
                    Toast.makeText(ScoringActivity.this,"记分对象必选",Toast.LENGTH_SHORT).show();
                    closeDialog();
                    return;
                }
                if (TextUtils.isEmpty(postedContentEt.getText().toString())){
                    Toast.makeText(ScoringActivity.this,"记分说明必填",Toast.LENGTH_SHORT).show();
                    closeDialog();
                    return;
                }

                SubmitjftReqData submitjftReqData = new SubmitjftReqData();
                submitjftReqData.setUid(Integer.parseInt(Global.uid));
                submitjftReqData.setType(type);
                submitjftReqData.setPid(Integer.parseInt(jfid)); // 计分项ID
                submitjftReqData.setOid(Integer.parseInt(dxid)); // 计分对象ID
                submitjftReqData.setContent(postedContentEt.getText().toString());
                submitjftReqData.setUploads(urlStrs);

                loginModel.getSubmitjft(ScoringActivity.this, submitjftReqData, new OnLoadComBackListener() {
                    @Override
                    public void onSuccess(Object msg) {
                        Toast.makeText(ScoringActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                        finish();
                        closeDialog();
                    }

                    @Override
                    public void onError(String msg) {
                     Toast.makeText(ScoringActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
                    closeDialog();
                    }
                });

            }

        }
    };
    // 上传图片
    // 提交图片上传
    private void sendPicPost() {

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
                    Message message = new Message();
                    message.arg1 = 20;
                    handler.sendMessage(message);
                    return;
//                else {
//                    Message message = new Message();
//                    message.arg1 = 30;
//                    handler.sendMessage(message);
//                    return;
//                }
            }
        }else {
            for (int i = 0; i < filenames.size(); i++) {
                ModelHttp.postPicHttpClient(ScoringActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
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
                                Message message = new Message();
                                message.arg1 = 20;
                                handler.sendMessage(message);
//                            else {
//                                Message message = new Message();
//                                message.arg1 = 30;
//                                handler.sendMessage(message);
//                            }
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
            // 记分项目返回
            Bundle bundleExtra = data.getBundleExtra("bundle");
            String kfxm = bundleExtra.getString("title");
            jfid = bundleExtra.getString("ID");
            tv_jfxm.setText(kfxm);
        }
        if (resultCode == 200){
            // 记分对象返回
            Bundle bundleExtra = data.getBundleExtra("bundle");
            String M_nickname = bundleExtra.getString("M_nickname");
            dxid = bundleExtra.getString("ID");
            tv_jfdx.setText(M_nickname);
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
}
