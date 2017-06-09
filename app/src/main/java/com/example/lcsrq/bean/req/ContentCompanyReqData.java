package com.example.lcsrq.bean.req;

/**
 * Created by 苏毅 on 2017/4/11.
 *
 *  车辆管理: 下拉的公司列表
 */

public class ContentCompanyReqData {
    public int uid;
    public int page;
    public int pagesize;
    public int sort;
    public int apisecret;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int apisecret() {
        return apisecret;
    }

    public void apisecret(int apisecret) {
        this.apisecret = apisecret;
    }
}
