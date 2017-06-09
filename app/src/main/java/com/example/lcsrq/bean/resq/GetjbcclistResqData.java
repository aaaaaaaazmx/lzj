package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.GetJbListDataJson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/7.
 */

public class GetjbcclistResqData implements Serializable {
    /**
     * id: "17",
     jb_id: "92",
     uid: "4",
     man: "rq1",
     dw: "all 我",
     data_json: {},
     creat_at: "2017-05-05 12:54:19",
     upload_json: "http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170505/20170505125418_62866.jpg,http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170505/20170505125418_23845.jpg,http://qzmoo.cn/rqapi/apiwx/data/uploads/upload/image/20170505/20170505125419_76634.jpg",
     m_account: "rq1",
     m_nickname: "rq1"
     */


    private String id;
    private String jb_id;
    private String uid;
    private String man;
    private String dw;
    private String creat_at;
    private String upload_json;
    private String m_account;
    private String m_nickname;
    private ArrayList<GetJbListDataJson> data_json;

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

    public ArrayList<GetJbListDataJson> getData_json() {
        return data_json;
    }

    public void setData_json(ArrayList<GetJbListDataJson> data_json) {
        this.data_json = data_json;
    }
}
