package com.example.lcsrq.activity.manger.My;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.MyrectificationReqDataim;
import com.example.lcsrq.bean.resq.GyzCheckDuoRespData;
import com.example.lcsrq.bean.resq.GyzCheckRespData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.fragment.MyZgFrgament;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.viewpagerindicator.TabPageIndicator;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.SmartTabLayout;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItems;

import java.util.ArrayList;

import static com.example.lcsrq.R.id.list_check;

/**
 * Created by 苏毅 on 2017/6/5.
 * 我的整改
 */

public class MyZhengGaiActiviity extends BaseActivity {
    private ViewPager vv_pager;
    private TabPageIndicator indicator;
    private LoginModel loginModel;
    private ArrayList<MyrectificationRespDataim> datas;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhenggai_activity);
        loginModel = new LoginModel();
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        vv_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ViewGroup tab;
    String [] Title = {"待签收","待整改","已整改","已验收"};

    @Override
    protected void findViews() {
        // 找到viewpager控件
        vv_pager = (ViewPager) findViewById(R.id.vv_pager);
        tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(R.layout.tab_top_layout, tab, false));

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        FragmentPagerItems pages = new FragmentPagerItems(this);

        for (int i=0;i<4;i++) {
            pages.add(FragmentPagerItem.of(Title[i], MyZgFrgament.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        vv_pager.setAdapter(adapter);
        viewPagerTab.setViewPager(vv_pager);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("我的整改");
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }
    }
}
