package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class GyzCheckZgJlRespData implements Serializable {


    /**
     *  "logid":"",
     "check_id":"整改项目规则id",
     "uid":"",
     "content":"提交的内容",
     "status":"0",
     "creat_at":"2017-03-27 14:00:07提交时间",
     "remark":"整改项目规则名称",
     "upload_json":"提交的图片"
     */

    private String logid;
    private String check_id;
    private String uid;
    private String content;
    private String status;
    private String creat_at;
    private String remark;
    private String upload_json;

    private String check_dw;
    private String supply_name;
    private String m_account;
    private String m_nickname;

    private String gsuid; //  公司人员UID
    private String fzuid; // 负责人员UID

    private String check_uids_names; // 检查人

    public String getCheck_uids_names() {
        return check_uids_names;
    }

    public void setCheck_uids_names(String check_uids_names) {
        this.check_uids_names = check_uids_names;
    }

    public String getGsuid() {
        return gsuid;
    }

    public void setGsuid(String gsuid) {
        this.gsuid = gsuid;
    }

    public String getFzuid() {
        return fzuid;
    }

    public void setFzuid(String fzuid) {
        this.fzuid = fzuid;
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
