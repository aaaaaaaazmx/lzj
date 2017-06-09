package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/14.
 * 获取供应站检查项目data返回参数
 */

public class GyzCheckRespData implements Serializable{

    /*
    id: "11",
catid: "15",
title: "网点从业人员有岗位培训合格证",
creat_at: "2017-04-05 11:01:59",
remark: "网点从业人员有岗位培训合格证",
cname: "资质证照"
     */

    public String id;
    public String catid;
    public String title;
    public String creat_at;
    public String remark;
    public String cname;
    public int flag = 0; //  用来标记检查项目中是否被点击
    public int state = -1; //  用来标记检查项目中是否可以被取消

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

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

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
