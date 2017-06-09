package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.req.ContentReqData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class ContentListRespData implements Serializable {

    private int code;
    private String status;
    private String request_type;
    private String response_type;
    private ArrayList<ContentRespData> data;
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

    public ArrayList<ContentRespData> getData() {
        return data;
    }

    public void setData(ArrayList<ContentRespData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
