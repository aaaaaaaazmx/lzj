package com.example.lcsrq.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.LoginActivity;
import com.example.lcsrq.activity.manger.SplashActivity;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.utils.SystemBarTintManager;
import com.example.lcsrq.xiangce.UiTool;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 苏毅 on 2017/3/29.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private CustomDialog loadingDialog;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        if (this instanceof LoginActivity || this instanceof SplashActivity ) {
            tintManager.setStatusBarTintResource(R.color.touming1);
        } else {
            tintManager.setStatusBarTintResource(R.color.title_bg);
        }

        // 友盟统计
        MobclickAgent.openActivityDurationTrack(false); // 不统计Activity
        MobclickAgent.setCatchUncaughtExceptions(true); // 手动打开
        MobclickAgent.setDebugMode(true);

        // 间隔时间
        // MobclickAgent.setSessionContinueMillis(3000);
        loadingDialog = new CustomDialog(this);
        findViews();
        addAction();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    // 页面按下事件


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (this instanceof HomeActivity ) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    // 添加点击事件
    protected abstract void addAction();
    // 初始化布局
    protected abstract void findViews();

    public void showLoading(String text) {
        loadingDialog.bindLoadingLayout(text);
        UiTool.setDialog(this, loadingDialog, Gravity.CENTER, -1, 1, -1);
    }
    public void closeDialog() {
        loadingDialog.dismiss();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
