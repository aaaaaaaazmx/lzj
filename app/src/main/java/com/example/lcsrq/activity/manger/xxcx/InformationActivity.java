package com.example.lcsrq.activity.manger.xxcx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/2.
 */

public class InformationActivity extends BaseActivity {

    private RelativeLayout rl_cxlx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
    }

    @Override
    protected void addAction() {
        rl_cxlx.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("信息查询");

        rl_cxlx = (RelativeLayout) findViewById(R.id.rl_cxlx);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_cxlx) {
            startActivity(new Intent(this,InfomationCarActivity.class));
        }
    }
}
