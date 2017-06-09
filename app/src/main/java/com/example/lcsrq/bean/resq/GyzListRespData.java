package com.example.lcsrq.bean.resq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/6/7.
 * 供应站整改项目返回参数
 */

public class GyzListRespData implements Serializable {
    private int code;
    private String status;
    private String request_type;
    private String response_type;
    private ArrayList<GyzListResppData> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public ArrayList<GyzListResppData> getData() {
        return data;
    }

    public void setData(ArrayList<GyzListResppData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
