package com.example.lcsrq.activity.manger.report;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/5/7.
 */

public class DialogJubao extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_jubao);
    }

    @Override
    protected void addAction() {

    }
    private AlertDialog builder;
    private TextView dialog_simple_sure;
    private TextView dialog_simple_cancel;
    @Override
    protected void findViews() {

        LayoutInflater inflaterDl = LayoutInflater.from(DialogJubao.this);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.hdhc_jianjie, null);
        builder = new AlertDialog.Builder(DialogJubao.this).create();
        builder.show();
        builder.setCancelable(false);
        builder.getWindow().setContentView(layout);

        WindowManager windowManager = DialogJubao.this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()) * 4 / 5; //设置宽度
        lp.height = (int) ((int) (display.getHeight()) * 0.6);
        builder.getWindow().setAttributes(lp);

        dialog_simple_sure = (TextView) layout.findViewById(R.id.dialog_simple_sure);

        dialog_simple_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DialogJubao.this, IReportctivity.class));
                startActivity(new Intent(DialogJubao.this, ReportGuideActivity.class));
                finish();
            }
        });

        dialog_simple_cancel = (TextView) layout.findViewById(R.id.dialog_simple_cancel);

        dialog_simple_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                finish();
            }
        });

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    builder.dismiss();
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
