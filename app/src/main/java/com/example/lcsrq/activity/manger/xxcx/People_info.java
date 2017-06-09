package com.example.lcsrq.activity.manger.xxcx;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.activity.manger.gyzmanger.Info;
import com.example.lcsrq.adapter.PeoPleAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.InfoReqData;
import com.example.lcsrq.bean.resq.XxCx_peopleResp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.UiTool;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class People_info extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private ListView lv_people;
    private LoginModel loginModel;
    private InfoReqData infoReqData;
    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private  ArrayList<XxCx_peopleResp>  data= new ArrayList<>();
    private int type;
    private String m_account;
    private AlertDialog builder;
    private String keyWord;
    private ArrayList<XxCx_peopleResp> loadMoreData;
    private PeoPleAdapter peoPleAdapter;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_info);
        showLoading("正在加载");
        loginModel = new LoginModel();
        infoReqData = new InfoReqData();
        initData();
    }
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String phonenum;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                // 跳传人的页面
                //                        // 弹出确认拨打框
//                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(People_info.this, R.style.Theme_Transparent)).create();
//                        dialog.setView(LayoutInflater.from(People_info.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
//                        UiTool.setDialog(People_info.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog
//
//                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
//                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
//                        TextView tipsTv = (TextView) dialog.findViewById(R.id.tipsTv);
//                        tipsTv.setText(m_account);
//                        sure.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // 手机被点击
//                                if (ContextCompat.checkSelfPermission(People_info.this,
//                                        Manifest.permission.CALL_PHONE)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(People_info.this,
//                                            new String[]{Manifest.permission.CALL_PHONE},
//                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
//                                } else {
//                                    callPhone(m_account);
//                                    dialog.dismiss();
//                                }
//                            }
//                        });
//                        cancle.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//设置宽度
// 手机被点击
                peoPleAdapter = new PeoPleAdapter(People_info.this, data, new PeoPleAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(final int position) {
//                        // 弹出确认拨打框
//                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(People_info.this, R.style.Theme_Transparent)).create();
//                        dialog.setView(LayoutInflater.from(People_info.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
//                        UiTool.setDialog(People_info.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog
//
//                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
//                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
//                        TextView tipsTv = (TextView) dialog.findViewById(R.id.tipsTv);
//                        tipsTv.setText(m_account);
//                        sure.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // 手机被点击
//                                if (ContextCompat.checkSelfPermission(People_info.this,
//                                        Manifest.permission.CALL_PHONE)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(People_info.this,
//                                            new String[]{Manifest.permission.CALL_PHONE},
//                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
//                                } else {
//                                    callPhone(m_account);
//                                    dialog.dismiss();
//                                }
//                            }
//                        });
//                        cancle.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
                        m_account = data.get(position).getM_account();

                        final LayoutInflater inflaterDl = LayoutInflater.from(People_info.this);
                        LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.sure_pop, null);
                        builder = new AlertDialog.Builder(People_info.this).create();
                        builder.show();
                        builder.getWindow().setContentView(layout);

                        TextView sure = (TextView) builder.findViewById(R.id.sure);
                        TextView cancle = (TextView) builder.findViewById(R.id.cancle);
                        TextView tipsTv = (TextView) builder.findViewById(R.id.tipsTv);
                        tipsTv.setText("联系电话 : " + m_account);
                        WindowManager windowManager = People_info.this.getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
                        lp.width = (int) ((int) (display.getWidth()) * 0.8); //设置宽度
                        lp.height = (int) ((int)(display.getHeight())/3.5);
                        builder.getWindow().setAttributes(lp);
                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 手机被点击
                                if (ContextCompat.checkSelfPermission(People_info.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(People_info.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else {
                                    callPhone(m_account);
                                    builder.dismiss();
                                }
                            }
                        });
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                            }
                        });
                    }
                });
                 lv_people.setAdapter(peoPleAdapter);
            }else if (msg.arg1 == 2){
                page++;
                data.addAll(loadMoreData);
                peoPleAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(phonenum);
            } else {
                Toast.makeText(People_info.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 打电话
    private void callPhone(String phonenum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "" + phonenum);
        intent.setData(data);
        //  检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }
        startActivity(intent);

    }

    private void initData() {
        infoReqData.setType(type);
        if (TextUtils.isEmpty(keyWord)){
            keyWord = "";
        }
        infoReqData.setKeyword(keyWord);
        // 查询人
        loginModel.getInfo_people(People_info.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                data = (ArrayList<XxCx_peopleResp>) msg;
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
    private int page= 2;

    private void loadMore(final int page) {
        infoReqData.setType(type);
        infoReqData.setPage(page);
        if (TextUtils.isEmpty(keyWord)){
            keyWord = "";
        }
        infoReqData.setKeyword(keyWord);
        // 查询人
        loginModel.getInfo_people(People_info.this, infoReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                loadMoreData = (ArrayList<XxCx_peopleResp>) msg;
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                closeDialog();
                Toast.makeText(People_info.this,"没有更多的数据了!",Toast.LENGTH_LONG).show();
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
        lv_people = (ListView) findViewById(R.id.lv_people);
//
         commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
         commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("人");

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
