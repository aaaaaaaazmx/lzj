package com.example.lcsrq.activity.manger.hdhc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyJuBaoActivity;
import com.example.lcsrq.adapter.HdhAdapter;
import com.example.lcsrq.adapter.MyAllHdhAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.HdhcDetailReqData;
import com.example.lcsrq.bean.req.HdhcReqData;
import com.example.lcsrq.bean.resq.HdhcDetailRespData;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class MyHdHcactvity extends BaseActivity {

    private LoginModel loginModel;
    private ArrayList<HdhcRespData> hcData;
    private ArrayList<HdhcRespData> hdData;
    private ListView lv_hdhc;
    private LinearLayout ll_hc;
    private LinearLayout ll_hd;
    private MyAllHdhAdapter myAllHdhAdapter;
    private ImageView iv_exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hdhc);
        loginModel = new LoginModel();
        initData();
    }

    Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                if (hcData.size() == 0){
                    Toast.makeText(MyHdHcactvity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }else {
                    //  黑车的数据
                    myAllHdhAdapter = new MyAllHdhAdapter(MyHdHcactvity.this);
                    myAllHdhAdapter.setList(hcData);
                    lv_hdhc.setAdapter(myAllHdhAdapter);
                }
            }
        }
    };

    private void initData() {
        HdhcReqData hcRepData = new HdhcReqData();
        hcRepData.setUid(Integer.parseInt(Global.uid)); //  用户ID
        hcRepData.setType(1);
        loginModel.getListOfHdhcforMY(MyHdHcactvity.this, hcRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                hcData = (ArrayList<HdhcRespData>) msg;

                Message message = handler.obtainMessage();
                message.arg1 =1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyHdHcactvity.this,msg.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        HdhcReqData hdRepData = new HdhcReqData();
        hdRepData.setUid(Integer.parseInt(Global.uid));
        hdRepData.setType(2);  // 黑点的数据

        loginModel.getListOfHdhcforMY(MyHdHcactvity.this, hdRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                hdData = (ArrayList<HdhcRespData>) msg;
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyHdHcactvity.this,msg.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void addAction() {
        ll_hc.setOnClickListener(this);
        ll_hd.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        lv_hdhc = (ListView) findViewById(R.id.lv_hdhc);  // ListView
        ll_hc = (LinearLayout) findViewById(R.id.ll_hc);
        ll_hd = (LinearLayout) findViewById(R.id.ll_hd);

        iv_exit = (ImageView) findViewById(R.id.iv_exit);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_hd){
            // 黑点

        }
        if (v.getId() == R.id.ll_hc){
            // 黑车

        }
        //  返回按钮
        if (v.getId() == R.id.iv_exit){
            finish();
        }
    }
}
