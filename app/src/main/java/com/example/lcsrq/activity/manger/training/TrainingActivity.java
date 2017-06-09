package com.example.lcsrq.activity.manger.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/2.
 * 行业培训
 */

public class TrainingActivity extends BaseActivity {
    private LinearLayout ll_yuyue, ll_tklx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
    }

    @Override
    protected void addAction() {
        ll_yuyue.setOnClickListener(this);
        ll_tklx.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        // x预约考试
        ll_yuyue = (LinearLayout) findViewById(R.id.ll_yuyue);

        // 题库练习
        ll_tklx = (LinearLayout) findViewById(R.id.ll_tklx);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_yuyue) {
            startActivity(new Intent(TrainingActivity.this, YuAppointmentActivity.class));
        } else if (v.getId() == R.id.ll_tklx) {
            startActivity(new Intent(TrainingActivity.this, ChoiseQsActivity.class));
        }

    }
}
