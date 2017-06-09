package com.example.lcsrq.activity.manger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GetYzmReqData;
import com.example.lcsrq.bean.req.LoginReqData;
import com.example.lcsrq.bean.resq.LoginRespData;
import com.example.lcsrq.bean.resq.getYzmRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.ExampleUtil;
import com.example.lcsrq.value.Global;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 苏毅 on 2017/4/3.
 */

public class LoginActivity extends BaseActivity{
    private Button btn_login;
    private TextView tv_yanzhengma;
    private LoginReqData loginBean;
    private LoginModel loginModel;
    private EditText et_phone, ed_password;
    private String phone, password;
    private SharedPreferences sp;
    private LinearLayout ll_yzm;
    private Random random;
    private int randomnum;
    private String yzm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginModel = new LoginModel();
        // 获取缓存uid
        sp = getSharedPreferences("uId", MODE_PRIVATE);
        Global.uid = sp.getString("uid", null);

        if (Global.uid != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

    }

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
            }
                //  打印日志
//            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };


    private static final int MSG_SET_TAGS = 1002;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_TAGS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
            }
        }
    };

    // 设置tag
    private void setTag(String tag) {

        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
                return;
            }
            tagSet.add(sTagItme);
        }
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

    }

    /**
     * 强制隐藏输入法键盘
     */
    private void hideInput(Context context, View view){
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void addAction() {
        btn_login.setOnClickListener(this);
        ll_yzm.setOnClickListener(this);
        tv_yanzhengma.setOnClickListener(this);
        ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() == 6){
                        hideInput(LoginActivity.this,ed_password);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 11){
                    hideInput(LoginActivity.this,et_phone);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void findViews() {

        random = new Random();

        btn_login = (Button) findViewById(R.id.btn_login);
        et_phone = (EditText) findViewById(R.id.et_phone);
        ed_password = (EditText) findViewById(R.id.ed_password);

        tv_yanzhengma = (TextView) findViewById(R.id.tv_yanzhengma);
        ll_yzm = (LinearLayout) findViewById(R.id.ll_yzm);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            phone = et_phone.getText().toString();
            password = ed_password.getText().toString();
            // 请求参数
            loginBean = new LoginReqData();

//            if (!StringTool.isNotNull(phone)) {
//                Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
//                et_phone.requestFocus();
//                return;
//            }
//
//            if (!StringTool.isNotNull(password)) {
//                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
//                ed_password.requestFocus();
//                return;
//            }
            // 如果不是空,如果是16位数的

//            if (StringTool.isMobile(phone)) {//用户
            loginBean.setAccount(phone);
//            }


            loginModel.login(LoginActivity.this, loginBean, new OnLoadComBackListener() {
                @Override
                public void onSuccess(Object msg) {
                    LoginRespData loginRespData = JSON.parseObject((String) msg, LoginRespData.class);

                    Global.uid = loginRespData.getUid();

                    // 缓存sp
                    sp.edit().putString("uid", Global.uid).commit();
                    setTag(loginRespData.getUid());  // 设置TAG
                    // 跳转引导页
                    startActivity(new Intent(LoginActivity.this, GuideActivity.class));

                    finish();
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(LoginActivity.this, msg.toString(), Toast.LENGTH_LONG).show();

                }
            });
        } else if (v.getId() == R.id.tv_yanzhengma) {
            // 获取验证码6位数
            int tmp = Math.abs(random.nextInt());
            yzm =  tmp % (999999 - 100000 + 1) + 100000 + "";

            Toast.makeText(LoginActivity.this,yzm+"",Toast.LENGTH_SHORT).show();
            String phone = et_phone.getText().toString();
            final GetYzmReqData getYzmReqData = new GetYzmReqData();
            getYzmReqData.setAppkey("d295bfaea5d45f019b63a45e9f4629f4");
            getYzmReqData.setAppid("b898dcd859d906bbc6d82068b87acfe2");
            getYzmReqData.setAppsecret("gas0419appsecret");
            getYzmReqData.setTplid("21");
            getYzmReqData.setMobile(phone);
            getYzmReqData.setContent("燃气登录验证码 :" +yzm + "，您正在注册成为用户，感谢您的支持！【长沙燃气】"); // 验证码
            loginModel.getYzm(LoginActivity.this, getYzmReqData, new OnLoadComBackListener() {
                @Override
                public void onSuccess(Object msg) {
                    getYzmRespData getYzmRespData = JSON.parseObject((String) msg, getYzmRespData.class);
                }

                @Override
                public void onError(String msg) {
            Toast.makeText(LoginActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
