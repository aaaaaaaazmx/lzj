package com.example.lcsrq.activity.manger.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;


/**
 * 错题本
 */
public class ErrorRecordActivity extends BaseActivity {

    private LinearLayout commonLeftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_record);
    }

    @Override
    protected void addAction() {

    }

    @Override
    protected void findViews() {

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("错题本");
    }

    @Override
    public void onClick(View v) {

    }
}
