package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class ContentRespData1{

    /**
     id: "45",
     catid: "10",
     title: "222",
     remark: "2222",
     creat_at: "2017-04-09 16:59:45",
     upload_path: "0",
     status: "1",
     cname: "区县动态"
     */

    private String  id;
    private String  catid;
    private String  title;
    private String  remark;
    private String  creat_at;
    private String  upload_path;
    private String  status;
    private String  cname;

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

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
