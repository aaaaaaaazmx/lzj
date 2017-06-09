package com.example.lcsrq.bean.req;

/**
 * Created by 苏毅 on 2017/4/11.
 * 轮播图
 */

public class ContentReqData {

    public int catid;  // 分类ID
    private int page;
    private int pagesize;
    private int sort;
    private String apisecret;

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

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }
}
