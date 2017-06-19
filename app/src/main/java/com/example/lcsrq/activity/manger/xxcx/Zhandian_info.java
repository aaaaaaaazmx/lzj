package com.example.lcsrq.activity.manger.xxcx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.adapter.CarAdapter;
import com.example.lcsrq.adapter.ZhanAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.InfoReqData;
import com.example.lcsrq.bean.resq.XxCx_CarResp;
import com.example.lcsrq.bean.resq.XxCx_ZhandianResp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class Zhandian_info extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{

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
        loginModel = new LoginModel();
        initData();
    }

    private int page = 2;  // 初始化为0
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                carAdapter = new CarAdapter(Zhandian_info.this);
                carAdapter.setData(data);
                lv_car.setAdapter(carAdapter);


            }else if (msg.arg1 == 2){
                page++;
                data.addAll(loadMoreData);
                carAdapter.notifyDataSetChanged();
            }
        }
    };


    private void initData() {
        InfoReqData infoReqData = new InfoReqData();
        infoReqData.setType(type);
        if (TextUtils.isEmpty(keyWord)){
            keyWord = "";
        }
        infoReqData.setKeyword(keyWord);
        loginModel.getInfo_car(Zhandian_info.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                data = (ArrayList<XxCx_CarResp>) msg;
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
        loginModel.getInfo_car(Zhandian_info.this, infoReqData, new OnLoadComBackListener() {

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
                Toast.makeText(Zhandian_info.this,"没有更多的数据了",Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }


    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        // 跳转供应站
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Zhandian_info.this, GyzDetailActivity.class);
                intent.putExtra("data_id",data.get(position).getId());
                startActivity(intent);
            }
        });
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
        commonTitleTv.setText("站点");

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

    // 下拉加载以及更多加载
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
