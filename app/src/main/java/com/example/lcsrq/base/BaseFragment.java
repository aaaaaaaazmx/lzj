package com.example.lcsrq.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.xiangce.UiTool;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 苏毅 on 2017/3/29.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private CustomDialog loadingDialog;
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MobclickAgent.setDebugMode(true);
//        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
//        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
//        MobclickAgent.openActivityDurationTrack(false);
//        // MobclickAgent.setAutoLocation(true);
//        // MobclickAgent.setSessionContinueMillis(1000);
//        // MobclickAgent.startWithConfigure(
//        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
//        // EScenarioType.E_UM_NORMAL));
//        MobclickAgent.setScenarioType(getActivity(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        loadingDialog = new CustomDialog(getActivity());
    };
    public void showLoading(String text) {
        loadingDialog.bindLoadingLayout(text);
        UiTool.setDialog(getActivity(), loadingDialog, Gravity.CENTER, -1, 1, -1);
    }
    public void closeDialog() {
        loadingDialog.dismiss();
    }

    public abstract void findViews(View root);

    public abstract void addAction();

    @Override
    public abstract void onClick(View v);

    public abstract void call(int callID, Object... args);
    public void refresh(){}
    String mPageName = "HomeActivity";
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
    }
}
