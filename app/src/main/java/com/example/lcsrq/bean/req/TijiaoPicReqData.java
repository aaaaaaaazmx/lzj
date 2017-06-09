package com.example.lcsrq.bean.req;

import java.io.File;
import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/4/18.
 *  提交请求参数
 */

public class TijiaoPicReqData implements Serializable {

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
