package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/6.
 */

public class FzrsBean implements Serializable {
/**
 * uname: "肖飞",
 dw: "雨花区城建局"
 */
private String uname;
private String dw;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }
}
