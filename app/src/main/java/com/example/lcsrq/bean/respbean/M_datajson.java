package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class M_datajson implements Serializable {
//    "m_datajson":{
//        "sex":"1",
//                "zw":"xxx",
//                "mcode":"bbb",
//                "start_end":"2018-03-14",
//                "remark":"ffff"
//    },

    /**
     * sex: "1",
     dw: "rq1",
     zw: "rq1",
     cz: "rq1",
     remark: "rq1"
     },
     */
    private String dw;
    private String cz;
    private String sex;
    private String zw;
    private String mcode;
    private String start_end;
    private String remark;

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getStart_end() {
        return start_end;
    }

    public void setStart_end(String start_end) {
        this.start_end = start_end;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
