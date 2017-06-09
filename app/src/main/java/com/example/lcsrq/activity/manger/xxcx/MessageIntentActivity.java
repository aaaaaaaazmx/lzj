package com.example.lcsrq.activity.manger.xxcx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class MessageIntentActivity extends BaseActivity {
    private LinearLayout commonLeftBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_intent);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("消息");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
    }
}
