package com.example.lcsrq.activity.manger.car;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.adapter.CarMangerAdapter;
import com.example.lcsrq.adapter.CarPopWindowAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCarReqData;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.resq.ContentCarRespData;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

public class CarMangerActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    private ListView lv_carlist;
    private ImageView commonRightImage;
    private LinearLayout commonLeftBtn;
    private ImageView left_btn;
    private RelativeLayout commonRightBtn;
    private TextView commonTitleTv;
    private ListView car_list;
    private LoginModel loginModel;

    private ArrayList<ContentCarRespData> datas = new ArrayList<ContentCarRespData>();  // 车辆管理
    private ArrayList<ContentComPanyRespData> comPanyRespDatas  = new ArrayList<ContentComPanyRespData>(); // 公司管理
    private boolean isNotNull = true;
    private OptionsPickerView optionsPopupWindowGS;
    private ArrayList<String> options1ItemsGS = new ArrayList<String>();
    private String gSid;  // 公司ID
    private ProgressActivity type_page_progress;
    private PullToRefreshView pullToRefreshView;
    private ArrayList<ContentCarRespData> loadMoreDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_manger);
        findViews();
        loginModel = new LoginModel();
        initData();
    }
    private CarMangerAdapter carMangerAdapter;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                // 添加适配器
                    carMangerAdapter = new CarMangerAdapter(CarMangerActivity.this);
                    carMangerAdapter.setDatas(datas);
                     lv_carlist.setAdapter(carMangerAdapter);
                    lv_carlist.deferNotifyDataSetChanged();
                    lv_carlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 跳转页面
                        Intent intent = new Intent(CarMangerActivity.this, CarDetailActivity.class);
                        intent.putExtra("did",datas.get(position).getId());
                        startActivity(intent);
                    }
                });

            }else if (msg.arg2 == 2){
//                optionsPopupWindowGS = new OptionsPopupWindow(CarMangerActivity.this);
                // 获取公司名字
                options1ItemsGS.add("不限");
                if (isNotNull) {
                    for (int i = 0; i < comPanyRespDatas.size(); i++) {
                        options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                    }
                    isNotNull = false;
                }

                optionsPopupWindowGS = new  OptionsPickerView.Builder(CarMangerActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        // 清楚保存的公司DI
                        gSid = null;
                        if (options1 > 0) {
                            //返回的分别是三个级别的选中位置
                            String tx = options1ItemsGS.get(options1 -1);
                            // 获取公司ID
                            gSid = comPanyRespDatas.get(options1 -1 ).getId();
                        }

                        // 获取公司名字,调用刷新方法
                        showLoading("正在加载");
                        initCar(gSid);
                    }
                }).build();

                // 三级联动效果
                optionsPopupWindowGS.setPicker(options1ItemsGS);
                //设置默认选中的三级项目
                optionsPopupWindowGS.setSelectOptions(0);

            }else if (msg.arg1 == 50){
                 page++;
                datas.addAll(loadMoreDatas);
                carMangerAdapter.notifyDataSetChanged();
            }
        }
    };

    private void initCar(final String gSid){
        ContentCarReqData contentCarReqData = new ContentCarReqData();
        if (gSid !=null){
            contentCarReqData.setCompany_id(Integer.parseInt(gSid));
        }
        loginModel.getListOfCar(this, contentCarReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                datas  = (ArrayList<ContentCarRespData>) msg;
                Message message = handler.obtainMessage();
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
                       initCar(gSid);
                    }
                });;
            }
        });
    }
    private void LoadMore(String gSid){
        ContentCarReqData contentCarReqData = new ContentCarReqData();
        if (gSid !=null){
            contentCarReqData.setCompany_id(Integer.parseInt(gSid));
        }
        contentCarReqData.setPage(page);

        loginModel.getListOfCar(this, contentCarReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {

                loadMoreDatas = (ArrayList<ContentCarRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg1 = 50;
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
                Toast.makeText(CarMangerActivity.this,"没有更多的数据了!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initData() {
        // 车辆管理
        // 子线程
        initCar(gSid);

    // 公司管理
        ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        contentCompanyReqData.setUid(Integer.parseInt(Global.uid));

        loginModel.getListOfCompany(CarMangerActivity.this, contentCompanyReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                comPanyRespDatas = (ArrayList<ContentComPanyRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 2;
                handler.sendMessage(message);
                closeDialog();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                closeDialog();
                Toast.makeText(CarMangerActivity.this,msg.toString(),Toast.LENGTH_LONG).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }

    @Override
    protected void findViews() {

        lv_carlist = (ListView) findViewById(R.id.lv_carlist);
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonRightImage = (ImageView) findViewById(R.id.commonRightImage); //右边图片按钮
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);    // 左边布局按钮
        commonLeftBtn.setVisibility(View.VISIBLE);
        left_btn = (ImageView) findViewById(R.id.left_btn);  // 左边图片按钮
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn); // 右边布局按钮

        commonTitleTv.setText("车辆管理");
        commonRightImage.setVisibility(View.VISIBLE);


        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        commonRightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //左边按钮
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }
        // 右边按钮
        else if (v.getId() == R.id.commonRightBtn) {

//            View view = View.inflate(this, R.layout.car_popwidow, null);
//            PopupWindow popupWindow = new PopupWindow(view, -1, -2, true);
//            popupWindow.setOutsideTouchable(true);
//            popupWindow.setFocusable(true);   // 设置焦点,则会点击消失
//
//
//            ListView car_list = (ListView) view.findViewById(R.id.car_list);
//
//            // 设置适配器
//            CarPopWindowAdapter carPopWindowAdapter = new CarPopWindowAdapter(this,comPanyRespDatas);
//            car_list.setAdapter(carPopWindowAdapter);
//
//            popupWindow.setTouchable(true);
//            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
////            popupWindow.setBackgroundDrawable();
//            // 设置好参数之后再show
//            popupWindow.showAtLocation(lv_carlist, Gravity.TOP + Gravity.RIGHT, 0, 150);

//            optionsPopupWindowGS.showAtLocation(lv_carlist, Gravity.BOTTOM, 0, 0);
            optionsPopupWindowGS.show();

        }
    }
    private int page = 2;
    // 下拉刷新和加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            LoadMore(gSid);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
            page = 2;
            initCar(gSid);
    }
}
