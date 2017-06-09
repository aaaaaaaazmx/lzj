package com.example.lcsrq.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.JuBaoActivity;
import com.example.lcsrq.activity.manger.My.MyHdActivity;
import com.example.lcsrq.activity.manger.My.MyJuBaoActivity;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.My.MyScoreActivity;
import com.example.lcsrq.activity.manger.My.MyZgActivity;
import com.example.lcsrq.activity.manger.My.MyZhengGaiActiviity;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.activity.manger.hdhc.MyHdHcactvity;
import com.example.lcsrq.base.BaseFragment;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.value.Global;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 苏毅 on 2017/3/30.
 */


public class MyFragment extends BaseFragment {
    private LayoutInflater inflater;
    private boolean scannerVisible = true;

    private TextView user_name;

    private RelativeLayout rl_my_zg;
    private RelativeLayout rl_my_hdhc,rl_contact;
    private SimpleDraweeView noLoginUserIconIv;
    private RelativeLayout rl_myjubao,rl_myjiancha;
    private RelativeLayout rl_jf;

    public MyFragment() {
        super();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_me, container, false);
        user_name = (TextView) view.findViewById(R.id.user_name);


        findViews(view);
        addAction();
        initData();
        return view;
    }

    private void initData() {
        if (!TextUtils.isEmpty(Global.usernName)) {
            user_name.setText(Global.usernName);
        }
    }

    @Override
    public void findViews(View root) {
        noLoginUserIconIv = (SimpleDraweeView) root.findViewById(R.id.noLoginUserIconIv);
        if (!TextUtils.isEmpty(Global.userIcon)) {
            noLoginUserIconIv.setImageURI(Uri.parse(Global.userIcon));
        }else {
            noLoginUserIconIv.setImageResource(R.mipmap.head);
        }

        rl_my_zg = (RelativeLayout) root.findViewById(R.id.rl_my_zg); //我的整改
        // 只有从业人员才能出现
//        if (Global.m_roleid.equals("1")){
//            rl_my_zg.setVisibility(View.VISIBLE);
//        }else {
//            rl_my_zg.setVisibility(View.GONE);
//        }
        rl_my_zg.setVisibility(View.VISIBLE);

        rl_my_hdhc = (RelativeLayout) root.findViewById(R.id.rl_my_hdhc); //我的黑点
        // 管理人员才出现我的黑点
//        if (Global.m_roleid.equals("3")){
//            rl_my_hdhc.setVisibility(View.VISIBLE);
//        }else {
//            rl_my_hdhc.setVisibility(View.GONE);
//        }
        rl_my_hdhc.setVisibility(View.VISIBLE);

        rl_contact = (RelativeLayout) root.findViewById(R.id.rl_contact); //我的通讯录
        // 如果是管理人员就出现我的通讯录
//        if (Global.m_roleid.equals("3")) {
//            rl_contact.setVisibility(View.VISIBLE);
//        }else {
//            rl_contact.setVisibility(View.GONE);
//        }
        rl_contact.setVisibility(View.VISIBLE);
        rl_myjubao = (RelativeLayout) root.findViewById(R.id.rl_myjubao); //我的举报

        //我的检查
        rl_myjiancha = (RelativeLayout) root.findViewById(R.id.rl_myjiancha);
        // 管人员才出现我的检查
//        if (Global.m_roleid.equals("3")){
//            rl_myjiancha.setVisibility(View.VISIBLE);
//        }else {
//            rl_myjiancha.setVisibility(View.GONE);
//        }
        rl_myjiancha.setVisibility(View.VISIBLE);

        rl_jf = (RelativeLayout) root.findViewById(R.id.rl_jf);  //我的计分

        //  如果是管理人员就不显示我的计分
//        if (Global.m_roleid.equals("3")){  // 如果是管理人员则不显示
//            rl_jf.setVisibility(View.GONE);
//        }
        rl_jf.setVisibility(View.VISIBLE);
    }

    @Override
    public void addAction() {
        rl_myjubao.setOnClickListener(this);
        rl_my_zg.setOnClickListener(this);
        rl_my_hdhc.setOnClickListener(this);
        rl_contact.setOnClickListener(this);
        rl_myjiancha.setOnClickListener(this);
        rl_jf.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //  跳转我的整改
        if (v.getId() == R.id.rl_my_zg) {
//            startActivity(new Intent(getActivity(), MyRectification.class));
            startActivity(new Intent(getActivity(), MyZhengGaiActiviity.class));
        } else if (v.getId() == R.id.rl_my_hdhc) {
            // 我的黑点
            if (Global.m_roleid.equals("3")) {
//                startActivity(new Intent(getActivity(), MyHdHcactvity.class));
                startActivity(new Intent(getActivity(), MyHdActivity.class));
            }else {
                Toast.makeText(getActivity(),"您没有权限",Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.rl_contact){
                 // 我的通讯录
                startActivity(new Intent(getActivity(), MycontactActivity.class));

        }else if (v.getId() == R.id.rl_myjubao){
            //我的举报
//            startActivity(new Intent(getActivity(),MyHdHcactvity.class));
//            startActivity(new Intent(getActivity(),MyJuBaoActivity.class));
            startActivity(new Intent(getActivity(),JuBaoActivity.class));
        }else if (v.getId() == R.id.rl_myjiancha){
            //  我的检查
            if (Global.m_roleid.equals("3")) {
                // 跳转我的检查
                startActivity(new Intent(getActivity(),MyZgActivity.class));
            }else {
                Toast.makeText(getActivity(),"您没有权限",Toast.LENGTH_SHORT).show();
            }
        }
        //w我的记分
        if (v.getId() == R.id.rl_jf){
            startActivity(new Intent(getActivity(), MyScoreActivity.class));
        }
    }
    @Override
    public void call(int callID, Object... args) {

    }
}
