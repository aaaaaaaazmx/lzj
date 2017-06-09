package com.example.lcsrq.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lcsrq.R;

/**
 * Created by 苏毅 on 2017/4/4.
 */

public class AppointmentAdapter extends BaseAdapter {

    String[] title = {"2017湖南公务员考试","政法干警","驾照考试"};
    private Context context;
    public AppointmentAdapter(Context context){
            this.context = context;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.list_item_appointment, null);
        TextView tv_kst = (TextView) view.findViewById(R.id.tv_kst);
        tv_kst.setText(title[position]);
        return view;
    }
}
