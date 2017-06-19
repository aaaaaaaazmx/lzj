package com.example.lcsrq;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.activity.manger.LoginActivity;
import com.example.lcsrq.bean.req.UserinfoReqData;
import com.example.lcsrq.bean.respbean.M_datajson;
import com.example.lcsrq.bean.resq.LoginRespData;
import com.example.lcsrq.bean.resq.UserinfoRespData;
import com.example.lcsrq.fragment.FirstFragment;

import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.fragment.MyFragment;
import com.example.lcsrq.fragment.MessageFragment;
import com.example.lcsrq.fragment.SecurityFragment;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;

/**
 * Created by 苏毅 on 2017/3/29.
 */

public class HomeActivity extends BaseActivity {

    private LinearLayout firstLl, msgLayout, selfLayout;
    private ImageView firstIv, msgIv, selfIv;
    private TextView firstTv, msgTv, selfTv;
    private FragmentManager fragmentManager;
    private FirstFragment firstFragment;
    private MessageFragment secondFragment;
    private MyFragment myFragment;
    private LoginModel loginModel;
    private int index = 0;
    private ImageView anquanIv;
    private TextView anquanTv;
    private LinearLayout anquanLl;
    private SecurityFragment securityFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTabSelection(index);
        loginModel = new LoginModel();
        iniData();  //  加载我的页面信息

    }

    private void iniData() {
        final UserinfoReqData userinfoReqData = new UserinfoReqData();
        userinfoReqData.setUid(Global.uid);
        loginModel.userinfo(this, userinfoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {

                UserinfoRespData userinfoRespData = JSON.parseObject((String) msg, UserinfoRespData.class);
                if (!TextUtils.isEmpty(userinfoRespData.getM_nickname())){
                    Global.usernName = userinfoRespData.getM_nickname(); //姓名
                }else {
                    Global.usernName = "";
                }

                if (!TextUtils.isEmpty(userinfoRespData.getCompany_id())){
                    Global.company_id = userinfoRespData.getCompany_id(); // 公司ID
                }else {
                    Global.company_id = "";
                }

                if (!TextUtils.isEmpty(userinfoRespData.getHead_photo())){
                    Global.userIcon = userinfoRespData.getHead_photo(); //用户头像
                }else {
                    Global.userIcon = "";
                }
                if (!TextUtils.isEmpty(userinfoRespData.getM_roleid())){
                    Global.m_roleid = userinfoRespData.getM_roleid(); //用户权限
                }else {
                    Global.m_roleid = "";
                }

                if (!TextUtils.isEmpty(userinfoRespData.getSupply_id())){
                    Global.Mysupply_id = userinfoRespData.getSupply_id(); //用户自带的供应站ID
                }else {
                    Global.Mysupply_id = "";
                }

                if (!TextUtils.isEmpty(userinfoRespData.getM_datajson().getDw())){
                    Global.My_dw = userinfoRespData.getM_datajson().getDw();  //单位
                }else {
                    Global.My_dw = "";
                }

                //{"code":"220581199011091197","company_id":"0","head_photo":"http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png","id":"4","m_account":"rq1","m_datajson":{"cz":"rq1","dw":"rq1","remark":"rq1","sex":"1","zw":"rq1"},
                // "m_nickname":"rq1","m_roleid":"3","sn":"01","supply_id":"0"}


                // TODO 头像
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(HomeActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void findViews() {

        fragmentManager = this.getSupportFragmentManager();

        // 首页
        firstLl = (LinearLayout) findViewById(R.id.firstLl);
        firstIv = (ImageView) findViewById(R.id.firstIv);
        firstTv = (TextView) findViewById(R.id.firstTv);

        // 消息
        msgLayout = (LinearLayout) findViewById(R.id.msgLayout);
        msgIv = (ImageView) findViewById(R.id.msgIv);
        msgTv = (TextView) findViewById(R.id.msgTv);

        // 我的
        selfLayout = (LinearLayout) findViewById(R.id.selfLayout);
        selfIv = (ImageView) findViewById(R.id.selfIv);
        selfTv = (TextView) findViewById(R.id.selfTv);

        // 安全隐患
        anquanLl = (LinearLayout) findViewById(R.id.anquanLl);
        anquanIv = (ImageView) findViewById(R.id.anquanIv);
        anquanTv = (TextView) findViewById(R.id.anquanTv);

        firstFragment = new FirstFragment();
        secondFragment = new MessageFragment();
        myFragment = new MyFragment();
        securityFragment = new SecurityFragment();
    }

    @Override
    protected void addAction() {
        firstLl.setOnClickListener(this);
        selfLayout.setOnClickListener(this);
        msgLayout.setOnClickListener(this);
        anquanLl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.firstLl) {
            setTabSelection(0);
        }
        if (v.getId() == R.id.msgLayout) {
            setTabSelection(1);
        }
        if (v.getId() == R.id.selfLayout) {
            setTabSelection(2);
        }
        if (v.getId() == R.id.anquanLl){
            setTabSelection(3);
        }
    }

    public void setTabSelection(int index) {
        // 重置按钮
        resetTabBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            //首页
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                firstTv.setTextColor(getResources().getColor(R.color.green));
                firstIv.setImageResource(R.mipmap.icon_dlsy);
                if (!firstFragment.isAdded()) {
                    transaction.replace(R.id.container, firstFragment);
                }
                transaction.show(firstFragment);
                break;
            // 消息
            case 1:
                msgTv.setTextColor(getResources().getColor(R.color.green));
                msgIv.setImageResource(R.mipmap.icon_xxdl);
                if (!secondFragment.isAdded()) {
                    transaction.replace(R.id.container, secondFragment);
                }
                transaction.show(secondFragment);
                break;
            // 我的
            case 2:
                selfTv.setTextColor(getResources().getColor(R.color.green));
                selfIv.setImageResource(R.mipmap.icon_wddl);
                if (!myFragment.isAdded()) {
                    transaction.replace(R.id.container, myFragment);
                }
                transaction.show(myFragment);
                break;

            case 3:
                anquanTv.setTextColor(getResources().getColor(R.color.green));
                anquanIv.setImageResource(R.mipmap.icom_aqyh_d);
                if (!securityFragment.isAdded()){
                    transaction.replace(R.id.container, securityFragment);
                }
                transaction.show(securityFragment);
                break;
        }
        transaction.commit();
    }

    /**
     * 清除所有的选中状态
     */
    private void resetTabBtn() {
        firstTv.setTextColor(getResources().getColor(R.color.bottom_text_color));
        firstIv.setImageResource(R.mipmap.icon_sy);

        msgTv.setTextColor(getResources().getColor(R.color.bottom_text_color));
        msgIv.setImageResource(R.mipmap.icon_xx);

        selfIv.setImageResource(R.mipmap.icon_wd);
        selfTv.setTextColor(getResources().getColor(R.color.bottom_text_color));

        anquanIv.setImageResource(R.mipmap.icom_aqyh);
        anquanTv.setTextColor(getResources().getColor(R.color.bottom_text_color));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }

        if (secondFragment != null) {
            transaction.hide(secondFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (securityFragment != null) {
            transaction.hide(securityFragment);
        }
    }


}
