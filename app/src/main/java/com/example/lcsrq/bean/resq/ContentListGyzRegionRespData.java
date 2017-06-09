package com.example.lcsrq.bean.resq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/13.
 *
 * 供应站地区详情列表
 */

public class ContentListGyzRegionRespData implements Serializable{

        private int code;
        private String status;
        private String msg;
        private String request_type;
        private String response_type;
        private ArrayList<ContentGyzRegionRespData> data;

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

    public ArrayList<ContentGyzRegionRespData> getData() {
        return data;
    }

    public void setData(ArrayList<ContentGyzRegionRespData> data) {
        this.data = data;
    }
}
