package com.example.lcsrq.activity.manger.gyzmanger;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.PeopleDetail;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.adapter.GyzCyrAdapter;
import com.example.lcsrq.adapter.GyzDzgAdapter;
import com.example.lcsrq.adapter.GyzFzrAdapter;
import com.example.lcsrq.adapter.GyzGsFzrAdapter;
import com.example.lcsrq.adapter.GyzJcAdapter;
import com.example.lcsrq.adapter.GyzLskfAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.MapDingwei;
import com.example.lcsrq.bean.req.ContentGyzDetailReqData;
import com.example.lcsrq.bean.req.GyzCheckZgJlReqData;
import com.example.lcsrq.bean.respbean.Child;
import com.example.lcsrq.bean.respbean.Data_ckloglist;
import com.example.lcsrq.bean.respbean.Data_fzr;
import com.example.lcsrq.bean.resq.ContentGyzDetailRespData;
import com.example.lcsrq.bean.resq.GyzCheckZgJlRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.fragment.FirstFragment;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DingweiUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.ExpandableListViewDY;
import com.example.lcsrq.xiangce.UiTool;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 供应站详情页面
 */

public class GyzDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ExpandableListViewDY fzr_list, cyr_list, dzg_list, lskf_list,gsfzr_list;

    private int[] imgs = {R.mipmap.ic_launcher_round};
    private String[] number = {"12313123123"};

    private String did;
    private LoginModel loginModel;
    private ContentGyzDetailRespData data; // 供应站数据返回详情

    private ViewPager viewpagerLayout;
    private TextView tv_company, tv_address, tv_gs_phone, tv_title, tv_pager;
    private LinearLayout ll_check, ll_weihu;
    private Timer timer = new Timer();
    private CirclePageIndicator vv_indicator;
    private LinearLayout ll_parent;
    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private ImageView ivGo;
    private CustomDialog choicePhotoDialog;
    private ImageView iv_phone;
    private ExpandableListViewDY jc_list;
    private ArrayList<GyzCheckZgJlRespData> checkdatas;
    private List<GyzCheckZgJlRespData> yashouDatas = new ArrayList<>();
    private boolean First = true;
    private DingweiUtil dingweiUtil;
    private TextView tv_fenshu;
    private TextView tv_zhengshu;
    private ProgressActivity type_page_progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_detail);
        showLoading("正在加载");
        loginModel = new LoginModel();

        // 刚进来的时候调用一次,获取到就不掉用了
        // 只调用一次
        dingweiUtil = new DingweiUtil(GyzDetailActivity.this);
        dingweiUtil.setFirst(true);
        dingweiUtil.initLocation();
        //  判断是否接收到经纬度
        if (Global.latitude != -1 && Global.longitude != -1){
            dingweiUtil.setFirst(false);
        }

        initData();
            timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg2 = 2;
                handler.sendMessage(message);
            }
        }, 5000, 5000);
    }
    // 公司人员UID
    String GSuID = "";
    //  负责人员UID
    String FZUid = "";

    // 图片的输地址
    private String[] imageUrl = new String[]{};
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    //  输入电话
    private String phonenum;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                commonTitleTv.setText(data.getTitle());
                        // 先清掉数组里面所有的东西
                imageUrl = null;
                // 添加轮播图
                //  获取到了网络地址连接
                String upload_json = data.getUpload_json();
                imageUrl = upload_json.split(",");

                GyzViewPAdapter gyzViewPAdapter = new GyzViewPAdapter(GyzDetailActivity.this);
                gyzViewPAdapter.setUrl(imageUrl);
                viewpagerLayout.setAdapter(gyzViewPAdapter);
                vv_indicator.setViewPager(viewpagerLayout);
                vv_indicator.setSnap(true);
                vv_indicator.onPageSelected(0);
                tv_pager.setText(1 + "/" + imageUrl.length);
                vv_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tv_pager.setText(position + 1 + "/" + imageUrl.length);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                tv_title.setText(data.getTitle());
                tv_address.setText(data.getAddress());
                tv_company.setText(data.getCompany());
                tv_gs_phone.setText(data.getTel());
                tv_fenshu.setText("累计记分 : " + data.getJf_value() + "分");
                tv_zhengshu.setText("证书有效期 : " + data.getStart_end());
                //  公司负责人
                GyzGsFzrAdapter gyzGsFzrAdapter = new GyzGsFzrAdapter(GyzDetailActivity.this, new GyzGsFzrAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(int position) {
                        if (data.getData_fzr_company().size() == 0){
                            return;
                        }
                        // 拨打电话的链接
                        phonenum  = data.getData_fzr_company().get(position).getTel();
                        // 弹出确认拨打框
                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(GyzDetailActivity.this, R.style.Theme_Transparent)).create();
                        dialog.setView(LayoutInflater.from(GyzDetailActivity.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
                        UiTool.setDialog(GyzDetailActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
                        TextView phone = (TextView) dialog.findViewById(R.id.tipsTv);
                        phone.setText(phonenum + "");

                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 手机被点击
                                if (ContextCompat.checkSelfPermission(GyzDetailActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(GyzDetailActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else {
                                    callPhone(phonenum);
                                    dialog.dismiss();
                                }
                            }
                        });
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                    }
                });
                gsfzr_list.setGroupIndicator(null);
                gyzGsFzrAdapter.setData_fzr(data.getData_fzr_company());
                gsfzr_list.setAdapter(gyzGsFzrAdapter);
                gsfzr_list.expandGroup(0);
                //  获取公司人员UID

                for (int i= 0; i < data.getData_fzr_company().size(); i++){
                    if (i == 0 ){
                        GSuID  =  data.getData_fzr_company().get(i).getUid();
                    }else {
                        GSuID = GSuID + "," +   data.getData_fzr_company().get(i).getUid();
                    }
                }
                //添加公司人员uid
                Global.Gyz_GsUid = GSuID;

                gsfzr_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String uid = data.getData_fzr_company().get(childPosition).getUid();
                        Intent intent = new Intent(GyzDetailActivity.this, PeopleDetail.class);
                        intent.putExtra("UID",uid);
                        startActivity(intent);
                        return true;
                    }
                });

                // 负责人f
                GyzFzrAdapter gyzFzrAdapter = new GyzFzrAdapter(GyzDetailActivity.this, new GyzFzrAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(int position) {
                        if (data.getData_fzr().size() == 0){
                            return;
                        }
                        phonenum = data.getData_fzr().get(position).getTel();

                        // 弹出确认拨打框
                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(GyzDetailActivity.this, R.style.Theme_Transparent)).create();
                        dialog.setView(LayoutInflater.from(GyzDetailActivity.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
                        UiTool.setDialog(GyzDetailActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
                        TextView phone = (TextView) dialog.findViewById(R.id.tipsTv);
                        phone.setText(phonenum + "");

                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 手机被点击
                                if (ContextCompat.checkSelfPermission(GyzDetailActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(GyzDetailActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else {
                                    callPhone(phonenum);
                                    dialog.dismiss();
                                }
                            }
                        });
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                fzr_list.setGroupIndicator(null);
                gyzFzrAdapter.setData_fzr(data.getData_fzr());
                fzr_list.setAdapter(gyzFzrAdapter);
                fzr_list.expandGroup(0);

                // 获取负责人UID
                for (int i = 0; i < data.getData_fzr().size(); i++){
                    if (i == 0){
                        FZUid = data.getData_fzr().get(i).getUid();
                    }else {
                        FZUid = FZUid +  "," + data.getData_fzr().get(i).getUid();
                    }
                }
                // 天机负责人员uid
                Global.Gyz_FzrUid = FZUid;

                // 点击item跳转详情
                fzr_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String uid = data.getData_fzr().get(childPosition).getUid();

                        Intent intent = new Intent(GyzDetailActivity.this, PeopleDetail.class);
                        intent.putExtra("UID",uid);
                        startActivity(intent);
                        return true;
                    }
                });

                // 从业人
                GyzCyrAdapter cyrFzrAdapter = new GyzCyrAdapter(GyzDetailActivity.this, new GyzCyrAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(int position) {
                        if (data.getData_ysg().size() == 0){
                            return;
                        }
                        // 拨打电话
                        phonenum = data.getData_ysg().get(position).getTel();
                        // 弹出确认拨打框
                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(GyzDetailActivity.this, R.style.Theme_Transparent)).create();
                        dialog.setView(LayoutInflater.from(GyzDetailActivity.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
                        UiTool.setDialog(GyzDetailActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
                        TextView phone = (TextView) dialog.findViewById(R.id.tipsTv);
                        phone.setText(phonenum + "");

                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 手机被点击
                                if (ContextCompat.checkSelfPermission(GyzDetailActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(GyzDetailActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else {
                                    callPhone(phonenum);
                                    dialog.dismiss();
                                }
                            }
                        });
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                cyrFzrAdapter.setData_ysg(data.getData_ysg());
                cyr_list.setGroupIndicator(null);
                cyr_list.setAdapter(cyrFzrAdapter);
                cyr_list.expandGroup(0); // 默认展开

                //  点击ITEM跳转详情
                cyr_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String uid = data.getData_ysg().get(childPosition).getUid();
                        Intent intent = new Intent(GyzDetailActivity.this, PeopleDetail.class);
                        intent.putExtra("UID",uid);
                        startActivity(intent);
                       return true;
                    }
                });


                // 检查记录
                GyzJcAdapter gyzJcAdapter = new GyzJcAdapter(GyzDetailActivity.this);
                gyzJcAdapter.setData(data);
                jc_list.setGroupIndicator(null);
                jc_list.setAdapter(gyzJcAdapter);
//                jc_list.expandGroup(0);


                // 存在问题
                GyzDzgAdapter dzgAdapter = new GyzDzgAdapter(GyzDetailActivity.this);
                dzgAdapter.setData(data);
                dzg_list.setGroupIndicator(null);
                dzg_list.setAdapter(dzgAdapter);
                dzg_list.expandGroup(0);

                // 历史扣分项目
                GyzLskfAdapter lskfadapter = new GyzLskfAdapter(GyzDetailActivity.this);
                lskfadapter.setData(data);
                lskf_list.setGroupIndicator(null);
                lskf_list.setAdapter(lskfadapter);
//                lskf_list.expandGroup(0);

            } else if (msg.arg2 == 2) {
                // 轮播图
                int currentItem = viewpagerLayout.getCurrentItem();
                if (currentItem < imageUrl.length - 1) {
                    currentItem++;
                } else {
                    currentItem = 0;
                }
                viewpagerLayout.setCurrentItem(currentItem);// 切换到下一个页面
                // message1.arg2 =4;
                handler.sendEmptyMessageDelayed(0, 3000);
            }else if (msg.arg2 == 50){
                // checkdatas
                // 检查记录
//                jc_list = (ExpandableListViewDY) findViewById(R.id.jc_list);
//                GyzJcAdapter gyzJcAdapter = new GyzJcAdapter(GyzDetailActivity.this);
//                jc_list.setGroupIndicator(null);
//                jc_list.setAdapter(gyzJcAdapter);
//                jc_list.expandGroup(0);
            }
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };


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
    ArrayList<String >lists = new ArrayList<String>();
    // 获得验收项目
    private void getYanShou(){
        // 获取供应站待整改项目
        loginModel = new LoginModel();
        final GyzCheckZgJlReqData gyzCheckZgJlReqData = new GyzCheckZgJlReqData();
        gyzCheckZgJlReqData.setStatus("1,2,4");//  2 表示已整改  1,2,4
        gyzCheckZgJlReqData.setSupply_id(Integer.parseInt(data.getId()));
        loginModel.putGyzCheckZgJl(GyzDetailActivity.this, gyzCheckZgJlReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                yashouDatas = (ArrayList<GyzCheckZgJlRespData>) msg;

                if (yashouDatas.size()!= 0){
//                    // 添加公司人员UID和负责人员UID
//                    yashouDatas.get(0).setGsuid(GSuID);
//                    yashouDatas.get(0).setFzuid(FZUid);
                    Intent intent = new Intent(GyzDetailActivity.this, GyzYanshouActivity.class);
                    intent.putExtra("data",(Serializable)yashouDatas);
                    intent.putExtra("supply_id",data.getId());
                    startActivity(intent);
                }
            }
            @Override
            public void onError(String msg) {
                closeDialog();
                if (msg.toString().equals("无数据")){
                    Intent intent = new Intent(GyzDetailActivity.this, GyzCheckActivity.class);
                    intent.putStringArrayListExtra("check_id",lists);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
         // 供应站详情
        // 请求参数
        ContentGyzDetailReqData contentGyzDetailReqData = new ContentGyzDetailReqData();
        contentGyzDetailReqData.setDid(Integer.parseInt(did));

        loginModel.getListOfGyzDetail(GyzDetailActivity.this, contentGyzDetailReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                data = JSON.parseObject((String) msg, ContentGyzDetailRespData.class);
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
            }

            @Override
            public void onError(String msg) {
                ll_parent.removeAllViews();
                // 加载失败弹出一个加载失败的框框
                //  异常页面
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取通讯录
                        initData();
                    }
                });;
            }
        });

        // 获取站点检查项整改记录
//        GyzCheckZgJlReqData gyzCheckZgJlReqData = new GyzCheckZgJlReqData();
//        gyzCheckZgJlReqData.setSupply_id(Integer.parseInt(did));

//        loginModel.putGyzCheckZgJl(GyzDetailActivity.this, gyzCheckZgJlReqData, new OnLoadComBackListener() {
//            @Override
//            public void onSuccess(Object msg) {
//                checkdatas = (ArrayList<GyzCheckZgJlRespData>) msg;
//                Toast.makeText(GyzDetailActivity.this,checkdatas.size() +"",Toast.LENGTH_LONG).show();
//                Message message = handler.obtainMessage();
//                message.arg2 = 50;
//                handler.sendMessage(message);
//                closeDialog();
//            }
//            @Override
//            public void onError(String msg) {
//            Toast.makeText(GyzDetailActivity.this,msg.toString(),Toast.LENGTH_LONG).show();
//                closeDialog();
//            }
//        });
    }

    @Override
    protected void addAction() {
        ll_weihu.setOnClickListener(this);
        ll_check.setOnClickListener(this);
        commonLeftBtn.setOnClickListener(this);
        ivGo.setOnClickListener(this);
        iv_phone.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 当页面在次显示的时候刷新数据
        showLoading("正在加载");
        initData(); //  重新加载
    }

    @Override
    protected void findViews() {
        // 无数据页面
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
        // 证书有效期
        tv_zhengshu = (TextView) findViewById(R.id.tv_zhengshu);
        // 扣分项目o
        tv_fenshu = (TextView) findViewById(R.id.tv_fenshu);
        // 电话
        iv_phone = (ImageView) findViewById(R.id.iv_phone);

        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindBDMapLayout(this);

        // 导航
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);

        // 获取did
        Intent intent = getIntent();
        did = intent.getStringExtra("data_id");
        Global.supply_id = did; // 供应站ID
        // 获取viewpagerindicaor
        vv_indicator = (CirclePageIndicator) findViewById(R.id.vv_indicator);
        tv_pager = (TextView) findViewById(R.id.tv_pager);

        // 公司负责人
        gsfzr_list = (ExpandableListViewDY) findViewById(R.id.gsfzr_list);

        // 负责人
        fzr_list = (ExpandableListViewDY) findViewById(R.id.fzr_list);
//        GyzFzrAdapter gyzFzrAdapter = new GyzFzrAdapter(this);
//        fzr_list.setGroupIndicator(null);
//        fzr_list.setAdapter(gyzFzrAdapter);

        // 从业人
        cyr_list = (ExpandableListViewDY) findViewById(R.id.cyr_list);
//        GyzCyrAdapter cyrFzrAdapter = new GyzCyrAdapter(this);
//        cyr_list.setGroupIndicator(null);
//        cyr_list.setAdapter(cyrFzrAdapter);

        // 待整改信息
        dzg_list = (ExpandableListViewDY) findViewById(R.id.dzg_list);

//        GyzDzgAdapter dzgAdapter = new GyzDzgAdapter(this);
//        dzg_list.setGroupIndicator(null);
//        dzg_list.setAdapter(dzgAdapter);
//        dzg_list.expandGroup(0);

        // 检查记录
        jc_list = (ExpandableListViewDY) findViewById(R.id.jc_list);

        // 历史扣分记录
        lskf_list = (ExpandableListViewDY) findViewById(R.id.lskf_list);
//        GyzLskfAdapter adapter = new GyzLskfAdapter(this);
//        lskf_list.setGroupIndicator(null);
//        lskf_list.setAdapter(adapter);
//        lskf_list.expandGroup(0);

        // 轮播图
        viewpagerLayout = (ViewPager) findViewById(R.id.viewpagerLayout);
        // 公司名
        tv_company = (TextView) findViewById(R.id.tv_company);
        // 公司地址
        tv_address = (TextView) findViewById(R.id.tv_address);
        // 公司电话
        tv_gs_phone = (TextView) findViewById(R.id.tv_gs_phone);
        // 供应站名称
        tv_title = (TextView) findViewById(R.id.tv_title);
        // 开始检查
        ll_check = (LinearLayout) findViewById(R.id.ll_check);
        ll_weihu = (LinearLayout) findViewById(R.id.ll_weihu);

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);

        //  到这里去
        ivGo = (ImageView) findViewById(R.id.iv_go);

    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }
    //  计算亮点之间的距离
    // 计算两点距离
    private final double EARTH_RADIUS = 6378137.0;

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_check) {
            // 跳传检查项目页面
//            startActivity(new Intent(GyzDetailActivity.this, GyzCheckActivity.class));
//            Intent intent = new Intent(GyzDetailActivity.this, GyzYanshouActivity.class);
//            intent.putExtra("supply_id",data.getId());
//            startActivity(intent);
            // 判断手机是否打开GPS 并判断是否在500米范围内
            // 如果服务器返回的经纬度都是0的话,直接跳转,不需要审核是否在500米范围内
//            if (data.getLat() == 0 && data.getLng() == 0){
//                Intent intent = new Intent(GyzDetailActivity.this, GyzXxwhActivity.class);
//                intent.putExtra("dizhi",data.getAddress() + "");
//                intent.putExtra("title",data.getTitle() + "");
//                startActivity(intent);
//                return;xx
//            }
            if (Global.m_roleid.equals("3")){
            showLoading("正在加载");
            ArrayList<Data_ckloglist> ckloglist = data.getCkloglist();
            boolean oPen = isOPen(GyzDetailActivity.this);
            if (oPen){
                //  判断多少米
                double v1 = gps2m(Global.latitude, Global.longitude, data.getLat(), data.getLng());
                if (Math.abs(v1) <= 500.0){
                    // 如果当前位置在指定范围内
                     // 现在不需要获取待整改项目了,直接传下去就行了
                    //  如果存在问题是空的, 就跳转checkactivity
//                    if (data.getCkloglist().size() != 0){
//                        Intent intent = new Intent(GyzDetailActivity.this, GyzYanshouActivity.class);
//                        intent.putExtra("data",(Serializable)ckloglist);
//                        intent.putExtra("supply_id",data.getId());
//                        startActivity(intent);
//                    }else {
//                        closeDialog();
//                        startActivity(new Intent(GyzDetailActivity.this, GyzCheckActivity.class));
//                    }
// 获取供应站待整改项目
//                    loginModel = new LoginModel();
//                    final GyzCheckZgJlReqData gyzCheckZgJlReqData = new GyzCheckZgJlReqData();
//                    gyzCheckZgJlReqData.setStatus(1);//  0 表示待整改
//                    gyzCheckZgJlReqData.setSupply_id(Integer.parseInt(data.getId()));
//                    loginModel.putGyzCheckZgJl(GyzDetailActivity.this, gyzCheckZgJlReqData, new OnLoadComBackListener() {
//                        @Override
//                        public void onSuccess(Object msg) {
//                            closeDialog();
//                            yashouDatas = (ArrayList<GyzCheckZgJlRespData>) msg;
//                            Intent intent = new Intent(GyzDetailActivity.this, GyzYanshouActivity.class);
//                            intent.putExtra("data",(Serializable)yashouDatas);
//                            intent.putExtra("supply_id",data.getId());
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                            closeDialog();
//                            startActivity(new Intent(GyzDetailActivity.this, GyzCheckActivity.class));
//                        }
//                    });

                    // 获取验收项目
                    getYanShou();
                 }else {
                    closeDialog();
                    Toast.makeText(GyzDetailActivity.this,"您目前没有在查处范围",Toast.LENGTH_SHORT).show();
                }

                //目前是直接跳转CHECKaCTIVITY 并
//                Intent intent = new Intent(GyzDetailActivity.this, GyzYanshouActivity.class);
//                intent.putExtra("data",(Serializable)ckloglist);
//                intent.putExtra("supply_id",data.getId());
//                startActivity(intent);

            }else {
                closeDialog();
                // 请打开GPS
                Toast.makeText(GyzDetailActivity.this,"请打开GPS定位",Toast.LENGTH_SHORT).show();
            }
            }else {
                Toast.makeText(GyzDetailActivity.this,"您没有权限",Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.ll_weihu) {
            if (Global.m_roleid.equals("3")){
            showLoading("正在加载");
            // 判断手机是否打开GPS 并判断是否在500米范围内
            boolean oPen = isOPen(GyzDetailActivity.this);
            if (oPen){
                double v1 = gps2m(Global.latitude, Global.longitude, data.getLat(), data.getLng());
                //  当前还没有定位成功,就是说服务器返回的经纬度都是0
                if (data.getLat() == 0 && data.getLng() == 0){
                    closeDialog();
                    Intent intent = new Intent(GyzDetailActivity.this, GyzXxwhActivity.class);
                    intent.putExtra("dizhi",data.getAddress() + "");
                    intent.putExtra("title",data.getTitle() + "");
                    startActivity(intent);
                    return;
                }

                if (Math.abs(v1) <= 500.0){
                    // 判断用户是否有权限
                    if (Global.m_roleid.equals("3")){
                        closeDialog();
                        Intent intent = new Intent(GyzDetailActivity.this, GyzXxwhActivity.class);
                        intent.putExtra("dizhi",data.getAddress() + "");
                        intent.putExtra("title",data.getTitle() + "");
                        startActivity(intent);
                    }else {
                        closeDialog();
                        Toast.makeText(GyzDetailActivity.this,"您没有权限!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    closeDialog();
                    Toast.makeText(GyzDetailActivity.this,"您目前没有在维护范围",Toast.LENGTH_SHORT).show();
                }
//                closeDialog();
//                Intent intent = new Intent(GyzDetailActivity.this, GyzXxwhActivity.class);
//                intent.putExtra("dizhi",data.getAddress() + "");
//                intent.putExtra("title",data.getTitle() + "");
//                startActivity(intent);
            }else {
                closeDialog();
                // 请打开GPS
                Toast.makeText(GyzDetailActivity.this,"请打开GPS定位",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(GyzDetailActivity.this,"您没有权限",Toast.LENGTH_SHORT).show();
        }
        }
        else if (v.getId() == R.id.commonLeftBtn){
            // 返回就是结束这个页面
            finish();
        }
        else if (v.getId() == R.id.iv_go){
            // 导航
            UiTool.setDialog(GyzDetailActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);

        }else if (v.getId() == R.id.parent || v.getId() == R.id.item_popupwindows_cancel){
            choicePhotoDialog.dismiss();
        }else if (v.getId() == R.id.item_popupwindows_two){
            // 跳转百度地图
            if(isAvilible(this,"com.baidu.BaiduMap")){
                Intent i1 = new Intent();
                i1.setData(Uri.parse("baidumap://map/marker?location="+ data.getLat() +","+  data.getLng() +"&title="+ data.getTitle() +"&content=makeamarker&traffic=on"));
                startActivity(i1);
                choicePhotoDialog.dismiss();
            }else {
                showDialog();
                choicePhotoDialog.dismiss();
            }
        }else if (v.getId() == R.id.iv_phone){
            // 手机被点击
            if (ContextCompat.checkSelfPermission(GyzDetailActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GyzDetailActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
                callPhone(data.getTel() + "");
            }
        }
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
    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    //  轮播图的页数
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class GyzViewPAdapter extends PagerAdapter {

        private Context mContext;
        private String[] imUrl = {};
        private BitmapUtils utils;

        public String[] getUrl() {
            return imUrl;
        }

        public void setUrl(String[] url) {
            imUrl = url;
        }

        public GyzViewPAdapter(Context context) {
            this.mContext = context;
            utils = new BitmapUtils(mContext);
        }

        @Override
        public int getCount() {
            return imUrl.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            utils.display(imageView, imUrl[position]);
            container.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiTool.showPic(mContext,imUrl[position], ImageView.ScaleType.FIT_XY);
                }
            });

            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(phonenum);
            } else {
                Toast.makeText(GyzDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
