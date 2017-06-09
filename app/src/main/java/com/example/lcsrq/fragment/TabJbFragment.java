package com.example.lcsrq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyHdDetailsActivity;
import com.example.lcsrq.activity.manger.My.MyJuBaoActivity;
import com.example.lcsrq.activity.manger.hdhc.DfzwActivity;
import com.example.lcsrq.activity.manger.hdhc.DfzwDetaiActivity;
import com.example.lcsrq.adapter.JuBaoAdapter;
import com.example.lcsrq.bean.req.HdhcReqData;
import com.example.lcsrq.bean.resq.AllCclistRespData;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/6.
 * //举报Fragment
 */

public class TabJbFragment extends Fragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private LoginModel loginModel;
    private ListView lv_list;
    private int position;
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private ArrayList<JuBaoBean> hcData;
    private JuBaoAdapter juBaoAdapter;
    private ArrayList<JuBaoBean> loadMoreData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_jv_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginModel = new LoginModel();
        initView(view);
        initData();
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                juBaoAdapter = new JuBaoAdapter(getActivity());
                juBaoAdapter.setList(hcData);
                lv_list.setAdapter(juBaoAdapter);
                juBaoAdapter.notifyDataSetChanged();

                lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // 跳传详情页面
                        JuBaoBean hdhcRespData = hcData.get(position); // 带入的数据
                        ArrayList<AllCclistRespData> cclist = hcData.get(position).getCclist();// 带入的数据
                        Intent intent = new Intent(getActivity(), MyHdDetailsActivity.class);
//                        intent.putExtra("dfzwdatas",(Serializable)hdhcRespData);
                        intent.putExtra("dfzw",(Serializable)cclist);
                        intent.putExtra("ID",hcData.get(position).getId());
                        intent.putExtra("state",hcData.get(position).getStatus());
                        Global.Flag = "0";
                        Global.States = "200";
                        startActivity(intent);

                    }
                });

            }
            else if (msg.arg1 == 2){
                page++;
                hcData.addAll(loadMoreData);
                juBaoAdapter.notifyDataSetChanged();
            }
        }

    };

    private void getHCList(){
        // 获取黑车列表
        HdhcReqData hdRepData = new HdhcReqData();
        hdRepData.setUid(Integer.parseInt(Global.uid));
        hdRepData.setType(position);
        loginModel.getListOfHdhc(getActivity(), hdRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                hcData = (ArrayList<JuBaoBean>) msg;

                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);

            }
            @Override
            public void onError(String msg) {
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getHCList();
                    }
                });
            }
        });

    }


    private void initData() {
        // 获取黑车
        getHCList();
    }

    private void initView(View view) {

        // 获取当前的position 开始值为0
        position = FragmentPagerItem.getPosition(getArguments()) + 1;

        lv_list = (ListView) view.findViewById(R.id.lv_list);
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pullToRefreshView);
        type_page_progress = (ProgressActivity) view.findViewById(R.id.type_page_progress);

        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

    }


    private int page = 2;
    private void LoadMore() {
        // 获取黑车列表
        HdhcReqData hdRepData = new HdhcReqData();
        hdRepData.setUid(Integer.parseInt(Global.uid));
        hdRepData.setPage(page);
        hdRepData.setType(position);

        loginModel.getListOfHdhc(getActivity(), hdRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadMoreData = (ArrayList<JuBaoBean>) msg;

                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(),msg.toString(),Toast.LENGTH_SHORT).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
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
            getHCList();
    }
}
