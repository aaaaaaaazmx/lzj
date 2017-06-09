package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/6.
 * 供应站提交Bean
 */

public class GyzTijiaoBean implements Serializable {
    private String content; //  输入的内容
    private String uploads; //  上传图片的连接
    private String check_id; //  项目检查ID

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
