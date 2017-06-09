package com.example.lcsrq.bean.req;

/**
 * Created by 苏毅 on 2017/4/14.
 * 黑点黑车的详情请求参数
 */

public class HdhcDetailReqData {

    public int did;
    public String apisecret;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
