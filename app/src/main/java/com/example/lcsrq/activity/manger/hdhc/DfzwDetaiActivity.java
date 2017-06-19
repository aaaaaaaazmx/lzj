package com.example.lcsrq.activity.manger.hdhc;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.adapter.DfzwDetailAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.HdhcDetailReqData;
import com.example.lcsrq.bean.resq.AllCclistRespData;
import com.example.lcsrq.bean.resq.HdhcDetailRespData;
import com.example.lcsrq.bean.resq.HdhcRespData;
import com.example.lcsrq.bean.resq.JuBaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DensityUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏毅 on 2017/5/12.
 *  打非治违详情页面
 */

public class DfzwDetaiActivity extends BaseActivity {

    private ArrayList<AllCclistRespData> dfzwList;
    private String did;
    private TextView tv_creat;
    private TextView tv_address;
    private TextView tv_jbcs;
    private TextView tv_cp;
    private TextView tv_yjfzr;
    private TextView tv_ejfzr;
    private TextView tv_content1;
    private SimpleDraweeView oneImgIv;
    private LoginModel loginModel;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private LinearLayout morePicLayout;
    private ListView ll_dfzw;
    private ListView cc_list;
    private DfzwDetailAdapter dfzwDetailAdapter;
    private int totalHeight = 0;
    private String state;
    private ImageView iv_state;
    private TextView tv_ccqk;
    private JuBaoBean dfzwdatas;
    private TextView commonRightText;
    private String flag;
    private ScrollView sv_container;
    private RelativeLayout layout_common_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dfzw_detail_activity
        setContentView(R.layout.dfzw_item);
        showLoading("正在加载");
        dfzwdatas = (JuBaoBean) getIntent().getSerializableExtra("dfzwdatas"); //  要查处的项目
        did = getIntent().getStringExtra("ID"); //  打非治违传过来的ID
        dfzwList = (ArrayList<AllCclistRespData>) getIntent().getSerializableExtra("dfzw"); //  获取传过来的CCList
        state = getIntent().getStringExtra("state");  // 传过来的状态

        initData();

        //  设置lstview的Adapter (查处情况)
        if (dfzwList.size() == 0 ){
            // 表示没有数据
            cc_list.setVisibility(View.GONE);
        }else {
            cc_list.setVisibility(View.VISIBLE);
            dfzwDetailAdapter = new DfzwDetailAdapter(DfzwDetaiActivity.this);
            dfzwDetailAdapter.setDfzwList(dfzwList);
            cc_list.setAdapter(dfzwDetailAdapter);


            int count = dfzwDetailAdapter.getCount();
            //  动态设置listview的高度
            for (int i = 0; i< count ; i++){
                View view = dfzwDetailAdapter.getView(i, null, cc_list);
                cc_list.measure(0,0);
                totalHeight += cc_list.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = cc_list.getLayoutParams();
            params.height = totalHeight + (cc_list.getDividerHeight() * (dfzwDetailAdapter.getCount() - 1));
            cc_list.setLayoutParams(params);

        }

        //区别状态
        if (state.equals("1")){
            iv_state.setImageResource(R.mipmap.icom_dcc);
            iv_state.setVisibility(View.VISIBLE);
        }
        if (state.equals("2")){
            iv_state.setImageResource(R.mipmap.icom_ccz);
            iv_state.setVisibility(View.VISIBLE);
        }
        if (state.equals("3")){
            iv_state.setImageResource(R.mipmap.icom_ycc);
            iv_state.setVisibility(View.VISIBLE);
        }
        if (state.equals("0")){
            iv_state.setVisibility(View.GONE);
        }


    }
    private HdhcDetailRespData data;
    int index = -1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 2 ){
            // 图片现在不需要显示了
                commonTitleTv.setText(data.getAreas());

                String imgUrlStr = data.getUpload_json();
                if (!TextUtils.isEmpty(imgUrlStr)) {
                    final String[] imgurls = imgUrlStr.split(",");
                    if (imgurls.length == 1) {
                        oneImgIv.setVisibility(View.VISIBLE);
                        morePicLayout.setVisibility(View.GONE);
                        DensityUtil.lzj(oneImgIv, imgurls[0]);
                        oneImgIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UiTool.showPic(DfzwDetaiActivity.this, imgurls[0]);
                            }
                        });
                    } else if (imgurls.length > 1) {
                        oneImgIv.setVisibility(View.GONE);
                        morePicLayout.setVisibility(View.VISIBLE);
                        for (int i = 0; i <imgs.length; i++) {
                            if (i < imgurls.length) {
                                DensityUtil.lzj(imgs[i], imgurls[i]);
                                index = i;
                                imgs[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UiTool.showPic(DfzwDetaiActivity.this, imgurls[index]);
                                    }
                                });
                            }
                            else {
                                double lineImg = imgurls.length / 4; // 图片显示不全 除以4
                                double lineNoImg = i / 3;
                                if (lineNoImg > lineImg) {
                                    imgs[i].setVisibility(View.GONE);
                                } else {
                                    imgs[i].setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                } else {
                    morePicLayout.setVisibility(View.GONE);
                    oneImgIv.setVisibility(View.GONE);
                }
                /**
                 *    // 时间
                 tv_creat = (TextView) findViewById(R.id.tv_creat);
                 // 黑车黑点地址
                 tv_address = (TextView) findViewById(R.id.tv_address);
                 // 举报次数
                 tv_jbcs = (TextView) findViewById(R.id.tv_jbcs);
                 // 车牌
                 tv_cp = (TextView) findViewById(R.id.tv_cp);
                 // 一级负责人
                 tv_yjfzr = (TextView) findViewById(R.id.tv_yjfzr);
                 // 二级负责人
                 tv_ejfzr = (TextView) findViewById(R.id.tv_ejfzr);
                 // 内容
                 tv_content1 = (TextView) findViewById(R.id.tv_content1);
                 */
                tv_creat.setText("时间 : " + data.getCreat_at());
                tv_address.setText(data.getAddress());
                tv_address.setTextColor(Color.BLACK); //  设置颜色字体
//                tv_cp.setText("车牌号 : " +data.getCart_number());

                if (dfzwList.size()!= 0) {
                    tv_jbcs.setText("举报次数 : " + dfzwList.size() + "");
                    tv_yjfzr.setText("一级负责人 : " + dfzwList.get(0).getM_nickname());
                    tv_ejfzr.setText("二级负责人 : " + dfzwList.get(0).getMan());
                }

                tv_content1.setText(data.getContent());  //  查处内容
            }
        }
    };
    private void initData() {

        // 供应站详情
        loginModel = new LoginModel();
        HdhcDetailReqData hdhcDetailReqData = new HdhcDetailReqData();
        hdhcDetailReqData.setDid(Integer.parseInt(did));
        loginModel.getListOfDetailHdhc(DfzwDetaiActivity.this, hdhcDetailReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                data = JSON.parseObject((String) msg, HdhcDetailRespData.class);
                Message message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                closeDialog();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(DfzwDetaiActivity.this,msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);

        tv_ccqk.setOnClickListener(this);
        commonRightText.setOnClickListener(this);
    }

    public SimpleDraweeView[] imgs = new SimpleDraweeView[9];

    @Override
    protected void findViews() {
        // sv_container
        layout_common_title = (RelativeLayout) findViewById(R.id.layout_common_title);
        layout_common_title.setFocusable(true);
        layout_common_title.setFocusableInTouchMode(true);
        layout_common_title.requestFocus();

        //  头部局
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);

        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightText.setText("查处");

        // 时间
        tv_creat = (TextView) findViewById(R.id.tv_creat);
        // 黑车黑点地址
        tv_address = (TextView) findViewById(R.id.tv_address);
        // 举报次数
        tv_jbcs = (TextView) findViewById(R.id.tv_jbcs);
        // 车牌
        tv_cp = (TextView) findViewById(R.id.tv_cp);
        // 一级负责人
        tv_yjfzr = (TextView) findViewById(R.id.tv_yjfzr);
        // 二级负责人
        tv_ejfzr = (TextView) findViewById(R.id.tv_ejfzr);
        // 内容
        tv_content1 = (TextView) findViewById(R.id.tv_content1);

        // 状态
        iv_state = (ImageView) findViewById(R.id.iv_state);
        // 查处情况
        tv_ccqk = (TextView) findViewById(R.id.tv_ccqk);

        // 图片
        oneImgIv = (SimpleDraweeView) findViewById(R.id.oneImgIv);
        imgs[0] = (SimpleDraweeView) findViewById(R.id.imgIv1);
        imgs[1] = (SimpleDraweeView) findViewById(R.id.imgIv2);
        imgs[2] = (SimpleDraweeView) findViewById(R.id.imgIv3);
        imgs[3] = (SimpleDraweeView) findViewById(R.id.imgIv4);
        imgs[4] = (SimpleDraweeView) findViewById(R.id.imgIv5);
        imgs[5] = (SimpleDraweeView) findViewById(R.id.imgIv6);
        imgs[6] = (SimpleDraweeView) findViewById(R.id.imgIv7);
        imgs[7] = (SimpleDraweeView) findViewById(R.id.imgIv8);
        imgs[8] = (SimpleDraweeView) findViewById(R.id.imgIv9);

        morePicLayout = (LinearLayout) findViewById(R.id.morePicLayout);

//        ll_dfzw = (ListView) findViewById(R.id.ll_dfzw);

        cc_list = (ListView) findViewById(R.id.cc_list);
//        //  查处情况  传过来的CCLIST


    }
    private boolean click = true;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }else if (v.getId() == R.id.tv_ccqk){
            if(click){
                cc_list.setVisibility(View.VISIBLE);
                click = false;
            }else {
                cc_list.setVisibility(View.GONE);
                click = true;
            }
        }
        if (v.getId() == R.id.commonRightText){
            if (state.equals("1")){
                    //表示待查处
                    //  跳传过去,并传过去JB_ID
                    Intent intent = new Intent(DfzwDetaiActivity.this, HdhcCheckActivity.class);
                    intent.putExtra("jb_id",data.getId());
                Toast.makeText(DfzwDetaiActivity.this,data.getId(),Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return;
                }else if (state.equals("2")){
                    Intent intent = new Intent(DfzwDetaiActivity.this, HdhcCheckActivity.class);
                    intent.putExtra("jb_id",data.getId());
                    startActivity(intent);
                return;
                }else {
                    // 表示已查处
                    return;
                }
        }
    }

}
