package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/18.
 */

public class TijiaoPicRespData implements Serializable{

    /**
     * "http_url": "http://localhost",
     "data_url": "/nfdata/apis/data/upload/img/20170305/20170305173203_79334.png"
     "full_url": "http://localhost/nfdata/apis/data/upload/img/20170305/20170305173203_79334.png"
     */

    private String http_url;
    private String data_url;
    private String full_url;

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }

    public String getData_url() {
        return data_url;
    }

    public void setData_url(String data_url) {
        this.data_url = data_url;
    }

    public String getFull_url() {
        return full_url;
    }

    public void setFull_url(String full_url) {
        this.full_url = full_url;
    }
}
