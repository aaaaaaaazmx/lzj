package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/7.
 * 供应站整改项目返回DATA参数
 */

public class GyzListResppData implements Serializable {

    /**
     * logid: "193",
     check_id: "11",
     supply_id: "119",
     uid: "4",
     content: "网点从业人员有岗位培训合格证",
     status: "4",
     creat_at: "2017-06-07 14:20:26",
     upload_json: "0",
     remark: "网点从业人员有岗位培训合格证",
     check_dw: "长沙市燃气热力管理局",
     supply_name: "长液-竹山塘供应站",
     m_account: "rq1",
     m_nickname: "rq1",
     company_id: "22",
     company_name: "长沙市液化石油气发展有限责任公司"
     */


    private String logid;
    private String check_id;
    private String supply_id;
    private String uid;
    private String content;
    private String status;
    private String creat_at;
    private String upload_json;
    private String remark;
    private String check_dw;
    private String supply_name;
    private String m_account;
    private String m_nickname;
    private String company_id;
    private String company_name;

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

    public String getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(String supply_id) {
        this.supply_id = supply_id;
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

    public String getUpload_json() {
        return upload_json;
    }

    public void setUpload_json(String upload_json) {
        this.upload_json = upload_json;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
