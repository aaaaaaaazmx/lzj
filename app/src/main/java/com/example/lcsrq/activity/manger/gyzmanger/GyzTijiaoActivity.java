package com.example.lcsrq.activity.manger.gyzmanger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MycontactActivity;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.adapter.CcrAdapter;
import com.example.lcsrq.adapter.PostListAdapter;
import com.example.lcsrq.adapter.TiJiaoAdapte;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.GyzCheckBean;
import com.example.lcsrq.bean.req.GyzJcReqDuoData;
import com.example.lcsrq.bean.respbean.GyzTijiaoBean;
import com.example.lcsrq.bean.respbean.TiJiaoBean;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.DensityUtil;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.xiangce.UiTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 苏毅 on 2017/5/4.
 */

public class GyzTijiaoActivity extends BaseActivity {

    private ListView lv_tijiao;
    private LinearLayout commonLeftBtn;
    private ArrayList<GyzCheckBean> list;
    private TextView tv_jcdw;
    private Button btn_submit;
    private String jcdw;
    private String check_names;
    private String check_uids;
    private LoginModel loginModel;
    private TextView tv_ccr;
    private TijiaoAdapter tijiaoAdapter;
    private ListView lv_jcr;
    private TiJiaoAdapte ccrAdapter;
    private int totalHeight = 0;
    private ArrayList<GyzTijiaoBean> lists = new ArrayList<>();
    private TextView tv_problem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_tijiao);
        loginModel = new LoginModel();
        list = (ArrayList<GyzCheckBean>) getIntent().getSerializableExtra("list");

        // 遍历
        for (int i =0 ;i<list.size(); i++){

            GyzTijiaoBean gyzTijiaoBean = new GyzTijiaoBean();
            gyzTijiaoBean.setCheck_id(list.get(i).getCheck_id());
            gyzTijiaoBean.setContent(list.get(i).getContent());
//            gyzTijiaoBean.setUploads(list.get(i).getUploads());
            gyzTijiaoBean.setUploads("0");  // 上传的时候不知道为什么这个东西不能设置为空, 设置为空就会报错
            //  如果是check_id为空,就表示是从验收那添加的,不需要添加到出专程lists

            if (TextUtils.isEmpty(list.get(i).getCheck_id())){
                //  直接不执行添加操作
                 continue;
            }else {
                lists.add(gyzTijiaoBean);
            }
        }

        if (list.size() > 0){
            tv_problem.setVisibility(View.VISIBLE);
            lv_tijiao.setVisibility(View.VISIBLE);

            for (GyzCheckBean bean:list ) {
                String imageUrl = bean.getUploads();
                if (!TextUtils.isEmpty(imageUrl)){
                    String[] urls = imageUrl.split(",");
//                    Toast.makeText(GyzTijiaoActivity.this,urls[0].toString()+"",Toast.LENGTH_SHORT).show();
                    urlList.add(urls);
                }
            }
            tijiaoAdapter = new TijiaoAdapter();
            tijiaoAdapter.setList(list);
            lv_tijiao.setAdapter(tijiaoAdapter);
            tijiaoAdapter.notifyDataSetChanged();

            int totalHeight = 0;
            for (int i = 0; i < tijiaoAdapter.getCount(); i++) {
                View listItem = tijiaoAdapter.getView(i, null, lv_tijiao);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lv_tijiao.getLayoutParams();
            layoutParams.height = totalHeight+ (lv_tijiao.getDividerHeight() * (tijiaoAdapter.getCount()) -1);
            lv_tijiao.setLayoutParams(layoutParams);

        }

    }

    private ArrayList<TiJiaoBean> arrayList = new ArrayList<>();  // 查处人
    private int weizhi = -1;
    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }
    private List<String []> urlList = new ArrayList<>();

    @Override
    protected void findViews() {
        // 存在问题这一行
        tv_problem = (TextView) findViewById(R.id.tv_problem);

        lv_tijiao = (ListView) findViewById(R.id.lv_tijiao);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("提交检查");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        //查处人
//        tv_ccr = (TextView) findViewById(R.id.tv_ccr);
        lv_jcr = (ListView) findViewById(R.id.lv_jcr);

        //检查单位
        tv_jcdw = (TextView) findViewById(R.id.tv_ccdw);
        tv_jcdw.setText(Global.My_dw + "");

        // 提交
        btn_submit = (Button) findViewById(R.id.btn_submit);

        // new出一个adaper
        ccrAdapter = new TiJiaoAdapte(GyzTijiaoActivity.this, new TiJiaoAdapte.OnAddOrdelClick() {
            @Override
            public void onCcClick(int position,ImageView imageView) {
                if (position == 0) {
                    arrayList.add(new TiJiaoBean("",""));
                    ccrAdapter.setArrayList(arrayList);
                    ccrAdapter.notifyDataSetChanged();
                } else {
                    arrayList.remove(position);
                    ccrAdapter.setArrayList(arrayList);
                    ccrAdapter.notifyDataSetChanged();
                }
                LinearLayout.LayoutParams para1;
                para1 = (LinearLayout.LayoutParams) lv_jcr.getLayoutParams();
                para1.height = DensityUtil.dip2px(GyzTijiaoActivity.this, 50.0f * arrayList.size());
                lv_jcr.setLayoutParams(para1);
            }

            @Override
            public void onTextClick(int position) {
                weizhi = position;
                Intent intent = new Intent(GyzTijiaoActivity.this, MycontactActivity.class);
                intent.putExtra("name",1);
                startActivityForResult(intent,200);
            }
        });
        lv_jcr.setAdapter(ccrAdapter);
        arrayList.add(new TiJiaoBean(Global.usernName,Global.uid));
        ccrAdapter.setArrayList(arrayList);
        ccrAdapter.notifyDataSetChanged();
    }
    //  用户的ID
    private  String UUid = "";

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn){
            finish();
        }else if (v.getId() == R.id.btn_submit){

            showLoading("正在加载");
            jcdw = tv_jcdw.getText().toString(); //  检查单位
            jcdw = Global.My_dw + "";
            // 表示没有添加检查人
            if(arrayList.size() == 1){
                UUid = Global.uid;  // UID
            }else {

//            如果不止一条 先清空
                UUid = "";
                UUid = Global.uid;
                for (int i= 1; i<arrayList.size();i++){
                    UUid += ","+arrayList.get(i).getUid();
                }
            }

            // 检查人的名字ID tj_check_uids
            GyzJcReqDuoData duoData = new GyzJcReqDuoData();
            duoData.setCheck_dw(jcdw);  //  检查单位
//            Toast.makeText(GyzTijiaoActivity.this,jcdw  + "" ,Toast.LENGTH_SHORT).show();
            duoData.setCheck_uids(UUid);  // 检查人的ID
//            Toast.makeText(GyzTijiaoActivity.this,UUid  + "" ,Toast.LENGTH_SHORT).show();
            duoData.setUid(Integer.parseInt(Global.uid));  // 检查人IDs
//            Toast.makeText(GyzTijiaoActivity.this,Global.uid+ "" ,Toast.LENGTH_SHORT).show();


            if (lists.size() != 0){
                duoData.setDatas(JSON.toJSON(lists).toString());  // datas
                Toast.makeText(GyzTijiaoActivity.this,lists.size() + "" ,Toast.LENGTH_SHORT).show();

            }

//            Toast.makeText(GyzTijiaoActivity.this,duoData.getDatas()+ "" ,Toast.LENGTH_SHORT).show();
//            System.out.println("woainiwoaini :" +JSON.toJSON(lists).toString()+ "" );
            duoData.setSupply_id(Global.supply_id);
//            Toast.makeText(GyzTijiaoActivity.this,Global.supply_id+ "" ,Toast.LENGTH_SHORT).show();

            // 如果数据是有的,则不传3 (3表示data字段无效) (目前不能这样判断了)
            if (TextUtils.isEmpty(duoData.getDatas())){
                duoData.setStatus("3");
            }

//            Toast.makeText(GyzTijiaoActivity.this,JSON.toJSON(lists).toString()+ "" ,Toast.LENGTH_SHORT).show();
//            Toast.makeText(GyzTijiaoActivity.this,duoData.getDatas().toString() + "" ,Toast.LENGTH_SHORT).show();

            loginModel.getGyzJcDUO(GyzTijiaoActivity.this, duoData, new OnLoadComBackListener() {
                //  提交成功
                @Override
                public void onSuccess(Object msg) {
                    closeDialog();
//                    startActivity(new Intent(GyzTijiaoActivity.this,GyzDetailActivity.class)); //跳转到详情页面
                    finish();
                }
                //  提交失败
                @Override
                public void onError(String msg) {
                    closeDialog();
                    Toast.makeText(GyzTijiaoActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String UID = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 200:
                Bundle bundleExtra = data.getBundleExtra("bundle");
                check_names = bundleExtra.getString("name");
                UID = bundleExtra.getString("UID");
                // 名字
                arrayList.set(weizhi, new TiJiaoBean(check_names,UID));
                ccrAdapter.setArrayList(arrayList);
                ccrAdapter.notifyDataSetChanged();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class TijiaoAdapter extends BaseAdapter{

        private ArrayList<GyzCheckBean> list = new ArrayList<>();

        public ArrayList<GyzCheckBean> getList() {
            return list;
        }

        public void setList(ArrayList<GyzCheckBean> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(GyzTijiaoActivity.this,R.layout.adapter_list,null);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.easyRecyclerView = (RecyclerView) convertView.findViewById(R.id.easyRV);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

                //横向移动
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(GyzTijiaoActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder.easyRecyclerView.setLayoutManager(linearLayoutManager);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 如果是空的,则不显示
//            if (TextUtils.isEmpty(list.get(position).getContent())){
//                holder.tv_content.setVisibility(View.GONE);
//            }else {
//                holder.tv_content.setText(list.get(position).getContent());
//            }

            holder.tv_content.setVisibility(View.GONE);
            // title
            if (TextUtils.isEmpty(list.get(position).getContent())){
                holder.tv_title.setVisibility(View.GONE);
            }else {
                holder.tv_title.setText(list.get(position).getContent() + "");
            }

            // 传的是图片
            if (urlList.size() != 0){
                Radapter radapter=new Radapter(urlList.get(position),GyzTijiaoActivity.this);
                holder.easyRecyclerView.setAdapter(radapter);
            }else {
                holder.easyRecyclerView.setVisibility(View.GONE);
            }

            return convertView;
        }
    }
    class ViewHolder{
        TextView tv_content;
        TextView tv_title;
        RecyclerView easyRecyclerView;
    }

    private class Radapter extends RecyclerView.Adapter{

        private String[] strings;
        private Context context;

        public Radapter(String[] strings, Context context) {
            this.strings = strings;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_r,null);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            //图片加载
            Log.d("====路径：",strings[position]);
//            Glide.with(GyzTijiaoActivity.this)
//                    .load(strings[position])
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.mipmap.mrt_zgt)
//                    .into(((ViewHolder)holder).imageView);

            ((ViewHolder)holder).imageView.setImageURI(Uri.parse(strings[position]));
            ((ViewHolder)holder).imageView.setScaleType(SimpleDraweeView.ScaleType.CENTER_CROP);
            ((ViewHolder)holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiTool.showPic(GyzTijiaoActivity.this, strings[position]);
                }
            });

        }

        @Override
        public int getItemCount() {
            return strings.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private SimpleDraweeView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView= (SimpleDraweeView) itemView.findViewById(R.id.iv_image);
            }
        }

    }
}
