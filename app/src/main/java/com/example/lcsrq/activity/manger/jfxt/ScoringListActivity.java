package com.example.lcsrq.activity.manger.jfxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyHdActivity;
import com.example.lcsrq.adapter.ScoringAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GetlistjftReqData;
import com.example.lcsrq.bean.respbean.GetlistjftData;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * / 计分系统
 */

public class ScoringListActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{

    private ListView lv_jfxt;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private ScoringAdapter scoringAdapter;
    private ImageView commonRightImage;
    private PullToRefreshView pullToRefreshView;
    private LoginModel loginModel;
    private ProgressActivity type_page_progress;
    private ArrayList<GetlistjftData> jftData;
    private ArrayList<GetlistjftData> loadaMoreData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroing_item);
        loginModel = new LoginModel();
        showLoading("正在加载");
        Getlistjft();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新
        Getlistjft();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                scoringAdapter = new ScoringAdapter(ScoringListActivity.this);
                scoringAdapter.setDatas(jftData);
                lv_jfxt.setAdapter(scoringAdapter);

            }
            else if (msg.arg1 ==2){
                page++;
                jftData.addAll(loadaMoreData);
                scoringAdapter.notifyDataSetChanged();
            }
        }
    };

    private void Getlistjft() {
        GetlistjftReqData getlistjftReqData = new GetlistjftReqData();

        loginModel.getListjft(ScoringListActivity.this, getlistjftReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                jftData = (ArrayList<GetlistjftData>) msg;

                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(ScoringListActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Getlistjft();
                    }
                });

            }
        });

    }

    private int page = 2;
    private void LoadMore(){
        GetlistjftReqData getlistjftReqData = new GetlistjftReqData();
        getlistjftReqData.setPage(page);
        loginModel.getListjft(ScoringListActivity.this, getlistjftReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadaMoreData = (ArrayList<GetlistjftData>) msg;

                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(ScoringListActivity.this,"没有更多的数据", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        commonRightImage.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        lv_jfxt = (ListView) findViewById(R.id.lv_jfxt);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("记分管理");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonRightImage = (ImageView) findViewById(R.id.commonRightImage);
        // 如果是管理人员  就显示开始记分
        if (Global.m_roleid.equals("3")){
            commonRightImage.setImageResource(R.mipmap.icon_jfgl);
            commonRightImage.setVisibility(View.VISIBLE);
        }else {
            commonRightImage.setVisibility(View.GONE);
        }



        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
        if (v.getId() == R.id.commonRightImage){
            // 跳转到选择页面
            startActivity(new Intent(ScoringListActivity.this,ScoringChoise.class));
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page =2;
        Getlistjft();
    }
}
