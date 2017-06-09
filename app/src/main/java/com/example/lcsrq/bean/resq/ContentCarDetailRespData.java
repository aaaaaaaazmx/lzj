package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/12.
 * 车辆详情返回参数
 */

/**
 *  "id":"18",
 "type":"cart",
 "title":"车牌号",
 "remark":"简述",
 "creat_at":"2017-03-28 10:08:37",
 "content":"内容",
 "upload_path":"http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png",
 "company_id":"20",
 "upload_json":"",
 "tel":"司机电话",
 */

/**
 *   "driver1":"5",
 "driver2":"5",
 "yxtime":"道路运输证",
 "xkz":"禁区通行证",
 "supply":"19",
 "company":"公司名称",
 "driver1_name":"驾驶员",
 "driver2_name":"押送员",
 */

import com.example.lcsrq.bean.respbean.Supplylist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  "supplylist":[
 {
 "id":"19",
 "title":"供应站1"
 }
 ]
 */
public class ContentCarDetailRespData implements Serializable{
/*
"id":"18",
    "type":"cart",
    "title":"车牌号",
    "remark":"简述",
    "creat_at":"2017-03-28 10:08:37",
    "content":"内容",
    "upload_path":"http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png",
    "company_id":"20",
    "upload_json":"",
    "tel":"司机电话",
    "driver1":"5",
    "driver2":"5",
    "yxtime":"道路运输证",
    "xkz":"禁区通行证",
    "supply":"19",
    "company":"公司名称",
    "driver1_name":"驾驶员",
    "driver2_name":"押送员",
 */
    public String id;
    public String type;
    public String title;
    public String remark;
    public String creat_at;
    public String content;
    public String upload_path;
    public String company_id;
    public String upload_json;
    public String tel;
    public String driver1;
    public String driver2;
    public String yxtime;
    public String xkz;
    public String supply;
    public String company;
    public String driver1_name;
    public String driver2_name;
//    public String supplylist;
    public ArrayList<Supplylist> supplylist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUpload_json() {
        return upload_json;
    }

    public void setUpload_json(String upload_json) {
        this.upload_json = upload_json;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDriver1() {
        return driver1;
    }

    public void setDriver1(String driver1) {
        this.driver1 = driver1;
    }

    public String getDriver2() {
        return driver2;
    }

    public void setDriver2(String driver2) {
        this.driver2 = driver2;
    }

    public String getYxtime() {
        return yxtime;
    }

    public void setYxtime(String yxtime) {
        this.yxtime = yxtime;
    }

    public String getXkz() {
        return xkz;
    }

    public void setXkz(String xkz) {
        this.xkz = xkz;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDriver1_name() {
        return driver1_name;
    }

    public void setDriver1_name(String driver1_name) {
        this.driver1_name = driver1_name;
    }

    public String getDriver2_name() {
        return driver2_name;
    }

    public void setDriver2_name(String driver2_name) {
        this.driver2_name = driver2_name;
    }

    public ArrayList<Supplylist> getSupplylist() {
        return supplylist;
    }

    public void setSupplylist(ArrayList<Supplylist> supplylist) {
        this.supplylist = supplylist;
    }
}
