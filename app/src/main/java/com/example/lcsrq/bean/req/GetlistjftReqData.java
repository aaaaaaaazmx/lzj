package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/9.
 * 获取计分列表 请求参数
 */

public class GetlistjftReqData implements Serializable {

    private int uid;
    private int oid_type;
    private int page;
    private int pagesize;
    private int apisecret;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOid_type() {
        return oid_type;
    }

    public void setOid_type(int oid_type) {
        this.oid_type = oid_type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getApisecret() {
        return apisecret;
    }

    public void setApisecret(int apisecret) {
        this.apisecret = apisecret;
    }
}
