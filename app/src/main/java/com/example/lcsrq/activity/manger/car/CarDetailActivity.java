package com.example.lcsrq.activity.manger.car;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.adapter.CarMangerAdapter;
import com.example.lcsrq.adapter.GYZAdapter;
import com.example.lcsrq.adapter.GyzLskfAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCarDetailReqData;
import com.example.lcsrq.bean.respbean.Supplylist;
import com.example.lcsrq.bean.resq.ContentCarDetailRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class CarDetailActivity extends BaseActivity {

    private ExpandableListView kf_list, gyz_list;
    private Map<String, List<String>> dataset = new HashMap<>();
    private String[] parentList = new String[]{"负责供应站", "second", "third"};

    private List<String> childrenList1 = new ArrayList<>();
    private List<String> childrenList2 = new ArrayList<>();


    private  LinearLayout commonLeftBtn;
    private  TextView commonTitleTv;
    private   LoginModel loginModel;
    private ContentCarDetailRespData contentCarDetailRespData;  // 车辆管理详情返回的数据
    private String did;

    private  TextView tv_cp,tv_jsy,tv_jf,tv_yyy,tv_sj_number,tv_dl,tv_jq,tv_company;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loginModel   = new LoginModel();
        initData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
//                System.out.println("datattttt" +  contentCarDetailRespData.getCompany());
                tv_cp.setText(contentCarDetailRespData.getTitle());
                tv_jsy.setText(contentCarDetailRespData.getDriver1_name());
//                tv_jf.setText();    缺少计分情况
                tv_yyy.setText(contentCarDetailRespData.getDriver2_name());
                tv_sj_number.setText(contentCarDetailRespData.getTel());
                tv_dl.setText(contentCarDetailRespData.getYxtime());
                tv_jq.setText(contentCarDetailRespData.getXkz());
                tv_company.setText(contentCarDetailRespData.getCompany());

                //供应站信息
                ArrayList<Supplylist> supplylist = contentCarDetailRespData.getSupplylist();
                String shenmezhan = "";
                for (Supplylist list1 : supplylist){
                    String title = list1.getTitle();
                    childrenList1.add(title);
                }
                dataset.put(parentList[0], childrenList1);
                gyz_list.setGroupIndicator(null);
                GYZAdapter myExpandableListViewAdapter = new GYZAdapter(CarDetailActivity.this, dataset, parentList);
                gyz_list.setAdapter(myExpandableListViewAdapter);

            }
        }
    };

    // 加载车辆详情数据
    private void initData() {

        ContentCarDetailReqData carDetailReqData = new ContentCarDetailReqData();  // 请求参数
        carDetailReqData.setDid(Integer.parseInt(did));
        carDetailReqData.setUid(Integer.parseInt(Global.uid));

        loginModel.getListOfCarDetail(CarDetailActivity.this, carDetailReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {

                contentCarDetailRespData  = JSON.parseObject((String) msg, ContentCarDetailRespData.class);
//                System.out.println("contentCarDetailRespData" + contentCarDetailRespData.getCompany());

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);


            }

            @Override
            public void onError(String msg) {

            }
        });


    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        Intent intent = getIntent();
        did  = intent.getStringExtra("did"); // 18

        kf_list = (ExpandableListView) findViewById(R.id.kf_list);// 扣分下拉列表
        gyz_list = (ExpandableListView) findViewById(R.id.gyz_list);    // 供应站下拉列表



        GyzLskfAdapter adapter = new GyzLskfAdapter(this);

//         去掉左边箭头
        kf_list.setGroupIndicator(null);


        kf_list.setAdapter(adapter);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("车辆管理");

        // 车牌
        tv_cp = (TextView) findViewById(R.id.tv_cp);
        // 驾驶员
        tv_jsy = (TextView) findViewById(R.id.tv_jsy);
        // 计分情况
         tv_jf = (TextView) findViewById(R.id.tv_jf);
        // 押运员
         tv_yyy = (TextView) findViewById(R.id.tv_yyy);
        // 司机电话
         tv_sj_number = (TextView) findViewById(R.id.tv_sj_number);
        // 道路运输证
         tv_dl = (TextView) findViewById(R.id.tv_dl);
        // 禁区运输证
         tv_jq = (TextView) findViewById(R.id.tv_jq);
        // 公司
         tv_company = (TextView) findViewById(R.id.tv_company);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
    }


}
