package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/12.
 *
 * 供应站详情
 */

public class Data_fzr implements Serializable {
    /**
     * tel":"15874075440",
     "m_nickname":"测试",
     "head_photo":"
     "uid":"12",
     */
    public String uid;
    public String tel;
    public String m_nickname;
    public String head_photo;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }
}
