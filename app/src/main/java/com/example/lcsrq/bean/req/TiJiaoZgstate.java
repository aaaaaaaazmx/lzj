package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/4.
 * 提交整改状态的请求参数
 */

public class TiJiaoZgstate implements Serializable {

    private int status_uid;
    private int status;
    private int did;
    private String apisecret;

    public int getStatus_uid() {
        return status_uid;
    }

    public void setStatus_uid(int status_uid) {
        this.status_uid = status_uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
