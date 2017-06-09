package com.example.lcsrq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.CountyDetailActivity;
import com.example.lcsrq.activity.manger.xxcx.MessageIntentActivity;
import com.example.lcsrq.adapter.MessageAdapte;
import com.example.lcsrq.adapter.MessageFAdapter;
import com.example.lcsrq.base.BaseFragment;

/**
 * Created by 苏毅 on 2017/3/30.
 */

public class MessageFragment extends BaseFragment {
    private LayoutInflater inflater;
    private boolean scannerVisible = true;
    private ListView lv_list;

    public MessageFragment() {
        super();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_message, container, false);
        findViews(view);
        addAction();
        return view;
    }

    private LinearLayout commonLeftBtn;
    @Override
    public void findViews(View root) {
        lv_list =(ListView) root.findViewById(R.id.lv_list);


        TextView commonTitleTv = (TextView) root.findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("消息");

        MessageAdapte messageAdapte = new MessageAdapte(getActivity());
        lv_list.setAdapter(messageAdapte);


    }

    @Override
    public void addAction() {
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), MessageIntentActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void call(int callID, Object... args) {

    }
}
