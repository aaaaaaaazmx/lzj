package com.example.lcsrq.crame;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 苏毅 on 2017/4/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Fresco
        Fresco.initialize(getApplicationContext());
    }
}
