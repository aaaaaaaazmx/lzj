package com.example.lcsrq.activity.manger.gyzmanger;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.My.MyRectification;
import com.example.lcsrq.activity.manger.hdhc.HdhcCheckActivity;
import com.example.lcsrq.activity.manger.xxcx.CarInfo;
import com.example.lcsrq.activity.manger.xxcx.InfomationCarActivity;
import com.example.lcsrq.adapter.CcrAdapter;
import com.example.lcsrq.adapter.GyzCheckAdapter;
import com.example.lcsrq.adapter.PostListAdapter;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.bean.GyzCheckBean;
import com.example.lcsrq.bean.req.GyzCheckReqData;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.GyzCheckDuoRespData;
import com.example.lcsrq.bean.resq.GyzCheckRespData;
import com.example.lcsrq.bean.resq.TijiaoPicRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
import com.example.lcsrq.http.ModelHttp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.http.ProcessListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.utils.StringTool;
import com.example.lcsrq.view.BaseGridView;
import com.example.lcsrq.view.ReFreshListView;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;
import com.viewpagerindicator.TabPageIndicator;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 供应站检查页面
 */

public class GyzCheckActivity extends BaseActivity implements MyPostGridAdapter.DeletePicImp {
    private ListView list_check;
    private LinearLayout commonLeftBtn;
    private ArrayList<GyzCheckRespData> datas = new ArrayList<>();
    private ViewPager vv_pager;
    private ArrayList<GyzCheckDuoRespData> duoData;
    private TabPageIndicator indicator;  // 第三方
    private ImageView btn_next;
    private GyzPagerAdapter myPagerAdapter;
    private ListviewAdapter listadapter;


    // 相机相册
    private RelativeLayout commonRightBtn;
    private TextView titleTv, commonRightText, locationTv, tv_dizhi;
    private LoginModel loginModel;

    private EditText postedContentEt;
    private LinearLayout locationLayout;
    // 显示图片
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;
    private BaseGridView gridView;
    private MyPostGridAdapter adapter;

    private String fileName;
    //弹窗
    private CustomDialog choicePhotoDialog;

    //    private FirstModel firstModel = new FirstModel();
    private String location;
    private ArrayList<File> filenames = new ArrayList<File>();
    private ArrayList<File> filenamesTake = new ArrayList<File>();
    private ArrayList<File> filenamesChoice = new ArrayList<File>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsTake = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsChoice = new ArrayList<Bitmap>();
    private String imgurl = "";
    private int imageUploadCount = 0;
    private String content;
    private PopupWindow popupWindow;
    // 保持在本地
    private List<GyzCheckBean> listmap = new ArrayList<>();
    private TextView tv_exit;
    private TextView tv_sure;
    private String contentdetail;
    private String check_id;
    private WindowManager.LayoutParams params;
    private String title;
    private ArrayList<String> check_ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyz_check);
        showLoading("正在加载");

        // 带过来的问题项目ID
        check_ids = getIntent().getStringArrayListExtra("check_id");
//        Toast.makeText(GyzCheckActivity.this, check_ids.toString()+ "",Toast.LENGTH_SHORT).show();

        loginModel = new LoginModel();
        initData();
    }
    private int mCurrentState;  // 滑动状态
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
//                GyzCheckAdapter gyzCheckAdapter = new GyzCheckAdapter(GyzCheckActivity.this,new CcrAdapter.OnAddOrdelClick() {
//                    @Override
//                    public void onCcClick(int position) {
//
//                        Toast.makeText(GyzCheckActivity.this,"dianji",Toast.LENGTH_SHORT).show();
//                        View view = View.inflate(GyzCheckActivity.this, R.layout.gyzr_check_popwidow, null);
//                        Button btn_bc = (Button) view.findViewById(R.id.btn_bc);
//                        PopupWindow popupWindow = new PopupWindow(view, -1, -2);
//                        popupWindow.setTouchable(true);
//                        popupWindow.setFocusable(true);
////
//                        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                return false;
//                            }
//                        });
//                        popupWindow.showAtLocation(list_check, Gravity.BOTTOM, 0, 0);
//                    }
//
//                    @Override
//                    public void onTextClick(int position) {
//
//                    }
//                });
//                gyzCheckAdapter.setDatas(datas);
//                list_check.setAdapter(gyzCheckAdapter);

                // 从验收项目跳转过来的 要标红
                for (int i = 0; i < duoData.size() ;i++){
                    ArrayList<GyzCheckRespData> oplist = duoData.get(i).getOplist(); // 假如这个东西有9个
                    for (int a = 0; a < oplist.size();a++){    // oplist.size 有5个
                        for (int j = 0; j < check_ids.size(); j++) {

                            if (oplist.get(a).getId().equals(check_ids.get(j).toString())) {
                                oplist.get(a).setFlag(1);
                                oplist.get(a).setState(1);  //  表示传下来的问题不能被点击了
                                GyzCheckBean gyzCheckBean = new GyzCheckBean();
                                gyzCheckBean.setCheck_id("");
                                gyzCheckBean.setUploads("");
                                // 唯一要传的就是这个title
                                gyzCheckBean.setContent(oplist.get(a).getTitle());
                                listmap.add(gyzCheckBean);
                            }
                        }
                    }
                };


                indicator.setVisibility(View.VISIBLE);
                myPagerAdapter = new GyzPagerAdapter();
                myPagerAdapter.setList(duoData);
                vv_pager.setAdapter(myPagerAdapter);
                myPagerAdapter.notifyDataSetChanged();
//                // 初始化indicator
                indicator.setViewPager(vv_pager);
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        if (position == duoData.size() - 1 && mCurrentState == 1) {
                            mCurrentState = -1;
                            final LayoutInflater inflaterDl = LayoutInflater.from(GyzCheckActivity.this);
                            LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.hdhc_sure, null);
                            builder = new AlertDialog.Builder(GyzCheckActivity.this).create();
                            builder.show();
                            builder.getWindow().setContentView(layout);

                            WindowManager windowManager = GyzCheckActivity.this.getWindowManager();
                            Display display = windowManager.getDefaultDisplay();
                            WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
                            lp.width = (int) (display.getWidth()) * 4/5; //设置宽度
                            lp.height = (int) ((int)(display.getHeight())/3.5);
                            builder.getWindow().setAttributes(lp);

                            tv_sure = (TextView) layout.findViewById(R.id.tv_sure);
                            tv_exit = (TextView) layout.findViewById(R.id.tv_exit);

                            tv_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    builder.dismiss();
                                }
                            });

                            tv_sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(GyzCheckActivity.this,GyzTijiaoActivity.class);
                                    intent.putExtra("list", (Serializable) listmap);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                    @Override
                    public void onPageSelected(int position) {
                        mCurrentPosition = position;
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {
                        mCurrentState = state;
                    }
                });

            } else if (msg.arg1 == 20) {

//                Toast.makeText(GyzCheckActivity.this, urlStrs + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(GyzCheckActivity.this, contentdetail + "", Toast.LENGTH_SHORT).show();
                GyzCheckBean gyzCheckBean = new GyzCheckBean();
                contentdetail = "";

                // 如果都没有
                if (TextUtils.isEmpty(urlStrs) && TextUtils.isEmpty(contentdetail)) {
                    gyzCheckBean.setContent(title + "");
                }

                gyzCheckBean.setContent(contentdetail);
                gyzCheckBean.setUploads(urlStrs);

                //如果没有图片
                if (TextUtils.isEmpty(urlStrs)) {
                    gyzCheckBean.setUploads("");
                }

                // 如果没有content
                if (TextUtils.isEmpty(contentdetail)) {
                    gyzCheckBean.setContent("");
                }

                gyzCheckBean.setContent(title + "");
                gyzCheckBean.setCheck_id(check_id);
                listmap.add(gyzCheckBean);

                popupWindow.dismiss();
//                Toast.makeText(GyzCheckActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                initData();//  刷新数据
//                myPagerAdapter.notifyDataSetChanged();
                urlStrs = "";
                urlList.clear();
                // 刷新Adapter
                myPagerAdapter.notifyDataSetChanged();
                listadapter.notifyDataSetChanged();
            }
        }
    };
    private int mCurrentPosition;
    private void initData() {
        GyzCheckReqData gyzCheckReqData = new GyzCheckReqData();
        loginModel.getListOfGyzCheckOFDUO(GyzCheckActivity.this, gyzCheckReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                duoData = (ArrayList<GyzCheckDuoRespData>) msg;
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                closeDialog();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(GyzCheckActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
                closeDialog();
            }
        });
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }

    @Override
    protected void findViews() {
        adapter = new MyPostGridAdapter(GyzCheckActivity.this, bitmaps, GyzCheckActivity.this);
        // 找到viewpager控件
        vv_pager = (ViewPager) findViewById(R.id.vv_pager);
        indicator = (TabPageIndicator) findViewById(R.id.indicator);

        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("开始检查");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);

        btn_next = (ImageView) findViewById(R.id.btn_next);

        // 相机相册
        ImageGridAdapter.mSelectedImage.clear();
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText.setVisibility(View.GONE);
        commonRightText.setText("确定");
        commonRightText.setBackgroundResource(R.drawable.corner_toumingbg_greenborder_2dp);

//        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindCameraLayout(this);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == this.RESULT_OK) {
                    String path = null;
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/temp" + "/" + fileName);
                    try {
                        Uri uri1 =
                                Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                        f.getAbsolutePath(), null, null));
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //获取包含所需数据的Cursor对象
                        Cursor cursor = this.managedQuery(uri1, proj, null, null, null);
                        if (cursor != null) {
                            //获取索引
                            int photocolumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            //将光标一直开头
                            cursor.moveToFirst();
                            //根据索引值获取图片路径
                            path = cursor.getString(photocolumn);
                        } else {
                            // 如果游标为空说明获取的已经是绝对路径了
                            path = uri1.getPath();
                        }
                        try {
                            Bitmap bm = UiTool.revitionImageSize(path);
                            bitmapsTake.add(bm);
                            bitmaps.clear();
                            bitmaps.addAll(bitmapsTake);
                            bitmaps.addAll(bitmapsChoice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        filenamesTake.add(new File(path));
                        filenames.clear();
                        filenames.addAll(filenamesTake);
                        filenames.addAll(filenamesChoice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case IMAGE_CODE:
                if (resultCode == this.RESULT_OK && data != null) {
                    List<String> images = (List<String>) data.getSerializableExtra("imageUrls");
//                    bitmapsChoice.clear();
                    filenamesChoice.clear();
                    for (String path : images) {
                        try {
                            Bitmap bm = UiTool.loadBitmap(path);
                            bitmapsChoice.add(bm);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        filenamesChoice.add(new File(path));
                    }
                    bitmaps.clear();
                    bitmaps.addAll(bitmapsTake);
                    bitmaps.addAll(bitmapsChoice);
//                    filenames.clear();
                    filenames.addAll(filenamesTake);
                    filenames.addAll(filenamesChoice);
                }
//                if (resultCode == this.RESULT_OK && data != null) {
//                    Uri uri1 = data.getData();
//
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    //获取包含所需数据的Cursor对象
//                    Cursor cursor = this.managedQuery(data.getData(), proj, null, null, null);
//                    if (cursor != null) {
//                        //获取索引
//                        int photocolumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        //将光标一直开头
//                        cursor.moveToFirst();
//                        //根据索引值获取图片路径
//                        path = cursor.getString(photocolumn);
//                    } else {
//                        // 如果游标为空说明获取的已经是绝对路径了
//                        path = uri1.getPath();
//                    }
//                    try {
//                        Bitmap bm = UiTool.loadBitmap(path);
//                        bitmaps.add(bm);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    tempFile = new File(path);
//                    filenames.add(tempFile);
//                }
                break;
            case LOCATION_CODE:
                if (resultCode == this.RESULT_OK && data != null) {
                    location = data.getStringExtra("location");
                    if (StringTool.isNotNull(location)) {
                        locationTv.setText(location);
                    } else {
                        locationTv.setText("所在的位置");
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    public void finish() {
        super.finish();
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap pic = bitmaps.get(i);
            if (pic != null && !pic.isRecycled()) {
                pic.recycle();
            }
        }
        bitmapsChoice.clear();
        bitmapsChoice.clear();
        bitmaps.clear();
    }
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE1 = 2;
    @Override
    public void deletePic(int position) {
        Bitmap pic = bitmaps.get(position);
        pic.recycle();
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();
    }

    // 调用相册
    private void XiangJi() {
        //先验证手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                fileName = String.valueOf(System.currentTimeMillis() + ".JPEG");
                File dir = new File(Environment.getExternalStorageDirectory() + "/temp");
                if (!dir.exists()) dir.mkdirs();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(dir, fileName);//localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent, TAKE_PICTURE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "没有找到储存目录", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "没有储存卡", Toast.LENGTH_LONG).show();
        }
        choicePhotoDialog.dismiss();
    }

    private void XiangCe() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_XIANGJI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                XiangJi();
            } else {
                Toast.makeText(GyzCheckActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private int MY_PERMISSIONS_REQUEST_XIANGJI = 1;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commonLeftBtn) {
            // 跳转详情页面
//           startActivity(new Intent(GyzCheckActivity.this,GyzDetailActivity.class));
            finish(); // 直接结束
        } else if ((v.getId() == R.id.btn_next)) {
            nextPager();
        } else   // p拍照
            if (v.getId() == R.id.item_popupwindows_one) {
                // 手机被点击
                if (ContextCompat.checkSelfPermission(GyzCheckActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GyzCheckActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_XIANGJI);
                } else {
                    XiangJi();
                }
                // 从相册选择
            } else if (v.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent1, IMAGE_CODE);
                // 手机被点击
                Intent intent = new Intent(this, ChoicePicActivity.class);
                startActivityForResult(intent, IMAGE_CODE);
                choicePhotoDialog.dismiss();
                // 取消
            } else if (v.getId() == R.id.parent || v.getId() == R.id.item_popupwindows_cancel) {
                choicePhotoDialog.dismiss();
            }
    }

    // 图片地址
    private ArrayList<String> urlList = new ArrayList<String>();
    // 点击是 就删除这条数据
    private ArrayList<GyzCheckBean> stateUrl = new ArrayList<GyzCheckBean>();
    // 图片的url
    private String urlStrs = "";
    private void sendPic() {
        // 如果添加了新的照片,就清除之前的集合  表示添加了图片或者删除了图片
//        if (filenames.size() != urlList.size()){
//            urlList.clear();
//            urlStrs="";
//            filenames.clear();
//        }

        // 没有图片
        if (filenames.size() == 0) {
            // 上传成功发送hanlder
            Message message = new Message();
            message.arg1 = 20;
            handler.sendMessage(message);
            return;
        }

        for (int i = 0; i < filenames.size(); i++) {
            ModelHttp.postPicHttpClient(GyzCheckActivity.this, "http://qzmoo.cn/rqapi/apiwx/formuploadimg", filenames.get(i), new ProcessListener() {
                @Override
                public boolean onDone(String key, String str) {
                    BaseRespData tijiaoPicRespData = JSON.parseObject(str, BaseRespData.class);
                    TijiaoPicRespData tijiaoPicRespData1 = JSON.parseObject(tijiaoPicRespData.getData(), TijiaoPicRespData.class);

                    // 添加集合
                    urlList.add(tijiaoPicRespData1.getFull_url());
                    if (urlList.size() == filenames.size()) {
                        for (String urlStr : urlList) {
                            urlStrs += urlStr + ",";
                        }
                        // 截取最后的逗号
                        urlStrs = urlStrs.substring(0, urlStrs.length() - 1);

                        // 上传成功发送hanlder
                        Message message = new Message();
                        message.arg1 = 20;
                        handler.sendMessage(message);
                    }


                    return false;
                }
            });
        }
    }


    private void nextPager() {
        //当前的位置
        int currentItem = vv_pager.getCurrentItem();
        vv_pager.setCurrentItem(++currentItem);

    }



    public class GyzPagerAdapter extends PagerAdapter {

        ArrayList<GyzCheckDuoRespData> list = new ArrayList<>();

        public ArrayList<GyzCheckDuoRespData> getList() {
            return list;
        }

        public void setList(ArrayList<GyzCheckDuoRespData> list) {
            this.list = list;
        }

        /**
         * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName().toString();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View inflate = View.inflate(GyzCheckActivity.this, R.layout.gyzcheck_list_view, null);
            list_check = (ListView) inflate.findViewById(R.id.list_check);
            listadapter = new ListviewAdapter();
            final ArrayList<GyzCheckRespData> oplist = duoData.get(position).getOplist();



            listadapter.setList(oplist);
            list_check.setAdapter(listadapter);
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    private AlertDialog builder;
    private Map<String,String> vv_pager_position = new HashMap<>();
    private Map<String,String> ll_view_position = new HashMap<>();
    private List<Button> button = new ArrayList<>();
    class ListviewAdapter extends BaseAdapter {
        private int pager_position= -1;
        private ArrayList<GyzCheckRespData> lists = new ArrayList<>();

        public ArrayList<GyzCheckRespData> getList() {
            return lists;
        }

        public void setList(ArrayList<GyzCheckRespData> list) {
            this.lists = list;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(GyzCheckActivity.this, R.layout.list_item_gyz_check, null);
                holder.newText = (TextView) convertView.findViewById(R.id.tv_news);
                holder.CheckBtn = (Button) convertView.findViewById(R.id.btn_check);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.newText.setText(lists.get(position).getTitle());
            holder.CheckBtn.setText("是");
            holder.CheckBtn.setTextColor(Color.parseColor("#3E697E"));
            holder.CheckBtn.setBackgroundResource(R.drawable.button_style);

//

            if (lists.get(0).getFlag()!=1 && position == 0){
                holder.CheckBtn.setText("是");
                holder.CheckBtn.setTextColor(Color.parseColor("#3E697E"));
                holder.CheckBtn.setBackgroundResource(R.drawable.button_style);
            }

            if (lists.get(position).getFlag() == 1){
                holder.CheckBtn.setText("是");
                holder.CheckBtn.setTextColor(Color.WHITE);
                holder.CheckBtn.setBackgroundResource(R.drawable.jiancha_button);
            }

            holder.CheckBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  如果是传下来的带验收项目,则提示已存在问题
                    if (lists.get(position).getState() == 1){
                          Toast.makeText(GyzCheckActivity.this,"已存在的问题,不能点击",Toast.LENGTH_SHORT).show();
                          return;
                    }
                    //  如果点击了已经查改的,就直接改变样式
                    if (lists.get(position).getFlag() == 1){
                        holder.CheckBtn.setText("是");
                        holder.CheckBtn.setTextColor(Color.parseColor("#3E697E"));
                        holder.CheckBtn.setBackgroundResource(R.drawable.button_style);
                        lists.get(position).setFlag(0);

                        for (int i = 0; i<listmap.size(); i++){
                            if (lists.get(position).getId() == listmap.get(i).getCheck_id()){
                                listmap.remove(i);
                            }
                        }
                        return;
                    }



                    //  点击弹出对话框
                    View view = View.inflate(GyzCheckActivity.this, R.layout.gyzr_check_popwidow, null);
                    Button btn_bc = (Button) view.findViewById(R.id.btn_bc);
                    popupWindow = new PopupWindow(view, -1, -2);
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(true);
//
//                    params = GyzCheckActivity.this.getWindow().getAttributes();
//                    params.alpha = 0.6f;
//                    getWindow().setAttributes(params);

                    popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            params = GyzCheckActivity.this.getWindow().getAttributes();
                            params.alpha = 1f;
                            getWindow().setAttributes(params);
                            return false;
                        }
                    });

                    popupWindow.showAtLocation(btn_next, Gravity.BOTTOM, 0, 0);
                    //图片列表
                    gridView = (BaseGridView) view.findViewById(R.id.gridview);
                    gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    locationLayout = (LinearLayout) view.findViewById(R.id.locationLayout);
                    postedContentEt = (EditText) view.findViewById(R.id.postedContentEt);
                    locationTv = (TextView) view.findViewById(R.id.locationTv);

                    postedContentEt.setOnClickListener(GyzCheckActivity.this);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                long arg3) {
                            if (arg2 == bitmaps.size()) {
                                UiTool.hideKeyboard(GyzCheckActivity.this);
                                if (bitmaps.size() >= 9) {
                                    Toast.makeText(GyzCheckActivity.this, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                                } else {
                                    UiTool.setDialog(GyzCheckActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                                }
                            }
                        }
                    });
                    locationLayout.setOnClickListener(this);

                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            for (int i = 0; i < bitmaps.size(); i++) {
                                Bitmap pic = bitmaps.get(i);
                                if (pic != null && !pic.isRecycled()) {
                                    pic.recycle();
                                }
                            }
                            filenames.clear();
                            bitmapsChoice.clear();
                            bitmapsChoice.clear();
                            bitmaps.clear();
                        }
                    });
                    // 点击提交按钮
                    btn_bc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(GyzCheckActivity.this,"dianji",Toast.LENGTH_SHORT).show();
                            // 获得提交图片
                            contentdetail = postedContentEt.getText().toString();
                            title = lists.get(position).getTitle(); //  检查项目的titile

                            //  保存ID
                            lists.get(position).setFlag(1);
                            state++;
                            lists.get(position).setState(state);
                            check_id = lists.get(position).getId();
                            sendPic();
                        }
                    });

                    popupWindow.dismiss();

//                    Toast.makeText(GyzCheckActivity.this,"dianji",Toast.LENGTH_SHORT).show();
                    // 获得提交图片
                    contentdetail = postedContentEt.getText().toString();
                    title = lists.get(position).getTitle(); //  检查项目的titile

                    //  保存ID
                    lists.get(position).setFlag(1);
                    state++;
                    lists.get(position).setState(state);
                    check_id = lists.get(position).getId();
                    sendPic();
                }
            });
            return convertView;
        }
    }
    private  int state = -1;
    class ViewHolder {
        TextView newText;
        Button CheckBtn;
    }
}
