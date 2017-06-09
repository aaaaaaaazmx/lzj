package com.example.lcsrq.activity.manger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/5.
 */

public class CountyDetailActivity extends BaseActivity {
    private LinearLayout commonLeftBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstment_countydetail);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("通知通报");
        ImageView left_btn = (ImageView) findViewById(R.id.left_btn);
         commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        left_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
    }
}
