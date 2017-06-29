package com.example.lcsrq.activity.manger.hdhc;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.InputTextActivity;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzXxwhActivity;
import com.example.lcsrq.activity.manger.training.ChoiseQsActivity;
import com.example.lcsrq.adapter.CcDwAdapter;
import com.example.lcsrq.adapter.CcrAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentSumbitHdhcReqData;
import com.example.lcsrq.bean.req.HdhcChacReqData;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.TijiaoPicRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
import com.example.lcsrq.crame.PostedNewActivity;
import com.example.lcsrq.crame.StringTool;
import com.example.lcsrq.http.ModelHttp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.http.ProcessListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DensityUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.BaseGridView;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;

import java.io.File;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

import static com.example.lcsrq.R.id.cancleTv;
import static com.example.lcsrq.R.id.start;

/**
 * 黑点黑车举报页面
 * Created by 苏毅 on 2017/4/1.
 */

public class HdhcCheckActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp {
    private RelativeLayout commonRightBtn;
    private EditText postedContentEt;
    private LinearLayout locationLayout;
    private TextView titleTv, commonRightText, locationTv;
    private LinearLayout commonLeftBtn;
    private int type = -1;  // 1-黑点  2黑车

    private EditText tipsTv;
    private EditText tv_ccr, tv_dw, tv_fzr, tv_sfz, tv_phone, tv_qy, tv_sp, tv_kp, tv_cp, tv_bz,tv_chulifangshi,et_ccdw;

    // 显示图片
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;
    private BaseGridView gridView;
    private MyPostGridAdapter adapter;

    private String fileName;

    //弹窗
    private CustomDialog choicePhotoDialog, input;

    //    private FirstModel firstModel = new FirstModel();
    private String location;
    private ArrayList<File> filenames = new ArrayList<File>();
    private ArrayList<File> filenamesTake = new ArrayList<File>();
    private ArrayList<String> urlList = new ArrayList<String>();
    private ArrayList<File> filenamesChoice = new ArrayList<File>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsTake = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsChoice = new ArrayList<Bitmap>();
    private String imgurl = "";
    private int imageUploadCount = 0;

    private ListView lv_ccr, lv_ccdw;
    private RelativeLayout rl_car;
    private String urlStrs = "";
    private String fzr, sfz, phone, qy, sp, kp, cp, bz,chachufangshi;

    private int dd;
    private LoginModel loginModel;
    private String jb_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdhc_check);
        loginModel = new LoginModel();

        // 跳传查出页面
        Intent intent = getIntent();
        type = intent.getIntExtra("11", -1);
        jb_id = intent.getStringExtra("jb_id");
        Toast.makeText(HdhcCheckActivity.this,jb_id+ "",Toast.LENGTH_SHORT).show();

        if (type == 1) {  // 表示黑车
            rl_car.setVisibility(View.VISIBLE);
        } else if (type == 2){
            rl_car.setVisibility(View.GONE);
        }
     }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        commonRightBtn.setOnClickListener(this);
        postedContentEt.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 点击查看大图
                if (adapter.getCount() -1 == bitmaps.size() && bitmaps.size() > 0){
                    if (arg2 == bitmaps.size()){
                        UiTool.setDialog(HdhcCheckActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                        adapter.notifyDataSetChanged();
                    }else {
                        UiTool.showPic(HdhcCheckActivity.this,bitmaps.get(arg2));
                        adapter.notifyDataSetChanged();
                    }
                }

                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(HdhcCheckActivity.this);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(HdhcCheckActivity.this, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(HdhcCheckActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);
    }

    private CcrAdapter ccrAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();  // 查处人
    private ArrayList<String> ccdw = new ArrayList<>();  // 查处单位
    private CcDwAdapter ccDwAdapter;
    private String ccrtext = "";
    private String ccdwtext = "";
    private int weizhi = -1;
    @Override
    protected void findViews() {
        rl_car = (RelativeLayout) findViewById(R.id.rl_car);
        lv_ccr = (ListView) findViewById(R.id.lv_ccr);  //查处人
        ccrAdapter = new CcrAdapter(HdhcCheckActivity.this, new CcrAdapter.OnAddOrdelClick() {
            @Override
            public void onCcClick(int position,ImageView imageView) {
                if (position == 0) {
                    arrayList.add("");
                    ccrAdapter.setArrayList(arrayList);
                    ccrAdapter.notifyDataSetChanged();
                } else {
                    arrayList.remove(position);
                    ccrAdapter.setArrayList(arrayList);
                    ccrAdapter.notifyDataSetChanged();
                }
                // 重画
                LinearLayout.LayoutParams para1;
                para1 = (LinearLayout.LayoutParams) lv_ccr.getLayoutParams();
                para1.height = DensityUtil.dip2px(HdhcCheckActivity.this, 50.0f * arrayList.size());
                lv_ccr.setLayoutParams(para1);
            }

            @Override
            public void onTextClick(final int position) {
                weizhi = position;
                // 跳转联系人
                Intent intent = new Intent(HdhcCheckActivity.this, MycontactActivity.class);
                intent.putExtra("name",0);
                startActivityForResult(intent,100);
            }
        });

        lv_ccr.setAdapter(ccrAdapter);
        arrayList.add(Global.usernName);
        ccrAdapter.setArrayList(arrayList);
        ccrAdapter.notifyDataSetChanged();
    // 查处单位
        lv_ccdw = (ListView) findViewById(R.id.lv_ccdw);
        ccDwAdapter = new CcDwAdapter(HdhcCheckActivity.this, new CcDwAdapter.OnAddOrdelClick() {
            @Override
            public void onCcClick(int position) {
                if (position == 0) {
                    ccdw.add("");
                    ccDwAdapter.setArrayList(ccdw);
                    ccDwAdapter.notifyDataSetChanged();
                } else {
                    ccdw.remove(position);
                    ccDwAdapter.setArrayList(ccdw);
                    ccDwAdapter.notifyDataSetChanged();
                }
                LinearLayout.LayoutParams para1;
                para1 = (LinearLayout.LayoutParams) lv_ccdw.getLayoutParams();
                para1.height = DensityUtil.dip2px(HdhcCheckActivity.this, 50.0f * ccdw.size());
                lv_ccdw.setLayoutParams(para1);

            }

            @Override
            public void onTextClick(final int position) {
//                Intent intent = new Intent(HdhcCheckActivity.this, InputTextActivity.class);
//                intent.putExtra("num", position);
//                startActivityForResult(intent, 10);

                InputMethodManager imm = (InputMethodManager) getSystemService(HdhcCheckActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(HdhcCheckActivity.this, R.style.Theme_Transparent)).create();
                dialog.setView(LayoutInflater.from(HdhcCheckActivity.this).inflate(R.layout.edittext_dialog, null), 0, 0, 0, 0);
                UiTool.setDialog(HdhcCheckActivity.this, dialog, Gravity.CENTER, -1, 0.9, -1); //弹出Dialog

                TextView okTv = (TextView) dialog.findViewById(R.id.okTv);
                TextView cancleTv = (TextView) dialog.findViewById(R.id.cancleTv);
                final EditText tipsTv2 = (EditText) dialog.findViewById(R.id.tipsTv1);
                okTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ccdwtext = tipsTv2.getText().toString();
                        ccdw.set(position, ccdwtext);
                        ccDwAdapter.setArrayList(ccdw);
                        ccDwAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                cancleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        lv_ccdw.setAdapter(ccDwAdapter);
        ccdw.add("");
        ccDwAdapter.setArrayList(ccdw);
        ccDwAdapter.notifyDataSetChanged();

        ImageGridAdapter.mSelectedImage.clear();
        titleTv = (TextView) findViewById(R.id.commonTitleTv);
//        titleTv.setText("黑点/黑车查处");
        titleTv.setText("打非治违查处");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("提交");
        commonRightText.setBackgroundResource(R.drawable.corner_toumingbg_greenborder_2dp);

        postedContentEt = (EditText) findViewById(R.id.postedContentEt);
        locationTv = (TextView) findViewById(R.id.locationTv);

        // 相册弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindCameraLayout(this);

        // 输入框弹窗
//        input = new CustomDialog(this);
//        input.bindEdittextLayout(this);
//        tipsTv = (EditText) input.findViewById(R.id.tipsTv);

//        //图片列表
        gridView = (BaseGridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter(this, bitmaps, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        locationLayout = (LinearLayout) findViewById(R.id.locationLayout);

        // 查找控件
//        tv_ccr = (EditText) findViewById(R.id.tv_ccr);
//        tv_dw = (EditText) findViewById(R.id.tv_dw);
        tv_fzr = (EditText) findViewById(R.id.tv_fzr);
        tv_sfz = (EditText) findViewById(R.id.tv_sfz);
        tv_phone = (EditText) findViewById(R.id.tv_phone);
        tv_qy = (EditText) findViewById(R.id.tv_qy);
        tv_sp = (EditText) findViewById(R.id.tv_sp);
        tv_kp = (EditText) findViewById(R.id.tv_kp);
        tv_cp = (EditText) findViewById(R.id.tv_cp);
        tv_bz = (EditText) findViewById(R.id.tv_bz);
        // 处理方式
        tv_chulifangshi = (EditText) findViewById(R.id.tv_chulifangshi);
        // 查处单位
        et_ccdw = (EditText) findViewById(R.id.et_ccdw);
        //  写入自带的单位
        et_ccdw.setText(Global.My_dw);
        et_ccdw.setFocusable(false);
    }

    String ccr = "";
    String ccdwname = "";

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.commonLeftBtn) {
            finish();
        } else if (view.getId() == R.id.commonRightBtn) {
            ccdwname = "";

            if (arrayList == null || arrayList.size() == 0) {
                return;
            }



        Toast.makeText(HdhcCheckActivity.this,ccr + "",Toast.LENGTH_SHORT).show();
            if (ccdw == null || ccdw.size() == 0) {
                return;
            }

            for (int i = 0; i < ccdw.size(); i++) {
                if (ccdw.get(i) != null && !ccdw.get(i).equals("")) {
                    if (ccdwname.equals("")) {
                        ccdwname = ccdw.get(i);
                    } else {
                        ccdwname = ccdwname + "," + ccdw.get(i);
                    }
                }
            }

            /**
             * tv_fzr = (EditText) findViewById(R.id.tv_fzr);
             tv_sfz = (EditText) findViewById(R.id.tv_sfz);
             tv_phone = (EditText) findViewById(R.id.tv_phone);
             tv_qy = (EditText) findViewById(R.id.tv_qy);
             tv_sp = (EditText) findViewById(R.id.tv_sp);
             tv_kp = (EditText) findViewById(R.id.tv_kp);
             tv_cp = (EditText) findViewById(R.id.tv_cp);
             tv_bz = (EditText) findViewById(R.id.tv_bz);
             */
            fzr = tv_fzr.getText().toString();
            sfz = tv_sfz.getText().toString();
            phone = tv_phone.getText().toString();
            qy = tv_qy.getText().toString();
            sp = tv_sp.getText().toString();
            kp = tv_kp.getText().toString();
            cp = tv_cp.getText().toString();
            bz = tv_bz.getText().toString();
            chachufangshi = tv_chulifangshi.getText().toString();
            // 提交照片并返回地址
//            sendPic(type);
            sendPicPost(type);
//            Toast.makeText(HdhcCheckActivity.this, "dianjile", Toast.LENGTH_LONG).show();
//            private  EditText tv_ccr,tv_dw,tv_fzr,tv_sfz,tv_phone,tv_qy,tv_sp,tv_kp,tv_cp,tv_bz;


            // 上传..........   提交黑点黑车查处  缺少jb_id
        } else if (view.getId() == R.id.okTv) {
            String s = tipsTv.getText().toString();
            input.dismiss();
        }
//        else if (view.getId() == cancleTv || view.getId() == R.id.parent) {
//            input.dismiss();
//        }
        // p拍照
        else if (view.getId() == R.id.item_popupwindows_one) {
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

            // 跳转到相册页面
//            Intent intent = new Intent(this, ChoicePicActivity.class);
//            startActivityForResult(intent, IMAGE_CODE);
//            choicePhotoDialog.dismiss();

            // 跳转到相册页面
            XiangCe();
        }
        else if (view.getId() == R.id.item_popupwindows_cancel) {
            choicePhotoDialog.dismiss();
        }
        else if (view.getId() == R.id.parent ){
            choicePhotoDialog.dismiss();
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 20) { // 黑车
               // Toast.makeText(HdhcCheckActivity.this,urlStrs.toString(),Toast.LENGTH_SHORT).show();
                sendHdHc(ccr, ccdwname, fzr, sfz, phone, qy, sp, kp, cp, bz, urlStrs,chachufangshi);
            } else if (msg.arg1 == 30) {  // 黑点
               // Toast.makeText(HdhcCheckActivity.this,urlStrs.toString(),Toast.LENGTH_SHORT).show();
                sendHdHc(ccr, ccdwname, fzr, sfz, phone, qy, sp, kp, cp, bz, urlStrs,chachufangshi);
            }
        }
    };
    private String imagUrl ;
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
                ModelHttp.postPicHttpClient(HdhcCheckActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
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
        }else switch (requestCode) {
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

//            case 0:
//                if (resultCode == RESULT_OK) {
//                    int p = data.getIntExtra("position", -1);
//                    String name = data.getStringExtra("ccr");
//                    arrayList.set(p, name);
//                    ccrAdapter.setArrayList(arrayList);
//                    ccrAdapter.notifyDataSetChanged();
//                }
//                break;
//
//            case 10:
//                if (resultCode == 1) {
//                    int o = data.getIntExtra("position1", -1);
//                    String name = data.getStringExtra("ccdw");
//                    ccdw.set(o, name);
//                    ccDwAdapter.setArrayList(ccdw);
//                    ccDwAdapter.notifyDataSetChanged();
//                }
//                break;

            case 100:
                Bundle bundleExtra = data.getBundleExtra("bundle");
                ccrtext = bundleExtra.getString("name");

                arrayList.set(weizhi, ccrtext);
                ccrAdapter.setArrayList(arrayList);
                ccrAdapter.notifyDataSetChanged();
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
        if (bitmapsChoice.size()!=0) {
            bitmapsChoice.remove(position);
        }
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void sendHdHc(String s, String ccr, String fzr, String sfz, String phone, String qy, String sp, String kp, String cp, String bz, String urlStrs, String chachufangshi) {

        HdhcChacReqData reqData = new HdhcChacReqData();

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null && !arrayList.get(i).equals("")) {
                if (ccr.equals("")) {
                    ccr = arrayList.get(i);
                } else {
                    ccr = ccr + "," + arrayList.get(i);
                }
            }
        }

        s = et_ccdw.getText().toString(); // 查处单位
        Toast.makeText(HdhcCheckActivity.this, s + "",Toast.LENGTH_SHORT).show();
        reqData.setUid(Integer.parseInt(Global.uid));
        reqData.setJb_id(Integer.parseInt(jb_id));  // 举报ID
        reqData.setMan(ccr);
        reqData.setDw(s);
        reqData.setData_man(fzr);
        reqData.setData_code(sfz);
        reqData.setData_tel(phone);
        reqData.setData_qy(qy);
        reqData.setData_cp(cp);
        reqData.setData_sp(sp);
        reqData.setData_kp(kp);
        reqData.setData_remark(bz);
        reqData.setUploads(urlStrs);
      //  Toast.makeText(HdhcCheckActivity.this,ccr + "",Toast.LENGTH_SHORT).show();

        // 查出方式
        reqData.setData_method(chachufangshi);

        loginModel.getsubmithdhcCc(this, reqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                Toast.makeText(HdhcCheckActivity.this, "查处成功!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(HdhcCheckActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
