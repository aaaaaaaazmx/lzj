package com.example.lcsrq.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lcsrq.activity.manger.gyzmanger.GyzDetailActivity;
import com.example.lcsrq.value.Global;

/**
 * Created by 苏毅 on 2017/5/26.
 */

public class DingweiUtil {
    private  Activity activity;
    // 定位相关
    private LocationClient mLocationClient;
    private double mLatitude;
    private double mLongtitude;
    private MyLocationListener myLocationListener;
    private boolean first;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public DingweiUtil(GyzDetailActivity gyzDetailActivity) {
        activity = gyzDetailActivity;
    }

    // 定位
    public void initLocation (){
        mLocationClient = new LocationClient(activity);
        myLocationListener = new MyLocationListener();
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); //坐标类型
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(myLocationListener);

        if (first){
            mLocationClient.start();
        }else {
            mLocationClient.stop();
        }
    }

    private  boolean isFirstIn = true;
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLatitude = bdLocation.getLatitude();
            mLongtitude = bdLocation.getLongitude();
            Global.latitude = mLatitude;  // 纬度
            Global.longitude = mLongtitude; // 经度
            }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
}
