package com.example.lcsrq.activity.manger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.xiangce.UiTool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by 苏毅 on 2017/4/26.
 */

public class SplashActivity extends BaseActivity {

    private ImageView iv_splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }
        ).start();
    }

    //设置动画
    private void setAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        iv_splash.startAnimation(alphaAnimation);


        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void addAction() {

    }

    @Override
    protected void findViews() {
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        setAnimation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            UiTool.getScreenConfig(this);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
