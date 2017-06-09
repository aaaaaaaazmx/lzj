package com.example.lcsrq.activity.manger.xxcx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/6/3.
 */

public class InfoGuideActivity extends BaseActivity {

    private Button btn_fanhui;
    private TextView tv_ren;
    private TextView tv_chepai;
    private TextView tv_zhandian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_guide);
    }

    @Override
    protected void addAction() {
        btn_fanhui.setOnClickListener(this);

        tv_ren.setOnClickListener(this);
        tv_chepai.setOnClickListener(this);
        tv_zhandian.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
        tv_ren = (TextView) findViewById(R.id.tv_ren);
        tv_chepai = (TextView) findViewById(R.id.tv_chepai);
        tv_zhandian = (TextView) findViewById(R.id.tv_zhandian);
    }

    @Override
    public void onClick(View v) {
    // 返回按钮
    if (v.getId() == R.id.btn_fanhui){
        finish();
    }

    // ren
        if (v.getId() ==R.id.tv_ren){
            Intent intent = new Intent(InfoGuideActivity.this,InfomationCarActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
        }
    // 车牌
        if (v.getId() == R.id.tv_chepai){
            Intent intent = new Intent(InfoGuideActivity.this,InfomationCarActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }

    // 站点
        if (v.getId() ==R.id.tv_zhandian){
            Intent intent = new Intent(InfoGuideActivity.this,InfomationCarActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
        }
    }
}
