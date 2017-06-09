package com.example.lcsrq.activity.manger.training;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;

/**
 * Created by 苏毅 on 2017/4/5.
 * <p>
 * 课题练习
 */

public class TestKtActivity extends BaseActivity {
    private Chronometer chro_exam;
    private int second = 59;  // 秒
    private int minute = 40;  // 分
    private ProgressBar pb;
    private int pbnum = 0;

    private LinearLayout ll_comit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_shi);
    }

    @Override
    protected void addAction() {
        ll_comit.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setMax(3000);
        chro_exam = (Chronometer) findViewById(R.id.chro_exam);
        chro_exam.setText(NowTime());
        chro_exam.start();
        chro_exam.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                second--;
                pbnum++;
                pb.setProgress(pbnum);
                if (second == -1) {
                    minute--;
                    second = 59;
                }
                if (minute == -1) {
                    chronometer.stop();
                }
                if (minute < 5) {
                    chronometer.setTextColor(Color.RED);
                    chronometer.setText(NowTime());
                } else {
                    chronometer.setTextColor(getResources().getColor(R.color.tl));
                    chronometer.setText(NowTime());
                }
            }
        });

        ll_comit = (LinearLayout) findViewById(R.id.ll_comit);
    }

    // 现在时间
    private String NowTime() {
        if (second < 10) {
            return (minute + ":0" + second);
        } else {
            return (minute + ":" + second);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ll_comit) {
            LayoutInflater inflaterDl = LayoutInflater.from(TestKtActivity.this);
            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.btn_test_dialog, null);
            AlertDialog builder = new AlertDialog.Builder(TestKtActivity.this).create();
            builder.show();
            builder.getWindow().setContentView(layout);
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()) ; //设置宽度
            lp.height = (int) display.getHeight() / 3;
            builder.getWindow().setAttributes(lp);
        }
    }
}
