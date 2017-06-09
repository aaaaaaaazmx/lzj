package com.example.lcsrq.activity.manger.report;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.gyzmanger.GyzXxwhActivity;
import com.example.lcsrq.activity.manger.gyzmanger.MapActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.crame.CustomDialog;

/**
 * Created by 苏毅 on 2017/5/7.
 *
 * 举报页面定位
 */

public class MyJuabaoMap extends BaseActivity {
    private CustomDialog choicePhotoDialog;
    private MapView mMapView;
    private BaiduMap mBaiDuMap;
    private LocationClient mLocationClient;
    private double mLatitude;
    private double mLongtitude;
    private BitmapDescriptor mIconLocation;
    private MyLocationListener myLocationListener;
    private TextView commonTitleTv;
    private LinearLayout commonLeftBtn;
    private TextView commonRightText;
    private RelativeLayout commonRightBtn;
    private String address;  // 地址

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dingwei_activity);
        initLocaiton();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void addAction() {
        commonLeftBtn.setOnClickListener(this);
        commonRightBtn.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindBDMapLayout(this);


        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiDuMap = mMapView.getMap();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiDuMap.setMapStatus(msu);

        commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("定位");

        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);


        commonRightBtn = (RelativeLayout) findViewById(R.id.commonRightBtn);
        commonRightText = (TextView) findViewById(R.id.commonRightText);
        commonRightText.setText("确定");
        commonRightText.setVisibility(View.VISIBLE);
        // 判断是否是23版本以上
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts(mMapView);
        } else {
            initLocaiton();
        }
    }

    public abstract static class PermissionUtil {

        public static boolean verifyPermissions(int[] grantResults) {
            // At least one result must be checked.
            if (grantResults.length < 1) {
                return false;
            }

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
    }
    private void initLocaiton() {
        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); //坐标类型
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

        // 初始化图片
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {
            if (MapActivity.PermissionUtil.verifyPermissions(grantResults)) {
                initLocaiton();
            } else {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiDuMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            //开启定位
            mLocationClient.start();
//            centerToMyLocation();
            // 开启方向传感器
//            myOrientationListener.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private boolean isFirstIn = true;
    private Context context = MyJuabaoMap.this;
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 定位之后的回调
            MyLocationData data = new MyLocationData.Builder()//
//                    .direction(mCurrentX)//
                    .accuracy(bdLocation.getRadius())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude()).build();
            mBaiDuMap.setMyLocationData(data);
            // 设置图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
            mBaiDuMap.setMyLocationConfiguration(configuration);



            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiDuMap.animateMapStatus(msu);
                isFirstIn = false;

                // 获取当前的位置
                Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
                address = bdLocation.getAddrStr();
                // 获取经纬度
                mLatitude = bdLocation.getLatitude();
                mLongtitude = bdLocation.getLongitude();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void showContacts(MapView mMapView) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            requestContactsPermissions(mMapView);
        } else {
            initLocaiton();
        }
    }

    private String[] PERMISSIONS_CONTACT = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private int REQUEST_CONTACTS = 1;

    private void requestContactsPermissions(MapView mMapView) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)
                ) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(mMapView, "permission_contacts_rationale", Snackbar.LENGTH_SHORT)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MyJuabaoMap.this, PERMISSIONS_CONTACT,
                                    REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.commonLeftBtn){
//            Intent intent = new Intent(MyJuabaoMap.this, GyzXxwhActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("lat","");
//            bundle.putString("long","");
//            intent.putExtra("bundle",bundle);
//            setResult(50,intent);
            finish();
        }else if (v.getId() == R.id.commonRightBtn){
            Intent intent = new Intent(MyJuabaoMap.this, GyzXxwhActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("address",address+"");
            intent.putExtra("bundle",bundle);
            setResult(100,intent);
            finish();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            Intent intent = new Intent(MyJuabaoMap.this, GyzXxwhActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("lat","");
//            bundle.putString("long","");
//            intent.putExtra("bundle",bundle);
//            setResult(50,intent);
//            finish();
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}
