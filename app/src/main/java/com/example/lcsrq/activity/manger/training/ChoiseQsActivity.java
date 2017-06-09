package com.example.lcsrq.activity.manger.training;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;


/**
 * 选择题库
 */
public class ChoiseQsActivity extends BaseActivity {
    private RelativeLayout ll_jzks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_qs);
    }


    @Override
    protected void addAction() {
        ll_jzks.setOnClickListener(this);
    }

    @Override
    protected void findViews() {

        ImageView left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setVisibility(View.VISIBLE);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("选择题库");

        ll_jzks = (RelativeLayout) findViewById(R.id.ll_jzks);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ll_jzks) {
            startActivity(new Intent(ChoiseQsActivity.this,TestKtActivity.class));
        }

    }
}
