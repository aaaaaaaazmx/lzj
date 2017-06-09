package com.example.lcsrq.activity.manger.cxjl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.ProgressWebView;
import com.example.lcsrq.adapter.CxjlDzgAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.resq.CxjlInfoRespDatas;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by 苏毅 on 2017/4/1.
 * c诚信信息
 */

public class CxInfoActivity extends BaseActivity {
    private ImageView left_btn,iv_img;
    private TextView tv_title,tv_data,tv_content;
    private LinearLayout commonLeftBtn;
    private String did;
    private LoginModel loginModel;
    private CxjlInfoRespDatas cxjlInfoRespDatas;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxjl_cxinfo);
         did = getIntent().getStringExtra("did"); //  诚信记录 // 首页轮播图
//         did = getIntent().getStringExtra("yemianDID");
//        Toast.makeText(CxInfoActivity.this,title+"",Toast.LENGTH_SHORT).show();

//        loginModel = new LoginModel();
//        initData();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg.arg1 == 1){
//                tv_content.setText(cxjlInfoRespDatas.getContent());
//                tv_title.setText(cxjlInfoRespDatas.getTitle());
//                tv_data.setText(cxjlInfoRespDatas.getCreat_at());
//                BitmapUtils utils = new BitmapUtils(CxInfoActivity.this);
//                utils.display(iv_img,cxjlInfoRespDatas.getUpload_path());
//            }
        }
    };

//    private void initData() {
//        ContentPicReqData contentPicReqData = new ContentPicReqData();
//        contentPicReqData.setDid(Integer.parseInt(did));
//        loginModel.getCxjl(CxInfoActivity.this, contentPicReqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                 cxjlInfoRespDatas = JSON.parseObject((String) msg, CxjlInfoRespDatas.class);
//                Message message = new Message();
//                message.arg1 = 1;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(CxInfoActivity.this,msg.toString(),Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private ProgressWebView wb_view;
    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        //获得传过来的tiile
        title = getIntent().getStringExtra("title");
        commonTitleTv.setText(title); // 设置标题
        commonTitleTv.setVisibility(View.VISIBLE);
        wb_view = (ProgressWebView) findViewById(R.id.wb_view);
        WebSettings settings = wb_view.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
        wb_view.loadUrl("file:///android_asset/new.html");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用 HTML 中的javaScript 函数
                wb_view.loadUrl("javascript:urls(" + did + ")");
            }
        },500);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
    }
}
