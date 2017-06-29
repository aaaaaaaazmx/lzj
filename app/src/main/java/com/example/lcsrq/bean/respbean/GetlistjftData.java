package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/9.
 */

public class GetlistjftData implements Serializable {

    /**
     * id: "18",
     oid: "41",
     oid_type: "2",
     oid_value: "2.0",
     company_id: "22",
     company_name: "长沙市液化石油气发展有限责任公司",
     status: "2",
     creat_at: "2017-06-09 00:46:45",
     oid_name: "章毅"
     */

    private String id;
    private String oid;
    private String oid_type;
    private String oid_value;
    private String company_id;
    private String company_name;
    private String status;
    private String creat_at;
    private String oid_name;
    private String m_nickname;
    private String ptitle;

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOid_type() {
        return oid_type;
    }

    public void setOid_type(String oid_type) {
        this.oid_type = oid_type;
    }

    public String getOid_value() {
        return oid_value;
    }

    public void setOid_value(String oid_value) {
        this.oid_value = oid_value;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getOid_name() {
        return oid_name;
    }

    public void setOid_name(String oid_name) {
        this.oid_name = oid_name;
    }
}
