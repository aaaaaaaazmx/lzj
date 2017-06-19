package com.example.lcsrq.bean.req;

import com.example.lcsrq.bean.resq.GyzCheckZgJlRespData;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/15.
 * 检查相整改记录
 */

public class GyzCheckZgJlReqData {

    private ArrayList<GyzCheckZgJlRespData> list = new ArrayList<>();

    public ArrayList<GyzCheckZgJlRespData> getList() {
        return list;
    }

    public void setList(ArrayList<GyzCheckZgJlRespData> list) {
        this.list = list;
    }

    private int supply_id;
    private String status;
    private int page;
    private int pagesize;
    private String apisecret;

    public int getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(int supply_id) {
        this.supply_id = supply_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
