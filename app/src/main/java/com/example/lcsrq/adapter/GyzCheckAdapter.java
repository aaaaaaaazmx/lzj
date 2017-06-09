package com.example.lcsrq.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzCheckActivity;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.bean.req.GyzJcReqData;
import com.example.lcsrq.bean.resq.GyzCheckRespData;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.MyPostGridAdapter;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.model.LoginModel;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.view.BaseGridView;
import com.example.lcsrq.xiangce.ChoicePicActivity;
import com.example.lcsrq.xiangce.ImageGridAdapter;
import com.example.lcsrq.xiangce.UiTool;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/3/31.
 */

public class GyzCheckAdapter extends BaseAdapter implements View.OnClickListener,MyPostGridAdapter.DeletePicImp {

    private Activity activity;


    private CustomDialog choicePhotoDialog;
    private TextView titleTv, commonRightText, locationTv;
    private EditText postedContentEt;
    private BaseGridView gridView;
    private MyPostGridAdapter adapter;
    private LinearLayout locationLayout;

    private String fileName;
    // 显示图片
    private static final int TAKE_PICTURE = 0x000001;
    private static final int IMAGE_CODE = 0x000002;
    private static final int LOCATION_CODE = 0x000003;

    private String location;
    private ArrayList<File> filenames = new ArrayList<File>();
    private ArrayList<File> filenamesTake = new ArrayList<File>();
    private ArrayList<File> filenamesChoice = new ArrayList<File>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsTake = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> bitmapsChoice = new ArrayList<Bitmap>();
    private String imgurl = "";
    private int imageUploadCount = 0;

    private CcrAdapter.OnAddOrdelClick onAddOrdelClick;
    private ArrayList<GyzCheckRespData> datas = new ArrayList<GyzCheckRespData>();

    public ArrayList<GyzCheckRespData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<GyzCheckRespData> datas) {
        this.datas = datas;
    }

    public GyzCheckAdapter(Activity activity,CcrAdapter.OnAddOrdelClick onAddOrdelClick) {
        this.activity = activity;
        this.onAddOrdelClick = onAddOrdelClick;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View inflate;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.list_item_gyz_check, null);
            holder.newText = (TextView) convertView.findViewById(R.id.tv_news);
            holder.CheckBtn = (Button) convertView.findViewById(R.id.btn_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GyzCheckRespData gyzCheckRespData = datas.get(position);
        holder.newText.setText(gyzCheckRespData.getTitle());

        holder.CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrdelClick.onCcClick(position,null);
//              Toast.makeText(activity,datas.get(position).getId() + "",Toast.LENGTH_LONG).show();
                View view = View.inflate(activity, R.layout.gyzr_check_popwidow, null);
                Button btn_bc = (Button) view.findViewById(R.id.btn_bc);
//                final EditText postedContentEt = (EditText) view.findViewById(R.id.postedContentEt);

//                findView(view);  // 初始化
//                addAction();
//
                PopupWindow popupWindow = new PopupWindow(view, -1, -2);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
//
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
//
//                //  点击保存提交整改的数据   供应站整改
//                btn_bc.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String s = postedContentEt.getText().toString();
//                        sendPost(s);
//
//                    }
//                });
//
//                popupWindow.showAtLocation(holder.CheckBtn, Gravity.BOTTOM, 0, 0);
            }
        });
        return convertView;
    }
    private   GyzJcReqData gyzJcReqData = new GyzJcReqData();
    // 提交
    private void sendPost(String s) {

        LoginModel login = new LoginModel();

        gyzJcReqData.setUid(Integer.parseInt(Global.uid));
        gyzJcReqData.setCheck_id("16");
        gyzJcReqData.setSupply_id("16");
        gyzJcReqData.setContent(s);
        gyzJcReqData.setUploads("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg ,http://img02.tooopen.com/images/20140504/sy_60294738471.jpg");

        login.getGyzJc(activity, gyzJcReqData, new OnLoadComBackListener() {
            @Override
            public void onSuccess(Object msg) {
                Toast.makeText(activity,"提交成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String msg) {

        Toast.makeText(activity,msg.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public  void findView(View view){
        ImageGridAdapter.mSelectedImage.clear();
        postedContentEt = (EditText) view.findViewById(R.id.postedContentEt);
        locationTv = (TextView) view.findViewById(R.id.locationTv);

        //弹窗
        choicePhotoDialog = new CustomDialog(activity);
        choicePhotoDialog.bindCameraLayout(this);

        //图片列表
        gridView = (BaseGridView) view.findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MyPostGridAdapter(activity, bitmaps, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        locationLayout = (LinearLayout)view.findViewById(R.id.locationLayout);
    }

    public void addAction(){
        postedContentEt.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bitmaps.size()) {
                    UiTool.hideKeyboard(activity);
                    if (bitmaps.size() >= 9) {
                        Toast.makeText(activity, "最多能上传9张图片", Toast.LENGTH_LONG).show();
                    } else {
                        UiTool.setDialog(activity, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                }
            }
        });
        locationLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_popupwindows_one) {
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
                    activity.startActivityForResult(intent, TAKE_PICTURE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity, "没有找到储存目录", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(activity, "没有储存卡", Toast.LENGTH_LONG).show();
            }
            choicePhotoDialog.dismiss();

            // 从相册选择
        } else if (view.getId() == R.id.item_popupwindows_two) {
//            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent1, IMAGE_CODE);
            Intent intent = new Intent(activity, ChoicePicActivity.class);
            activity.startActivityForResult(intent, IMAGE_CODE);
            choicePhotoDialog.dismiss();
            // 取消
        } else if (view.getId() == R.id.parent || view.getId() == R.id.item_popupwindows_cancel) {
            choicePhotoDialog.dismiss();
        }
    }

    public void deletePic(int position) {
        Bitmap pic = bitmaps.get(position);
        pic.recycle();
        bitmaps.remove(position);
        filenames.remove(position);
        adapter.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView newText;
        Button CheckBtn;
    }

    public interface OnAddOrdelClick {
        public void onCcClick(int position);
        public void onTextClick(int position);
    }
}
