package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/24.
 */

public class TiJiaoBean implements Serializable {
    private String Uid;
    private String UserName;

    public TiJiaoBean(String usernName, String uid) {
        this.Uid = uid;
        this.UserName = usernName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
