package com.example.lcsrq.http;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class UrlParser {
    //正式地址
    public static final String BASE_URL_ONLINE = "http://qzmoo.cn/rqapi/apiwx/";//正式数据地址

    //使用地址
    public static final String BASE_URL = BASE_URL_ONLINE;//数据地址


    //接口名
    public static final String Key_login = "userlogin";//例：登陆接口

    //用户信息接口
    public static final String Key_userinfo = "userinfo";//例：用户信息接口

    //获取文章内容列表
    public static final String Key_contentlist = "contentlist";//例：获取文章内容列表

    //获取文章内容详情
    public static final String Key_contentshow = "contentshow";//例：获取文章内容详情

    // 获取车辆列表
    public static final String Key_cartlist = "cartlist";//例：获取车辆列表

    // 获取公司列表
    public static final String Key_companylist = "companylist";//例：获取公司列表

    // 获取供应站列表
    public static final String Key_supplylist = "supplylist";//例：获取供应站列表

    // 获取车辆详情
    public static final String Key_cartshow = "cartshow";//例：获取车辆详情

    // 获取供应站详情
    public static final String Key_supplyshow = "supplyshow";//例：获取供应站详情


    // 获取地区级联列表
    public static final String Key_areatreelist = "areatreelist";//例：获取地区级联列表

    //  提交黑点黑车举报
    public static final String Key_submitjb = "submitjb";

    //  获取我的黑点黑车举报
    public static final String Key_jblistmy = "jblistmy";

    //  获取给配给谁的黑点黑车举报列表
    public static final String Key_jblist = "jblist";

    //  获取所有黑点黑车举报列表
    public static final String Key_jblistall = "jblistall";

    //  获取黑点黑车举报列表详情
    public static final String Key_jbshow = "jbshow";

    //  提交黑点黑车查处
    public static final String Key_submitjbcc = "submitjbcc";

    //  获取检查项目  单数组
    public static final String Key_checklist1 = "checklist1";

    //  获取检查项目  多数组
    public static final String Key_checklist = "checklist";

    //  获取站点检查项整改记录
    public static final String Key_getzglog = "getzglog";

    //  查询信息
    public static final String Key_queryinfo = "queryinfo";

    //getmyzglog 获取我的整改记录
    public static final String Key_getmyzglog = "getmyzglog";

 //getmyzglog 提交站点检查项整改(单条)
    public static final String Key_submitzg = "submitzg";

    //getmyzglog 提交站点检查项整改(多条)
    public static final String Key_submitzgm = "submitzgm";

    //getmyzglog 提交供应站信息维护
    public static final String Key_submitsupplyinfo = "submitsupplyinfo";


    // 提交图片表单地址
    public static final String Key_formuploadimg = "formuploadimg";

    // 发送短信
    public static final String Key_sendmsg = "sendmsg";
    // 获取我的联系人
    public static final String Key_gettxl = "gettxl";

    // 提交整改记录状态
    public static final String Key_submitcklogstatus = "submitcklogstatus";

    // 根据黑点黑车举报ID获取查处列表
    public static final String Key_getjbcclist = "getjbcclist";


    // 获取供应站检查项整改记录
    public static final String Key_getzglogall = "getzglogall";

    // 获取我的检查
    public static final String Key_getzglogmytimes = "getzglogmytimes";

    //根据计分类型获取计分项目列表
    public static final String Key_getlistjfp = "getlistjfp";

    //提交记分
    public static final String Key_submitjft = "submitjft";

    // 获取计分列表
    public static final String Key_getlistjft = "getlistjft";
    // 提交计分状态
    public static final String Key_submitjftstatus = "submitjftstatus";

    // 返回数据列表

    public static String getUrl(String key) {
        if (key != null && key.equals(Key_login)) {
            return BASE_URL + Key_login;
        } else if (key != null && key.equals(Key_userinfo)) {
            return BASE_URL + Key_userinfo;
        } else if (key != null && key.equals(Key_contentlist)) {
            return BASE_URL + Key_contentlist;
        } else if (key != null && key.equals(Key_contentshow)) {
            return BASE_URL + Key_contentshow;
        } else if (key != null && key.equals(Key_cartlist)) {
            return BASE_URL + Key_cartlist;
        } else if (key != null && key.equals(Key_companylist)) {
            return BASE_URL + Key_companylist;
        } else if (key != null && key.equals(Key_supplylist)) {
            return BASE_URL + Key_supplylist;
        } else if (key != null && key.equals(Key_cartshow)) {
            return BASE_URL + Key_cartshow;
        } else if (key != null && key.equals(Key_supplyshow)) {
            return BASE_URL + Key_supplyshow;
        } else if (key != null && key.equals(Key_checklist)) {
            return BASE_URL + Key_checklist;
        } else if (key != null && key.equals(Key_areatreelist)) {
            return BASE_URL + Key_areatreelist;
        } else if ((key != null && key.equals(Key_submitjb))) {
            return BASE_URL + Key_submitjb;
        } else if ((key != null && key.equals(Key_jblist))) {
            return BASE_URL + Key_jblist;
        } else if ((key != null && key.equals(Key_jbshow))) {
            return BASE_URL + Key_jbshow;
        } else if ((key != null && key.equals(Key_submitjbcc))) {
            return BASE_URL + Key_submitjbcc;
        } else if ((key != null && key.equals(Key_checklist1))) {
            return BASE_URL + Key_checklist1;
        } else if ((key != null && key.equals(Key_getzglog))) {
            return BASE_URL + Key_getzglog;
        } else if ((key != null && key.equals(Key_queryinfo))) {
            return BASE_URL + Key_queryinfo;
        } else if ((key != null && key.equals(Key_getmyzglog))) {
            return BASE_URL + Key_getmyzglog;
        }else if ((key != null && key.equals(Key_submitzg))) {
            return BASE_URL + Key_submitzg;
        }else if ((key != null && key.equals(Key_submitsupplyinfo))) {
            return BASE_URL + Key_submitsupplyinfo;
        }else if ((key != null && key.equals(Key_formuploadimg))) {
            return BASE_URL + Key_formuploadimg;
        }else if ((key != null && key.equals(Key_sendmsg))) {
            return BASE_URL + Key_sendmsg;
        }else if ((key != null && key.equals(Key_jblistall))) {
            return BASE_URL + Key_jblistall;
        }else if ((key != null && key.equals(Key_gettxl))) {
            return BASE_URL + Key_gettxl;
        }else if ((key != null && key.equals(Key_submitcklogstatus))) {
            return BASE_URL + Key_submitcklogstatus;
        }else if ((key != null && key.equals(Key_submitzgm))) {
            return BASE_URL + Key_submitzgm;
        }else if ((key != null && key.equals(Key_getjbcclist))) {
            return BASE_URL + Key_getjbcclist;
        }else if ((key != null && key.equals(Key_jblistmy))) {
            return BASE_URL + Key_jblistmy;
        }else if ((key != null && key.equals(Key_getzglogall))) {
            return BASE_URL + Key_getzglogall;
        }else if ((key != null && key.equals(Key_getzglogmytimes))) {
            return BASE_URL + Key_getzglogmytimes;
        }else if ((key != null && key.equals(Key_getlistjfp))) {
            return BASE_URL + Key_getlistjfp;
        }else if ((key != null && key.equals(Key_submitjft))) {
            return BASE_URL + Key_submitjft;
        }else if ((key != null && key.equals(Key_getlistjft))) {
            return BASE_URL + Key_getlistjft;
        }else if ((key != null && key.equals(Key_submitjftstatus))) {
            return BASE_URL + Key_submitjftstatus;
        }
        return null;
    }

}
