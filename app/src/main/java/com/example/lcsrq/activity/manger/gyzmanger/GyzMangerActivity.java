package com.example.lcsrq.activity.manger.gyzmanger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.car.CarMangerActivity;
import com.example.lcsrq.adapter.GyzMangerAdapter;
import com.example.lcsrq.adapter.GyzPopWindowAdapter;
import com.example.lcsrq.adapter.PostListAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.ContentGyzReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.bean.resq.ContentGyzRegionRespData;
import com.example.lcsrq.bean.resq.ContentGyzRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.UiTool;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class GyzMangerActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private TextView tv_company, tv_jiedao, tv_quxian;

    private TextView commonTitleTv, tv_ditu;
    private ListView list_gyz;
    private LinearLayout ll_street;
    private LinearLayout commonLeftBtn;
    private LinearLayout ll_company, ll_qx;

    private LoginModel loginModel;

    // 返回的数据
    private ArrayList<ContentGyzRespData> datas;
    private ArrayList<ContentGyzRegionRespData> regionRespDatas;

    //条件筛选
    private OptionsPickerView optionsPopupWindow;
    private OptionsPickerView optionsPopupWindowJD;
    private OptionsPickerView optionsPopupWindowGS;
    // 选择项
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<String> options1ItemsJD = new ArrayList<String>();
    private ArrayList<String> options1ItemsGS = new ArrayList<String>();
    //  二级联动集合
    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<Child> child = new ArrayList<>();
    private int mPosition = -1;

    // 公司数据地址
    private ArrayList<ContentComPanyRespData> comPanyRespDatas = new ArrayList<ContentComPanyRespData>(); // 公司管理
    private String qXid;
    private String gSid;
    private CustomDialog choicePhotoDialog;
    private ContentGyzRespData jingweidu;
    private PullToRefreshView pullToRefreshView;
    private ArrayList<ContentGyzRespData> loadMoreData;
    private GyzMangerAdapter adapter;
    private ProgressActivity type_page_progress;
    private TextView commonRightText;
    private String jDid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_manger);
        showLoading("正在加载");
        loginModel = new LoginModel();
        datas = new ArrayList<ContentGyzRespData>();
        initData();
    }
    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GyzMangerActivity.this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //market为路径，id为包名
                //显示手机上所有的market商店
                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                Intent intent = new Intent("android.intent.action.MAIN");
//                intent.addCategory("android.intent.category.APP_MARKET");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent,"请选择要查看的市场"));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                // 重新初始化
                list_gyz = (ListView) findViewById(R.id.list_gyz);
                adapter = new GyzMangerAdapter(GyzMangerActivity.this, new GyzMangerAdapter.OnAddOrdelClick() {

                    @Override
                    public void onCcClick(int position) {
                        jingweidu = datas.get(position);
                        UiTool.setDialog(GyzMangerActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                    @Override
                    public void onTextClick(int position) {

                    }
                });
                adapter.setDatas(datas);
                list_gyz.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // 刷新
                // 点击条目跳转页面
                list_gyz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  传送过去一个DID
                        Intent intent = new Intent(GyzMangerActivity.this, GyzDetailActivity.class);
                        intent.putExtra("data_id", datas.get(position).getId());
                        startActivity(intent);
                        //  获取供应站ID
                        Global.supply_id = datas.get(position).getId();
                    }
                });
            } else {
                if (msg.arg2 == 2) {
                    options1Items.add("不限");
                    // 默认是不限制
//                    options1Items.add("不限");
                    // 遍历数组
                    for (int i = 0; i < regionRespDatas.size(); i++) {
                        // 获得第一级的名称
                        options1Items.add(regionRespDatas.get(i).getName());
                    }
                    optionsPopupWindow = new  OptionsPickerView.Builder(GyzMangerActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
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
                            initGYZlist(qXid,gSid);
                        }
                    }) .setTitleSize(20)
                            .setTitleColor(Color.BLACK)//标题文字颜色
                            .setContentTextSize(18).build();
                    // 三级联动效果
                    optionsPopupWindow.setPicker(options1Items);
                    optionsPopupWindow.setSelectOptions(0);
                } else if (msg.arg2 == 5) {
//                    optionsPopupWindowGS = new OptionsPopupWindow(GyzMangerActivity.this);
                    // 获取公司名字
                    options1ItemsGS.add("不限");
                    if (isNotNull) {
                        for (int i = 0; i < comPanyRespDatas.size(); i++) {
                            options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                        }
                        isNotNull = false;
                    }

                    optionsPopupWindowGS = new  OptionsPickerView.Builder(GyzMangerActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            // 清楚保存的公司DI
                            gSid = null;
                            //返回的分别是三个级别的选中位置
                            String tx = options1ItemsGS.get(options1);
                            tv_company.setText(tx);
                            if (options1 > 0) {
                                // 获取公司ID
                                gSid = comPanyRespDatas.get(options1 - 1).getId();
                                // 获取公司名字,调用刷新方法
//                                initCompany();
                                // 填写公司名字
                                tv_company.setText(tx);
                            }
                            showLoading("正在加载");
                            initGYZlist(qXid,gSid); //  刷新公司
                        }
                    }) .setTitleSize(20)
                            .setTitleColor(Color.BLACK)//标题文字颜色
                            .setContentTextSize(18).build();

                    // 三级联动效果
                    optionsPopupWindowGS.setPicker(options1ItemsGS);
                    //设置默认选中的三级项目
                    optionsPopupWindowGS.setSelectOptions(0);

                }else if (msg.arg2 == 20){
                    page++;
                    datas.addAll(loadMoreData);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };



    private void initData() {
        // 供应站列表
        initGYZlist(qXid, gSid);

        // 供应站地区列表
        ContentGyzRegionReqData contentGyzRegionReqData = new ContentGyzRegionReqData();

        // 如果是管理人员, 则根据UID来获取
        if (Global.m_roleid.equals("3")){
            contentGyzRegionReqData.setUid(Global.uid);
        }
        // 如果是管理人员并且公司是长沙市燃气热力管理局 则不需要传UID
        if (Global.m_roleid.equals("3") && Global.My_dw.equals("长沙市燃气热力管理局")){
            contentGyzRegionReqData.setUid("0");
        }

        // 如果是公司人员则不传uid (UID为0就是 = null)
        if (Global.m_roleid.equals("2")){
            contentGyzRegionReqData.setUid("0");
        }


        contentGyzRegionReqData.setLevel(1);  //  返回2级列表

        loginModel.getListOfGyzRegion(GyzMangerActivity.this, contentGyzRegionReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                regionRespDatas = (ArrayList<ContentGyzRegionRespData>) msg;
                // 接受成功
                Message message = new Message();
                message.arg2 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(GyzMangerActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });

        initCompany();
    }
    private int page = 2;
    // 获取供应站列表
    private void initGYZlist(final String qXid, final String gSid) {
        // 供应站列表
        // 返回参数
        final ContentGyzReqData data = new ContentGyzReqData();
        // 请求参数暂时不需要
        // 如果当中有一个为空
        if (!TextUtils.isEmpty(qXid)) {
            data.setAreaid(qXid);
        }

        if (!TextUtils.isEmpty(jDid)){
            data.setAreaid(jDid);
        }


        if (!TextUtils.isEmpty(gSid)){
            data.setCompany_id(gSid);
        }
        loginModel.getListOfGyz(GyzMangerActivity.this, data, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                datas = (ArrayList<ContentGyzRespData>) msg;
                // 发送消息
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(GyzMangerActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
                //  异常页面
                // 点击重试,重新刷新
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initGYZlist(qXid,gSid);
                    }
                });;
            }
        });
    }

    private void loadMore(String qXid, String gSid,int page) {
        // 供应站列表
        // 返回参数
        final ContentGyzReqData data = new ContentGyzReqData();
        // 请求参数暂时不需要
        // 如果当中有一个为空
        if (!TextUtils.isEmpty(qXid)) {
            data.setAreaid(qXid);
        }

        if (!TextUtils.isEmpty(jDid)){
            data.setAreaid(jDid);
        }

        if (!TextUtils.isEmpty(gSid)){
            data.setCompany_id(gSid);
        }
            // 设置每夜的条数
        data.setPage(page +"");

        loginModel.getListOfGyz(GyzMangerActivity.this, data, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoreData = (ArrayList<ContentGyzRespData>) msg;
                // 发送消息
                Message message = handler.obtainMessage();
                message.arg2 = 20;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(GyzMangerActivity.this,"没有更多的数据了", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }


    // 刷新公司
    private void initCompany() {
        final ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        // 如果不是管理人员  就传UID来获取公司列表, 因为管理人员可以看全部

        if (!Global.m_roleid.equals("3")){
            // 公司管理
            contentCompanyReqData.setUid(Integer.parseInt(Global.uid));
        }

        if (Global.My_dw.equals("长沙市燃气热力管理局") && Global.m_roleid.equals("3")){
            loginModel.getListOfCompany(GyzMangerActivity.this, contentCompanyReqData, new OnLoadComBackListener() {
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
                    Toast.makeText(GyzMangerActivity.this, "公司列表获取失败", Toast.LENGTH_LONG).show();
                    pullToRefreshView.onHeaderRefreshComplete();
                    pullToRefreshView.onFooterRefreshComplete();
//                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initData();
//                    }
//                });;
                }
            });
            return;
        }

        loginModel.getListOfCompany(GyzMangerActivity.this, contentCompanyReqData, new OnLoadComBackListener() {
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
                Toast.makeText(GyzMangerActivity.this, "公司列表获取失败", Toast.LENGTH_LONG).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
//                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initData();
//                    }
//                });;
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        tv_ditu.setOnClickListener(this);
        ll_street.setOnClickListener(this); //街道
        ll_company.setOnClickListener(this); // 公司
        ll_qx.setOnClickListener(this); // 区县
        commonRightText.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindBDMapLayout(this);

        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_jiedao = (TextView) findViewById(R.id.tv_jiedao);
        tv_quxian = (TextView) findViewById(R.id.tv_quxian);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("供应站管理");

        commonLeftBtn.setVisibility(View.VISIBLE);

        tv_ditu = (TextView) findViewById(R.id.tv_ditu);
        ll_street = (LinearLayout) findViewById(R.id.ll_street);
        tv_ditu.setVisibility(View.VISIBLE);

        // 设置适配器
//        list_gyz = (ListView) findViewById(R.id.list_gyz);
//        GyzMangerAdapter adapter = new GyzMangerAdapter(this, datas);
//        list_gyz.setAdapter(adapter);
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        ll_qx = (LinearLayout) findViewById(R.id.ll_qx);

        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        // 异常处理页面
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);


        // 右上角的字
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightText.setText("地图");

    }

    private boolean isNotNull = true;
    private boolean isFirst = true;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonRightText){

            if(isAvilible(this,"com.baidu.BaiduMap")){
                startActivity(new Intent(this, MapActivity.class));
            }else {
                showDialog();
                choicePhotoDialog.dismiss();
            }
        }
        if (v.getId() == R.id.tv_ditu) {
//            Intent intent = new Intent(GyzMangerActivity.this, MapActivity.class);
//            startActivity(intent);
            if(isAvilible(this,"com.baidu.BaiduMap")){
                startActivity(new Intent(this, MapActivity.class));
            }else {
                showDialog();
                choicePhotoDialog.dismiss();
            }
        } else if (v.getId() == R.id.ll_street) {
//            optionsPopupWindowJD = new OptionsPopupWindow(GyzMangerActivity.this);

            // 必须先选区县在选街道
            if (TextUtils.isEmpty(tv_quxian.getText().toString())) {
                Toast.makeText(GyzMangerActivity.this, "请您先选择区县", Toast.LENGTH_SHORT).show();
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

            optionsPopupWindowJD = new  OptionsPickerView.Builder(GyzMangerActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1ItemsJD.get(options1);
                    tv_jiedao.setText(tx);
                    // 获取街道ID
                    if (options1 == 0){
                        jDid = "0";
                    }else {
                        jDid = children.get(options1 - 1).getId();
                        Toast.makeText(GyzMangerActivity.this, jDid +"",Toast.LENGTH_SHORT).show();
                    }
                    initGYZlist(qXid,gSid);
                }
            }).setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowJD.setPicker(options1ItemsJD);
            //设置选择的三级单位
//            optionsPopupWindow.setLabels("省", "市", "区");
            //设置默认选中的三级项目
            optionsPopupWindowJD.setSelectOptions(0);

            optionsPopupWindowJD.show();
        } else if (v.getId() == R.id.commonLeftBtn) {
            finish();
        } else if (v.getId() == R.id.ll_company) { //   popwindow  t弹出公司列表
            //   popwindow  t弹出公司列表
//            pwOptions.showAtLocation(tv_company, Gravity.BOTTOM, 0, 0);
//            View view = View.inflate(this, R.layout.gyz_popwindow, null);
//            PopupWindow popupWindow = new PopupWindow(view, -1, -2, true);
//            ListView gyz_list = (ListView) view.findViewById(R.id.gyz_list);
//
//            // 设置适配器
//            GyzPopWindowAdapter carPopWindowAdapter = new GyzPopWindowAdapter(this);
//            gyz_list.setAdapter(carPopWindowAdapter);
//
//            popupWindow.setTouchable(true);
//            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//            popupWindow.showAtLocation(list_gyz, Gravity.TOP + Gravity.RIGHT, 0, 250);

//            optionsPopupWindowGS.showAtLocation(tv_jiedao, Gravity.BOTTOM, 0, 0);
            optionsPopupWindowGS.show();

        } else if (v.getId() == R.id.ll_qx) {
            // 弹出区县选择框
//            optionsPopupWindow.showAtLocation(tv_company, Gravity.BOTTOM, 0, 0);
            optionsPopupWindow.show();
        } else if (v.getId() == R.id.item_popupwindows_two) {
            // 现在没有供应站经纬度
            // 如果安装了
            if(isAvilible(this,"com.baidu.BaiduMap")){
                Intent i1 = new Intent();
                i1.setData(Uri.parse("baidumap://map/marker?location=" + jingweidu.getLat() + "," + jingweidu.getLng() + "&title=" + jingweidu.getTitle() + "&content=makeamarker&traffic=on"));
                startActivity(i1);
                choicePhotoDialog.dismiss();
            }else {
                showDialog();
                choicePhotoDialog.dismiss();
            }
        } else if (v.getId() == R.id.parent || v.getId() == R.id.item_popupwindows_cancel) {
            choicePhotoDialog.dismiss();
        }
    }


    // 下拉加载
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        //  刷新供应站列表
        page = 2;
        initGYZlist(qXid,gSid);
    }

    // 上啦刷新
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        // 刷新供应列表
        loadMore(qXid,gSid,page);
    }
}

