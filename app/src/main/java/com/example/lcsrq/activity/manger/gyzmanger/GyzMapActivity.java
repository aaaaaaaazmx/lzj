package com.example.lcsrq.activity.manger.gyzmanger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/3/31
 * 供应站地图搜索页面.
 */

public class GyzMapActivity extends BaseActivity {
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_mapsearch);
    }

    @Override
    protected void addAction() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }
}
