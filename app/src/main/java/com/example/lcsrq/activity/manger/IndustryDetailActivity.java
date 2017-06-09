package com.example.lcsrq.activity.manger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.JSKit;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.xxcx.MessageIntentActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.resq.PicRespData;
import com.example.lcsrq.bean.resq.contentPicRespData;
import com.example.lcsrq.fragment.FirstFragment;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by 苏毅 on 2017/4/5.
 */

public class IndustryDetailActivity extends BaseActivity {
    private ProgressWebView mWebView;
    private LinearLayout commonLeftBtn;
    private int flags = -1; //  默认为负1  判断跳转过来的是哪个动态
    private String qxdid, hydid;

    private TextView commonTitleTv, tv_title, tv_data, tv_content;
    private ImageView iv_img;

    private LoginModel loginModel;
    private Intent intent;
    private contentPicRespData hyDetail, qxDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstment_detail);
//        loginModel = new LoginModel();
        intent = getIntent();

        qxdid = intent.getStringExtra("qxdid");
        hydid = intent.getStringExtra("hydid");
        flags = intent.getIntExtra("flag1", -1);

        if (flags == 0) {
            // 区县动态
            initQxData();
//            paresQxData();
        } else if (flags ==1) {
            // 行业动态
            paresHyData();
        }
    }

    private void paresHyData() {
//        //实例化js对象
//        js = new JSKit(this);
//        //设置参数
//        mWebView.getSettings().setBuiltInZoomControls(true);
//
//        //内容的渲染需要webviewChromClient去实现，设置webviewChromClient基类，解决js中alert不弹出的问题和其他内容渲染问题
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        //把js绑定到全局的myjs上，myjs的作用域是全局的，初始化后可随处使用
//        mWebView.addJavascriptInterface(js, "myjs");

        mWebView.loadUrl("file:///android_asset/new.html");


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用 HTML 中的javaScript 函数
                mWebView.loadUrl("javascript:urls(" + hydid + ")");
            }
        },500);
    }

   private  Handler mHandler = new Handler();

    private void initQxData() {
//        //实例化js对象
//        js = new JSKit(this);
//        //设置参数
//        mWebView.getSettings().setBuiltInZoomControls(true);
//
//        //内容的渲染需要webviewChromClient去实现，设置webviewChromClient基类，解决js中alert不弹出的问题和其他内容渲染问题
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        //把js绑定到全局的myjs上，myjs的作用域是全局的，初始化后可随处使用
//        mWebView.addJavascriptInterface(js, "myjs");

        mWebView.loadUrl("file:///android_asset/new.html");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用 HTML 中的javaScript 函数
                mWebView.loadUrl("javascript:urls(" + qxdid + ")");
            }
        },500);
    }


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.arg1 == 1) {
//                // hangyd
//                BitmapUtils utils = new BitmapUtils(IndustryDetailActivity.this);
//                utils.display(iv_img, hyDetail.getUpload_path());
//                tv_content.setText(Html.fromHtml(hyDetail.getContent()));
//                tv_data.setText(hyDetail.getCreat_at());
//                tv_title.setText(hyDetail.getTitle());
//                commonTitleTv.setText(hyDetail.getCname());
//            } else if (msg.arg2 == 2) {
//                // 区县
//
//                BitmapUtils utils = new BitmapUtils(IndustryDetailActivity.this);
//                utils.display(iv_img, qxDetail.getUpload_path());
//                tv_content.setText(Html.fromHtml(qxDetail.getContent()));
//                tv_data.setText(qxDetail.getCreat_at());
//                tv_title.setText(qxDetail.getTitle());
//                commonTitleTv.setText(qxDetail.getCname());
//            }
//        }
//    };

    // 行业动态
//    private void paresHyData() {
//        ContentPicReqData contentPicReqData = new ContentPicReqData();
//        contentPicReqData.setDid(Integer.parseInt(hydid));
//        loginModel.getcontentshow(IndustryDetailActivity.this, contentPicReqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                hyDetail = JSON.parseObject((String) msg, contentPicRespData.class);
//                Message message = handler.obtainMessage();
//                message.arg1 = 1;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(IndustryDetailActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    // 区县动态
//    private void paresQxData() {
//        ContentPicReqData contentPicReqData = new ContentPicReqData();
//        contentPicReqData.setDid(Integer.parseInt(qxdid));
//        loginModel.getcontentshow(IndustryDetailActivity.this, contentPicReqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                qxDetail = JSON.parseObject((String) msg, contentPicRespData.class);
//                Message message = handler.obtainMessage();
//                message.arg2 = 2;
//                handler.sendMessage(message);
//
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(IndustryDetailActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
//            }
//        });

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    private  JSKit js;
    @Override
    protected void findViews() {

        //webView
        mWebView = (ProgressWebView) findViewById(R.id.wb_view);
//        //设置可以支持缩放
//        mWebView.getSettings().setSupportZoom(true);
//
//        //自适应屏幕
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
//        //扩大比例的缩放
//        mWebView.getSettings().setUseWideViewPort(true);
//
        // 设置字体大小
        WebSettings settings = mWebView.getSettings();
//        settings.setTextSize(WebSettings.TextSize.SMALLER);
//
//        mWebView.getSettings().setBuiltInZoomControls(true);


        settings.setJavaScriptEnabled(true);// 表示支持js
//        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
//        settings.setUseWideViewPort(true);// 支持双击缩放

//        mWebView.setWebViewClient(new WebViewClient() {
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


        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("区县动态");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

//        iv_img = (ImageView) findViewById(R.id.iv_img);
//        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_data = (TextView) findViewById(R.id.tv_data);
//        tv_content = (TextView) findViewById(R.id.tv_content);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
    }
}
