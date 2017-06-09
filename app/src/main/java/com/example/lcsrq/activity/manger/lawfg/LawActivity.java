package com.example.lcsrq.activity.manger.lawfg;

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
import com.example.lcsrq.adapter.LawFlfgAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/1.
 */

public class LawActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView lv_law;
    private LoginModel loginModel;
    private LinearLayout commonLeftBtn;
    private ArrayList<ContentRespData> flfgList = new ArrayList<ContentRespData>();  //法律法规
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private LawFlfgAdapter lawFlfgAdapter;
    private TextView tv_fl;
    private TextView tv_xzfg;
    private TextView tv_dffg;
    private TextView tv_gfbz;
    private TextView tv_gfwj;
    private TextView tv_qita;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        loginModel = new LoginModel();
        showLoading("正在加载");
        initData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                lawFlfgAdapter = new LawFlfgAdapter(LawActivity.this);
                lawFlfgAdapter.setFlfgList(flfgList);
                lv_law.setAdapter(lawFlfgAdapter);
                lawFlfgAdapter.notifyDataSetChanged(); // 刷新
            }else if (msg.arg1 == 2){
                page++;
                flfgList.addAll(loadMoreData);
                lawFlfgAdapter.notifyDataSetChanged();
            }
        }
    };

    private int CatId= 5;

    private void initData() {
        final ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(CatId);

        loginModel.getLBList(this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                flfgList = (ArrayList<ContentRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
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
    private ArrayList<ContentRespData> loadMoreData;

    //加载更多
    private void LoadMore() {
        final ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(CatId); //  区别
        contentReqData.setPage(page);
        loginModel.getLBList(this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoreData = (ArrayList<ContentRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(LawActivity.this, "没有更多的数据了!", Toast.LENGTH_LONG).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }




    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        lv_law.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LawActivity.this,LawDetailActivity.class);
                intent.putExtra("did",flfgList.get(position).getId());
                startActivity(intent);
//                startActivity(new Intent(LawActivity.this,LawDetailActivity.class));
            }
        });

        tv_fl.setOnClickListener(this);
        tv_xzfg.setOnClickListener(this);
        tv_dffg.setOnClickListener(this);
        tv_gfbz.setOnClickListener(this);
        tv_gfwj.setOnClickListener(this);
        tv_qita.setOnClickListener(this);
    }

    @Override
    protected void findViews() {

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("法律法规");

         commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        lv_law = (ListView) findViewById(R.id.lv_law);

        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);


        // 6个组件
        tv_fl = (TextView) findViewById(R.id.tv_fl);
        tv_xzfg = (TextView) findViewById(R.id.tv_xzfg);
        tv_dffg = (TextView) findViewById(R.id.tv_dffg);
        tv_gfbz = (TextView) findViewById(R.id.tv_gfbz);
        tv_gfwj = (TextView) findViewById(R.id.tv_gfwj);
        tv_qita = (TextView) findViewById(R.id.tv_qita);
    }

    @Override
    public void onClick(View v) {
    if (v.getId() == R.id.commonLeftBtn){
        finish();
    }
    // 法律
    if (v.getId() == R.id.tv_fl){
            CatId = 25;
            initData();
    }
    //行政法规
    if (v.getId() == R.id.tv_xzfg){
        CatId = 26;
        initData();
    }
    // 地方法规
     if (v.getId() == R.id.tv_dffg){
         CatId = 27;
         initData();
     }
     // 规范标准
      if (v.getId() == R.id.tv_gfbz){
          CatId = 28;
          initData();
      }
      // 规范性文件
       if (v.getId() == R.id.tv_gfwj){
           CatId = 29;
           initData();
       }
       // 其他
        if (v.getId() == R.id.tv_qita){
            CatId = 21;
            initData();
        }
    }

    private int page =2;
    //下拉刷新,和加在更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page =2;
        initData();
    }
}
