package com.example.lcsrq.bean.resq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/17.
 *
 * 首页轮播图
 */

public class GetPicofHomeListRespData implements Serializable{

    private int code;
    private String status;
    private String msg;
    private ArrayList<GetPicRespData> data;
    private String request_type;
    private String response_type;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<GetPicRespData> getData() {
        return data;
    }

    public void setData(ArrayList<GetPicRespData> data) {
        this.data = data;
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
}
