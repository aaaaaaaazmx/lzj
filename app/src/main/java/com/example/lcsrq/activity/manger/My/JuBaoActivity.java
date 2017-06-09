package com.example.lcsrq.activity.manger.My;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.fragment.TabJbFragment;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.SmartTabLayout;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItems;

/**
 * Created by 苏毅 on 2017/6/6.
 */

public class JuBaoActivity extends BaseActivity {

    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private ViewPager vv_pager;
    private ViewGroup tab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jubao_activity);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
    }
    String [] Title = {"黑车","黑点"};

    @Override
    protected void findViews() {
        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("我的举报");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        vv_pager = (ViewPager) findViewById(R.id.vv_pager);
        tab = (ViewGroup) findViewById(R.id.tab);

        tab.addView(LayoutInflater.from(this).inflate(R.layout.tab_top_layout, tab, false));
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        FragmentPagerItems pages = new FragmentPagerItems(this);

        for (int i=0;i<Title.length;i++) {
            pages.add(FragmentPagerItem.of(Title[i], TabJbFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        vv_pager.setAdapter(adapter);
        viewPagerTab.setViewPager(vv_pager);
    }

    @Override
    public void onClick(View v) {
     if (v.getId() == R.id.commonLeftBtn){
         finish();
     }

    }
}
