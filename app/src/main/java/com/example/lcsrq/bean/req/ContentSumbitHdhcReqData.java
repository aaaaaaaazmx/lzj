package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/14.
 * <p>
 * 提交黑点黑车请求参数
 */

public class ContentSumbitHdhcReqData implements Serializable {

    private int uid;  // 必须
    private int type;  // 必须  1-黑车；2-黑点；
    private String areas;  // 必须
    private String address;  // 必须
    private String content;  // 必须
    private String uploads;  // 必须
    private String apisecret;
    private String cart_number;  // 必须  1-黑车；2-黑点；
    private String areasid;

    public String getAreasid() {
        return areasid;
    }

    public void setAreasid(String areasid) {
        this.areasid = areasid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCart_number() {
        return cart_number;
    }

    public void setCart_number(String cart_number) {
        this.cart_number = cart_number;
    }
}
