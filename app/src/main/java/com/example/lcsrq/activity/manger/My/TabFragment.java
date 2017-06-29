package com.example.lcsrq.activity.manger.My;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.bean.req.MyrectificationReqDataim;
import com.example.lcsrq.bean.req.TiJiaoZgstate;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItems;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;

public class TabFragment extends Fragment implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    TextView tabfragmenttextview;
    private ListView lv_list;
    private ArrayList<MyrectificationRespDataim> datas;
    private LoginModel loginModel;
    private MyAdapter myAdapter;
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private ArrayList<MyrectificationRespDataim> loadMoredatas;
    private int pagesize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginModel = new LoginModel();
        initView(view);
        initData();
    }



    // 解析数据
    private void initData() {
        getMyRecti();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1){
                myAdapter = new MyAdapter();
                myAdapter.setList(datas);
                lv_list.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
            else if (msg.arg2 == 2){
                page++;
                datas.addAll(loadMoredatas);
                myAdapter.notifyDataSetChanged();
            }
        }
    };

    int page = 2;
    private void LoadMore(int page) {
        // 我的整改
        MyrectificationReqDataim myrectificationReqDataim = new MyrectificationReqDataim();
        myrectificationReqDataim.setUid(Integer.parseInt(Global.uid));
        myrectificationReqDataim.setPage(page);
        myrectificationReqDataim.setStatus(mCurrentPosition); //筛选条件

        loginModel.getMyRectification(getContext(), myrectificationReqDataim, new OnLoadComBackListener() {

            @Override
            public void onSuccess(Object msg) {
                loadMoredatas = (ArrayList<MyrectificationRespDataim>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 2;
                handler.sendMessage(message);

                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();

            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(),"没有更多的数据", Toast.LENGTH_LONG).show();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        LoadMore(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 2;
        getMyRecti(); //  获取的我整改
    }

    public class MyAdapter extends BaseAdapter {

        private ArrayList<MyrectificationRespDataim> list = new ArrayList<>();

        public ArrayList<MyrectificationRespDataim> getList() {
            return list;
        }

        public void setList(ArrayList<MyrectificationRespDataim> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.myzhenggai, null);
                holder.tv_detail= (TextView) convertView.findViewById(R.id.tv_detail); // 整改详情
                holder.tv_gongyinghzan = (TextView) convertView.findViewById(R.id.tv_gongyinghzan); //  供应站
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); // 时间
                holder.tv_riqi = (TextView) convertView.findViewById(R.id.tv_riqi);  // 检查时间
                holder.tv_jcr = (TextView) convertView.findViewById(R.id.tv_jcr); //  检查人
                holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);  // 检查单位


                holder.morePicLayout = (LinearLayout) convertView.findViewById(R.id.morePicLayout);
                // 动态
                holder.oneImgIv = (SimpleDraweeView) convertView.findViewById(R.id.oneImgIv);
                holder.imgs[0] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv1);
                holder.imgs[1] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv2);
                holder.imgs[2] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv3);
                holder.imgs[3] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv4);
                holder.imgs[4] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv5);
                holder.imgs[5] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv6);
                holder.imgs[6] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv7);
                holder.imgs[7] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv8);
                holder.imgs[8] = (SimpleDraweeView) convertView.findViewById(R.id.imgIv9);

                holder.btn_zg = (Button) convertView.findViewById(R.id.btn_zg);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_gongyinghzan.setText("检查站点 : " + list.get(position).getSupply_name());
            holder.tv_detail.setText(list.get(position).getRemark());
            holder.tv_time.setText("检查时间 : " + list.get(position).getCreat_at());
            holder.tv_riqi.setText("检查公司 : " + list.get(position).getCompany_name());
            holder.tv_riqi.setMaxEms(6);
            holder.tv_riqi.setEllipsize(TextUtils.TruncateAt.END);
            holder.tv_jcr.setText("检查人 : " + list.get(position).getCheck_uids_names());
            holder.tv_danwei.setText("检查单位 : " + list.get(position).getCheck_dw());

            switch (list.get(position).getStatus()) {
                case "1":
                    holder.btn_zg.setText("待整改");
                    break;
                case "2":
                    holder.btn_zg.setText("已整改");
                    break;
                case "3":
                    holder.btn_zg.setText("已验收");
                    break;
                case "4":
                    holder.btn_zg.setText("待签收");
                    break;
            }


//             横向滑动传的是图片
//            if (urlList.size() != 0) {
//                Radapter radapter = new Radapter(urlList.get(position), MyRectification.this);
//                holder.easyRecyclerView.setAdapter(radapter);
//            }
//
//            if (urlList.size() != 0) {
//                RecycleAdapter radapter = new RecycleAdapter(MyRectification.this);
//                radapter.setStrings(urlList.get(position));
//                holder.easyRecyclerView.setAdapter(radapter);
//            }

            if (list.get(position).getFlag() == "1"){
                holder.btn_zg.setText("查处中");
                holder.btn_zg.setTextColor(Color.WHITE);
                holder.btn_zg.setBackgroundResource(R.drawable.jiancha_button);
            }

            // 整改点击事件  提交整改状态
            holder.btn_zg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 如果是管理人员,则不可以验收  或者是公司人员,不能点击
                    if (Global.m_roleid.equals("3") || Global.m_roleid.equals("2")){
                        return;
                    }
                    if (list.get(position).getFlag() == "1"){
                        return;
                    }
                    //状态码
                    String status = list.get(position).getStatus();
                    if (Integer.parseInt(status) == 3){  //  如果是已验收, 就直接跳过去
                        return;
                    }
                    //检查码
                    String check_id = list.get(position).getLogid();
                    // 用户UID
                    String uid = Global.uid;
                    // 设置请求参数
                    TiJiaoZgstate tiJiaoZgstate = new TiJiaoZgstate();
                    tiJiaoZgstate.setDid(Integer.parseInt(check_id));
                    tiJiaoZgstate.setStatus_uid(Integer.parseInt(uid));
                    if (Integer.parseInt(status) == 1) {  //待整改
                        status = "2";
                    } else if (Integer.parseInt(status) == 2) { // 已整改
                        status = "3";
                    }else if(Integer.parseInt(status) == 4) { //  代签收
                        status = "1";
                    }
                    tiJiaoZgstate.setStatus(Integer.parseInt(status));
                    loginModel.TijiaZgstate(getActivity(), tiJiaoZgstate, new OnLoadComBackListener() {
                        @Override
                        public void onSuccess(Object msg) {
                            list.get(position).setFlag("1"); //  添加标记,用来标记已经点击过了
                            Toast.makeText(getActivity(), "查处成功", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onError(String msg) {
                            Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            return convertView;
        }
    }

    public class ViewHolder {
        TextView tv_gongyinghzan;
        Button btn_zg;
        TextView tv_time;
        TextView tv_riqi;
        TextView tv_jcr;
        TextView tv_danwei;
        TextView tv_detail;
        //        RecyclerView easyRecyclerView;
        public LinearLayout morePicLayout;
        public SimpleDraweeView oneImgIv;
        public SimpleDraweeView[] imgs = new SimpleDraweeView[9];

    }
    int  mCurrentPosition = -1;
    private void getMyRecti(){
        Toast.makeText(getActivity(),mCurrentPosition + "",Toast.LENGTH_SHORT).show();
        // 我的整改
        MyrectificationReqDataim myrectificationReqDataim = new MyrectificationReqDataim();
        myrectificationReqDataim.setUid(Integer.parseInt(Global.uid));
        myrectificationReqDataim.setStatus(mCurrentPosition);
        loginModel.getMyRectification(getActivity(), myrectificationReqDataim, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                datas = (ArrayList<MyrectificationRespDataim>) msg;
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);

                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                if (msg.toString().equals("无数据")){

                }else {
                    Toast.makeText(getActivity(),msg.toString(), Toast.LENGTH_LONG).show();
                }
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    private void initView(View root) {

        tabfragmenttextview = (TextView) root.findViewById(R.id.tab_fragment_textview);
        pagesize = FragmentPagerItem.getPosition(getArguments());
        tabfragmenttextview.setText(String.valueOf(pagesize));
        mCurrentPosition = pagesize;


        // 如果当页面== 0,就变成4
        if (pagesize == 0){
            mCurrentPosition = 4;
        }



        // ListView
        lv_list = (ListView) root.findViewById(R.id.lv_list);

           // 下拉刷新
        pullToRefreshView = (PullToRefreshView) root.findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        // 异常加载页面
        type_page_progress = (ProgressActivity) root.findViewById(R.id.type_page_progress);
    }
}
