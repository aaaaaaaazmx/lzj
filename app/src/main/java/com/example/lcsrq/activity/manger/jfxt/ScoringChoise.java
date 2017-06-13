package com.example.lcsrq.activity.manger.jfxt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/6/12.
 */

public class ScoringChoise extends BaseActivity {

    private TextView tv_ren,tv_chepai,tv_zhandian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_guide);
    }

    @Override
    protected void addAction() {
        tv_ren.setOnClickListener(this);
        tv_chepai.setOnClickListener(this);
        tv_zhandian.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        tv_ren = (TextView) findViewById(R.id.tv_ren);
        tv_chepai = (TextView) findViewById(R.id.tv_chepai);
        tv_zhandian = (TextView) findViewById(R.id.tv_zhandian);
    }

    @Override
    public void onClick(View v) {
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
