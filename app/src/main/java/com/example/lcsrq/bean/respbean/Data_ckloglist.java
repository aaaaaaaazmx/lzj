package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/11.
 * 供应站详情
 */

public class Data_ckloglist implements Serializable {
/**
 * logid: "18",
 check_id: "11",
 uid: "4",
 content: "图图图",
 status: "1",
 creat_at: "2017-05-05 16:00:55",
 upload_json: "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170505/20170505160041_58468.jpg,http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170505/20170505160041_99192.jpg",
 remark: "网点从业人员有岗位培训合格证",
 check_dw: "KTV图图",
 supply_name: "冰宇-和平村供应站",
 m_account: "rq1",
 m_nickname: "rq1"
 */

private String logid;
private String check_id;
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

}
