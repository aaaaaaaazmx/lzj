package com.example.lcsrq.bean.resq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/28.
 * 获取整改记录多数组
 */

public class GyzCheckDuoRespData implements Serializable{

//    id: "15",
//    name: "资质证照",
//    oplist: [
//    {
//        id: "11",
//            catid: "15",
//            title: "网点从业人员有岗位培训合格证",
//            creat_at: "2017-04-05 11:01:59",
//            remark: "网点从业人员有岗位培训合格证",
//            cname: "资质证照"
//    },
//    {
//        id: "10",
//                catid: "15",
//            title: "有燃气经营许可证",
//            creat_at: "2017-04-05 11:01:45",
//            remark: "有燃气经营许可证",
//            cname: "资质证照"
//    }
//]

    private String id;
    private String name;
    private ArrayList<GyzCheckRespData>  oplist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GyzCheckRespData> getOplist() {
        return oplist;
    }

    public void setOplist(ArrayList<GyzCheckRespData> oplist) {
        this.oplist = oplist;
    }
}
