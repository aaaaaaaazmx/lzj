package com.example.lcsrq.bean.respbean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/6/9.
 */

public class Row implements Serializable {
    /**
     * d: "4",
     m_roleid: "3",
     m_account: "rq1",
     m_nickname: "rq1",
     sn: "01",
     code: "220581199011091197",
     company_id: "0",
     supply_id: "0",
     head_photo: "http://qzmoo.cn/cart/uploads/upload/20170411/b4b147bc522828731f1a016bfa72c073.png",
     m_datajson: {
     sex: "1",
     dw: "长沙市燃气热力管理局",
     zw: "rq1",
     cz: "rq1",
     remark: "rq1"
     },
     address: "0",
     jf_value: 0
     */

    private String id;
    private String m_roleid;
    private String m_account;
    private String m_nickname;
    private String sn;
    private String code;
    private String company_id;
    private String supply_id;
    private String head_photo;
    private String address;
    private String jf_value;
    private M_datajson m_datajson;

}
