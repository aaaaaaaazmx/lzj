package com.example.lcsrq.activity.manger.hdhc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.adapter.DfzwAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.HdhcDetailReqData;
import com.example.lcsrq.bean.req.HdhcReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.resq.AllCclistRespData;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.bean.resq.ContentGyzRegionRespData;
import com.example.lcsrq.bean.resq.HdhcDetailRespData;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.ImageLoader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/10.
 * <p>
 * // 黑点黑车变成打非治违
 */

public class DfzwActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {

    private LoginModel loginModel;
    private PullToRefreshView pullToRefreshView;
    private ListView list_dfzw;
    private ArrayList<JuBaoBean> dfzwdatas;
    private DfzwAdapter adapter;
    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private ArrayList<JuBaoBean> loadMoreDatas;
    private ArrayList<ContentGyzRegionRespData> regionRespDatas;
    private ArrayList<ContentComPanyRespData> comPanyRespDatas;

    //  下拉列表
    ArrayList<String> options1ItemsGS = new ArrayList<>();
    ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<Child> child = new ArrayList<>();
    private OptionsPickerView optionsPopupWindowJD;
    private OptionsPickerView optionsPopupWindowGS;
    private OptionsPickerView optionsPopupWindow;
    private OptionsPickerView optionsPopupWindowState;
    private String qXid = "";
    private LinearLayout ll_company;
    private LinearLayout ll_qx;
    private LinearLayout ll_street;
    private TextView tv_quxian,tv_jiedao;
    private TextView tv_company;
    private boolean isFirst =true;
    private boolean isNotNull = true;
    private String gSid;
    ArrayList<String> options1ItemsJD = new ArrayList<>();
    ArrayList<String> options1ItemsState = new ArrayList<>();
    private TextView commonRightText;
    private ProgressActivity type_page_progress;
    private LinearLayout ll_state;
    private TextView tv_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dfzw);
        showLoading("正在加载");
        loginModel = new LoginModel();
        initData();
    }

    private int page = 2;

    private void initData() {
        // 获取公司列表
        initCompany();
        // 供应站地区列表
        ContentGyzRegionReqData contentGyzRegionReqData = new ContentGyzRegionReqData();
        contentGyzRegionReqData.setLevel(1);  //  返回2级列表
        loginModel.getListOfGyzRegion(DfzwActivity.this, contentGyzRegionReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                regionRespDatas = (ArrayList<ContentGyzRegionRespData>) msg;
                // 接受成功
                Message message = new Message();
                message.arg2 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(DfzwActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();

            }
        });
        // 打非治违列表
        getDfzw();

    }

    // 刷新公司
    private void initCompany() {
        // 公司管理
        final ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        contentCompanyReqData.setUid(Integer.parseInt(Global.uid));

        loginModel.getListOfCompany(DfzwActivity.this, contentCompanyReqData, new OnLoadComBackListener() {

            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                comPanyRespDatas = (ArrayList<ContentComPanyRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 11;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(DfzwActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }
    private  int state = 0;

    private void getDfzw(){
        //  获取没有黑车了
        HdhcReqData hdhcReqData = new HdhcReqData();
//        hdhcReqData.setUid(Integer.parseInt(Global.uid));

        hdhcReqData.setType(2);

        if (!TextUtils.isEmpty(qXid)){
            hdhcReqData.setAreaid(Integer.parseInt(qXid));
        }
        //!TextUtils.isEmpty(tv_state.getText().toString())
        if (!TextUtils.isEmpty(tv_state.getText().toString())){
            if (tv_state.getText().toString().equals("不限")){
                state = 0;
            }else if (tv_state.getText().toString().equals("待查处")){
                state = 1;
            }else if (tv_state.getText().toString().equals("查处中")){
                state = 2;
            }else if (tv_state.getText().toString().equals("已查处")){
                state = 3;
            }
        }
        hdhcReqData.setStatus(state); //筛选条件

        loginModel.getAllListOfHdhc(DfzwActivity.this, hdhcReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                dfzwdatas = (ArrayList<JuBaoBean>) msg;
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(DfzwActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDfzw();
                    }
                });
            }
        });
    }
    private void LoadMore(int page) {
        HdhcReqData hdhcReqData = new HdhcReqData();
//        hdhcReqData.setUid(Integer.parseInt(Global.uid));
        hdhcReqData.setType(2);

        hdhcReqData.setPage(page);
        if (!TextUtils.isEmpty(qXid)){
            hdhcReqData.setAreaid(Integer.parseInt(qXid));
        }
        //!TextUtils.isEmpty(tv_state.getText().toString())
        if (!TextUtils.isEmpty(tv_state.getText().toString())){
            if (tv_state.getText().toString().equals("不限")){
                state = 0;
            }else if (tv_state.getText().toString().equals("待查处")){
                state = 1;
            }else if (tv_state.getText().toString().equals("查处中")){
                state = 2;
            }else if (tv_state.getText().toString().equals("已查处")){
                state = 3;
            }
        }
        hdhcReqData.setStatus(state); //筛选条件
        loginModel.getAllListOfHdhc(DfzwActivity.this, hdhcReqData, new OnLoadComBackListener() {

            @Override
            public void onSuccess(Object msg) {
                loadMoreDatas = (ArrayList<JuBaoBean>) msg;
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(DfzwActivity.this, "没有更多的数据了", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                //  接收数据
                adapter = new DfzwAdapter(DfzwActivity.this);
                adapter.setList(dfzwdatas);
                list_dfzw.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else if (msg.arg1 == 2) {
                page++;
                dfzwdatas.addAll(loadMoreDatas);
                adapter.notifyDataSetChanged();
            }else if (msg.arg2 == 2){
                // 地区
                options1Items.add("不限");
                // 遍历数组
                for (int i = 0; i < regionRespDatas.size(); i++) {
                    // 获得第一级的名称
                    options1Items.add(regionRespDatas.get(i).getName());
                }
                optionsPopupWindow = new  OptionsPickerView.Builder(DfzwActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        //  先清空集合里面的所有内容
                        children.clear();
                        // 清楚保存的ID
                        qXid = null;
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1); //  options1 表示索引
                        tv_quxian.setText(tx);

                        if (options1 > 0) {
                            child = regionRespDatas.get(options1 - 1).getChild(); //拿到街道下面的子项
                            children.addAll(child); // 添加子项
                            tv_quxian.setText(tx);
                            isFirst = false;
                            // 获取供应站id
                            qXid = regionRespDatas.get(options1 - 1).getId();
                        }
                        getDfzw();
                    }
                }) .setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18).build();
                // 三级联动效果
                optionsPopupWindow.setPicker(options1Items);
                optionsPopupWindow.setSelectOptions(0);
            }else if (msg.arg2 == 11){
                // 获取公司名字
                if (isNotNull) {
                    for (int i = 0; i < comPanyRespDatas.size(); i++) {
                        options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                    }
                    isNotNull = false;
                }
                optionsPopupWindowGS = new  OptionsPickerView.Builder(DfzwActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        // 清楚保存的公司DI
                        gSid = null;
                        //返回的分别是三个级别的选中位置
                        String tx = options1ItemsGS.get(options1);
                        // 获取公司ID
                        gSid = comPanyRespDatas.get(options1).getId();
                        // 获取公司名字,调用刷新方法
                        showLoading("正在加载");
                        initCompany();
                        // 填写公司名字
                        tv_company.setText(tx);
                    }
                }) .setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18)
                        .build();
                // 三级联动效果
                optionsPopupWindowGS.setPicker(options1ItemsGS);
                //设置默认选中的三级项目
                optionsPopupWindowGS.setSelectOptions(0);
            }
        }
    };

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        ll_qx.setOnClickListener(this);
        ll_street.setOnClickListener(this);
        commonRightText.setOnClickListener(this);
        ll_state.setOnClickListener(this);

        list_dfzw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 如果不是已查处(已经验收),并不能跳转详情
                if (!dfzwdatas.get(position).getStatus().equals("3")){
                        return;
                }
                // 跳传详情页面
                JuBaoBean hdhcRespData = dfzwdatas.get(position); // 带入的数据
                ArrayList<AllCclistRespData> cclist = dfzwdatas.get(position).getCclist();// 带入的数据
                Intent intent = new Intent(DfzwActivity.this, DfzwDetaiActivity.class);
                intent.putExtra("dfzwdatas",(Serializable)hdhcRespData);
                intent.putExtra("dfzw",(Serializable)cclist);
                intent.putExtra("ID",dfzwdatas.get(position).getId());
                intent.putExtra("state",dfzwdatas.get(position).getStatus());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void findViews() {
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        list_dfzw = (ListView) findViewById(R.id.list_dfzw);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("打非治违");

        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        tv_company = (TextView) findViewById(R.id.tv_company);
        ll_qx = (LinearLayout) findViewById(R.id.ll_qx);
        tv_quxian = (TextView) findViewById(R.id.tv_quxian);
        ll_street = (LinearLayout) findViewById(R.id.ll_street);
        tv_jiedao = (TextView) findViewById(R.id.tv_jiedao);
        ll_state = (LinearLayout) findViewById(R.id.ll_state);
        tv_state = (TextView) findViewById(R.id.tv_state);

        // 查处按钮隐藏
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightText.setText("查处");
        commonRightText.setVisibility(View.GONE);

        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
    }
    private boolean First  = true;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }else if (v.getId() == R.id.ll_company){
            optionsPopupWindowGS.show();
        }else if (v.getId() == R.id.ll_qx){
            optionsPopupWindow.show();
        }else if (v.getId() == R.id.ll_street){
            // 必须先选区县在选街道
            if (TextUtils.isEmpty(tv_quxian.getText().toString())) {
                Toast.makeText(DfzwActivity.this, "请您先选择区县", Toast.LENGTH_SHORT).show();
            }
            // 表示选择的是不限制
            if (tv_quxian.getText().toString().equals("不限") || children.size() == 0) {
                options1ItemsJD.clear();
                options1ItemsJD.add("不限");
            }
            // 街道
            // 如果区县选择的是不限,或者children是不限
            // 反之:
            if (!isFirst) {
                options1ItemsJD.clear();
                options1ItemsJD.add("不限");
                for (int i = 0; i < children.size(); i++) {
                    options1ItemsJD.add(children.get(i).getName() + "");
                }
                isFirst = true;
            }
            //监听确定选择按钮
            optionsPopupWindowJD = new  OptionsPickerView.Builder(DfzwActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    //返回的分别是三个级别的选中位置
                    //返回的分别是三个级别的选中位置
                    String tx = options1ItemsJD.get(options1);
                    tv_jiedao.setText(tx);
                }
            }) .setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowJD.setPicker(options1ItemsJD);
            //设置默认选中的三级项目
            optionsPopupWindowJD.setSelectOptions(0);
            optionsPopupWindowJD.show();
        }else if (v.getId() == R.id.commonRightText){
            Intent intent = new Intent(DfzwActivity.this, HdhcCheckActivity.class);
            intent.putExtra("jb_id",dfzwdatas.get(0).getId());
            startActivity(intent);
        }else if (v.getId() == R.id.ll_state) {  //  点击状态弹出框框

            if (First) {
                options1ItemsState.add("不限");
                options1ItemsState.add("待查处");
                options1ItemsState.add("查处中");
                options1ItemsState.add("已查处");
            }
            //监听确定选择按钮
            optionsPopupWindowState = new  OptionsPickerView.Builder(DfzwActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    String state = options1ItemsState.get(options1);
                    tv_state.setText(state);
                    // 打非治违列表
                    getDfzw();
                    adapter.notifyDataSetChanged();
                }
            }) .setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowState.setPicker(options1ItemsState);
            //设置默认选中的三级项目
            optionsPopupWindowState.setSelectOptions(0);
            optionsPopupWindowState.show();
            First = false;

        }
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        getDfzw();
    }
}
