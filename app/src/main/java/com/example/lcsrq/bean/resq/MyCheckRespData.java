package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/7.
 * 我的检查返回参数
 */

public class MyCheckRespData  implements Serializable{
    /**
     * id: "33",
     uid: "4",
     supply_id: "119",
     check_uids: "4",
     check_dw: "长沙市燃气热力管理局",
     creat_at: "2017-06-07 14:20:26",
     supply_name: "长液-竹山塘供应站",
     company_id: "22",
     company_name: "长沙市液化石油气发展有限责任公司"
     */


    private String id;
    private String uid;
    private String supply_id;
    private String check_uids;
    private String check_dw;
    private String creat_at;
    private String supply_name;
    private String company_id;
    private String company_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(String supply_id) {
        this.supply_id = supply_id;
    }

    public String getCheck_uids() {
        return check_uids;
    }

    public void setCheck_uids(String check_uids) {
        this.check_uids = check_uids;
    }

    public String getCheck_dw() {
        return check_dw;
    }

    public void setCheck_dw(String check_dw) {
        this.check_dw = check_dw;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getSupply_name() {
        return supply_name;
    }

    public void setSupply_name(String supply_name) {
        this.supply_name = supply_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
