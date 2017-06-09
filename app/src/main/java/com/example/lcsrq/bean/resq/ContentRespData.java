package com.example.lcsrq.bean.resq;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/11.
 * <p>
 */

public class ContentRespData implements Serializable {
    /**
     "id":"9",
     "catid":"5",
     "title":"测试",
     "remark":"0",
     "creat_at":"2017-03-27 14:00:07",
     "cname":"法律法规",
     "upload_path":""
     */
    private String id;
    private String catid;
    private String title;
    private String remark;
    private String creat_at;

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

    private String cname;
    private String content;
    private String upload_path;

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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
