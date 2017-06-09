package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/17.
 */

public class GetPicRespData  {
    /**
     * id: "47",
     catid: "14",
     title: "11111",
     remark: "0",
     creat_at: "2017-04-12 15:20:31",
     upload_path: "http://qzmoo.cn/cart/uploads/upload/20170412/9a4a78dfad899143b963ecd98d9ecd0b.png",
     status: "1",
     cname: "图片轮播"
     */

    private String id;
    private String catid;
    private String title;
    private String status;
    private String remark;
    private String upload_path;
    private String creat_at;
    private String cname;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
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
