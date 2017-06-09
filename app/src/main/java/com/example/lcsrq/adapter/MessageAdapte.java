package com.example.lcsrq.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lcsrq.R;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class MessageAdapte extends BaseAdapter {

    private Activity activity;
    public MessageAdapte(Activity activity) {
    this.activity =activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(activity, R.layout.messager_item, null);
        return inflate;
    }
}
