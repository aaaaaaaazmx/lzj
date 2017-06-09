package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/7.
 */

public class GetJbListDataJson implements Serializable {

    /**
     * data_man: "哈哈",
     data_code: "他咯途中",
     data_tel: "旅途。他",
     data_qy: "具有专业",
     data_sp: "KTV图",
     data_kp: "记录兔兔",
     data_cp: "图图图图",
     data_remark: "jututu"
     */

    private String data_man;
    private String data_code;
    private String data_tel;
    private String data_qy;
    private String data_sp;
    private String data_kp;
    private String data_cp;
    private String data_remark;

    public String getData_man() {
        return data_man;
    }

    public void setData_man(String data_man) {
        this.data_man = data_man;
    }

    public String getData_code() {
        return data_code;
    }

    public void setData_code(String data_code) {
        this.data_code = data_code;
    }

    public String getData_tel() {
        return data_tel;
    }

    public void setData_tel(String data_tel) {
        this.data_tel = data_tel;
    }

    public String getData_qy() {
        return data_qy;
    }

    public void setData_qy(String data_qy) {
        this.data_qy = data_qy;
    }

    public String getData_sp() {
        return data_sp;
    }

    public void setData_sp(String data_sp) {
        this.data_sp = data_sp;
    }

    public String getData_kp() {
        return data_kp;
    }

    public void setData_kp(String data_kp) {
        this.data_kp = data_kp;
    }

    public String getData_cp() {
        return data_cp;
    }

    public void setData_cp(String data_cp) {
        this.data_cp = data_cp;
    }

    public String getData_remark() {
        return data_remark;
    }

    public void setData_remark(String data_remark) {
        this.data_remark = data_remark;
    }
}
