package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/11.
 * 列表图片bean对象
 */

public class PicRespData {
    /**
     * "id":"9",
     * "catid":"5",
     * "title":"测试",
     * "remark":"0",
     * "creat_at":"2017-03-27 14:00:07",
     * "content":"0",
     * "upload_path":"http://localhost/cart/uploads/upload/20170409/cfcd208495d565ef66e7dff9f98764da.png",
     * "cname":"法律法规"
     */

    private String id;
    private String catid;
    private String title;
    private String remark;
    private String creat_at;
    private String content;
    private String upload_path;
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
