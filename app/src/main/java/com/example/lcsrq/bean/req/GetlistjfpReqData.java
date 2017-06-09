package com.example.lcsrq.bean.req;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/8.
 * 获得记分项目请求参数
 */

public class GetlistjfpReqData implements Serializable {
    private int type;
    private int page;
    private int pagesize;
    private int apisecret;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
