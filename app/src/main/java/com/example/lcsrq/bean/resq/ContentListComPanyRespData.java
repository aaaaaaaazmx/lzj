package com.example.lcsrq.bean.resq;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/11.
 * 车辆管理  下拉公司列表
 */

public class ContentListComPanyRespData {

    public int code;
    public int status;
    public String msg;
    public String request_type;
    public String response_type;
    public ArrayList<ContentComPanyRespData> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public ArrayList<ContentComPanyRespData> getData() {
        return data;
    }

    public void setData(ArrayList<ContentComPanyRespData> data) {
        this.data = data;
    }
}
