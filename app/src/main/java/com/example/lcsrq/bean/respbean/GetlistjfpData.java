package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/8.
 * j记分项目DATA返回残苏
 */

public class GetlistjfpData implements Serializable {
    /**
     * id: "75",
     catid: "2",
     title: "项目11",
     title1: "5"
     */

    private String id ;
    private String catid ;
    private String title ;
    private String title1 ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }
}
