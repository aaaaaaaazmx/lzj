package com.example.lcsrq.bean.req;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/4.
 * 提交多条请求参数
 */

public class GyzJcReqDuoData implements Serializable{

    private int uid;
    private String supply_id;
    private String check_uids;
    private String check_dw;
    private String datas;
    private String apisecret;
    private String status;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(String supply_id) {
        this.supply_id = supply_id;
    }

    public String getCheck_uids() {
        return check_uids;
    }

    public void setCheck_uids(String check_uids) {
        this.check_uids = check_uids;
    }

    public String getCheck_dw() {
        return check_dw;
    }

    public void setCheck_dw(String check_dw) {
        this.check_dw = check_dw;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
