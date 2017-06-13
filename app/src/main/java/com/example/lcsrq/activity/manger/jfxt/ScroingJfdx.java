package com.example.lcsrq.activity.manger.jfxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.activity.manger.xxcx.CarInfo;
import com.example.lcsrq.activity.manger.xxcx.People_info;
import com.example.lcsrq.activity.manger.xxcx.Zhandian_info;
import com.example.lcsrq.adapter.JfdxAdapter;
import com.example.lcsrq.adapter.JfdxForZdAdater;
import com.example.lcsrq.adapter.PeoPleAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.InfoReqData;
import com.example.lcsrq.bean.resq.XxCx_CarResp;
import com.example.lcsrq.bean.resq.XxCx_peopleResp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class ScroingJfdx extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{

    private ImageView iv_sousuo;
    private ImageView iv_exit;
    private EditText et_name;
    private TextView tv_sousuo;
    private int type =  - 1;
    private String name;
    private LoginModel loginModel;
    private InfoReqData infoReqData;
    private ListView lv_jfdx;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private ArrayList<XxCx_peopleResp> propledata;
    private JfdxAdapter jfdxAdapter;
    private ArrayList<XxCx_peopleResp> loadMoreData;
    private ArrayList<XxCx_CarResp> zhandianData;
    private JfdxForZdAdater jfdxForZdAdater;
    private ArrayList<XxCx_CarResp> cardata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_jfdx);
        loginModel = new LoginModel();
        type = getIntent().getIntExtra("type", -1); //  查找类型
        infoReqData = new InfoReqData();
    }

    @Override
    protected void addAction() {
        tv_sousuo.setOnClickListener(this);
        iv_sousuo.setOnClickListener(this);
        iv_exit.setOnClickListener(this);

        lv_jfdx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (type){

                    case 1: // 人
                        Intent intent1 = new Intent(ScroingJfdx.this, HdhcCheckActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("M_nickname",propledata.get(position).getM_nickname()+"");
                        bundle.putString("ID",propledata.get(position).getId()+"");
                        intent1.putExtra("bundle",bundle);
                        setResult(200,intent1);
                        finish();
                        break;
                    case 2: //站点
                        Intent intent2 = new Intent(ScroingJfdx.this, HdhcCheckActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("M_nickname",zhandianData.get(position).getTitle()+"");
                        bundle2.putString("ID",zhandianData.get(position).getId()+"");
                        intent2.putExtra("bundle",bundle2);
                        setResult(200,intent2);
                        finish();
                        break;

                    case 3: //车辆
                        Intent intent3 = new Intent(ScroingJfdx.this, HdhcCheckActivity.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("M_nickname",cardata.get(position).getTitle()+"");
                        bundle3.putString("ID",cardata.get(position).getId()+"");
                        intent3.putExtra("bundle",bundle3);
                        setResult(200,intent3);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    protected void findViews() {

        iv_sousuo = (ImageView) findViewById(R.id.iv_sousuo);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_sousuo = (TextView) findViewById(R.id.tv_sousuo);
        lv_jfdx = (ListView) findViewById(R.id.lv_jfdx);

        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 ==1){
                jfdxAdapter = new JfdxAdapter(ScroingJfdx.this);
                jfdxAdapter.setDatas(propledata);
                lv_jfdx.setAdapter(jfdxAdapter);
                jfdxAdapter.notifyDataSetChanged();
            }
            if (msg.arg1 == 2){
                page++;
                propledata.addAll(loadMoreData);
                jfdxAdapter.notifyDataSetChanged();
            }
            // 站点
            if (msg.arg1 == 3){
                jfdxForZdAdater = new JfdxForZdAdater(ScroingJfdx.this);
                jfdxForZdAdater.setDatas(zhandianData);
                lv_jfdx.setAdapter(jfdxForZdAdater);
                jfdxForZdAdater.notifyDataSetChanged();
            }
            //车辆
            if (msg.arg1 == 4){
                jfdxForZdAdater = new JfdxForZdAdater(ScroingJfdx.this);
                jfdxForZdAdater.setDatas(cardata);
                lv_jfdx.setAdapter(jfdxForZdAdater);
                jfdxForZdAdater.notifyDataSetChanged();
            }
        }
    };
    // 获取人
    private  void getPeoPle(){
        if (!TextUtils.isEmpty(name)){
            infoReqData.setKeyword(name);
        }
        infoReqData.setType(type);

        // 查询人
        loginModel.getInfo_people(ScroingJfdx.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                propledata = (ArrayList<XxCx_peopleResp>) msg;
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                // 点击重试,重新刷新
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPeoPle();
                    }
                });;
            }
        });
    }
    // 获取站点
    private void getZhandian() {

        infoReqData.setType(type);
        if (!TextUtils.isEmpty(name)){
            infoReqData.setKeyword(name);
        }

        loginModel.getInfo_car(ScroingJfdx.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                zhandianData = (ArrayList<XxCx_CarResp>) msg;
                Message message = new Message();
                message.arg1 = 3;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                // 点击重试,重新刷新
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getZhandian();
                    }
                });;
            }
        });
    }
    // 获取车辆
    private void getCar() {
        infoReqData.setType(type);

        if (!TextUtils.isEmpty(name)){
            infoReqData.setKeyword(name);
        }

        loginModel.getInfo_car(ScroingJfdx.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                closeDialog();
                cardata = (ArrayList<XxCx_CarResp>) msg;
                Message message = new Message();
                message.arg1 = 4;
                handler.sendMessage(message);
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                // 点击重试,重新刷新
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCar();
                    }
                });;
            }
        });
    }
    private int page = 2;
    private void LoadMore(){
        if (!TextUtils.isEmpty(name)){
            infoReqData.setKeyword(name);
        }

        if (type == -1){
            type = 1;
        }
        infoReqData.setType(type);
        infoReqData.setPage(page);
        // 查询人
        loginModel.getInfo_people(ScroingJfdx.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadMoreData = (ArrayList<XxCx_peopleResp>) msg;
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(ScroingJfdx.this,"没有更多的数据",Toast.LENGTH_SHORT).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_exit){
            finish();
        }
        // 搜索
        if (v.getId() == R.id.iv_sousuo){
            showLoading("正在加载");
            name = et_name.getText().toString();
            switch (type){
                case 1: //人
                    getPeoPle();
                    break;
                case 2: // 站点
                    getZhandian();
                    break;
                case 3: // 车辆
                    getCar();
                    break;
            }

        }
        // 搜索
        if (v.getId() == R.id.tv_sousuo){
            showLoading("正在加载");
            name = et_name.getText().toString();
            switch (type){
                case 1: //人
                    getPeoPle();
                    break;
                case 2: // 站点
                    getZhandian();
                    break;
                case 3: // 车辆
                    getCar();
                    break;
            }
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
            page = 2;
            getPeoPle();
    }
}
