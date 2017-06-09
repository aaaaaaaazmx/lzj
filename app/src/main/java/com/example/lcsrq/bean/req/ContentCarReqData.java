package com.example.lcsrq.bean.req;

// * 获取车辆列表

public class ContentCarReqData {

    public int company_id;
    public int page;
    public int pagesize;
    public int sort;
    public int apisecret;

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

    public int getApisecret() {
        return apisecret;
    }

    public void setApisecret(int apisecret) {
        this.apisecret = apisecret;
    }
}
