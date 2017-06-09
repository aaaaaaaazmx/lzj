package com.example.lcsrq.activity.manger.xxcx;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.adapter.CarAdapter;
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
 * Created by 苏毅 on 2017/4/15.
 */

public class CarInfo extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView lv_car;
    private LoginModel loginModel;
    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private int type;
    private ArrayList<XxCx_CarResp> data = new ArrayList<>();
    private String keyWord;
    private ArrayList<XxCx_CarResp> loadMoreData;
    private CarAdapter carAdapter;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_info);
        showLoading("正在加载");
        loginModel = new LoginModel();
        initData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                carAdapter = new CarAdapter(CarInfo.this);
                carAdapter.setData(data);
                lv_car.setAdapter(carAdapter);
            }
            else if (msg.arg1 == 2){
                page++;
                data.addAll(loadMoreData);
                carAdapter.notifyDataSetChanged();
            }

        }
    };
    private int page = 2;
    
    private void initData() {
        InfoReqData infoReqData = new InfoReqData();
        infoReqData.setType(type);
        if (TextUtils.isEmpty(keyWord)){
            keyWord = "";
        }
        infoReqData.setKeyword(keyWord);
        loginModel.getInfo_car(CarInfo.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                closeDialog();
                data = (ArrayList<XxCx_CarResp>) msg;
                Message message = new Message();
                message.arg1 = 1;
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
                        initData();
                    }
                });;
            }
        });
    }

    private void loadMore(final int page) {
        InfoReqData infoReqData = new InfoReqData();
        infoReqData.setType(type);
        infoReqData.setPage(page);
        if (TextUtils.isEmpty(keyWord)){
            keyWord = "";
        }
        infoReqData.setKeyword(keyWord);
        loginModel.getInfo_car(CarInfo.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoreData = (ArrayList<XxCx_CarResp>) msg;
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

            }

            @Override
            public void onError(String msg) {
                closeDialog();
                Toast.makeText(CarInfo.this,"没有更多的数据了!",Toast.LENGTH_LONG).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }


    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        type = getIntent().getIntExtra("type", -1);
        keyWord = getIntent().getStringExtra("keyWord");
        lv_car = (ListView) findViewById(R.id.lv_car);
//
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("车");

        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
    }

    // 下拉刷新 以及加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            loadMore(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        initData();
    }
}
