package com.example.lcsrq.activity.manger.xxcx;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
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
import com.example.lcsrq.activity.manger.jfxt.ScoringActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.InfoReqData;
import com.example.lcsrq.bean.resq.XxCx_peopleResp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/2.
 */

public class InfomationCarActivity extends BaseActivity{

    private  RelativeLayout rl_lxl;
    private TextView commonTitleTv,tv_leixing,tv_input;
    private LinearLayout ll_ren,ll_car,ll_gyz;
    private Button btn_check;
    private EditText et_text_input;

    private int type = -1;
    private RelativeLayout rl_jf;
    private RelativeLayout rl_phone;
    private RelativeLayout rl_sfz;
    private EditText tv_text;
    private String keyWord;
    private String keyWord1;
    private EditText et_dizhi;
    private RelativeLayout rl_dizhi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_car);
         type = getIntent().getIntExtra("type",-1);

        if (type == 1){
            tv_leixing.setText("人");
            tv_leixing.setTextColor(Color.BLACK);
            tv_input.setText("姓名:");
//                    rl_jf.setVisibility(View.VISIBLE);
            rl_phone.setVisibility(View.GONE);
            rl_sfz.setVisibility(View.GONE);
        }else if (type == 2){
            tv_leixing.setText("站点");
            tv_leixing.setTextColor(Color.BLACK);
            tv_input.setText("站点:");
            type = 2;  // 站点
//                    rl_jf.setVisibility(View.GONE);
            rl_phone.setVisibility(View.GONE);
            rl_sfz.setVisibility(View.GONE);
        }else if (type == 3){
            tv_leixing.setText("车");
            tv_leixing.setTextColor(Color.BLACK);
            tv_input.setText("车牌号:");
            type = 3;  //车牌
//                    rl_jf.setVisibility(View.GONE);
            rl_phone.setVisibility(View.GONE);
            rl_sfz.setVisibility(View.GONE);
        }

    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        rl_lxl.setOnClickListener(this);
        btn_check.setOnClickListener(this);
    }
private LinearLayout commonLeftBtn;
    @Override
    protected void findViews() {
         commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        rl_lxl = (RelativeLayout) findViewById(R.id.rl_lx);
//        TextView commonTitleTv = findViewById(R.id.commonTitleTv);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("信息查询");

         tv_leixing = (TextView) findViewById(R.id.tv_leixing);
            tv_input = (TextView) findViewById(R.id.tv_input);

         et_text_input = (EditText) findViewById(R.id.et_text_input);

        btn_check = (Button) findViewById(R.id.btn_check);

//        rl_jf = (RelativeLayout) findViewById(R.id.rl_jf);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_sfz = (RelativeLayout) findViewById(R.id.rl_sfz);
        // 输入的类型
        tv_text = (EditText) findViewById(R.id.tv_text);

        //  根据地址查询  输入的地址
        et_dizhi = (EditText) findViewById(R.id.et_dizhi);
        rl_dizhi = (RelativeLayout) findViewById(R.id.rl_dizhi);
    }

    private AlertDialog builder;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_check){
            if (tv_leixing.getText().toString().equals("")){
             type = 3; // 表示车辆
            }
              switch (type){
                  case  1:
                      keyWord = tv_text.getText().toString();
                      // 表示人
                      Intent intent = new Intent(InfomationCarActivity.this, People_info.class);
                      intent.putExtra("type",type);
                      intent.putExtra("keyWord", keyWord);
                      startActivity(intent);
                      break;
                  case 2:
                      keyWord = tv_text.getText().toString();
                      Toast.makeText(InfomationCarActivity.this,et_dizhi.getText().toString() + "",Toast.LENGTH_SHORT).show();
                      // 表示站点
                      Intent intent1 = new Intent(InfomationCarActivity.this, Zhandian_info.class);
                      intent1.putExtra("type",type);
                      intent1.putExtra("keyWord", keyWord);
                      startActivity(intent1);

                      break;
                  case 3:
                      keyWord = tv_text.getText().toString();
                      // 表示车辆
                      Intent intent2 = new Intent(InfomationCarActivity.this, CarInfo.class);
                      intent2.putExtra("type",type);
                      intent2.putExtra("keyWord", keyWord);
                      startActivity(intent2);
                      break;
              }

        }else if (v.getId() == R.id.rl_lx){
            LayoutInflater inflaterDl = LayoutInflater.from(this);
            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.scro_xxcx_dialog, null);
            builder = new AlertDialog.Builder(InfomationCarActivity.this).create();
            builder.show();
            builder.getWindow().setContentView(layout);

            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()) * 4/5; //设置宽度
            lp.height = (int) display.getHeight() / 3;
            builder.getWindow().setAttributes(lp);

             ll_ren = (LinearLayout) layout.findViewById(R.id.ll_ren);
             ll_car = (LinearLayout) layout.findViewById(R.id.ll_car);
             ll_gyz = (LinearLayout) layout.findViewById(R.id.ll_gyz);

            // 点击人
            ll_ren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_leixing.setText("人员");
                    tv_leixing.setTextColor(Color.BLACK);
                    tv_input.setText("姓名:");
//                    rl_jf.setVisibility(View.VISIBLE);
                    rl_phone.setVisibility(View.GONE);
                    rl_sfz.setVisibility(View.GONE);
                    type = 1;  // 表示人
                    builder.dismiss();
                }
            });
            // 点击车辆
            ll_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_leixing.setText("车辆");
                    tv_leixing.setTextColor(Color.BLACK);
                    tv_input.setText("车牌号:");
                    type = 3;  //车牌
//                    rl_jf.setVisibility(View.GONE);
                    rl_phone.setVisibility(View.GONE);
                    rl_sfz.setVisibility(View.GONE);
                    builder.dismiss();
                }
            });
            // 点击站点
            ll_gyz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_leixing.setText("站点");
                    tv_leixing.setTextColor(Color.BLACK);
                    tv_input.setText("站点:");
                    type = 2;  // 站点
//                    rl_jf.setVisibility(View.GONE);
                    rl_phone.setVisibility(View.GONE);
                    rl_sfz.setVisibility(View.GONE);
                    //  供应站有地址
                    rl_dizhi.setVisibility(View.VISIBLE);
                    builder.dismiss();
                }
            });

        }else if (v.getId() == R.id.commonLeftBtn){
            finish();
        }

    }
}
