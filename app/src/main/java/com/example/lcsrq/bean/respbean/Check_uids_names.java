package com.example.lcsrq.bean.respbean;

import android.drm.DrmStore;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/18.
 *  我的整改,查处人的名字
 */

public class Check_uids_names implements Serializable {

    private String uid;
    private String uname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
