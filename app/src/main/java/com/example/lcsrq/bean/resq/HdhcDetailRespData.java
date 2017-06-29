package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.FzrsBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/14.
 */

public class HdhcDetailRespData implements Serializable{
    public String id;
    public String uid;
    public String type;
    public String cart_number;
    public String areas;
    public String address;
    public String content;
    public String creat_at;
    public String upload_json;
    private ArrayList<FzrsBean> fzrs;

    public ArrayList<FzrsBean> getFzrs() {
        return fzrs;
    }

    public void setFzrs(ArrayList<FzrsBean> fzrs) {
        this.fzrs = fzrs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    private String cc_user;

    public String getCc_user() {
        return cc_user;
    }

    public void setCc_user(String cc_user) {
        this.cc_user = cc_user;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "HdhcRespData{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", cart_number='" + cart_number + '\'' +
                ", areas='" + areas + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                ", creat_at='" + creat_at + '\'' +
                ", upload_json='" + upload_json + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCart_number() {
        return cart_number;
    }

    public void setCart_number(String cart_number) {
        this.cart_number = cart_number;
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

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getUpload_json() {
        return upload_json;
    }

    public void setUpload_json(String upload_json) {
        this.upload_json = upload_json;
    }

}
