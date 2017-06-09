
package com.example.lcsrq.activity.manger.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.lcsrq.R;

import java.util.ArrayList;
import java.util.List;

public class BaiduMapActivity extends Activity {

    public final static String LATITUDE = "latitude";
    public final static String LONGITUDE = "longitude";
    public final static String ADDRESS = "address";
    public final static String NAME = "name";
    private LatLng originalLL, currentLL;//初始化时的经纬度和地图滑动时屏幕中央的经纬度

    static MapView mMapView = null;
    private GeoCoder mSearch = null;
    private LocationClient mLocClient;// 定位相关
    public MyLocationListenner myListener = new MyLocationListenner();
    private PoiSearch mPoiSearch;
    private List<PoiInfo> datas;
    private PoiInfo lastInfo = null;
    public static BaiduMapActivity instance = null;
    private BaiduMap mBaiduMap;
    private MapStatusUpdate myselfU;

    private boolean changeState = true;//当滑动地图时再进行附近搜索
    private int preCheckedPosition = 0;//点击的前一个位置

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        instance = this;
        setContentView(R.layout.activity_baidumap);
        init();

    }

    private void init() {
        mMapView = (MapView) findViewById(R.id.bmap_View);
        mSearch = GeoCoder.newInstance();
        ImageView centerIcon = (ImageView) findViewById(R.id.bmap_center_icon);

        datas = new ArrayList<PoiInfo>();

        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra(LATITUDE, 0);
        LocationMode mCurrentMode = LocationMode.NORMAL;
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mPoiSearch = PoiSearch.newInstance();
        mMapView.setLongClickable(true);


        // 隐藏百度logo ZoomControl
        int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ImageView || child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }
        // 隐藏比例尺
        mMapView.showScaleControl(false);
        if (latitude == 0) {
            mMapView = new MapView(this, new BaiduMapOptions());
            mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
            mBaiduMap.setMyLocationEnabled(true);
            showMapWithLocationClient();
            setOnclick();
        }
    }

    /**
     * 设置点击事件
     */
    private void setOnclick() {

        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                changeState = true;
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new MyMapStatusChangeListener());
        mSearch.setOnGetGeoCodeResultListener(new MyGetGeoCoderResultListener());
    }

    private boolean isSearchFinished;
    private boolean isGeoCoderFinished;

    private void refreshAdapter() {
        if (isSearchFinished && isGeoCoderFinished) {

            isSearchFinished = false;
            isGeoCoderFinished = false;
        }
    }
    /**
     * 根据经纬度进行反地理编码
     */
    private class MyGetGeoCoderResultListener implements OnGetGeoCoderResultListener {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            lastInfo.address = result.getAddress();
            lastInfo.location = result.getLocation();
            lastInfo.name = "[位置]";
            datas.add(lastInfo);
            preCheckedPosition = 0;
            isGeoCoderFinished = true;
            refreshAdapter();

        }
    }

    /**
     * 监听位置发生了变化
     */
    private class MyMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {
            if (changeState) {
                datas.clear();

            }
        }
        @Override
        public void onMapStatusChange(MapStatus mapStatus) {
        }
        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            LatLng target = mapStatus.target;
            Toast.makeText(BaiduMapActivity.this, target.latitude + "" +  target.longitude + "",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 显示当前的位置信息
     */
    private void showMapWithLocationClient() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(10000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
        super.onPause();
        lastInfo = null;
    }


    @Override
    protected void onDestroy() {
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.onDestroy();
        super.onDestroy();
    }

    /**
     * 监听函数，有新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            if (lastInfo != null) {
                return;
            }
            lastInfo = new PoiInfo();
            mBaiduMap.clear();


            LatLng llA = new LatLng(location.getLatitude(), location.getLongitude());
            Toast.makeText(BaiduMapActivity.this,location.getAddrStr(),Toast.LENGTH_SHORT).show();

            lastInfo.location = llA;
            lastInfo.address = location.getAddrStr();
            lastInfo.name = "[位置]";

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            CoordinateConverter converter = new CoordinateConverter();//坐标转换工具类
//            converter.coord(ll);//设置源坐标数据
//            converter.from(CoordinateConverter.CoordType.COMMON);//设置源坐标类型
//            LatLng convertLatLng = converter.convert();
            OverlayOptions myselfOOA = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_arrow_gray_right))
                    .zIndex(4).draggable(true);
            mBaiduMap.addOverlay(myselfOOA);
            myselfU = MapStatusUpdateFactory.newLatLngZoom(ll, 17.0f);
            mBaiduMap.animateMapStatus(myselfU);
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }
}
