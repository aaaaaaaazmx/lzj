package com.example.lcsrq.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class ExpandableListViewDY extends ExpandableListView {
    public ExpandableListViewDY(Context context) {
        super(context);
    }

    public ExpandableListViewDY(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListViewDY(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpandableListViewDY(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
