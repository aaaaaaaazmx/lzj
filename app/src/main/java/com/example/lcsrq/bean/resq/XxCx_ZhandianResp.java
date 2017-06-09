package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class XxCx_ZhandianResp {

    /**
     id: "19",
     title: "供应站1",
     tel: "888888",
     company_id: "20",
     status: "1"
     */

    public String id;
    public String title;
    public String tel;
    public String company_id;
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
