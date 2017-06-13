package com.example.lcsrq.activity.manger;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.UserinfoReqData;
import com.example.lcsrq.bean.resq.UserinfoRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;

/**
 * Created by 苏毅 on 2017/4/3.
 */

public class PeopleDetail extends BaseActivity {

    private String uid;
    private LoginModel loginModel;
    private LinearLayout commonLeftBtn;
    private SimpleDraweeView user_avator;
    private TextView user_name;
    private TextView tv_bianhao;
    private View viewById;
    private TextView tv_company;
    private TextView tv_code;
    private TextView tv_phone;
    private TextView tv_jineng;
    private TextView tv_youxiaoqi;
    private UserinfoRespData datas;
    private TextView tv_jfqk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        showLoading("正在加载");
        uid = getIntent().getStringExtra("UID");
        loginModel = new LoginModel();
        initData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                // 用户名
                user_name.setText("姓名 : " + datas.getM_nickname());
                // 放置图片
                user_avator.setImageURI(Uri.parse(datas.getHead_photo()));
                // 设置编号
                tv_bianhao.setText("编号 : "+datas.getSn()+ "");
                // 公司名称
                tv_company.setText("公司名称 : " + datas.getCompany_name()+ "");
                //  身份证号码
                tv_code.setText("身份证号码 : "+datas.getCode()+ "");
                //  电话号码
                tv_phone.setText("电话号码 : "+ datas.getM_account() + "");
                // 技能证书编号
                tv_jineng.setText("技能证书编号 : "+datas.getM_datajson().getMcode() + "");
                // 技能有效期
                tv_youxiaoqi.setText("技能证书有效期 : "+datas.getM_datajson().getStart_end());
                //  记分情况
                tv_jfqk.setText(datas.getJf_value() + "分");
            }
        }
    };

    // 获取用户信息
    private void initData() {
        final UserinfoReqData userinfoReqData = new UserinfoReqData();
        userinfoReqData.setUid(uid);
        loginModel.userinfo(this, userinfoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                datas = JSON.parseObject((String) msg, UserinfoRespData.class);
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                closeDialog();
                Toast.makeText(PeopleDetail.this, msg.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {

        ImageView left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setVisibility(View.VISIBLE);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("个人详情");
            // 个人头像
        user_avator = (SimpleDraweeView) findViewById(R.id.user_avator);
        //  名字
        user_name = (TextView) findViewById(R.id.user_name);
        // 编号
        tv_bianhao = (TextView) findViewById(R.id.tv_bianhao);
        // 公司
        tv_company = (TextView) findViewById(R.id.tv_company);
        //身份证
        tv_code = (TextView) findViewById(R.id.tv_code);
        // 电话号码
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        //技能证书编号
        tv_jineng = (TextView) findViewById(R.id.tv_jineng);
        // 技能证书有效期
        tv_youxiaoqi = (TextView) findViewById(R.id.tv_youxiaoqi);
        // 计分情况
        tv_jfqk = (TextView) findViewById(R.id.tv_jfqk);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==R.id.commonLeftBtn){
            finish();
        }
    }
}
