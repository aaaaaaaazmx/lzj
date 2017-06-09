package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/18.
 * 供应站信息维护请求参数
 */

public class GyzXxwhReqData implements Serializable {

    private int supply_id;
    private String uploads;
    private String apisecret;
    private double lng;
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(int supply_id) {
        this.supply_id = supply_id;
    }


    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
