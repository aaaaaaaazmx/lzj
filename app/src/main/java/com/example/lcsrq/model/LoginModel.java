package com.example.lcsrq.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.lcsrq.bean.req.ContentCarDetailReqData;
import com.example.lcsrq.bean.req.ContentCarReqData;
import com.example.lcsrq.bean.req.ContentCompanyReqData;
import com.example.lcsrq.bean.req.ContentGyzDetailReqData;
import com.example.lcsrq.bean.req.ContentGyzRegionReqData;
import com.example.lcsrq.bean.req.ContentGyzReqData;
import com.example.lcsrq.bean.req.ContentPicReqData;
import com.example.lcsrq.bean.req.ContentReqData;
import com.example.lcsrq.bean.req.ContentSumbitHdhcReqData;
import com.example.lcsrq.bean.req.GetJblisttReqData;
import com.example.lcsrq.bean.req.GetYzmReqData;
import com.example.lcsrq.bean.req.GetjbcclistReqData;
import com.example.lcsrq.bean.req.GetlistjfpReqData;
import com.example.lcsrq.bean.req.GetlistjftReqData;
import com.example.lcsrq.bean.req.GyzCheckReqData;
import com.example.lcsrq.bean.req.GyzCheckZgJlReqData;
import com.example.lcsrq.bean.req.GyzJcReqData;
import com.example.lcsrq.bean.req.GyzJcReqDuoData;
import com.example.lcsrq.bean.req.GyzListReqData;
import com.example.lcsrq.bean.req.GyzXxwhReqData;
import com.example.lcsrq.bean.req.HdhcChacReqData;
import com.example.lcsrq.bean.req.HdhcDetailReqData;
import com.example.lcsrq.bean.req.HdhcReqData;
import com.example.lcsrq.bean.req.InfoReqData;
import com.example.lcsrq.bean.req.LoginReqData;
import com.example.lcsrq.bean.req.MyCheckReqData;
import com.example.lcsrq.bean.req.MyCheckReqDatas;
import com.example.lcsrq.bean.req.MyContentReqData;
import com.example.lcsrq.bean.req.MyrectificationReqDataim;
import com.example.lcsrq.bean.req.SubmitjftReqData;
import com.example.lcsrq.bean.req.SubmitjftstatusReqData;
import com.example.lcsrq.bean.req.TiJiaoZgstate;
import com.example.lcsrq.bean.req.TijiaoPicReqData;
import com.example.lcsrq.bean.req.UserinfoReqData;
import com.example.lcsrq.bean.resq.BaseRespData;
import com.example.lcsrq.bean.resq.ContentListCarRespData;
import com.example.lcsrq.bean.resq.ContentListComPanyRespData;
import com.example.lcsrq.bean.resq.ContentListGyzRegionRespData;
import com.example.lcsrq.bean.resq.ContentListGyzRespData;
import com.example.lcsrq.bean.resq.ContentListRespData;
import com.example.lcsrq.bean.resq.ContentListRespData1;
import com.example.lcsrq.bean.resq.GetPicofHomeListRespData;
import com.example.lcsrq.bean.resq.GetlistjfpRespData;
import com.example.lcsrq.bean.resq.GetlistjftRespData;
import com.example.lcsrq.bean.resq.GyzListCheckDuoReqData;
import com.example.lcsrq.bean.resq.GyzListCheckReqData;
import com.example.lcsrq.bean.resq.GyzListCheckZgJlRespData;
import com.example.lcsrq.bean.resq.GyzListRespData;
import com.example.lcsrq.bean.resq.GyzListResppData;
import com.example.lcsrq.bean.resq.HdhcListRespData;
import com.example.lcsrq.bean.resq.MyContentListRespData;
import com.example.lcsrq.bean.resq.MyrectificationListReqDataim;
import com.example.lcsrq.bean.resq.XxCx_CarRespData;
import com.example.lcsrq.bean.resq.XxCx_PeopleRespData;
import com.example.lcsrq.bean.resq.XxCx_ZhandianRespData;
import com.example.lcsrq.http.ModelHttp;
import com.example.lcsrq.http.OnLoadComBackListener;
import com.example.lcsrq.http.ProcessListener;
import com.example.lcsrq.http.UrlParser;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class LoginModel {
    // 登陆
    public void login(Context context, LoginReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_login, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    listener.onSuccess(respData.getData());

                } else {

                    listener.onError(respData.getMsg());

                }
                return false;
            }
        });
    }

    //  获取个人信息
    public void userinfo(Context context, UserinfoReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_userinfo, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());

                }
                return false;
            }
        });
    }

    // 获取列表名字
    public void getLBList(Context context, ContentReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentlist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                ContentListRespData respData = JSON.parseObject(str, ContentListRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        listener.onError("暂无数据!");
                    }

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    //列表图片对象
    public void getListOfPic(Context context, ContentPicReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentshow, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 车辆管理.获取车辆列表
    public void getListOfCar(Context context, ContentCarReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_cartlist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                ContentListCarRespData respData = JSON.parseObject(str, ContentListCarRespData.class);

                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }


    // 车辆管理.获取公司列表
    public void getListOfCompany(Context context, ContentCompanyReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_companylist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                ContentListComPanyRespData respData = JSON.parseObject(str, ContentListComPanyRespData.class);

                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 供应站管理.获取供应站列表
    public void getListOfGyz(Context context, ContentGyzReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_supplylist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                ContentListGyzRespData respData = JSON.parseObject(str, ContentListGyzRespData.class);

                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 供应站管理.获取供应站列表详情
    public void getListOfGyzDetail(Context context, ContentGyzDetailReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_supplyshow, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取区县行业动态数据
    public void getcontentshow(Context context, ContentPicReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentshow, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 车辆管理.获取车辆列表详情
    public void getListOfCarDetail(Context context, ContentCarDetailReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_cartshow, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 供应站地区联级列表
    public void getListOfGyzRegion(Context context, ContentGyzRegionReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_areatreelist, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                ContentListGyzRegionRespData respData = JSON.parseObject(str, ContentListGyzRegionRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }


    // 提交黑点黑车举报
    public void getSubmitHdhc(Context context, ContentSumbitHdhcReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitjb, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取分配给谁的黑点黑车举报列表
    public void getListOfHdhc(Context context, HdhcReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_jblist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                HdhcListRespData respData = JSON.parseObject(str, HdhcListRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }
    // 获取我的黑点黑车举报列表
    public void getListOfHdhcforMY(Context context, HdhcReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_jblistmy, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                HdhcListRespData respData = JSON.parseObject(str, HdhcListRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取所有的黑点黑车举报列表
    public void getAllListOfHdhc(Context context, HdhcReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_jblistall, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                HdhcListRespData respData = JSON.parseObject(str, HdhcListRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        System.out.println("暂无数据");
                    }
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取黑点黑车举报列表详情  可以获得举报ID
    public void getListOfDetailHdhc(Context context, HdhcDetailReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_jbshow, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交黑点黑车查处
    public void getListOfHdhcChac(Context context, HdhcChacReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_jbshow, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取供应站检查项目  单数组
    public void getListOfGyzCheck(Context context, GyzCheckReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_checklist1, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                GyzListCheckReqData respData = JSON.parseObject(str, GyzListCheckReqData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取供应站检查项目  多数组
    public void getListOfGyzCheckOFDUO(Context context, GyzCheckReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_checklist, baseReq, new ProcessListener() {

                        @Override
                        public boolean onDone(String key, String str) {
                   GyzListCheckDuoReqData respData = JSON.parseObject(str, GyzListCheckDuoReqData.class);
                            if (respData != null && respData.getCode() == 1) {
                                listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取供应站检查项整改记录
    public void putGyzCheckZgJl(Context context, GyzCheckZgJlReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getzglog, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                GyzListCheckZgJlRespData respData = JSON.parseObject(str, GyzListCheckZgJlRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    //queryinfo  查询信息_人
    public void getInfo_people(Context context, InfoReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_queryinfo, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                XxCx_PeopleRespData respData = JSON.parseObject(str, XxCx_PeopleRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }
    //queryinfo  查询信息_车
    public void getInfo_car(Context context, InfoReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_queryinfo, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                XxCx_CarRespData respData = JSON.parseObject(str, XxCx_CarRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    //queryinfo  查询信息_站点
    public void getInfo_Zhandian(Context context, InfoReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_queryinfo, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                XxCx_ZhandianRespData respData = JSON.parseObject(str, XxCx_ZhandianRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取我的整改记录
    public void getMyRectification(Context context, MyrectificationReqDataim baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getmyzglog, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                MyrectificationListReqDataim respData = JSON.parseObject(str, MyrectificationListReqDataim.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }
    // 获取我的整改记录
    public void getMyCheck(Context context, MyCheckReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getzglogmytimes, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                MyCheckReqDatas respData = JSON.parseObject(str, MyCheckReqDatas.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交黑点黑车查处
    public void getsubmithdhcCc(Context context, HdhcChacReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitjbcc, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交站点检查项(多条)
    public void getGyzJcDUO(Context context, GyzJcReqDuoData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitzgm, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交站点检查项(单条)
    public void getGyzJc(Context context, GyzJcReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitzg, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }


    // 提交供应站信息维护
    public void getGyzXxwh(Context context, GyzXxwhReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitsupplyinfo, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取区县行业动态数据
    public void getCxjl(Context context, ContentPicReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentshow, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取firstfragmen
    public void getLBListof(Context context, ContentReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentlist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                    ContentListRespData1 respData = JSON.parseObject(str, ContentListRespData1.class);

                if (respData != null && respData.getCode() == 1) {

                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        listener.onError("暂无数据!");
                    }

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取首页轮播图
    public void getLBListOfPic(Context context, ContentReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_contentlist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                GetPicofHomeListRespData respData = JSON.parseObject(str, GetPicofHomeListRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        listener.onError("暂无数据!");
                    }

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取验证码
    public void getYzm(Context context, GetYzmReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_sendmsg, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);

                if (respData != null && respData.getCode() == 1) {

                        listener.onSuccess(respData.getData());
                        listener.onError("暂无数据!");

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取我的通讯录
    public void getContents(Context context, MyContentReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_gettxl, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                MyContentListRespData respData = JSON.parseObject(str, MyContentListRespData.class);

                if (respData != null && respData.getCode() == 1) {

                    if (respData.getData() != null && respData.getData().size() > 0) {
                        listener.onSuccess(respData.getData());
                    } else {
                        listener.onError("暂无数据!");
                    }

                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交整改状态记录
    public void TijiaZgstate(Context context, TiJiaoZgstate baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_submitcklogstatus, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);

                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    //根据黑点黑车举报ID获取查处列表
    public void getjbcclist(Context context, GetJblisttReqData baseReq, final OnLoadComBackListener listener) {

        ModelHttp.postHttpClient(context, UrlParser.Key_getjbcclist, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {

                GetjbcclistReqData respData = JSON.parseObject(str, GetjbcclistReqData.class);

                if (respData != null && respData.getCode() == 1) {

                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 获取供应站检查项整改记录
    public void getGYZList(Context context, GyzListReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getzglogall, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                GyzListRespData respData = JSON.parseObject(str, GyzListRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 根据计分类型获取计分项目列表
    public void getListJfp(Context context, GetlistjfpReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getlistjfp, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                GetlistjfpRespData respData = JSON.parseObject(str, GetlistjfpRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交计分
    public void getSubmitjft(Context context, SubmitjftReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitjft, baseReq, new ProcessListener() {

            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

    // 提交记分状态
    public void getSubmitjft(Context context, SubmitjftstatusReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_submitjftstatus, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                BaseRespData respData = JSON.parseObject(str, BaseRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                    System.out.println("暂无数据");
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }


    // 记分项目列表
    public void getListjft(Context context, GetlistjftReqData baseReq, final OnLoadComBackListener listener) {
        ModelHttp.postHttpClient(context, UrlParser.Key_getlistjft, baseReq, new ProcessListener() {
            @Override
            public boolean onDone(String key, String str) {
                GetlistjftRespData respData = JSON.parseObject(str, GetlistjftRespData.class);
                if (respData != null && respData.getCode() == 1) {
                    listener.onSuccess(respData.getData());
                } else {
                    listener.onError(respData.getMsg());
                }
                return false;
            }
        });
    }

}
