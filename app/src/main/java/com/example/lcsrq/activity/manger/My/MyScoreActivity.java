package com.example.lcsrq.activity.manger.My;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.activity.manger.jfxt.ScoringActivity;
import com.example.lcsrq.activity.manger.jfxt.ScoringListActivity;
import com.example.lcsrq.adapter.ScoringAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GetlistjftReqData;
import com.example.lcsrq.bean.req.SubmitjftstatusReqData;
import com.example.lcsrq.bean.respbean.GetlistjftData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.logging.Level;

import static com.example.lcsrq.R.id.commonRightImage;

/**
 * Created by 苏毅 on 2017/6/3.
 *  我的记分
 */

public class MyScoreActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener {

    private ListView lv_myjf;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private ScoringAdapter scoringAdapter;
    private PullToRefreshView pullToRefreshView;
    private LoginModel loginModel;
    private ProgressActivity type_page_progress;
    private ArrayList<GetlistjftData> myjftData;
    private ArrayList<GetlistjftData> loadMoreData;
    private AlertDialog builder;
    private TextView sure;
    private TextView cancle;
    private String did;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscore_activity);
        loginModel = new LoginModel();
        showLoading("正在加载");
        Getlistjft();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1==1){
                scoringAdapter = new ScoringAdapter(MyScoreActivity.this);
                scoringAdapter.setDatas(myjftData);
                lv_myjf.setAdapter(scoringAdapter);
                scoringAdapter.notifyDataSetChanged();
            }
            else if (msg.arg1 == 2 ){
                page++;
                myjftData.addAll(loadMoreData);
                scoringAdapter.notifyDataSetChanged();
            }

        }
    };
    private  int status = -1;
    // 提交记分状态
    private void Submitjftstatus(){
        SubmitjftstatusReqData submitjftstatusReqData = new SubmitjftstatusReqData();
        submitjftstatusReqData.setStatus_uid(Integer.parseInt(Global.uid)); // 操作用户ID
        submitjftstatusReqData.setDid(Integer.parseInt(did)); // 记分项目ID
        submitjftstatusReqData.setStatus(status);
        loginModel.getSubmitjft(MyScoreActivity.this, submitjftstatusReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                Toast.makeText(MyScoreActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                Getlistjft();
                closeDialog();
                builder.dismiss();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyScoreActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
                scoringAdapter.notifyDataSetChanged();
                Getlistjft();
                closeDialog();
                builder.dismiss();
            }
        });
    }


    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);

        //  点击条目弹出对话框
        lv_myjf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 管理人员不能点
                if (Global.m_roleid.equals("3")){
                    return;
                }

                //  只有代签收能点
                if (myjftData.get(position).getStatus().equals("1")) {

                    final LayoutInflater inflaterDl = LayoutInflater.from(MyScoreActivity.this);
                    LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.js_pop, null);
                    builder = new AlertDialog.Builder(MyScoreActivity.this).create();
                    builder.show();
                    builder.getWindow().setContentView(layout);

                    WindowManager windowManager = MyScoreActivity.this.getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
                    lp.width = (int) (display.getWidth()) * 4 / 5; //设置宽度
                    lp.height = (int) ((int) (display.getHeight()) / 3.9);
                    builder.getWindow().setAttributes(lp);
                    // 确认
                    sure = (TextView) layout.findViewById(R.id.sure);
                    // 取消
                    cancle = (TextView) layout.findViewById(R.id.cancle);

                    // 签收
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showLoading("正在提交");
                            did = myjftData.get(position).getId();
                            status = 2;
                            Submitjftstatus();
                        }
                    });

                    // 复议
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showLoading("正在提交");
                            did = myjftData.get(position).getId();
                            status = 3;
                            Submitjftstatus();
                        }
                    });
                }else {
                    return;
                }
            }
        });
    }
    private void Getlistjft() {

        GetlistjftReqData getlistjftReqData = new GetlistjftReqData();
        getlistjftReqData.setUid(Integer.parseInt(Global.uid));

        loginModel.getListjft(MyScoreActivity.this, getlistjftReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                myjftData = (ArrayList<GetlistjftData>) msg;

                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyScoreActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
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
        getlistjftReqData.setUid(Integer.parseInt(Global.uid));
        getlistjftReqData.setPage(page);

        loginModel.getListjft(MyScoreActivity.this, getlistjftReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadMoreData = (ArrayList<GetlistjftData>) msg;

                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyScoreActivity.this, "没有更多的数据", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }
    @Override
    protected void findViews() {

        lv_myjf = (ListView) findViewById(R.id.lv_myjf);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("我的计分");
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        scoringAdapter = new ScoringAdapter(MyScoreActivity.this);
        lv_myjf.setAdapter(scoringAdapter);

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
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        Getlistjft();
    }
}
