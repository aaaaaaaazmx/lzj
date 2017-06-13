package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/9.
 *
 */

public class Data_jftloglist implements Serializable{
    /**
     *   "id":"10",
     "oid_value":"5.0",
     "status":"2",
     "creat_at":"2017-06-08 17:06:59",
     "pname":"项目11",
     "uname":"rq1"
     */

    private String id;
    private String oid_value;
    private String status;
    private String creat_at;
    private String pname;
    private String uname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid_value() {
        return oid_value;
    }

    public void setOid_value(String oid_value) {
        this.oid_value = oid_value;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
