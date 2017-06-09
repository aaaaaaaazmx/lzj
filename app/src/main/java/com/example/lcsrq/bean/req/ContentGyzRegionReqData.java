package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/13.
 *  供应站地区联级列表
 *  请求参数
 */

public class ContentGyzRegionReqData implements Serializable {

    private int pid;
    private int  level;
    private String  apisecret;
    private String  uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
