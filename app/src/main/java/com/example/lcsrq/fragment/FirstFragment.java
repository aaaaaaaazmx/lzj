package com.example.lcsrq.fragment;

import android.app.AlertDialog;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.CountyDetailActivity;
import com.example.lcsrq.activity.manger.GuideActivity;
import com.example.lcsrq.activity.manger.IndustryDetailActivity;
import com.example.lcsrq.activity.manger.car.CarMangerActivity;
import com.example.lcsrq.activity.manger.cxjl.CxInfoActivity;
import com.example.lcsrq.activity.manger.cxjl.CxjlActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.activity.manger.hdhc.DfzwActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.activity.manger.jfxt.ScoringActivity;
import com.example.lcsrq.activity.manger.jfxt.ScoringListActivity;
import com.example.lcsrq.activity.manger.lawfg.LawActivity;
import com.example.lcsrq.activity.manger.report.DialogJubao;
import com.example.lcsrq.activity.manger.report.IReportctivity;
import com.example.lcsrq.activity.manger.training.TrainingActivity;
import com.example.lcsrq.activity.manger.xxcx.InfoGuideActivity;
import com.example.lcsrq.activity.manger.xxcx.InfomationCarActivity;
import com.example.lcsrq.activity.manger.xxcx.InformationActivity;
import com.example.lcsrq.adapter.FirstAdapter;
import com.example.lcsrq.adapter.PostListAdapter;
import com.example.lcsrq.base.BaseFragment;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.resq.ContentRespData1;
import com.example.lcsrq.bean.resq.GetPicRespData;
import com.example.lcsrq.bean.resq.PicRespData;
import com.example.lcsrq.bean.resq.ContentRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.CacheUtils;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.view.ReFreshListView;
import com.example.lcsrq.xiangce.UiTool;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class FirstFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    private LayoutInflater inflater;
    private Timer timer = new Timer(); //为了方便取消定时轮播，将 Timer 设为全局
    private boolean scannerVisible = true;

    private PullToRefreshView pullToRefreshView;
    private ListView listView;
    private View header;
    private ViewPager viewpagerLayout;
    private LinearLayout countyTvLayout, industryLayout;
    private TextView countyTv, industryTv;
    private View countyLine, industryLine;
    private PostListAdapter adapter;
    private AlertDialog builder;
    private ArrayList<String> listImage = new ArrayList<>();
    // 动态的flag
    private int flag = 0;

    // 列表名
    private LinearLayout gyz_manger, car_manger, ll_hdhc, ll_jfxt, ll_cxjl, ll_flfg, ll_hypx, ll_xxcx, ll_wyjb;
    // 列表名字
    private TextView tv_flfg;
    private ImageView iv_flfg;

    // 原始的数据,第一次进来显示的数据
    private ArrayList<ContentRespData1> list11 = new ArrayList<>();


    // 行业动态的Listview数据
    private ArrayList<ContentRespData1> list = new ArrayList<>();


    // 曲线动态的listview的数据
    private ArrayList<ContentRespData1> list0 = new ArrayList<>();


    private LoginModel loginModel;
    private ArrayList<ContentRespData1> hy = new ArrayList<>();
    private ArrayList<ContentRespData1> qx = new ArrayList<>();
    private ArrayList<GetPicRespData> picRespDatas = new ArrayList<>();
    private FirstAdapter firstAdapter;
    private CirclePageIndicator vv_indicator;
    private TextView dialog_simple_sure;
    private TextView dialog_simple_cancel;
    private ProgressActivity progressActivity;
    private ArrayList<ContentRespData1> loadMorehy;
    private ArrayList<ContentRespData1> loadMoreqx;
    private String lunbotuJson;
    private String qxdongtaiJson;
    private String hydongtaiJson;
    private View view;

    public FirstFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;  //  这个是必须的

//        view = inflater.inflate(R.layout.test_activity, container, false);
//         // 获取缓存
////       lunbotuJson = CacheUtils.getCache("lunbotu", getActivity());  // 轮播图
////       qxdongtaiJson = CacheUtils.getCache("qxdongtai", getActivity()); // 区县JSON
////       hydongtaiJson = CacheUtils.getCache("hydongtai", getActivity()); //  行业JSON
//
//            findViews(view);
//            addAction();
//            loginModel = new LoginModel();
//            initData();  // 获取数据
//            startTimer(0); // 开启定时器
//        return view;


        if (view == null){
            view = inflater.inflate(R.layout.test_activity, container, false);
            findViews(view);
            addAction();
            loginModel = new LoginModel();
            initData();  // 获取数据
            startTimer(0); // 开启定时器
        }else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
        }
        return view;
    }

    private PostListAdapter postListAdapter;
    private Message message1 = new Message();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 10) {
                // 添加曲线动态的listview的数据
//                for (ContentRespData1 qxdw:qx){
//                    list0.add(qxdw);
//                }
//                list11.addAll(qx);
                firstAdapter = new FirstAdapter(getActivity(), new FirstAdapter.OnAddOrdelClick() {
                    @Override
                    public void onCcClick(int position) {
                        initData();
                    }
                });
                listView.setAdapter(firstAdapter);
                firstAdapter.setList(qx);
                firstAdapter.notifyDataSetChanged();
            }
            if (msg.arg2 == 10) {
                // 添加行业动态
                list11.clear();
                for (ContentRespData1 hydw : hy) {
                    list.add(hydw);
                }
            }

            if (msg.arg2 == 3) {
                // 添加轮播图
                listImage.clear();
                for (GetPicRespData data : picRespDatas) {
                    listImage.add(data.getUpload_path());
                }

                ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getActivity());
                viewpagerAdapter.setList(listImage);
                viewpagerLayout.setAdapter(viewpagerAdapter);

                vv_indicator.setViewPager(viewpagerLayout);
                vv_indicator.setSnap(true);
                vv_indicator.onPageSelected(0);

            } else if (msg.arg2 == 4) {
                int currentItem = viewpagerLayout.getCurrentItem();
                if (currentItem < listImage.size() - 1) {
                    currentItem++;
                    System.out.println("llllllllllllllll" + currentItem);
                } else {
                    currentItem = 0;
                }
                viewpagerLayout.setCurrentItem(currentItem);// 切换到下一个页面
                // message1.arg2 =4;
//                handler.sendEmptyMessageDelayed(0, 3000);

            } else if (msg.arg1 == 50) {
                page++;
                qx.addAll(loadMoreqx);
                firstAdapter.notifyDataSetChanged();
            } else if (msg.arg2 == 60) {
                page++;
                hy.addAll(loadMorehy);
                firstAdapter.notifyDataSetChanged();
            }
        }
    };

    // 加载更多
    private void loadMore(int page) {
        //曲线动态
        if (flag == 0) {
            // 区县动态
            final ContentReqData qxdtReqData = new ContentReqData();
            qxdtReqData.setCatid(10);
            qxdtReqData.setPage(page);


            loginModel.getLBListof(getActivity(), qxdtReqData, new OnLoadComBackListener() {
                @Override
                public void onSuccess(Object msg) {
                    loadMoreqx = (ArrayList<ContentRespData1>) msg;
                    Message message = handler.obtainMessage();
                    message.arg1 = 50;
                    handler.sendMessage(message);
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(getActivity(),"没有更多的数据了" , Toast.LENGTH_LONG).show();
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }
            });
        } else if (flag == 1) {
            // 行业动态
            final ContentReqData hydtReqData = new ContentReqData();
            hydtReqData.setCatid(9);
            hydtReqData.setPage(page);

            loginModel.getLBListof(getActivity(), hydtReqData, new OnLoadComBackListener() {
                @Override
                public void onSuccess(Object msg) {
                    loadMorehy = (ArrayList<ContentRespData1>) msg;
                    Message message = handler.obtainMessage();
                    message.arg2 = 60;
                    handler.sendMessage(message);
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(getActivity(), "没有更多的数据了", Toast.LENGTH_LONG).show();
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }
            });
        }


    }

    private void initData() {
        // 行业动态
            final ContentReqData hydtReqData = new ContentReqData();
            hydtReqData.setCatid(9);

            loginModel.getLBListof(getActivity(), hydtReqData, new OnLoadComBackListener() {
                @Override
                public void onSuccess(Object msg) {
                    hy = (ArrayList<ContentRespData1>) msg;

                    //  缓存行业动态,没网络的时候不会显示空白
                    String hydongtai = JSON.toJSONString(hy);

                    Message message = handler.obtainMessage();
                    message.arg2 = 10;
                    handler.sendMessage(message);
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                    closeDialog();
                    pullToRefreshView.onFooterRefreshComplete();
                    pullToRefreshView.onHeaderRefreshComplete();
                }
            });

        // 区县动态
        final ContentReqData qxdtReqData = new ContentReqData();
        qxdtReqData.setCatid(10);

        loginModel.getLBListof(getActivity(), qxdtReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                qx = (ArrayList<ContentRespData1>) msg;

                // 缓存曲线动态界面
//                String qxdongtai = JSON.toJSONString(qx);
//                CacheUtils.setCache("qxdongtai",qxdongtai,getActivity());

                Message message = handler.obtainMessage();
                message.arg1 = 10;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });

        // 获取首页轮播图
        ContentReqData PicReqData = new ContentReqData();
        PicReqData.setCatid(14);
        loginModel.getLBListOfPic(getActivity(), PicReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                picRespDatas = (ArrayList<GetPicRespData>) msg;

                // 缓存轮播图
//                String lunbotu = JSON.toJSONString(picRespDatas);
//                CacheUtils.setCache("lunbotu",lunbotu,getActivity());

                Message message = new Message();
                message.arg2 = 3;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    // 开启times;
    public void startTimer(int times) {
        if (times == 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.arg2 = 4;
                    handler.sendMessage(message);
                }
            }, 5000, 5000);
        } else {
            timer.cancel();
        }
    }

    @Override
    public void findViews(View root) {
        progressActivity = new ProgressActivity(getActivity());
        //顶部title
        TextView commonTitleTv = (TextView) root.findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("长沙燃气");

        // 添加头布局
        listView = (ListView) root.findViewById(R.id.list);
        header = inflater.inflate(R.layout.view_main_header, null);
        listView.addHeaderView(header);

        pullToRefreshView = (PullToRefreshView) root.findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        // vv_indicator
        vv_indicator = (CirclePageIndicator) header.findViewById(R.id.vv_indicator);

        //广告栏
//        viewpagerLayout = (FrameLayout) header.findViewById(R.id.viewpagerLayout);
        viewpagerLayout = (ViewPager) header.findViewById(R.id.viewpagerLayout);

        //list类型
        countyTvLayout = (LinearLayout) header.findViewById(R.id.countyTvLayout);
        countyTv = (TextView) header.findViewById(R.id.countyTv);
        countyLine = header.findViewById(R.id.countyLine);

        industryLayout = (LinearLayout) header.findViewById(R.id.industryLayout);
        industryLine = header.findViewById(R.id.industryLine);
        industryTv = (TextView) header.findViewById(R.id.industryTv);


        gyz_manger = (LinearLayout) header.findViewById(R.id.gyz_manger);
        car_manger = (LinearLayout) header.findViewById(R.id.car_manger);
        ll_jfxt = (LinearLayout) header.findViewById(R.id.ll_jfxt);
        ll_hdhc = (LinearLayout) header.findViewById(R.id.ll_hdhc);
        ll_cxjl = (LinearLayout) header.findViewById(R.id.ll_cxjl);
        ll_flfg = (LinearLayout) header.findViewById(R.id.ll_flfg);
        ll_hypx = (LinearLayout) header.findViewById(R.id.ll_hypx);
        ll_xxcx = (LinearLayout) header.findViewById(R.id.ll_xxcx);
        ll_wyjb = (LinearLayout) header.findViewById(R.id.ll_wyjb);

        // 法律法规
        tv_flfg = (TextView) header.findViewById(R.id.tv_flfg);
        iv_flfg = (ImageView) header.findViewById(R.id.iv_flfg);
    }

    @Override
    public void addAction() {
//        viewpagerLayout.setOnClickListener(this);
        countyTvLayout.setOnClickListener(this);
        industryLayout.setOnClickListener(this);
        ll_xxcx.setOnClickListener(this);
        ll_hypx.setOnClickListener(this);
        ll_flfg.setOnClickListener(this);
        ll_cxjl.setOnClickListener(this);
        ll_jfxt.setOnClickListener(this);
        ll_hdhc.setOnClickListener(this);
        ll_wyjb.setOnClickListener(this);
        gyz_manger.setOnClickListener(this);
        car_manger.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag == 1) {
                    String hydid = hy.get(position - 1).getId();
                    // 行业动态
                    Intent intent = new Intent(getActivity(), IndustryDetailActivity.class);
                    intent.putExtra("flag1", flag);
                    intent.putExtra("hydid", hydid);
                    startActivity(intent);

                } else if (flag == 0) {

                    String qxdid = qx.get(position - 1).getId(); // string
                    //  区县动态
                    Intent qxdt = new Intent(getActivity(), IndustryDetailActivity.class);
                    qxdt.putExtra("flag1", flag);
                    qxdt.putExtra("qxdid", qxdid);

                    startActivity(qxdt);
                }
            }
        });

    }

    private int type = -1;

    @Override
    public void onClick(View v) {
      if (v.getId() == R.id.countyTvLayout) { // 曲线动态
            setChoice(countyTv, countyLine);
//            list11.clear();
//            qx.clear();
            page = 2;
            flag = 0;
            firstAdapter.setList(qx);
            firstAdapter.notifyDataSetChanged();
//            list11.addAll(list0);
//            postListAdapter.notifyDataSetChanged();

        } else if ((v.getId() == R.id.industryLayout)) { // 行业动态
            page = 2;
            setChoice(industryTv, industryLine);
            flag = 1;
            firstAdapter.setList(hy);
            firstAdapter.notifyDataSetChanged();

        } else if (v.getId() == R.id.car_manger) { // 车辆管理
            showLoading("正在加载");
//            Toast.makeText(getActivity(), "点击了车辆管理", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), CarMangerActivity.class));
            closeDialog();
        } else if (v.getId() == R.id.gyz_manger) { // 供应站管理
//            Toast.makeText(getActivity(), "点击了供应站管理", Toast.LENGTH_LONG).show();

          //  表示从业人员,直接跳转供应站详情
          if (Global.m_roleid.equals("1")){
              showLoading("正在加载");
              closeDialog();
              Intent intent = new Intent(getActivity(), GyzDetailActivity.class);
              intent.putExtra("data_id", Global.Mysupply_id); //  传过去一个供应站ID ,从业人员自身自带的
              startActivity(intent);
          }else {
              showLoading("正在加载");
              closeDialog();
              startActivity(new Intent(getActivity(), GyzMangerActivity.class));
          }

        } else if (v.getId() == R.id.ll_hdhc) {  // 黑点黑车
            showLoading("正在加载");
//            startActivity(new Intent(getActivity(), HdhcActivity.class));
            startActivity(new Intent(getActivity(), DfzwActivity.class));
            closeDialog();
        } else if (v.getId() == R.id.ll_jfxt) { //j记分系统
            showLoading("正在加载");
//            Toast.makeText(getActivity(), "功能正在完善中", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), ScoringListActivity.class));
//            startActivity(new Intent(getActivity(), ScoringActivity.class));
            closeDialog();
        } else if (v.getId() == R.id.ll_cxjl) { //诚信记录
            showLoading("正在加载");
            startActivity(new Intent(getActivity(), CxjlActivity.class));
            closeDialog();
        } else if (v.getId() == R.id.ll_flfg) { // 法律法规
            showLoading("正在加载");
            startActivity(new Intent(getActivity(), LawActivity.class));
            closeDialog();
        } else {
            if (v.getId() == R.id.ll_hypx) {   //行业培训
                showLoading("正在加载");
                Toast.makeText(getActivity(), "功能正在完善中", Toast.LENGTH_LONG).show();
                closeDialog();
//            startActivity(new Intent(getActivity(), TrainingActivity.class));
            } else {
                if (v.getId() == R.id.ll_xxcx) {  // 信息查询
                    showLoading("正在加载");
//                    startActivity(new Intent(getActivity(), InfomationCarActivity.class));
                    startActivity(new Intent(getActivity(), InfoGuideActivity.class));
                    closeDialog();
                } else {
                    if (v.getId() == R.id.ll_wyjb) {  // 我要举报
                        startActivity(new Intent(getActivity(), DialogJubao.class));
//                showLoading("正在加载");
                        // 弹出pop框  举报黑点黑车
//                UiTool.setDialog(getActivity(), dialog, Gravity.CENTER, -1, 1, 0.8); //弹出Dialog
//                TextView tv_hd = (TextView) dialog.findViewById(R.id.tv_hd);

                        // 黑点f
//                tv_hd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        type = 2;  // 表示黑点
//                        Intent intent1 = new Intent(getActivity(), ReportActivity.class);
//                        intent1.putExtra("11", 2);
//                        startActivity(intent1);
//                        dialog.dismiss();
//                        closeDialog();
//                    }
//                });
//                TextView tv_hc = (TextView) dialog.findViewById(R.id.tv_hc);
//                // 黑车
//                tv_hc.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        type = 1;  // 表示黑车
//                        Intent intent = new Intent(getActivity(), ReportActivity.class);
//                        intent.putExtra("11", 1);
//                        startActivity(intent);
//                        dialog.dismiss();
//                        closeDialog();
//                    }
//                });
//                LinearLayout ll_parent = (LinearLayout) dialog.findViewById(R.id.ll_parent);
//                ll_parent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        closeDialog();
//                    }
//                });


//            startActivity(new Intent(getActivity(), IReportctivity.class));
//            closeDialog();
                    }
                }
            }
        }
    }

    @Override
    public void call(int callID, Object... args) {

    }

//    @Override
//    public void onFooterRefresh(PullToRefreshView view) {
//            pullToRefreshView.onHeaderRefreshComplete();
//    }

//    @Override
//    public void onHeaderRefresh(PullToRefreshView view) {
//        pullToRefreshView.onFooterRefreshComplete();
//    }

    private void setChoice(TextView tv, View line) {
        resetTabBtn();
        tv.setTextColor(getResources().getColor(R.color.green));
        line.setBackgroundResource(R.color.green);
    }

    /**
     * 清除所有的选中状态
     */

    private void resetTabBtn() {
        countyTv.setTextColor(getResources().getColor(R.color.tanlan));
        industryTv.setTextColor(getResources().getColor(R.color.tanlan));
        countyLine.setBackgroundResource(R.color.gray_line);
        industryLine.setBackgroundResource(R.color.gray_line);
    }

    private int page = 2;

    // 下拉刷新
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        loadMore(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        initData();
    }


    public class ViewpagerAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<String> list = new ArrayList<>();
        private BitmapUtils utils;

        public ViewpagerAdapter(Context context) {
            this.mContext = context;
            utils = new BitmapUtils(mContext);
            utils.configDefaultLoadingImage(R.mipmap.picture_dtd);
        }

        public ArrayList<String> getList() {
            return list;
        }

        public void setList(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setFocusable(true);
            imageView.setClickable(true);
            utils.display(imageView, list.get(position));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  页面滑动的ID
                    String did = picRespDatas.get(position).getId();

                    //  如果DID为空,则不响应.
                    if (TextUtils.isEmpty(did)){
                        return;
                    }
                   String title = picRespDatas.get(position).getTitle();
                    Intent intent = new Intent(getActivity(), CxInfoActivity.class);
                    intent.putExtra("did",did);
                    intent.putExtra("title",title);
                    startActivity(intent);

                }
            });

            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
