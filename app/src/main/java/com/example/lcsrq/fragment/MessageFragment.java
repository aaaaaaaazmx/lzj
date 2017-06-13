package com.example.lcsrq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.CountyDetailActivity;
import com.example.lcsrq.activity.manger.xxcx.MessageIntentActivity;
import com.example.lcsrq.adapter.MessageAdapte;
import com.example.lcsrq.adapter.MessageFAdapter;
import com.example.lcsrq.base.BaseFragment;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class MessageFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private LayoutInflater inflater;
    private boolean scannerVisible = true;
    private ListView lv_list;
    private LoginModel loginModel;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private ArrayList<ContentRespData> tzData;
    private MessageAdapte messageAdapte;
    private View view;
    private ArrayList<ContentRespData> loadMoreData;

    public MessageFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_message, container, false);
//        loginModel = new LoginModel();
//        findViews(view);
//        addAction();
//        initData();
//        Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();
//        return view;

        if (view == null){
            view =  inflater.inflate(R.layout.activity_message,null);
            loginModel = new LoginModel();
            findViews(view);
            addAction();
            initData();
        }else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
        }
        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){

                messageAdapte = new MessageAdapte(getActivity());
                messageAdapte.setDatas(tzData);
                lv_list.setAdapter(messageAdapte);
                messageAdapte.notifyDataSetChanged();
            }
            else if(msg.arg1 == 2){
                // 刷新
                page++;
                tzData.addAll(loadMoreData);
                messageAdapte.notifyDataSetChanged();
            }
        }
    };

    private void initData() {
        // 通知通报
        final ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(33);

        loginModel.getLBList(getActivity(), contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                tzData = (ArrayList<ContentRespData>) msg;
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
    private int page = 2;

    private void LoadMore(){
        // 通知通报
        final ContentReqData contentReqData = new ContentReqData();
        contentReqData.setCatid(33);
        contentReqData.setPage(page);
        loginModel.getLBList(getActivity(), contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
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
                Toast.makeText(getActivity(),"没有更多的数据",Toast.LENGTH_SHORT).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }
    private LinearLayout commonLeftBtn;
    @Override
    public void findViews(View root) {
        lv_list =(ListView) root.findViewById(R.id.lv_list);

        TextView commonTitleTv = (TextView) root.findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("通知通报");

        type_page_progress = (ProgressActivity) root.findViewById(R.id.type_page_progress);
        pullToRefreshView = (PullToRefreshView) root.findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

    }

    @Override
    public void addAction() {
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), MessageIntentActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void call(int callID, Object... args) {

    }

    // 下拉刷新和加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        initData();
    }
}
