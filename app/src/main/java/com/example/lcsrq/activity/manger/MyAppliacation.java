package com.example.lcsrq.activity.manger;

import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.fragment.FirstFragment;
import com.example.lcsrq.utils.CrashHandler;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.imagepipeline.producers.DownsampleUtil;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;
import com.xiaochao.lcrapiddeveloplibrary.Exception.core.Recovery;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 苏毅 on 2017/4/3.
 */

public class MyAppliacation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Fresco

        Fresco.initialize(getApplicationContext());

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //  异常管理
        CrashHandler handler = new CrashHandler();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
}
