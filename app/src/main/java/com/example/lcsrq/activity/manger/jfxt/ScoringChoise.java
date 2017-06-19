package com.example.lcsrq.activity.manger.jfxt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/6/12.
 */

public class ScoringChoise extends BaseActivity {

    private TextView tv_ren,tv_chepai,tv_zhandian;
    private Button btn_fanhui;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring_choise);
    }

    @Override
    protected void addAction() {
        tv_ren.setOnClickListener(this);
        tv_chepai.setOnClickListener(this);
        tv_zhandian.setOnClickListener(this);
        btn_fanhui.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        tv_ren = (TextView) findViewById(R.id.tv_ren);
        tv_chepai = (TextView) findViewById(R.id.tv_chepai);
        tv_zhandian = (TextView) findViewById(R.id.tv_zhandian);
        btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
    }

    @Override
    public void onClick(View v) {
        // 返回
        if (v.getId() == R.id.btn_fanhui){
            finish();
        }
        // ren
        if (v.getId() == R.id.tv_ren){
            Intent intent = new Intent(ScoringChoise.this,ScoringActivity.class);
            intent.putExtra("choise","1");
            startActivity(intent);
            finish();
        }
        // che
        if (v.getId() == R.id.tv_chepai){
            Intent intent = new Intent(ScoringChoise.this,ScoringActivity.class);
            intent.putExtra("choise","3");
            startActivity(intent);
            finish();
        }
        // zhandian
        if (v.getId() == R.id.tv_zhandian){
            Intent intent = new Intent(ScoringChoise.this,ScoringActivity.class);
            intent.putExtra("choise","2");
            startActivity(intent);
            finish();
        }

    }
}
