package com.example.lcsrq.bean.req;

/**
 * Created by 苏毅 on 2017/4/15.
 * 提交供应站信息维护
 */

public class GyzXxxwhReqData {

    public int supply_id;
    public String address;
    public String uploads;
    public String apisecret;

    public int getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(int supply_id) {
        this.supply_id = supply_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
