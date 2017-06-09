package com.example.lcsrq.bean.resq;

import com.example.lcsrq.R;
import com.example.lcsrq.bean.respbean.M_datajson;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class UserinfoRespData implements Serializable {
    /**
     * ""id:"1",
     * "m_roleid":"1",
     * "m_account":"",
     * "m_nickname":"测试",
     * "sn":"aaa",
     * "code":"1111",
     * "company_id":"20",
     * "supply_id":"19",
     * "company_name":"测试",
     * "supply_name":"供应站1"
     * "m_datajson":{
     * "sex":"1",
     * "zw":"xxx",
     * "mcode":"bbb",
     * "start_end":"2018-03-14",
     * "remark":"ffff"
     * head_photo: "http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png",
     * },
     */

    private String id;
    private String m_roleid;
    private String m_account;
    private String m_nickname;
    private String sn;
    private String code;
    private String company_id;
    private String supply_id;
    private String head_photo;

    private String company_name;
    private String supply_name;


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSupply_name() {
        return supply_name;
    }

    public void setSupply_name(String supply_name) {
        this.supply_name = supply_name;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }

    private M_datajson m_datajson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_roleid() {
        return m_roleid;
    }

    public void setM_roleid(String m_roleid) {
        this.m_roleid = m_roleid;
    }

    public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(String supply_id) {
        this.supply_id = supply_id;
    }

    public M_datajson getM_datajson() {
        return m_datajson;
    }

    public void setM_datajson(M_datajson m_datajson) {
        this.m_datajson = m_datajson;
    }

}
