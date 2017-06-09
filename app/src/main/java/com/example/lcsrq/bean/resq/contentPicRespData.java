package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/16.
 */

public class contentPicRespData implements Serializable {

    /**
     * id: "45",
     catid: "10",
     title: "222",
     remark: "2222",
     creat_at: "2017-04-09 16:59:45",
     content: "<p>第一章</p><p>第二章</p><p><br/></p><p>呼呼</p>",
     upload_path: "0",
     cname: "区县动态"
     */

    public String id;
    public String catid;
    public String title;
    public String remark;
    public String creat_at;
    public String content;
    public String upload_path;
    public String cname;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
