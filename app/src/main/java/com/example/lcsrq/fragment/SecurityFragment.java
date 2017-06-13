package com.example.lcsrq.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.cxjl.CxInfoActivity;
import com.example.lcsrq.activity.manger.cxjl.CxjlActivity;
import com.example.lcsrq.adapter.CxjlDzgAdapter;
import com.example.lcsrq.adapter.SecurityAdapter;
import com.example.lcsrq.base.BaseFragment;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.req.GyzListReqData;
import com.example.lcsrq.bean.req.MyrectificationReqDataim;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.bean.resq.GyzListResppData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

import static com.example.lcsrq.R.id.tv_state;

/**
 * Created by 苏毅 on 2017/5/24.
 * 安全隐患
 */

public class SecurityFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {

    private LinearLayout commonLeftBtn;
    private ListView lv_cxjl;
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private LoginModel loginModel;
    private CxjlDzgAdapter cxjlDzgAdapter;
    private ArrayList<MyrectificationRespDataim> datas;
    private ArrayList<GyzListResppData> loadMoredatas;
    private SecurityAdapter securityAdapter;
    private ArrayList<ContentComPanyRespData> comPanyRespDatas;
    private ArrayList<String> options1ItemsGS = new ArrayList<String>();
    private boolean isNotNull = true;
    private OptionsPickerView optionsPopupWindowGS;
    private TextView tv_company;
    private LinearLayout ll_company;
    private ImageView commonRightImage;
    private LinearLayout ll_root;
    private ArrayList<GyzListResppData> zgData;
    private boolean first = true; // 表示第一次进入
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = View.inflate(getActivity(), R.layout.aqyh_activity, null);
//        loginModel = new LoginModel();
//        findViews(view);
//
//            showLoading("正在加载");
//            initData();
//            addAction();
//            first = false;
//            Toast.makeText(getActivity(),first + "",Toast.LENGTH_SHORT).show();
//
//        return view;


        if (view == null){
            view =  inflater.inflate( R.layout.aqyh_activity,null);
            loginModel = new LoginModel();
            findViews(view);
            initData();
            addAction();
        }else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
        }
        return view;
    }

    private int page = 2;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1){
                securityAdapter = new SecurityAdapter(getActivity());
                securityAdapter.setDatas(zgData);
                lv_cxjl.setAdapter(securityAdapter);
                securityAdapter.notifyDataSetChanged();

            }else if (msg.arg2 == 2){
                page++;
                zgData.addAll(loadMoredatas);
                securityAdapter.notifyDataSetChanged();
            }
            else if (msg.arg2 == 5){
                options1ItemsGS.add("不限");
                if (isNotNull) {
                    for (int i = 0; i < comPanyRespDatas.size(); i++) {
                        options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                    }
                    isNotNull = false;
                }

                optionsPopupWindowGS = new  OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        // 清楚保存的公司DI
                        gSid = null;
                        //返回的分别是三个级别的选中位置
                        String tx = options1ItemsGS.get(options1);
                        tv_company.setText(tx);

                        if (options1 > 0) {
                            String conpany = options1ItemsGS.get(options1 - 1);
                            // 获取公司ID
                            gSid = comPanyRespDatas.get(options1 - 1).getId();
                            // 填写公司名字
                            tv_company.setText(tx);
                        }
                        // 获取公司名字,调用刷新方法
                        showLoading("正在加载");
                         getMYzg();  //  刷新我的整改
                    }
                }) .setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18).build();

                // 三级联动效果
                optionsPopupWindowGS.setPicker(options1ItemsGS);
                //设置默认选中的三级项目
                optionsPopupWindowGS.setSelectOptions(0);
            }
        }
    };
    private String gSid;

    // 刷新公司
    private void initCompany() {
        final ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        //  只有供应站管理才增加权限,其他的都不需要
        loginModel.getListOfCompany(getActivity(), contentCompanyReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                comPanyRespDatas = (ArrayList<ContentComPanyRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 5;
                handler.sendMessage(message);
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }


    private void getMyRecti(){
        // 我的整改
        MyrectificationReqDataim myrectificationReqDataim = new MyrectificationReqDataim();
        myrectificationReqDataim.setUid(Integer.parseInt(Global.uid));
        myrectificationReqDataim.setStatus(1);  //  待整改

        if (!TextUtils.isEmpty(gSid)){
            myrectificationReqDataim.setCompany_id(gSid);
        }

        loginModel.getMyRectification(getActivity(), myrectificationReqDataim, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                datas = (ArrayList<MyrectificationRespDataim>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });;
            }
        });
    }

    private void getMYzg(){

        GyzListReqData gyzListReqData = new GyzListReqData();
        if (!TextUtils.isEmpty(gSid)){
            gyzListReqData.setCompany_id(gSid);
        }
        loginModel.getGYZList(getActivity(), gyzListReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                zgData = (ArrayList<GyzListResppData>) msg;
                closeDialog();

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMYzg();
                    }
                });;
            }
        });
    }

    @Override
    public void findViews(View root) {
        TextView commonTitleTv = (TextView) root.findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("安全隐患");

        lv_cxjl = (ListView) root.findViewById(R.id.lv_cxjl);

        //  下拉刷新
        pullToRefreshView = (PullToRefreshView) root.findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        // 异常处理
        type_page_progress = (ProgressActivity) root.findViewById(R.id.type_page_progress);

        // 地区联级父布局
        ll_root = (LinearLayout) root.findViewById(R.id.ll_root);
        ll_root.setVisibility(View.GONE);

        tv_company = (TextView) root.findViewById(R.id.tv_company);
        ll_company = (LinearLayout) root.findViewById(R.id.ll_company);

        commonRightImage = (ImageView) root.findViewById(R.id.commonRightImage);
        commonRightImage.setVisibility(View.VISIBLE);
    }

    private void initData() {

        // 获取我的整改待整改项目
//         getMyRecti();
            getMYzg();

        // 公司
        initCompany();
    }

    private void LoadMore(int page) {
        GyzListReqData gyzListReqData = new GyzListReqData();
        gyzListReqData.setPage(page + "");
        if (!TextUtils.isEmpty(gSid)){
            gyzListReqData.setCompany_id(gSid);
        }
        loginModel.getGYZList(getActivity(), gyzListReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoredatas = (ArrayList<GyzListResppData>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg2 = 2;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(),"没有更多的数据了", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });

    }
    @Override
    public void addAction() {
        ll_company.setOnClickListener(this);
        commonRightImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_company){
            optionsPopupWindowGS.show();
        }
        if (v.getId() == R.id.commonRightImage){
            optionsPopupWindowGS.show();
        }
    }

    @Override
    public void call(int callID, Object... args) {

    }

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
