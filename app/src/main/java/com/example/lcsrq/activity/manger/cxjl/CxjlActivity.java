package com.example.lcsrq.activity.manger.cxjl;

import android.content.Intent;
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
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.adapter.CxjlDzgAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.bean.resq.CxjlInfoRespDatas;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class CxjlActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener {
    private ListView lv_cxjl;
    private CxjlDzgAdapter cxjlDzgAdapter;
    private LoginModel loginModel;
    private LinearLayout commonLeftBtn;

    private    ArrayList<ContentRespData>  datas = new ArrayList<>();
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private ArrayList<ContentRespData> loadMoredatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxjl);
        showLoading("正在加载");
         loginModel = new LoginModel();
        initData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                cxjlDzgAdapter = new CxjlDzgAdapter(CxjlActivity.this,datas);
                lv_cxjl.setAdapter(cxjlDzgAdapter);
            }else if (msg.arg1 == 2){
                page++;
                datas.addAll(loadMoredatas);
                cxjlDzgAdapter.notifyDataSetChanged();
            }
        }
    };


    private void initData() {
        ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(13);
        loginModel.getLBList(CxjlActivity.this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
               datas = (ArrayList<ContentRespData>) msg;
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(CxjlActivity.this,msg.toString(),Toast.LENGTH_LONG).show();
                // 异常页面处理
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });;
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }

    private void LoadMore(int page) {
        ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(13);
        contentReqData.setPage(page);

        loginModel.getLBList(CxjlActivity.this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoredatas = (ArrayList<ContentRespData>) msg;
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(CxjlActivity.this,"没有更多的数据了",Toast.LENGTH_LONG).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }

    @Override
    protected void addAction() {
        lv_cxjl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id1 = datas.get(position).getId();
                String title = datas.get(position).getTitle();
                Intent intent = new Intent(CxjlActivity.this, CxInfoActivity.class);
                intent.putExtra("did",id1);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        commonLeftBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("诚信记录");

         commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        lv_cxjl = (ListView) findViewById(R.id.lv_cxjl);

        //  下拉刷新
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        // 异常处理
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
    }
    private  int page = 2;
    //下拉刷新和加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            LoadMore(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        initData();
    }
}
