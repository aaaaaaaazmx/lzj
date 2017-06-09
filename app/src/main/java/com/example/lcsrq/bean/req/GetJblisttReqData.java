package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/7.
 * 请求参数
 */

public class GetJblisttReqData implements Serializable {

    private int jb_id;
    private int page;
    private int pagesize;
    private String apisecret;

    public int getJb_id() {
        return jb_id;
    }

    public void setJb_id(int jb_id) {
        this.jb_id = jb_id;
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

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
