package com.example.lcsrq.activity.manger.report;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.activity.manger.xxcx.CarInfo;
import com.example.lcsrq.activity.manger.xxcx.InfomationCarActivity;
import com.example.lcsrq.activity.manger.xxcx.People_info;
import com.example.lcsrq.activity.manger.xxcx.Zhandian_info;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.xiangce.UiTool;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class IReportctivity extends BaseActivity {
    private RelativeLayout rl_lxl;
    private TextView commonTitleTv, tv_leixing, tv_input, tv_hd, tv_hc;
    private LinearLayout ll_hd, ll_car, ll_gyz;
    private Button btn_check;
    private EditText et_text_input;
    private RelativeLayout rl_hd;
    private RelativeLayout rl_hc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ireport_activity);
    }

    @Override
    protected void addAction() {
//        rl_lxl.setOnClickListener(this);
//        btn_check.setOnClickListener(this);
        commonLeftBtn.setOnClickListener(this);

        rl_hd.setOnClickListener(this);
        rl_hc.setOnClickListener(this);
    }

    private LinearLayout commonLeftBtn;

    @Override
    protected void findViews() {
//        rl_lxl = (RelativeLayout) findViewById(R.id.rl_lx);
//        TextView commonTitleTv = findViewById(R.id.commonTitleTv);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("举报类型");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        tv_leixing = (TextView) findViewById(R.id.tv_leixing);
        tv_input = (TextView) findViewById(R.id.tv_input);

//        et_text_input = (EditText) findViewById(R.id.et_text_input);

//        btn_check = (Button) findViewById(R.id.btn_check);

        rl_hd = (RelativeLayout) findViewById(R.id.rl_hd); // 黑点
        rl_hc = (RelativeLayout) findViewById(R.id.rl_hc); // 黑车
    }

    private AlertDialog builder;
    private int type = -1;

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_check) {
//            if (tv_leixing.getText().toString().equals("")) {
//                Toast.makeText(IReportctivity.this, "请选择查询类型", Toast.LENGTH_LONG).show();
//                return;
//            }
//            switch (type) {
//                case 1:  // 黑车
//                    Intent intent = new Intent(IReportctivity.this, ReportActivity.class);
//                    intent.putExtra("11", 1);
//                    startActivity(intent);
//                    break;
//                case 2: // 黑点
//                    Intent intent1 = new Intent(IReportctivity.this, ReportActivity.class);
//                    intent1.putExtra("11", 2);
//                    startActivity(intent1);
//                    break;
//            }
//
//        } else if (v.getId() == R.id.rl_lx) {

//            LayoutInflater inflaterDl = LayoutInflater.from(this);
//            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.report_dialog, null);
//            builder = new AlertDialog.Builder(IReportctivity.this).create();
//            builder.show();
//            builder.getWindow().setContentView(layout);
//            WindowManager windowManager = getWindowManager();
//            Display display = windowManager.getDefaultDisplay();
//            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
//            lp.width = (int) (display.getWidth()); //设置宽度
//            lp.height = (int) display.getHeight() / 3;
//            builder.getWindow().setAttributes(lp);
//
//            ll_hd = (LinearLayout) layout.findViewById(R.id.ll_hd);
//            ll_car = (LinearLayout) layout.findViewById(R.id.ll_car);

            // 点击黑点
//            ll_hd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tv_leixing.setText("黑点");
//                    tv_leixing.setTextColor(Color.BLACK);
//                    type = 2;  // 表示黑点
//                    builder.dismiss();
//                }
//            });
//            // 点击黑车
//            ll_car.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tv_leixing.setText("黑车");
//                    tv_leixing.setTextColor(Color.BLACK);
//                    type = 1;  // 表示黑车
//                    builder.dismiss();
//                }
//            });
//        } else
            if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
        else if (v.getId() == R.id.rl_hd){
            type = 2;  // 表示黑点
            Intent intent = new Intent(IReportctivity.this, ReportActivity.class);
            intent.putExtra("11", 2);
            startActivity(intent);
        }else if (v.getId() ==R.id.rl_hc){
            type = 1;  // 表示黑车
            Intent intent = new Intent(IReportctivity.this, ReportActivity.class);
            intent.putExtra("11", 1);
            startActivity(intent);
        }

    }
}
