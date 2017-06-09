package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/15.
 */

public class CxjlInfoRespData {

    /**
     * id: "49",
     catid: "13",
     title: "22222",
     remark: "0",
     creat_at: "2017-04-12 15:20:52",
     upload_path: "0",
     status: "1",
     cname: "黑名单管理"
     */


    private int id;
    private int catid;
    private int title;
    private int remark;
    private int creat_at;
    private int upload_path;
    private int status;
    private int cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public int getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(int creat_at) {
        this.creat_at = creat_at;
    }

    public int getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(int upload_path) {
        this.upload_path = upload_path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCname() {
        return cname;
    }

    public void setCname(int cname) {
        this.cname = cname;
    }
}
