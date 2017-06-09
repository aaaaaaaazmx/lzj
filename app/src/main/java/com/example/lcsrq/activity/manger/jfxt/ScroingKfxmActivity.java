package com.example.lcsrq.activity.manger.jfxt;

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

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyZgActivity;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.adapter.GetlistjfpAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GetlistjfpReqData;
import com.example.lcsrq.bean.respbean.GetlistjfpData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.ImageLoader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * 记分系统- 扣分选项
 */

public class ScroingKfxmActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private LinearLayout commonLeftBtn;
    private int type = -1;
    private LoginModel loginModel;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private ArrayList<GetlistjfpData> jfdatas;
    private ListView lv_kfxm;
    private GetlistjfpAdapter getlistjfpAdapter;
    private ArrayList<GetlistjfpData> loadMoreData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_jfxt_kfxx
        setContentView(R.layout.activity_kfxm);
        showLoading("正在加载");
        loginModel = new LoginModel();
        type = getIntent().getIntExtra("type",-1); //  计分类型
        // 根据计分类型获取计分项目列表
        getListJfp();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                getlistjfpAdapter = new GetlistjfpAdapter(ScroingKfxmActivity.this);
                getlistjfpAdapter.setDatas(jfdatas);
                lv_kfxm.setAdapter(getlistjfpAdapter);
                getlistjfpAdapter.notifyDataSetChanged();
            }
            else if (msg.arg1 == 2){
                page++;
                jfdatas.addAll(loadMoreData);
                getlistjfpAdapter.notifyDataSetChanged();
            }
        }
    };

    // 根据计分类型获取计分项目列表
    private void getListJfp(){
        GetlistjfpReqData getlistjfpReqData = new GetlistjfpReqData();
        getlistjfpReqData.setType(type); //记分类型
        loginModel.getListJfp(ScroingKfxmActivity.this, getlistjfpReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                jfdatas = (ArrayList<GetlistjfpData>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(ScroingKfxmActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListJfp();
                    }
                });
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);

        lv_kfxm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent1 = new Intent(ScroingKfxmActivity.this, HdhcCheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",jfdatas.get(position).getTitle()+"");
                bundle.putString("ID",jfdatas.get(position).getId()+"");
                intent1.putExtra("bundle",bundle);
                setResult(100,intent1);

                finish();
            }
        });
    }

    @Override
    protected void findViews() {

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("扣分选项");

        // 下拉刷新
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        // 异常加载页面
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);

        lv_kfxm = (ListView) findViewById(R.id.lv_kfxm);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
//            startActivity(new Intent(ScroingKfxmActivity.this,ScoringActivity.class));
            finish();
        }
    }
    private int  page = 2 ;
    private void LoadMore(){
        GetlistjfpReqData getlistjfpReqData = new GetlistjfpReqData();
        getlistjfpReqData.setType(type); //记分类型
        getlistjfpReqData.setPage(page);
        loginModel.getListJfp(ScroingKfxmActivity.this, getlistjfpReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadMoreData = (ArrayList<GetlistjfpData>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(ScroingKfxmActivity.this, "没有更多的数据!", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
                LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        getListJfp();
    }
}
