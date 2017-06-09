package com.example.lcsrq.activity.manger.hdhc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.adapter.HdhAdapter;
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

public class HdhcActivity extends BaseActivity {
    private TextView tv_hd, tv_hc,tv_show;
    private LinearLayout ll_hc, ll_hd;
    private ListView lv_hdhc;
    private ImageView iv_exit;

    private int flag = 0;  // 判断点击的是否是黑点还是黑车    默认是黑车

    private List<String> list0;
    private List<String> list1;

    // 原始的数据,第一次进来显示的数据
    private List<HdhcRespData> list = new ArrayList<HdhcRespData>();
    // 黑点数据
    private List<HdhcRespData> hd_list = new ArrayList<HdhcRespData>();
    //  黑车数据
    private List<HdhcRespData> hc_list = new ArrayList<HdhcRespData>();


    private LoginModel loginModel;
    // 获取黑点黑车返回的列表
    private ArrayList<HdhcRespData> datas = new ArrayList<HdhcRespData>();
    private ArrayList<HdhcRespData> hdData = new ArrayList<HdhcRespData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdhc);
        loginModel = new LoginModel();
        initData();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                hdhAdapter = new HdhAdapter(HdhcActivity.this);
                hdhAdapter.setList(datas);
                lv_hdhc.setAdapter(hdhAdapter);
            }
        }
    };


    private void initData() {
        //  获取黑车的查处情况
        HdhcReqData hdhcReqData = new HdhcReqData();
//        hdhcReqData.setUid(Integer.parseInt(Global.uid));
        hdhcReqData.setType(1);

        loginModel.getAllListOfHdhc(HdhcActivity.this, hdhcReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {

                datas = (ArrayList<HdhcRespData>) msg;
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(HdhcActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // 获取黑点列表 目前没有数据

        HdhcReqData hcRepData = new HdhcReqData();
//        hdhcReqData.setUid(Integer.parseInt(Global.uid));
        hdhcReqData.setType(2);

        loginModel.getAllListOfHdhc(HdhcActivity.this, hdhcReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                hdData = (ArrayList<HdhcRespData>) msg;
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(HdhcActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private HdhcDetailRespData data;


    @Override
    protected void addAction() {
        ll_hc.setOnClickListener(this);
        ll_hd.setOnClickListener(this);
        iv_exit.setOnClickListener(this);

        // 点击黑点黑车条目 跳转详情页面
//        lv_hdhc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //  黑车
//                if (flag == 0) {
//                    HdhcRespData hdhcRespData = datas.get(position); // 获取当前点击条目的ID, 根据ID 跳转详情
//                    HdhcDetailReqData hdhcDetailReqData = new HdhcDetailReqData();
//                    hdhcDetailReqData.setDid(Integer.parseInt(hdhcRespData.getId()));
//
//                    loginModel.getListOfDetailHdhc(HdhcActivity.this, hdhcDetailReqData, new OnLoadComBackListener() {
//                        @Override
//                        public void onSuccess(Object msg) {
//                             data = JSON.parseObject((String) msg, HdhcDetailRespData.class);
//                            Message message = new Message();
//                            message.arg1 = 2;
//                            handler.sendMessage(message);
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                        Toast.makeText(HdhcActivity.this,msg.toString(),Toast.LENGTH_LONG).show();
//                        }
//                    });
////                    Intent intent = new Intent(HdhcActivity.this, ReportActivity.class);
//                        Intent intent = new Intent(HdhcActivity.this, HdhcCheckActivity.class);
//                        intent.putExtra("11",1);
//                        startActivity(intent);
//
//
//                } else {
//                    // 黑点
//                    Toast.makeText(HdhcActivity.this, "点击了黑点", Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//        });
    }

    @Override
    protected void findViews() {
        ll_hc = (LinearLayout) findViewById(R.id.ll_hc);
        ll_hd = (LinearLayout) findViewById(R.id.ll_hd);
        tv_hd = (TextView) findViewById(R.id.tv_hd);
        tv_hc = (TextView) findViewById(R.id.tv_hc);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        lv_hdhc = (ListView) findViewById(R.id.lv_hdhc);
         tv_show = (TextView) findViewById(R.id.tv_show);
    }

    HdhAdapter hdhAdapter;

    @Override
    public void onClick(View v) {
        if (R.id.ll_hd == v.getId()) {
            flag = 1;  // 黑点添加flag
            // 设置选择
            setChoice(ll_hd, tv_hd);
                // 显示
            if (hdData.size() == 0){
                tv_show.setVisibility(View.VISIBLE);
                lv_hdhc.setVisibility(View.GONE);
                return;
            }
            tv_show.setVisibility(View.GONE);
            lv_hdhc.setVisibility(View.VISIBLE);
                hdhAdapter.setList(hdData);
                hdhAdapter.notifyDataSetInvalidated();

        } else if (R.id.ll_hc == v.getId()) {  //黑车
            flag = 0;
            setChoice(ll_hc, tv_hc);
            // 显示
            if (datas.size() == 0){
                tv_show.setVisibility(View.VISIBLE);
                lv_hdhc.setVisibility(View.GONE);
                return;
            }
            tv_show.setVisibility(View.GONE);
            lv_hdhc.setVisibility(View.VISIBLE);

                hdhAdapter.setList(datas);
                hdhAdapter.notifyDataSetInvalidated();


        } else if (v.getId() == R.id.iv_exit) {
            finish();
        }

    }

    private void setChoice(LinearLayout ll_hd, TextView tv_hd) {
        resetTabBtn();
        ll_hd.setBackgroundResource(R.drawable.button_style_shixin);
        tv_hd.setTextColor(Color.BLACK);
    }

    private void resetTabBtn() {
        ll_hd.setBackgroundResource(R.drawable.corner_toumingbg_grayotherborder_2dp);
        ll_hc.setBackgroundResource(R.drawable.corner_toumingbg_grayotherborder_2dp);

        tv_hd.setTextColor(Color.WHITE);
        tv_hc.setTextColor(Color.WHITE);
    }
}
