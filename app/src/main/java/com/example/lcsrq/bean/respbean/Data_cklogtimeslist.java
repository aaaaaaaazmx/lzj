package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/7.
 * 供应站详情存在问题
 */

public class Data_cklogtimeslist implements Serializable {
    /**
     * times_at: "2017-06-06 10:11:23",
     times_id: "2",
     supply_name: "百江-14号供应站",
     m_account: "rq1",
     m_nickname: "rq1"
     */
    private  String times_at;
    private  String times_id;
    private  String supply_name;
    private  String m_account;
    private  String m_nickname;
    private  String check_dw;

    public String getCheck_dw() {
        return check_dw;
    }

    public void setCheck_dw(String check_dw) {
        this.check_dw = check_dw;
    }

    public String getTimes_at() {
        return times_at;
    }

    public void setTimes_at(String times_at) {
        this.times_at = times_at;
    }

    public String getTimes_id() {
        return times_id;
    }

    public void setTimes_id(String times_id) {
        this.times_id = times_id;
    }

    public String getSupply_name() {
        return supply_name;
    }

    public void setSupply_name(String supply_name) {
        this.supply_name = supply_name;
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
}
