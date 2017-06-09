package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.Data_json;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/7.
 *
 * 所有的黑点和车查出列表
 */

public class AllCclistRespData implements Serializable {
    /**
     *  "id":"16",
     "jb_id":"1",
     "uid":"4",
     "man":"ss",
     "dw":"ss",
     "data_json":{
     "data_man":"s",
     "data_code":"s",
     "data_tel":"s",
     "data_qy":"s",
     "data_sp":"s",
     "data_kp":"s",
     "data_cp":"s",
     "data_remark":"s"
     */

private String id;
private String jb_id;
private String uid;
private String man;
private String dw;
private Data_json data_json;
private String data_man;
private String data_code;
private String data_tel;
private String data_qy;
private String data_sp;
private String data_kp;
private String data_cp;
private String data_remark;

    /**
     * creat_at: "2017-05-18 14:33:57",
     upload_json: "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170518/20170518143321_10447.jpg",
     m_account: "rq1",
     m_nickname: "rq1"
     * @return
     */

    public String creat_at;
    public String upload_json;
    public String m_account;
    public String m_nickname;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJb_id() {
        return jb_id;
    }

    public void setJb_id(String jb_id) {
        this.jb_id = jb_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public Data_json getData_json() {
        return data_json;
    }

    public void setData_json(Data_json data_json) {
        this.data_json = data_json;
    }

    public String getData_man() {
        return data_man;
    }

    public void setData_man(String data_man) {
        this.data_man = data_man;
    }

    public String getData_code() {
        return data_code;
    }

    public void setData_code(String data_code) {
        this.data_code = data_code;
    }

    public String getData_tel() {
        return data_tel;
    }

    public void setData_tel(String data_tel) {
        this.data_tel = data_tel;
    }

    public String getData_qy() {
        return data_qy;
    }

    public void setData_qy(String data_qy) {
        this.data_qy = data_qy;
    }

    public String getData_sp() {
        return data_sp;
    }

    public void setData_sp(String data_sp) {
        this.data_sp = data_sp;
    }

    public String getData_kp() {
        return data_kp;
    }

    public void setData_kp(String data_kp) {
        this.data_kp = data_kp;
    }

    public String getData_cp() {
        return data_cp;
    }

    public void setData_cp(String data_cp) {
        this.data_cp = data_cp;
    }

    public String getData_remark() {
        return data_remark;
    }

    public void setData_remark(String data_remark) {
        this.data_remark = data_remark;
    }
}
