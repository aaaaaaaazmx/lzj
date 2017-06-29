package com.example.lcsrq.activity.manger.My;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lcsrq.Constant.Constant;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzMangerActivity;
import com.example.lcsrq.adapter.RecycleAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.MyrectificationReqDataim;
import com.example.lcsrq.bean.req.TiJiaoZgstate;
import com.example.lcsrq.bean.respbean.Check_uids_names;
import com.example.lcsrq.bean.resq.ContentComPanyRespData;
import com.example.lcsrq.bean.resq.MyrectificationRespDataim;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DensityUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.PullToRefreshView;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的整改
 */
public class MyRectification extends BaseActivity
     implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    private ArrayList<MyrectificationRespDataim> datas;
    private LoginModel loginModel;
    private ListView lv_wdzg;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout commonLeftBtn;
    private TextView commonTitleTv;
    private PullToRefreshView pullToRefreshView;
    private ProgressActivity type_page_progress;
    private ArrayList<MyrectificationRespDataim> loadMoredatas;
    private String uname;
    private LinearLayout ll_company;
    private TextView tv_company;
    private LinearLayout ll_state;
    private TextView tv_state;
    private ArrayList<ContentComPanyRespData> comPanyRespDatas;
    // 我的整改
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rectification);
        loginModel = new LoginModel();
        showLoading("正在加载");
        initData();
    }

    List<String[]> urlList = new ArrayList<>();
    private boolean isNotNull = true;
    private ArrayList<String> options1ItemsGS = new ArrayList<String>();
    private OptionsPickerView optionsPopupWindowGS;
    private String gSid;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {

                myAdapter = new MyAdapter();
                myAdapter.setList(datas);
                lv_wdzg.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }else if (msg.arg2 ==2 ){
                page++;
                datas.addAll(loadMoredatas);
                myAdapter.notifyDataSetChanged();
            } else if (msg.arg2 == 5) {  // 公司
                 options1ItemsGS.add("不限");
                    if (isNotNull) {
                    for (int i = 0; i < comPanyRespDatas.size(); i++) {
                        options1ItemsGS.add(comPanyRespDatas.get(i).getTitle());
                    }
                    isNotNull = false;
                }

                optionsPopupWindowGS = new  OptionsPickerView.Builder(MyRectification.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        // 清楚保存的公司DI
                        gSid = null;
                        //返回的分别是三个级别的选中位置
                        String tx = options1ItemsGS.get(options1);
                        tv_company.setText(tx);

                        if (options1 > 0) {
                            String conpany = options1ItemsGS.get(options1 - 1);
                            // 获取公司ID
                            gSid = comPanyRespDatas.get(options1 - 1).getId();
                            // 填写公司名字
                            tv_company.setText(tx);
                        }
                        // 获取公司名字,调用刷新方法
                            showLoading("正在加载");
                            getMyRecti();  //  刷新我的整改
                    }
                }) .setTitleSize(20)
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setContentTextSize(18).build();

                // 三级联动效果
                optionsPopupWindowGS.setPicker(options1ItemsGS);
                //设置默认选中的三级项目
                optionsPopupWindowGS.setSelectOptions(0);

            }
        }
    };
    private int state = 0;

    private void getMyRecti(){
        // 我的整改
        MyrectificationReqDataim myrectificationReqDataim = new MyrectificationReqDataim();
        myrectificationReqDataim.setUid(Integer.parseInt(Global.uid));

        if (!TextUtils.isEmpty(gSid)){
            myrectificationReqDataim.setCompany_id(gSid);
        }

        // 筛选状态
        //!TextUtils.isEmpty(tv_state.getText().toString())
        if (!TextUtils.isEmpty(tv_state.getText().toString())){
            if (tv_state.getText().toString().equals("不限")){
                state = 0;
            }else if (tv_state.getText().toString().equals("待查处")){
                state = 1;
            }else if (tv_state.getText().toString().equals("查处中")){
                state = 2;
            }else if (tv_state.getText().toString().equals("已查处")){
                state = 3;
            }
        }
        myrectificationReqDataim.setStatus(state); //筛选条件

        loginModel.getMyRectification(MyRectification.this, myrectificationReqDataim, new OnLoadComBackListener() {

            @Override
            public void onSuccess(Object msg) {
                type_page_progress.showContent();
                datas = (ArrayList<MyrectificationRespDataim>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyRectification.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
                type_page_progress.showError(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });;
            }
        });
    }

    private void initData() {

        // 获取我的整改
        getMyRecti();

        // 获取公司列表
        initCompany();
    }

    // 刷新公司
    private void initCompany() {
        final ContentCompanyReqData contentCompanyReqData = new ContentCompanyReqData();
        //  只有供应站管理才增加权限,其他的都不需要
        loginModel.getListOfCompany(MyRectification.this, contentCompanyReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                closeDialog();
                comPanyRespDatas = (ArrayList<ContentComPanyRespData>) msg;
                Message message = handler.obtainMessage();
                message.arg2 = 5;
                handler.sendMessage(message);
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyRectification.this, msg.toString(), Toast.LENGTH_LONG).show();
                pullToRefreshView.onHeaderRefreshComplete();
                pullToRefreshView.onFooterRefreshComplete();
            }
        });
    }
    private void LoadMore(int page) {
        // 我的整改
        MyrectificationReqDataim myrectificationReqDataim = new MyrectificationReqDataim();
        myrectificationReqDataim.setUid(Integer.parseInt(Global.uid));
        myrectificationReqDataim.setPage(page);

        if (!TextUtils.isEmpty(gSid)){
            myrectificationReqDataim.setCompany_id(gSid);
        }

        // 筛选状态
        //!TextUtils.isEmpty(tv_state.getText().toString())
        if (!TextUtils.isEmpty(tv_state.getText().toString())){
            if (tv_state.getText().toString().equals("不限")){
                state = 0;
            }else if (tv_state.getText().toString().equals("待查处")){
                state = 1;
            }else if (tv_state.getText().toString().equals("查处中")){
                state = 2;
            }else if (tv_state.getText().toString().equals("已查处")){
                state = 3;
            }
        }
        myrectificationReqDataim.setStatus(state); //筛选条件

        loginModel.getMyRectification(MyRectification.this, myrectificationReqDataim, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                loadMoredatas = (ArrayList<MyrectificationRespDataim>) msg;
                closeDialog();
                Message message = handler.obtainMessage();
                message.arg2 = 2;
                handler.sendMessage(message);
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MyRectification.this,"没有更多的数据了", Toast.LENGTH_LONG).show();
                closeDialog();
                pullToRefreshView.onFooterRefreshComplete();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);

        lv_wdzg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转我的整改item的详情页面
                // 已经或暂时,不知道要不要展示
            }
        });

        ll_company.setOnClickListener(this);
        ll_state.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        lv_wdzg = (ListView) findViewById(R.id.lv_wdzg);
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("我的整改");

        // 下拉刷新
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        // 异常加载页面
        type_page_progress = (ProgressActivity) findViewById(R.id.type_page_progress);

        //公司
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        tv_company = (TextView) findViewById(R.id.tv_company);
        //状态
        ll_state = (LinearLayout) findViewById(R.id.ll_state);
        tv_state = (TextView) findViewById(R.id.tv_state);

    }

    private boolean First= true;
    private ArrayList<String> options1ItemsState = new ArrayList<>();
    private OptionsPickerView optionsPopupWindowState;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            finish();
        }else if (v.getId() == R.id.ll_state){
            // 状态
            if (First) {
                options1ItemsState.add("不限");
                options1ItemsState.add("待查处");
                options1ItemsState.add("查处中");
                options1ItemsState.add("已查处");
            }
            optionsPopupWindowState = new  OptionsPickerView.Builder(MyRectification.this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    String state = options1ItemsState.get(options1);
                    tv_state.setText(state);
                    //  根据装填获取我的整改
                    getMyRecti();
                }
            }) .setTitleSize(20)
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setContentTextSize(18).build();
            // 三级联动效果
            optionsPopupWindowState.setPicker(options1ItemsState);
            //设置默认选中的三级项目
            optionsPopupWindowState.setSelectOptions(0);
            optionsPopupWindowState.show();
            First = false;
        }
        else if (v.getId() == R.id.ll_company){
            optionsPopupWindowGS.show();
        }
    }
    private int page = 2;
    // 下拉刷新,以及加载
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(MyRectification.this, R.layout.myzhenggai, null);
                holder.tv_detail= (TextView) convertView.findViewById(R.id.tv_detail); // 整改详情
                holder.tv_gongyinghzan = (TextView) convertView.findViewById(R.id.tv_gongyinghzan); //  供应站
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); // 时间
                holder.tv_riqi = (TextView) convertView.findViewById(R.id.tv_riqi);  // 检查时间
                holder.tv_jcr = (TextView) convertView.findViewById(R.id.tv_jcr); //  检查人
                holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);  // 检查单位

                // 横向滑动的图片
//                holder.easyRecyclerView = (RecyclerView) convertView.findViewById(R.id.easyRV);
//                linearLayoutManager = new LinearLayoutManager(MyRectification.this);
//                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                holder.easyRecyclerView.setLayoutManager(linearLayoutManager);

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
            //图片
            // 目前不要请图片
//            String imgUrlStr = list.get(position).getUpload_json();
//            if (!TextUtils.isEmpty(imgUrlStr)) {
//                final String[] imgurls = imgUrlStr.split(",");
//                if (imgurls.length == 1) {
//                    holder.oneImgIv.setVisibility(View.VISIBLE);
//                    holder.morePicLayout.setVisibility(View.GONE);
//                    holder.oneImgIv.setImageURI(Uri.parse(imgurls[0]));
//                    holder.oneImgIv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            UiTool.showPic(MyRectification.this, imgurls[0]);
//                        }
//                    });
//                } else if (imgurls.length > 1) {
//                    holder.oneImgIv.setVisibility(View.GONE);
//                    holder.morePicLayout.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < holder.imgs.length; i++) {
//                        if (i < imgurls.length) {
//                            holder.imgs[i].setImageURI(Uri.parse(imgurls[i]));
//                            final int index = i;
//                            holder.imgs[i].setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    UiTool.showPic(MyRectification.this, imgurls[index]);
//                                }
//                            });
//                        } else {
//                            int lineImg = imgurls.length / 3;
//                            int lineNoImg = i / 3;
//                            if (lineNoImg > lineImg) {
//                                holder.imgs[i].setVisibility(View.GONE);
//                            } else {
//                                holder.imgs[i].setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    }
//                }
//            } else {
//                holder.morePicLayout.setVisibility(View.GONE);
//                holder.oneImgIv.setVisibility(View.GONE);
//            }

            /**
             *   holder.tv_detail= (TextView) convertView.findViewById(R.id.tv_detail); // 整改详情
             holder.tv_gongyinghzan = (TextView) convertView.findViewById(R.id.tv_gongyinghzan); //  供应站
             holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); // 时间
             holder.tv_riqi = (TextView) convertView.findViewById(R.id.tv_riqi);  // 检查时间
             holder.tv_jcr = (TextView) convertView.findViewById(R.id.tv_jcr); //  检查人
             holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);  // 检查单位
             */
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
                    holder.btn_zg.setText("验收");
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
                    // 如果是管理人员,则不可以验收
                    if (Global.m_roleid.equals("3")){
                        return;
                    }
                    if (list.get(position).getFlag() == "1"){
                        return;
                    }
                    //状态码
                    String status = list.get(position).getStatus();
                    if (Integer.parseInt(status) == 3){  //  如果是已验收, 就直接跳过去
                        closeDialog();
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
                    if (Integer.parseInt(status) == 1) {
                        status = "2";
                    } else if (Integer.parseInt(status) == 2) {
                        status = "3";
                    }
                    tiJiaoZgstate.setStatus(Integer.parseInt(status));
                    loginModel.TijiaZgstate(MyRectification.this, tiJiaoZgstate, new OnLoadComBackListener() {
                        @Override
                        public void onSuccess(Object msg) {
                            list.get(position).setFlag("1"); //  添加标记,用来标记已经点击过了
                            Toast.makeText(MyRectification.this, "查处成功", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onError(String msg) {
                            Toast.makeText(MyRectification.this, msg.toString(), Toast.LENGTH_SHORT).show();
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

    private class Radapter extends RecyclerView.Adapter {

        private String[] strings;
        private Context context;

        public Radapter(String[] strings, Context context) {
            this.strings = strings;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_r, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            //图片加载
            Glide.with(MyRectification.this)
                    .load(strings[position])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.mrt_zgt)
                    .into(((ViewHolder) holder).imageView);

            ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyRectification.this, "dianji", Toast.LENGTH_SHORT).show();
                    UiTool.showPic(context, strings[position]);
                    // 自动旋转
                    try {
                        UiTool.loadBitmap(strings[position]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return strings.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv_image);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }

    }

}
