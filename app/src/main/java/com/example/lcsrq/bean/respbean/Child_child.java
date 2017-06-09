package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/13.
 * 供应站地区详情三级列表
 */

public class Child_child implements Serializable {

    private String id;
    private String pid;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
