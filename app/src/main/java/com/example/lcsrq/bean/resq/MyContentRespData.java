package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.M_datajson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/5/3.
 * 我的通讯录返回参数(DATA)
 */

public class MyContentRespData implements Serializable {

    /**
     * "id":"4",
     "m_roleid":"3",
     "m_nickname":"rq1",
     "m_account":"rq1",
     "head_photo":"http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png"
     */

    private String id;
    private String m_roleid;
    private String m_nickname;
    private String m_account;
    private String head_photo;
    private M_datajson m_datajson;

    public M_datajson getM_datajson() {
        return m_datajson;
    }

    public void setM_datajson(M_datajson m_datajson) {
        this.m_datajson = m_datajson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_roleid() {
        return m_roleid;
    }

    public void setM_roleid(String m_roleid) {
        this.m_roleid = m_roleid;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }


}
