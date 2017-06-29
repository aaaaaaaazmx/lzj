package com.example.lcsrq.activity.manger.My;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.activity.manger.hdhc.DfzwActivity;
import com.example.lcsrq.activity.manger.hdhc.DfzwDetaiActivity;
import com.example.lcsrq.activity.manger.hdhc.MyHdHcactvity;
import com.example.lcsrq.adapter.DfzwAdapter;
import com.example.lcsrq.adapter.MyHdAdapter;
import com.example.lcsrq.adapter.PostListAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.HdhcReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.resq.AllCclistRespData;
import com.example.lcsrq.bean.resq.ContentGyzRegionRespData;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/16.
 * 我的黑点
 */

public class MyHdActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private ListView lv_hd;
    private LoginModel loginModel;
    private ArrayList<JuBaoBean> hdData;
    private MyHdAdapter adapter;
    private LinearLayout ll_company;
    private TextView tv_company;
    private LinearLayout ll_qx;
    private TextView tv_quxian;
    private LinearLayout ll_street;
    private TextView tv_jiedao;
    private ArrayList<ContentGyzRegionRespData> diQuDatas;
    private OptionsPickerView optionsPopupWindow;
    private OptionsPickerView optionsPopupWindowJD;
    private ArrayList<JuBaoBean> loadMoreData;
    private LinearLayout ll_state;
    private TextView tv_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hd);
        loginModel = new LoginModel();
        showLoading("正在加载");
        initData();
    }

    ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<Child> children = new ArrayList<>();
    private String qXid = "";
    private ArrayList<Child> child = new ArrayList<>();
    private boolean isFirst = true;
    ArrayList<String> options1ItemsJD = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
//                Toast.makeText(MyHdActivity.this,hdData.size()+"",Toast.LENGTH_SHORT).show();

                adapter = new MyHdAdapter(MyHdActivity.this);
                adapter.setList(hdData);
                lv_hd.setAdapter(adapter);

            } else if (msg.arg2 == 2) {
                // 地区联级
//                diQuDatas
//                optionsPopupWindow = new OptionsPopupWindow(MyHdActivity.this);
                options1Items.add("不限");
                // 默认是不限制
//                    options1Items.add("不限");
                // 遍历数组
                for (int i = 0; i < diQuDatas.size(); i++) {
                    // 获得第一级的名称
                    options1Items.add(diQuDatas.get(i).getName());
                }
                //返回的分别是三个级别的选中位置
                //  先清空集合里面的所有内容
                // 清楚保存的ID
                //返回的分别是三个级别的选中位置
                //  options1 表示索引
                //拿到街道下面的子项
                // 添加子项
                // 获取供应站id
                //标题文字颜色
                optionsPopupWindow = new OptionsPickerView.Builder(MyHdActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        //  先清空集合里面的所有内容
                        children.clear();
                        // 清楚保存的ID
                        qXid = null;
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1); //  options1 表示索引
                        tv_quxian.setText(tx);

                        if (options1 > 0) {
                            child = diQuDatas.get(options1 - 1).getChild(); //拿到街道下面的子项
                            children.addAll(child); // 添加子项
                            tv_quxian.setText(tx);
                            isFirst = false;
                            // 获取供应站id
                            qXid = diQuDatas.get(options1 - 1).getId();
                        }
                        getHdList();
                        adapter.notifyDataSetChanged();
                    }
                }).setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18).build();
                // 三级联动效果
                optionsPopupWindow.setPicker(options1Items);
                optionsPopupWindow.setSelectOptions(0);
            }else if (msg.arg1 ==2){
                page++;
                hdData.addAll(loadMoreData);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void getDiQu() {
        // 供应站地区列表
        ContentGyzRegionReqData contentGyzRegionReqData = new ContentGyzRegionReqData();
        contentGyzRegionReqData.setLevel(1);  //  返回2级列表
        loginModel.getListOfGyzRegion(MyHdActivity.this, contentGyzRegionReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                diQuDatas = (ArrayList<ContentGyzRegionRespData>) msg;
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
                Toast.makeText(MyHdActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }
    private String jDid;
    private  int state = 0;
    private void getHdList() {

        // 获取黑点列表
        HdhcReqData hcRepData = new HdhcReqData();
        hcRepData.setUid(Integer.parseInt(Global.uid));
//        // 如果区县ID是空的
        if (!TextUtils.isEmpty(qXid)) {
            hcRepData.setAreaid(Integer.parseInt(qXid));
        }

        if (!TextUtils.isEmpty(jDid)){
            hcRepData.setAreaid(Integer.parseInt(jDid));
        }

        hcRepData.setType(2);
        // 筛选状态
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

        //getListOfHdhcforMY   我的黑点黑车
        //getListOfHdhc         分配给谁的黑点黑车
        hcRepData.setStatus(state); //筛选条件
        loginModel.getListOfHdhc(MyHdActivity.this, hcRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                hdData = (ArrayList<JuBaoBean>) msg;
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyHdActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getHdList();
                    }
                });
            }
        });
    }
    private void LoadMore() {
        // 获取黑点列表
        HdhcReqData hcRepData = new HdhcReqData();
        hcRepData.setUid(Integer.parseInt(Global.uid));
//        // 如果区县ID是空的
        if (!TextUtils.isEmpty(qXid)) {
            hcRepData.setAreaid(Integer.parseInt(qXid));
        }
        if (!TextUtils.isEmpty(jDid)){
            hcRepData.setAreaid(Integer.parseInt(jDid));
        }
        // 筛选状态
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
        hcRepData.setStatus(state); //筛选条件
        hcRepData.setPage(page); //  加载页数
        hcRepData.setType(2);

        loginModel.getListOfHdhc(MyHdActivity.this, hcRepData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                loadMoreData = (ArrayList<JuBaoBean>) msg;
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();

                Message message = handler.obtainMessage();
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyHdActivity.this, "没有更多的数据了!", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }

    private void initData() {
        // 地区联级
        getDiQu();
        // 获取黑点列表
        getHdList();
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        ll_qx.setOnClickListener(this);
        ll_street.setOnClickListener(this);
        ll_state.setOnClickListener(this);
        lv_hd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 我的黑点,全部能点击
                // 跳传详情页面
                ArrayList<AllCclistRespData> cclist = hdData.get(position).getCclist();
                JuBaoBean juBaoBean = hdData.get(position);
                Intent intent = new Intent(MyHdActivity.this, MyHdDetailsActivity.class);
                intent.putExtra("dfzwdatas",(Serializable) juBaoBean);
                intent.putExtra("dfzw", (Serializable) cclist);
                intent.putExtra("ID", hdData.get(position).getId());
                intent.putExtra("state",hdData.get(position).getStatus());
                intent.putExtra("clfs",hdData.get(position).getCc_method());
                Global.Flag = "100";
                Global.States = "0";
                startActivity(intent);

            }
        });
    }
    @Override
    protected void findViews() {
        //  异常页面处理
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("我的打非治违");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);



        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);

        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        lv_hd = (ListView) findViewById(R.id.lv_hd);

        //  地区联级
        ll_qx = (LinearLayout) findViewById(R.id.ll_qx);
        tv_quxian = (TextView) findViewById(R.id.tv_quxian);
        ll_street = (LinearLayout) findViewById(R.id.ll_street);
        tv_jiedao = (TextView) findViewById(R.id.tv_jiedao);
        ll_state = (LinearLayout) findViewById(R.id.ll_state);
        tv_state = (TextView) findViewById(R.id.tv_state);

    }
    private boolean First = true;
    private ArrayList<String> options1ItemsState = new ArrayList<>();
    private OptionsPickerView optionsPopupWindowState;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
        else if (v.getId() == R.id.ll_qx){  // 区县
            optionsPopupWindow.show();
        }else if (v.getId() == R.id.ll_street){ // 街道
            // 必须先选区县在选街道
            if (TextUtils.isEmpty(tv_quxian.getText().toString())) {
                Toast.makeText(MyHdActivity.this, "请您先选择区县", Toast.LENGTH_SHORT).show();
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
            //返回的分别是三个级别的选中位置
            //返回的分别是三个级别的选中位置
            //标题文字颜色
            optionsPopupWindowJD = new OptionsPickerView.Builder(MyHdActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    //返回的分别是三个级别的选中位置
                    String tx = options1ItemsJD.get(options1);
                    tv_jiedao.setText(tx);
                }
            }).setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowJD.setPicker(options1ItemsJD);
            //设置默认选中的三级项目
            optionsPopupWindowJD.setSelectOptions(0);
            optionsPopupWindowJD.show();
        }else if (v.getId() == R.id.ll_state){
            if (First) {
                options1ItemsState.add("不限");
                options1ItemsState.add("待查处");
                options1ItemsState.add("查处中");
                options1ItemsState.add("已查处");
            }
            optionsPopupWindowState = new  OptionsPickerView.Builder(MyHdActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    String state = options1ItemsState.get(options1);
                    tv_state.setText(state);
                    // 打非治违列表
                    getHdList();
                    // 获取街道ID
                    if (options1 == 0){
                        jDid = "0";
                    }else {
                        jDid = children.get(options1 - 1).getId();
                        Toast.makeText(MyHdActivity.this, jDid +"",Toast.LENGTH_SHORT).show();
                    }
                    getHdList();
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
    //下拉加载和加载更多
    private int page = 2 ;
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        getHdList();
    }
}
