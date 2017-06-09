package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/7.
 * 我的检查请求参数
 */

public class MyCheckReqData implements Serializable {
    private String uid;
    private String page;
    private String pagesize;
    private String apisecret;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
