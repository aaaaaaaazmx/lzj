package com.example.lcsrq.activity.manger.My;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.PeopleDetail;
import com.example.lcsrq.activity.manger.gyzmanger.DingWeiActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzTijiaoActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzXxwhActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.adapter.MyContactAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.ContentGyzReqData;
import com.example.lcsrq.bean.req.MyContentReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.bean.resq.ContentGyzRegionRespData;
import com.example.lcsrq.bean.resq.ContentGyzRespData;
import com.example.lcsrq.bean.resq.MyContentRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.UiTool;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

import static com.example.lcsrq.R.id.tv_jiedao;
import static com.example.lcsrq.R.id.tv_quxian;

/**
 * Created by 苏毅 on 2017/5/3.
 * 我的通讯录
 */

public class MycontactActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{

    private ListView lv_contact;
    private LoginModel loginModel;
    private ArrayList<MyContentRespData> datas;
    private MyContactAdapter myContactAdapter;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private int nameID;
    private LinearLayout ll_company;
    private LinearLayout ll_qx;
    private LinearLayout ll_street;
    private ArrayList<ContentComPanyRespData> comPanyRespDatas;
    private TextView tv_company;
    private String gSid;
    private ArrayList<ContentGyzRegionRespData> regionRespDatas;
    private String qXid = "";
    private TextView tv_quxian,tv_jiedao;
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private ArrayList<MyContentRespData> loadMoredatas;
    private OptionsPickerView optionsPopupWindowJD;
    private OptionsPickerView optionsPopupWindowGS;
    private OptionsPickerView optionsPopupWindow;
    ArrayList<String> options1ItemsJD = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        loginModel = new LoginModel();
        Intent intent = getIntent();
        nameID = intent.getIntExtra("name", -1);
        showLoading("正在加载");
        initData();
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String phonenum;
    private  int mPosition = -1;
    private  boolean isNotNull = true;
    private boolean isFirst = true;
    ArrayList<String> options1ItemsGS = new ArrayList<>();
    ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<Child> child = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                // listview Adapter
                myContactAdapter = new MyContactAdapter(MycontactActivity.this, new MyContactAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(int position) {
                        phonenum = datas.get(position).getM_account();
                        // 点击拨打手机
                        // 弹出确认拨打框
                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(MycontactActivity.this, R.style.Theme_Transparent)).create();
                        dialog.setView(LayoutInflater.from(MycontactActivity.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
                        UiTool.setDialog(MycontactActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

                        // 手机被点击
                        if (ContextCompat.checkSelfPermission(MycontactActivity.this,
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MycontactActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else {
                            callPhone(phonenum);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onTextClick(int position) {

                    }
                });
                myContactAdapter.setDatas(datas);
                lv_contact.setAdapter(myContactAdapter);
                myContactAdapter.notifyDataSetChanged();

                lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (nameID == 0){
                            mPosition = position;
                            // 黑点黑车查处提交
                            Intent intent1 = new Intent(MycontactActivity.this, HdhcCheckActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name",datas.get(position).getM_nickname()+"");
                            intent1.putExtra("bundle",bundle);
                            setResult(100,intent1);
                            finish();
                            return;

                        }else
                            if (nameID == 1){
                            String id1 = datas.get(position).getId();
                                Global.check_uids = id1;
                                // 供应站整改项目提交
                            Intent intent = new Intent(MycontactActivity.this, GyzTijiaoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name",datas.get(position).getM_nickname()+"");
//                            bundle.putString("check_uids ",id1);
                            intent.putExtra("bundle",bundle);
                            bundle.putString("UID",datas.get(position).getId()+"");
                            setResult(200,intent);
                            finish();
                                return;
                        }

                        Intent intent = new Intent(MycontactActivity.this, PeopleDetail.class);
                        intent.putExtra("UID",datas.get(position).getId());
                        startActivity(intent);
                    }
                });
            }else if (msg.arg2 == 5){
                // 获取公司名字
                if (isNotNull) {
                    for (int i = 0; i < comPanyRespDatas.size(); i++) {
                            options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                    }
                    isNotNull = false;
                }
                optionsPopupWindowGS = new  OptionsPickerView.Builder(MycontactActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
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

            // 地区
            else if (msg.arg2 == 2){
//                optionsPopupWindow = new OptionsPopupWindow(MycontactActivity.this);

                options1Items.add("不限");
                     // 默认是不限制
//                    options1Items.add("不限");
                // 遍历数组
                for (int i = 0; i < regionRespDatas.size(); i++) {
                    // 获得第一级的名称
                    options1Items.add(regionRespDatas.get(i).getName());
                }
                optionsPopupWindow = new  OptionsPickerView.Builder(MycontactActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
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
                        getMyContact();
                        myContactAdapter.notifyDataSetChanged();
                    }
                }) .setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18).build();
                // 三级联动效果
                optionsPopupWindow.setPicker(options1Items);
                optionsPopupWindow.setSelectOptions(0);
            }else if (msg.arg2 == 55){
                page++;
                datas.addAll(loadMoredatas);
                myContactAdapter.notifyDataSetChanged();
            }
        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(phonenum);
            } else {
                Toast.makeText(MycontactActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
    private String jDid;
    private void getMyContact(){
        //获取通讯录
        MyContentReqData contentReqData = new MyContentReqData();
        if (!TextUtils.isEmpty(qXid)){
            contentReqData.setAreaid(Integer.parseInt(qXid));
        }

        if (!TextUtils.isEmpty(jDid)){
            contentReqData.setAreaid(Integer.parseInt(jDid));
        }
        // 不需要什么参数
        loginModel.getContents(MycontactActivity.this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                datas = (ArrayList<MyContentRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MycontactActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                //  异常页面
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取通讯录
                        getMyContact();
                    }
                });;
            }
        });
    }

    private void LoadMore(){
        //获取通讯录
        MyContentReqData contentReqData = new MyContentReqData();
        if (!TextUtils.isEmpty(qXid)){
            contentReqData.setAreaid(Integer.parseInt(qXid));
        }
        if (!TextUtils.isEmpty(jDid)){
            contentReqData.setAreaid(Integer.parseInt(jDid));
        }
        contentReqData.setPage(page);
        // 不需要什么参数
        loginModel.getContents(MycontactActivity.this, contentReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoredatas = (ArrayList<MyContentRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 55;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
            @Override
            public void onError(String msg) {
                Toast.makeText(MycontactActivity.this, "没有更多的数据了!", Toast.LENGTH_SHORT).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }
    private void initData() {
        // 供应站地区列表
        ContentGyzRegionReqData contentGyzRegionReqData = new ContentGyzRegionReqData();
        contentGyzRegionReqData.setLevel(1);  //  返回2级列表

        loginModel.getListOfGyzRegion(MycontactActivity.this, contentGyzRegionReqData, new OnLoadComBackListener() {
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
                Toast.makeText(MycontactActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();

            }
        });

        // 获取通讯录
        getMyContact();

        // 获取公司列表
        initCompany();

    }
    // 刷新公司
    private void initCompany() {
        // 公司管理
        final ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        contentCompanyReqData.setUid(Integer.parseInt(Global.uid));

        loginModel.getListOfCompany(MycontactActivity.this, contentCompanyReqData, new OnLoadComBackListener() {

            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                closeDialog();
                comPanyRespDatas = (ArrayList<ContentComPanyRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 5;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MycontactActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                closeDialog();
            }
        });
    }
    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        ll_qx.setOnClickListener(this);
        ll_street.setOnClickListener(this);
        // 跳转个人详情页面

//        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //datas  数据
//                Intent intent = new Intent(MycontactActivity.this, PeopleDetail.class);
//                intent.putExtra("UID",datas.get(position).getId());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void findViews() {

        lv_contact = (ListView) findViewById(R.id.lv_contact);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("通讯录");
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        tv_company = (TextView) findViewById(R.id.tv_company);
        ll_qx = (LinearLayout) findViewById(R.id.ll_qx);
        tv_quxian = (TextView) findViewById(R.id.tv_quxian);
        ll_street = (LinearLayout) findViewById(R.id.ll_street);
         tv_jiedao = (TextView) findViewById(R.id.tv_jiedao);
        //下拉刷新
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        //  异常页面处理
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
        else if (v.getId() == R.id.ll_company){  // 公司
            optionsPopupWindowGS.show();
        }else if (v.getId() == R.id.ll_qx){  // 区县
            optionsPopupWindow.show();
        }else if (v.getId() == R.id.ll_street){ // 街道
            // 必须先选区县在选街道
            if (TextUtils.isEmpty(tv_quxian.getText().toString())) {
                Toast.makeText(MycontactActivity.this, "请您先选择区县", Toast.LENGTH_SHORT).show();
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
            optionsPopupWindowJD = new  OptionsPickerView.Builder(MycontactActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    //返回的分别是三个级别的选中位置
                    //返回的分别是三个级别的选中位置
                    String tx = options1ItemsJD.get(options1);
                    tv_jiedao.setText(tx);
                    // 获取街道ID
                    if (options1 == 0){
                        jDid = "0";
                    }else {
                        jDid = children.get(options1 - 1).getId();
                        Toast.makeText(MycontactActivity.this, jDid +"",Toast.LENGTH_SHORT).show();
                    }
                    getMyContact();
                }
            }) .setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowJD.setPicker(options1ItemsJD);
            //设置默认选中的三级项目
            optionsPopupWindowJD.setSelectOptions(0);
            optionsPopupWindowJD.show();
        }
    }
    private int page = 2;
    // 下拉刷新,加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
            page = 2;
        // 获取通讯录
        getMyContact();
    }
}
