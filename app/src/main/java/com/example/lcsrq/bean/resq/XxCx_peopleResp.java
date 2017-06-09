package com.example.lcsrq.bean.resq;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class XxCx_peopleResp implements Parcelable {
    /**
     * "id":"5",
     "m_roleid":"1",
     "m_nickname":"测试",
     "m_account":"15874075440",
     "head_photo":""
     */

    /**
     * id: "5",
     m_roleid: "1",
     m_nickname: "测试",
     m_account: "15874075440",
     head_photo: ""
     },

     */

    private  String id;
    private  String m_roleid;
    private  String m_nickname;
    private  String m_account;
    private  String head_photo;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
