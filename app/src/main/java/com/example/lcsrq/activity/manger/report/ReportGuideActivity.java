package com.example.lcsrq.activity.manger.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/6/3.
 */

public class ReportGuideActivity extends BaseActivity {
    private TextView tv_hd;
    private TextView tv_hc;
    private Button btn_fanhui;
    private int type = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_guide);
    }

    @Override
    protected void addAction() {
        tv_hd.setOnClickListener(this);
        tv_hc.setOnClickListener(this);
        btn_fanhui.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        tv_hd = (TextView) findViewById(R.id.tv_hd);
        tv_hc = (TextView) findViewById(R.id.tv_hc);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
    }

    @Override
    public void onClick(View v) {
        //返回按钮
    if (v.getId() == R.id.btn_fanhui){
        finish();
    }
    //  黑点
        if (v.getId()== R.id.tv_hd){
            type = 2;  // 表示黑点
            Intent intent = new Intent(ReportGuideActivity.this, ReportActivity.class);
            intent.putExtra("11", 2);
            startActivity(intent);
        }

        // 黑车
        if (v.getId() == R.id.tv_hc){
            type = 1;  // 表示黑车
            Intent intent = new Intent(ReportGuideActivity.this, ReportActivity.class);
            intent.putExtra("11", 1);
            startActivity(intent);
        }
    }
}
