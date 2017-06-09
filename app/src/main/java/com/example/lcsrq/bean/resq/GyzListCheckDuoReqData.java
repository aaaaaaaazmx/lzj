package com.example.lcsrq.bean.resq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/28.
 */

public class GyzListCheckDuoReqData implements Serializable {

    public int code;
    public String status;
    public String msg;
    public String request_type;
    public String response_type;
    public ArrayList<GyzCheckDuoRespData> data;

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

    public ArrayList<GyzCheckDuoRespData> getData() {
        return data;
    }

    public void setData(ArrayList<GyzCheckDuoRespData> data) {
        this.data = data;
    }

}
