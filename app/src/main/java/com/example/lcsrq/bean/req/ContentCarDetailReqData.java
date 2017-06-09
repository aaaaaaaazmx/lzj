package com.example.lcsrq.bean.req;

/**
 * Created by 苏毅 on 2017/4/12.
 * 车辆详情 请求参数
 */

public class ContentCarDetailReqData {

    public int did;
    public String apisecret;
    public int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
