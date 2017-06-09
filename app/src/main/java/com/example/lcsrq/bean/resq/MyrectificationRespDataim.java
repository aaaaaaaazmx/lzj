package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.Check_uids_names;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 * 我的整改记录返回data参数
 */

public class MyrectificationRespDataim implements Serializable {

    /**
     * "logid":"",
     "check_id":"整改项目规则id",
     "uid":"",
     "content":"提交的内容",
     "status":"0",
     "creat_at":"2017-03-27 14:00:07提交时间",
     "remark":"整改项目规则名称",
     "upload_json":"提交的图片"
     */

    public String logid;
    public String check_id;
    public String uid;
    public String content;
    public String status;
    public String creat_at;
    public String remark;
    public String upload_json;
    private String check_dw;
    private String supply_name;

    private String company_name;
    private String company_id;

    private String flag;  // 用来标记哪些点击过了

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private ArrayList<Check_uids_names> check_uids_names;

    public ArrayList<Check_uids_names> getCheck_uids_names() {
        return check_uids_names;
    }

    public void setCheck_uids_names(ArrayList<Check_uids_names> check_uids_names) {
        this.check_uids_names = check_uids_names;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCheck_dw() {
        return check_dw;
    }

    public void setCheck_dw(String check_dw) {
        this.check_dw = check_dw;
    }

    public String getSupply_name() {
        return supply_name;
    }

    public void setSupply_name(String supply_name) {
        this.supply_name = supply_name;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpload_json() {
        return upload_json;
    }

    public void setUpload_json(String upload_json) {
        this.upload_json = upload_json;
    }
}
