package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.FzrsBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/6.
 * 我的举报(分配给谁的黑点黑车举报)
 */

public class JuBaoBean implements Serializable {

   private  String id;
   private  String status;
   private  String uid;
   private  String cc_uids;
   private  String cc_uids2;
   private  String type;
   private  String cart_number;
   private  String areas;
   private  String address;
   private  String content;
   private  String creat_at;
   private  String upload_json;
   private ArrayList<FzrsBean> fzrs;
    public ArrayList<AllCclistRespData> cclist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCc_uids() {
        return cc_uids;
    }

    public void setCc_uids(String cc_uids) {
        this.cc_uids = cc_uids;
    }

    public String getCc_uids2() {
        return cc_uids2;
    }

    public void setCc_uids2(String cc_uids2) {
        this.cc_uids2 = cc_uids2;
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

    public ArrayList<FzrsBean> getFzrs() {
        return fzrs;
    }

    public void setFzrs(ArrayList<FzrsBean> fzrs) {
        this.fzrs = fzrs;
    }

    public ArrayList<AllCclistRespData> getCclist() {
        return cclist;
    }

    public void setCclist(ArrayList<AllCclistRespData> cclist) {
        this.cclist = cclist;
    }
}
