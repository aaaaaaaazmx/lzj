package com.example.lcsrq.bean.resq;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.example.lcsrq.bean.respbean.Child;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/13.
 * 供应站地区详情列表
 *
 * f供应站返回参数
 */

public class ContentGyzRegionRespData implements Serializable,IPickerViewData {
    /**
     * "id":"5",
     "pid":"0",
     "name":"开福区",
     */

    private String id;
    private String pid;
    private String name;

    private ArrayList<Child> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Child> getChild() {
        return child;
    }

    public void setChild(ArrayList<Child> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }

}
