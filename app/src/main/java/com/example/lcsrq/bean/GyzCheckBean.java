package com.example.lcsrq.bean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/4.
 * g供应站开始检查
 */

public class GyzCheckBean implements Serializable {

    private String content; //  输入的内容
    private String uploads; //  上传图片的连接
    private String check_id; //  项目检查ID
    private int flag = 0;  // 状态  方便用来删除



    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
