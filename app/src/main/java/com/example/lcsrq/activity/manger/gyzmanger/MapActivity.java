package com.example.lcsrq.activity.manger.gyzmanger;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.example.lcsrq.R;
import com.example.lcsrq.activity.manger.hdhc.ReportActivity;
import com.example.lcsrq.base.BaseActivity;
import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.value.Global;
import com.example.lcsrq.xiangce.UiTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 苏毅 on 2017/4/24.
 * /供应站mapactivity
 */

public class MapActivity extends Activity implements View.OnClickListener,CloudListener {
    // 所有数据的集合
    private List<CloudPoiInfo> infos = new ArrayList<>();
    private MapView mMapView;
    private BaiduMap mBaiDuMap;

    //判断是否第一次进来
    private boolean isFirstIn = true;

    // 定位相关
    private LocationClient mLocationClient;
    private double mLatitude;
    private double mLongtitude;
    private MyLocationListener myLocationListener;

    //自定义定位图标
    private BitmapDescriptor mIconLocation;
    // 方向传感器
    private MyOrientationListener myOrientationListener;

    // 覆盖物
    private BitmapDescriptor mMarker;

    private RelativeLayout mMarkerly;
    private Button btn_zhoubian;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private double tzlongitude;
    private double tzlatitude;
    private TextView companyTitle;
    private TextView viewById;
    private LinearLayout commonLeftBtn;

    public MapActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        CloudManager.getInstance().init(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_activity);
        findViews();
        initLocaiton();

        final UiSettings settings=mBaiDuMap.getUiSettings();
        settings.setAllGesturesEnabled(false);   //关闭一切手势操作
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setZoomGesturesEnabled(false);
        settings.setScrollGesturesEnabled(false);

        // 延迟5秒执行
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                zhoubian();// 选择周边
                settings.setAllGesturesEnabled(true);
                settings.setOverlookingGesturesEnabled(true);
                settings.setZoomGesturesEnabled(true);
                settings.setScrollGesturesEnabled(true);
            }
        },2000);


        mBaiDuMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
//                    Toast.makeText(MapActivity.this,marker.getZIndex() + "",Toast.LENGTH_SHORT).show();// 获取没个坐标的位置
                // 公司名字
                companyTitle = (TextView) mMarkerly.findViewById(R.id.id_info_title);
                TextView address = (TextView) mMarkerly.findViewById(R.id.id_info_address); // 公司地址
                final TextView phone = (TextView) mMarkerly.findViewById(R.id.id_info_phone); // 公司电话
                LinearLayout ll_goAddress = (LinearLayout) mMarkerly.findViewById(R.id.ll_goAddress);  // 到这里去

                companyTitle.setText(infos.get(marker.getZIndex()).title);  // 设置标题
                address.setText(infos.get(marker.getZIndex()).address);  // 设置地址

                String phone1 = (String) infos.get(marker.getZIndex()).extras.get("phone");  // 设置电话

                if(phone1 == null || phone1 == ""){
                    phone1 = "无";
                }
                phone.setText(phone1 + "");  //设置电话

                InfoWindow infoWindow;

                final TextView textView = new TextView(context);
                textView.setBackgroundResource(R.mipmap.location_tips);
                textView.setPadding(30, 20, 30, 50);
                textView.setText(infos.get(marker.getZIndex()).title);
                textView.setTextColor(Color.parseColor("#ffffff"));


                final LatLng latLng = marker.getPosition();
                android.graphics.Point p = mBaiDuMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                final LatLng ll = mBaiDuMap.getProjection().fromScreenLocation(p);
                infoWindow = new InfoWindow(textView, ll, -10);
                mBaiDuMap.showInfoWindow(infoWindow);
                mMarkerly.setVisibility(View.VISIBLE);

                // 到这去跳转百度地图
                ll_goAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tzlongitude = infos.get(marker.getZIndex()).longitude;
                        tzlatitude = infos.get(marker.getZIndex()).latitude;
                        // 弹窗
                        UiTool.setDialog(MapActivity.this, choicePhotoDialog, Gravity.CENTER, -1, 1, -1);
                    }
                });

                // 冒泡点击,跳转详情
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapActivity.this,GyzDetailActivity.class);
                        intent.putExtra("phone",infos.get(marker.getZIndex()).extras.get("phone") + ""); // 传递电话
                        intent.putExtra("data_id",infos.get(marker.getZIndex()).extras.get("data_id") + "");//  传递ID
                        startActivity(intent);
                    }
                });
                // 拨打电话
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phonenum = infos.get(marker.getZIndex()).extras.get("phone").toString();
                        // 弹出确认拨打框
                        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(MapActivity.this, R.style.Theme_Transparent)).create();
                        dialog.setView(LayoutInflater.from(MapActivity.this).inflate(R.layout.sure_pop, null), 0, 0, 0, 0);
                        UiTool.setDialog(MapActivity.this, dialog, Gravity.CENTER, -1, 0.8, -1); //弹出Dialog

//                        TextView sure = (TextView) dialog.findViewById(R.id.sure);
//                        TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
//                        sure.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // 手机被点击
//                                if (ContextCompat.checkSelfPermission(MapActivity.this,
//                                        Manifest.permission.CALL_PHONE)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(MapActivity.this,
//                                            new String[]{Manifest.permission.CALL_PHONE},
//                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
//                                } else {
//                                    callPhone(phonenum);
//                                    dialog.dismiss();
//                                }
//                            }
//                        });
//                        cancle.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });

                    }
                });

                return true;
            }
        });




        //点击其他地方的时候 就消失
        mBaiDuMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerly.setVisibility(View.GONE);
                mBaiDuMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }



    private void callPhone(String phonenum){

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "" + phonenum);
        intent.setData(data);
        //  检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }
        startActivity(intent);

    }
    private String phonenum;
    //弹窗
    private CustomDialog choicePhotoDialog;
    private void findViews() {
        //弹窗
        choicePhotoDialog = new CustomDialog(this);
        choicePhotoDialog.bindBDMapLayout(this);

        mMarkerly = (RelativeLayout) findViewById(R.id.id_marker_ly);

        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiDuMap = mMapView.getMap();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiDuMap.setMapStatus(msu);

        btn_zhoubian = (Button) findViewById(R.id.btn_zhoubian);
        btn_zhoubian.setOnClickListener(this);

        // 标题
        viewById = (TextView) findViewById(R.id.commonTitleTv);
        viewById.setText("供应站分布图");
        // 返回按钮
        commonLeftBtn = (LinearLayout) findViewById(R.id.commonLeftBtn);
        commonLeftBtn.setVisibility(View.VISIBLE);
        commonLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 判断是否是23版本以上
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts(mMapView);
        } else {
            initLocaiton();
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
                            ActivityCompat.requestPermissions(MapActivity.this, PERMISSIONS_CONTACT,
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
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
        CloudManager.getInstance().destroy(); // 云检索销毁
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
            myOrientationListener.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        zhoubian();
    }


    public void initLocaiton() {
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
        // 初始化方向传感器
        myOrientationListener = new MyOrientationListener(context);

        // 方向传感器
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });

    }

    private Context context = this;
    private float mCurrentX;  // 当前的位置

    @Override
    public void onClick(View v) {

        //点击周边, 搜索周边
        if (v.getId() == R.id.btn_zhoubian){
            zhoubian();
        }else if (v.getId() == R.id.item_popupwindows_two){
            // 跳转百度地图
            Intent i1 = new Intent();
            i1.setData(Uri.parse("baidumap://map/marker?location="+ tzlatitude +","+  tzlongitude +"&title="+ companyTitle.getText().toString() +"&content=makeamarker&traffic=on"));
            startActivity(i1);
//                showDialog();

        }else if (v.getId() == R.id.parent || v.getId() == R.id.item_popupwindows_cancel){
            choicePhotoDialog.dismiss();
        }


    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(MapActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }


    // 搜索周边
    private void zhoubian() {
        /**
         * NearbySearchInfo:周边检索设置参数类，继承自 BaseCloudSearchInfo
         * */
        NearbySearchInfo info = new NearbySearchInfo();

        //access_key（必须），最大长度50
        info.ak = Global.baidu_ak;
        ////geo table 表主键（必须）
        info.geoTableId = 166887;
        //检索半径，可选；单位为米，默认为1000米。样例：500
        info.radius = 30000;
        //  默认最大显示50个供应站
        info.pageSize = 50;

        //检索中心点坐标（经纬度），必选；样例：116.4321,38.76623
//        Toast.makeText(BaiDuYunActivity.this, latitude + "+" + longitude + "", Toast.LENGTH_SHORT).show();
//            info.location = "116.404566,39.914974,";
        info.location = mLongtitude + "," + mLatitude;
//        CloudManager instance = CloudManager.getInstance();
        CloudManager.getInstance().nearbySearch(info);
    }
    OverlayOptions options;
    Marker marker = null;
    @Override
    public void onGetSearchResult(CloudSearchResult result, int i) {
        if (result != null && result.poiList != null && result.poiList.size() > 0) {
            //清空地图所有的 Overlay 覆盖物以及 InfoWindow
            mBaiDuMap.clear();

            /**
             * public static BitmapDescriptor fromResource(int resourceId)
             * 根据资源 Id 创建 bitmap 描述信息
             * */
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.maker);

            LatLng ll;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            List<CloudPoiInfo> poiList = result.poiList;
            for (int j = 0; j<result.poiList.size(); j++){
                infos.add(poiList.get(j));
                ll = new LatLng(poiList.get(j).latitude, poiList.get(j).longitude);
                /**
                 * OverlayOptions:地图覆盖物选型基类
                 *
                 * public MarkerOptions icon(BitmapDescriptor icon):
                 * 设置 Marker 覆盖物的图标，相同图案的 icon 的 marker
                 * 最好使用同一个 BitmapDescriptor 对象以节省内存空间。
                 * @param icon - Marker 覆盖物的图标
                 * @return 该 Marker 选项对象
                 *
                 * public MarkerOptions position(LatLng position):
                 * 设置 marker 覆盖物的位置坐标
                 * @param position - marker 覆盖物的位置坐标
                 * @param 该 Marker 选项对象
                 * */
                OverlayOptions oo = new MarkerOptions()
                        .icon(bd)
                        .position(ll);
                /**
                 * addOverlay(OverlayOptions options):
                 * 向地图添加一个 Overlay
                 * */
                mBaiDuMap.addOverlay(oo).setZIndex(j);

                /**
                 * public LatLngBounds.Builder include(LatLng point)
                 * 让该地理范围包含一个地理位置坐标
                 * @param point - 地理位置坐标
                 * @return 该构造器对象
                 * */
                builder.include(ll);
            }

            /**
             * public LatLngBounds build()
             * 创建地理范围对象
             * @return 创建出的地理范围对象
             * */
            LatLngBounds bounds = builder.build();

            /**
             * MapStatusUpdateFactory:生成地图状态将要发生的变化
             *
             * public static MapStatusUpdate newLatLngBounds(LatLngBounds bounds)
             * 设置显示在屏幕中的地图地理范围
             * @param bounds - 地图显示地理范围，不能为 null
             * @return 返回构造的 MapStatusUpdate， 如果 bounds 为 null 则返回空。
             * */
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiDuMap.animateMapStatus(u);
        }


    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult result, int i) {
        CloudPoiInfo poiInfo = result.poiInfo;
        if (result != null) {
            if (result.poiInfo != null) {
                Toast.makeText(MapActivity.this, result.poiInfo.title, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MapActivity.this, "status:" + result.status, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult result, int i) {

    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 定位之后的回调
            MyLocationData data = new MyLocationData.Builder()//
                    .direction(mCurrentX)//
                    .accuracy(bdLocation.getRadius())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude()).build();
            mBaiDuMap.setMyLocationData(data);
            // 设置图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
            mBaiDuMap.setMyLocationConfiguration(configuration);


            // 更新经纬度
            mLatitude = bdLocation.getLatitude();
            mLongtitude = bdLocation.getLongitude();

            if (isFirstIn) {

                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiDuMap.animateMapStatus(msu);
                isFirstIn = false;

                // 获取当前的位置
                Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

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

}
