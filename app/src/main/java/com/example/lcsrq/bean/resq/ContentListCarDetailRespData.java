package com.example.lcsrq.bean.resq;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/12.
 *
 * // 车辆详情
 */

public class ContentListCarDetailRespData {

    private int code;
    private String status;
    private String msg;
    private String request_type;
    private String response_type;
    private ContentCarDetailRespData data;

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

    public ContentCarDetailRespData getData() {
        return data;
    }

    public void setData(ContentCarDetailRespData data) {
        this.data = data;
    }
}
