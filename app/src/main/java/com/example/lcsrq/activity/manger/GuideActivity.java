package com.example.lcsrq.activity.manger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lcsrq.HomeActivity;
import com.example.lcsrq.R;

/**
 * Created by 苏毅 on 2017/4/27.
 */

public class GuideActivity extends Activity {

    private ViewPager vv_pager;
    private int mImages[] = {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
    private int mCurrentState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        initView();
        initData();
    }

    private void initData() {
        final GuideAdapter guideAdapter = new GuideAdapter();
        vv_pager.setAdapter(guideAdapter);

        vv_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 如果是最后一个页面了
                if (position == mImages.length - 1 && mCurrentState == 1){
                        // 跳转到主页面
                    startActivity(new Intent(GuideActivity.this,HomeActivity.class));
                    finish();
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                mCurrentState = state;

            }
        });

    }

    private void initView() {
        vv_pager = (ViewPager) findViewById(R.id.vv_pager);
    }

    public class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView =new ImageView(GuideActivity.this);
            imageView.setImageResource(mImages[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
