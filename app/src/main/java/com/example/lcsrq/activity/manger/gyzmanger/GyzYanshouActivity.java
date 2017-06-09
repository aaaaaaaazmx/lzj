package com.example.lcsrq.activity.manger.gyzmanger;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.adapter.YanShouAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.GyzCheckZgJlReqData;
import com.example.lcsrq.bean.req.TiJiaoZgstate;
import com.example.lcsrq.bean.respbean.Data_ckloglist;
import com.example.lcsrq.bean.resq.GyzCheckZgJlRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.UiTool;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 苏毅 on 2017/5/13.
 * 验收页面
 */

public class GyzYanshouActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener {

    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private LoginModel loginModel;
    private  ArrayList<Data_ckloglist> yashouDatas;
    private ArrayList<GyzCheckZgJlRespData> Datas;
    private String supply_id;
    private PullToRefreshView pullToRefreshView;
    private ListView lv_yanshou;
    private ProgressActivity type_page_progress;
    private YanShouAdapter yanShouAdapter;
    private TextView commonRightText;
    private AlertDialog builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanshou);
//        initData(); //加载第二页的数据或者是第三页的数据
        yashouDatas = ( ArrayList<Data_ckloglist>) getIntent().getSerializableExtra("data");
        Toast.makeText(GyzYanshouActivity.this,yashouDatas.size() + "",Toast.LENGTH_SHORT).show();
        supply_id = getIntent().getStringExtra("supply_id");
        Toast.makeText(GyzYanshouActivity.this,supply_id+"",Toast.LENGTH_SHORT).show();
        yanShouAdapter = new YanShouAdapter(GyzYanshouActivity.this);
        yanShouAdapter.setDatas(yashouDatas);
        lv_yanshou.setAdapter(yanShouAdapter);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg.arg1 == 1) {
//                page++;
//                shanglaPager = page;
//                yashouDatas.addAll(Datas);
//                yanShouAdapter.notifyDataSetChanged();
//            }
        }
    };
    private int page = 2;
    // 加载数据
    private void initData() {
        loginModel = new LoginModel();
        final GyzCheckZgJlReqData gyzCheckZgJlReqData = new GyzCheckZgJlReqData();
        gyzCheckZgJlReqData.setPage(page);
        gyzCheckZgJlReqData.setStatus(1);// 待整改
        gyzCheckZgJlReqData.setSupply_id(Integer.parseInt(supply_id));
        loginModel.putGyzCheckZgJl(GyzYanshouActivity.this, gyzCheckZgJlReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                Datas = (ArrayList<GyzCheckZgJlRespData>) msg;

                Message message = handler.obtainMessage();
                message.arg1 =1;
                handler.sendMessage(message);
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(GyzYanshouActivity.this,"没有更多的数据了!",Toast.LENGTH_SHORT).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                closeDialog();
            }
        });
        }
    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        commonRightText.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("验收");

        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("跳转检查");

        // 下拉刷新
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        lv_yanshou = (ListView) findViewById(R.id.lv_yanshou);

//        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
    }

    private void Tijiao(final int position) {
        //状态码
        String status = 3 + ""; // 验收
        String check_id = yashouDatas.get(position).getLogid(); //  查处ID
        //检查码
        // 用户UID
        String uid = Global.uid;  // 用户ID
        // 设置请求参数
        TiJiaoZgstate tiJiaoZgstate = new TiJiaoZgstate();
        tiJiaoZgstate.setDid(Integer.parseInt(check_id));
        tiJiaoZgstate.setStatus_uid(Integer.parseInt(uid));

//        if (Integer.parseInt(status) == 1) {
//            status = "2";
//        } else if (Integer.parseInt(status) == 2) {
//            status = "3";
//        } else {
//            status = "3";
//        }
        tiJiaoZgstate.setStatus(Integer.parseInt(status));
        //提交整改准记录状态
        loginModel.TijiaZgstate(GyzYanshouActivity.this, tiJiaoZgstate, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                if (position  == yashouDatas.size()){
                    Toast.makeText(GyzYanshouActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                    yanShouAdapter.notifyDataSetChanged();
                    Toast.makeText(GyzYanshouActivity.this, "全部验收", Toast.LENGTH_SHORT).show();
                    closeDialog();
                    startActivity(new Intent(GyzYanshouActivity.this,GyzCheckActivity.class));
                    finish();
                }
            }
            @Override
            public void onError(String msg) {
                closeDialog();
                Toast.makeText(GyzYanshouActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Tijiao(position);
                    }
                });;
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }else if (v.getId() == R.id.commonRightText){
            // 点击一键验收
//            final LayoutInflater inflaterDl = LayoutInflater.from(GyzYanshouActivity.this);
//            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.hdhc_sure, null);
//            builder = new AlertDialog.Builder(GyzYanshouActivity.this).create();
//            builder.show();
//            builder.getWindow().setContentView(layout);
//
//            WindowManager windowManager = GyzYanshouActivity.this.getWindowManager();
//            Display display = windowManager.getDefaultDisplay();
//            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
//            lp.width = (int) (display.getWidth()) * 4/5; //设置宽度
//            lp.height = (int) ((int)(display.getHeight())/3.5);
//            builder.getWindow().setAttributes(lp);
//
//            TextView tv_sure = (TextView) layout.findViewById(R.id.tv_sure);
//            TextView tv_exit = (TextView) layout.findViewById(R.id.tv_exit);
//
//            tv_exit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    builder.dismiss();
//                }
//            });
//
//            tv_sure.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   startActivity(new Intent(GyzYanshouActivity.this,GyzCheckActivity.class));
//                    finish();
//                }
//            });

//            final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(GyzYanshouActivity.this, R.style.Theme_Transparent)).create();
//            dialog.setView(LayoutInflater.from(GyzYanshouActivity.this).inflate(R.layout.yanshou_pop, null), 0, 0, 0, 0);
//            UiTool.setDialog(GyzYanshouActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

            final LayoutInflater inflaterDl = LayoutInflater.from(GyzYanshouActivity.this);
            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.yanshou_pop, null);
            builder = new AlertDialog.Builder(GyzYanshouActivity.this).create();
            builder.show();
            builder.getWindow().setContentView(layout);

            WindowManager windowManager = GyzYanshouActivity.this.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()) * 4/5; //设置宽度
            lp.height = (int) ((int)(display.getHeight())/3.9);
            builder.getWindow().setAttributes(lp);

            TextView sure = (TextView) layout.findViewById(R.id.sure);
            TextView cancle = (TextView) layout.findViewById(R.id.cancle);

            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  一件验收
//                    showLoading("正在提交");
//                    for (int i = 0; i < yashouDatas.size(); i++){
//                        Tijiao(i);
//                    }
                // 挑战检查页面
                    startActivity(new Intent(GyzYanshouActivity.this,GyzCheckActivity.class));
                    finish();
                }
            });
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    dialog.dismiss();
                    builder.dismiss();
                }
            });

        }
    }

    private int shanglaPager = 0;
    // 下拉刷新和加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
                initData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
            yanShouAdapter.notifyDataSetChanged();
            pullToRefreshView.onHeaderRefreshComplete();
    }
}
