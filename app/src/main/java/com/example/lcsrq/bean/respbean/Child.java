package com.example.lcsrq.bean.respbean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/13.
 * 供应站地区详情列表
 * 返回参数
 * 二级列表
 */

public class Child implements Serializable,IPickerViewData {

/**
 * "id":"8",
 "pid":"7",
 "name":"左岸社区"
 */
private String id;
private String pid;
private String name;
private ArrayList<Child_child> child;

    public ArrayList<Child_child> getChild() {
        return child;
    }

    public void setChild(ArrayList<Child_child> child) {
        this.child = child;
    }

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

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
