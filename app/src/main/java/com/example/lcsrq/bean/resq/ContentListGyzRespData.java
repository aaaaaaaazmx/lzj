package com.example.lcsrq.bean.resq;

import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/11.
 */

public class ContentListGyzRespData {
    /**ContentListCarRespData
     * "code":"1",
     "status":"1000000",
     "msg":"成功",
     "data":[
     {
     "id":"18",
     "title":"湘A88888",
     "tel":"8888888",
     "company_id":"20",
     "company":"测试"
     }
     ],
     "request_type":"post",
     "response_type":"json"
     */

    private int code;
    private String status;
    private String msg;
    private String request_type;
    private String response_type;
    private ArrayList<ContentGyzRespData> data;

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

    public ArrayList<ContentGyzRespData> getData() {
        return data;
    }

    public void setData(ArrayList<ContentGyzRespData> data) {
        this.data = data;
    }
}
