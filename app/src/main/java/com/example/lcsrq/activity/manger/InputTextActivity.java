package com.example.lcsrq.activity.manger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class InputTextActivity extends BaseActivity {
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private EditText ed_input;
    private Button btn_sure;
    private int num = -1;
    private int position = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_text);
        num = getIntent().getIntExtra("num", -1);

        position = getIntent().getIntExtra("position", -1);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        ed_input = (EditText) findViewById(R.id.ed_input);
        btn_sure = (Button) findViewById(R.id.btn_sure);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        } else if (v.getId() == R.id.btn_sure) {
            if (position != -1) {
                Intent intent = new Intent();
                intent.putExtra("ccr", ed_input.getText().toString());
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            } else if (num != -1) {
                Intent intent = new Intent();
                intent.putExtra("ccdw", ed_input.getText().toString());
                intent.putExtra("position1", num);
                setResult(1, intent);
                finish();
            }

        }

    }
}
