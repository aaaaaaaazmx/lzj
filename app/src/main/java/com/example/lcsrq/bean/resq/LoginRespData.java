package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.Row;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class LoginRespData implements Serializable {
    private String uid;
    private Row rows;

    public Row getRows() {
        return rows;
    }

    public void setRows(Row rows) {
        this.rows = rows;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
