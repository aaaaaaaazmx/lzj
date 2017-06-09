package com.example.lcsrq.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 苏毅 on 2017/4/20.
 */

public class ReFreshListView extends ListView implements AbsListView.OnScrollListener {

    public static final int STATE_PULL_REFREH = 0; //下拉刷新
    public static final int STATE_RELLASE_REFREH = 1; //松开刷新
    public static final int STATE_REFRESHING = 2; //刷新中
    private int mCurrentState = STATE_PULL_REFREH; // 默认是下拉刷新

    public ReFreshListView(Context context) {
        super(context);
        initHeaderView();
        FooterView();
    }

    public ReFreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        FooterView();
    }

    public ReFreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        FooterView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReFreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHeaderView();
        FooterView();
    }

    private ImageView iv_arr;
    private TextView tv_time, tv_title;
    private ProgressBar pbProgress;

    /**
     * 初始化头部局
     */
    public void initHeaderView() {
        mHeaderview = View.inflate(getContext(), R.layout.view_refresh_header, null);
        this.addHeaderView(mHeaderview);

        iv_arr = (ImageView) mHeaderview.findViewById(R.id.iv_arr);
        tv_time = (TextView) mHeaderview.findViewById(R.id.tv_time);
        tv_title = (TextView) mHeaderview.findViewById(R.id.tv_title);
        pbProgress = (ProgressBar) mHeaderview.findViewById(R.id.pb_progress);

        mHeaderview.measure(0, 0); //重新测量
        measuredHeight = mHeaderview.getMeasuredHeight();
        mHeaderview.setPadding(0, -measuredHeight, 0, 0);

        initArrAnim();
        tv_time.setText("最后刷新时间:" + getCurrentTime());
    }


    /**
     * 初始化脚布局
     */
    public void FooterView() {
        mFoterView = View.inflate(getContext(), R.layout.view_refresh_footer, null);
        this.addFooterView(mFoterView);
        mFoterView.measure(0, 0);

        mFoterViewHeight = mFoterView.getMeasuredHeight();
        mFoterView.setPadding(0, -mFoterViewHeight, 0, 0);

        this.setOnScrollListener(this);
    }


    private View mHeaderview, mFoterView;
    private int endY, measuredHeight, mFoterViewHeight;
    private int startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }
                endY = (int) ev.getRawY();
                //移动偏移量
                int dy = endY - startY;
                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    int padding = dy - measuredHeight;
                    mHeaderview.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentState != STATE_RELLASE_REFREH) {
                        mCurrentState = STATE_RELLASE_REFREH; //将状态改为松开刷新
                        refreshState();
                    } else if (padding <= 0 && mCurrentState != STATE_PULL_REFREH) {
                        mCurrentState = STATE_PULL_REFREH; // 状态改为下拉刷新
                        refreshState();
                    }

                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_RELLASE_REFREH) {
                    mCurrentState = STATE_REFRESHING;
                    refreshState();
                } else if (mCurrentState == STATE_PULL_REFREH) {
                    mHeaderview.setPadding(0, -measuredHeight, 0, 0);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    //松开刷新
    private void refreshState() {

        switch (mCurrentState) {
            case STATE_PULL_REFREH:
                tv_title.setText("下拉刷新");
//                iv_arr.setVisibility(View.VISIBLE);
//                pbProgress.setVisibility(View.INVISIBLE);
//                iv_arr.startAnimation(animationDown);
                break;
            case STATE_RELLASE_REFREH:
                tv_title.setText("松开刷新");
//                iv_arr.setVisibility(View.VISIBLE);
//                pbProgress.setVisibility(View.INVISIBLE);
//                iv_arr.startAnimation(animationUp);
                break;
            case STATE_REFRESHING:
                tv_title.setText("正在加载");
//                iv_arr.clearAnimation(); // 必须先清除动画才能隐藏
//                iv_arr.setVisibility(View.INVISIBLE);
//                pbProgress.setVisibility(View.VISIBLE);
                if (listener != null) {
                    listener.OnRefresh(); // 刷新方法
                }
                mHeaderview.setPadding(0,0,0,0); //显示
                break;

        }
    }

    /**
     * 箭头动画
     */
    private RotateAnimation animationDown;
    private RotateAnimation animationUp;

    private void initArrAnim() {

        animationUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationUp.setDuration(200);
        animationUp.setFillAfter(true);

        animationDown = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationDown.setDuration(200);
        animationDown.setFillAfter(true);
    }

    // 滑动监听
    public void setRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    private boolean isLoadingMore;
    private boolean scrollFlag;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING && !isLoadingMore) {

            if (getLastVisiblePosition() == getCount() - 1) {
                // 表示滑动到最后一条
                mFoterView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);  // 改变Listview的显示问题
                isLoadingMore = true;

                if (listener != null) {
                    listener.onLoadMore();
                }
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // 下拉刷新的接口
    private OnRefreshListener listener;

    public interface OnRefreshListener {
        public void OnRefresh();
        public void onLoadMore();
    }

    //收起下拉刷新的控件
    public void OnRefreshComplete(boolean success) {
        if (isLoadingMore) {
            //隐藏叫布局
            mFoterView.setPadding(0, -mFoterViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            mCurrentState = STATE_PULL_REFREH;
            tv_title.setText("下拉刷新");
            iv_arr.setVisibility(VISIBLE);
            pbProgress.setVisibility(INVISIBLE);
            //隐藏
            mHeaderview.setPadding(0, -measuredHeight, 0, 0);
            if (success) {
                tv_time.setText("最后刷新时间 " + getCurrentTime());
            }
        }
    }

    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
