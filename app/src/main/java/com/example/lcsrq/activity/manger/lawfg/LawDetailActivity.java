package com.example.lcsrq.activity.manger.lawfg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.bean.resq.PicRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class LawDetailActivity extends BaseActivity {
    private LoginModel loginModel;
    private ArrayList<ContentRespData> flfgList = new ArrayList<ContentRespData>();
    private String did;
    private TextView tv_content, tv_date, tv_title;
    private ImageView iv_pics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_lawinfo);
        loginModel = new LoginModel();
        Intent intent = getIntent();
        did = intent.getStringExtra("did");
//        initData();
    }

//    private void initData() {
//        // 列表图片
//        ContentPicReqData contentPicReqData = new ContentPicReqData();
//        contentPicReqData.setDid(Integer.parseInt(did));
//
//        loginModel.getListOfPic(LawDetailActivity.this, contentPicReqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                PicRespData picRespData = JSON.parseObject((String) msg, PicRespData.class);
//                //http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png
//                BitmapUtils utils = new BitmapUtils(LawDetailActivity.this);
//                utils.display(iv_pics, picRespData.getUpload_path());
//                tv_content.setText(picRespData.getContent());
//                tv_date.setText(picRespData.getCreat_at());
//                tv_title.setText(picRespData.getTitle());
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(LawDetailActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private ProgressWebView wb_view;
    private LinearLayout commonLeftBtn;

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setVisibility(View.INVISIBLE);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);


        wb_view = (ProgressWebView) findViewById(R.id.wb_view);

        WebSettings settings = wb_view.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
//        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
//        settings.setUseWideViewPort(true);// 支持双击缩放
//
//        wb_view.setWebViewClient(new WebViewClient() {
//
//            /**
//             * 网页开始加载
//             */
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                System.out.println("网页开始加载");
//                showLoading("正在加载");
//            }
//
//            /**
//             * 网页加载结束
//             */
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                System.out.println("网页开始结束");
//                closeDialog();
//            }
//
//
//
//            /**
//             * 所有跳转的链接都会在此方法中回调
//             */
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // tel:110
//                System.out.println("跳转url:" + url);
//                view.loadUrl(url);
//                return true;
//            }
//        });

        wb_view.loadUrl("file:///android_asset/new.html");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用 HTML 中的javaScript 函数
                wb_view.loadUrl("javascript:urls(" + did + ")");
            }
        },500);
    }
    private Handler mHandler = new Handler();
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
    }
}
