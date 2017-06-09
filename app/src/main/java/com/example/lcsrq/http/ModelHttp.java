package com.example.lcsrq.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.lcsrq.activity.manger.MyAppliacation;
import com.example.lcsrq.bean.req.LoginReqData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * Created by 苏毅 on 2017/4/10.
 */

public class ModelHttp {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void postHttpClient(final Context context, final String key, final Object baseReq, final ProcessListener processListener) {

        RequestParams requestParams = getReq(baseReq);//请求数据

        final String url = UrlParser.getUrl(key);

//        UiTool.showLog("请求地址：" + url + "/n  请求参数：" + JSONObject.toJSONString(baseReq)); // 土司弹出

        // 超时6秒
        client.setTimeout(6000);

        // 设置连接超时
        client.setConnectTimeout(6000);


        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String str = new String(bytes, "UTF-8");
                    System.out.println("11111111111111" + str);
//                    UiTool.showLog("请求地址：" + url + "/n  返回参数：" + str);
                    System.out.println("请求地址：" + url + "/n  返回参数：" + str);
                    processListener.onDone(key, str);
                } catch (UnsupportedEncodingException e) {
                    String str = "{\"data\":null,\"error_code\":\"-1\",\"msg\":\"暂无网络！\"}";//
                    processListener.onDone(key, str);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                String str = "{\"data\":null,\"error_code\":\"-1\",\"msg\":\"暂无网络！\"}";//
                processListener.onDone(key, str);
            }
        });
    }


    public static void postPicHttpClient(final Context context, final String key, final File file, final ProcessListener processListener) {

        RequestParams params = new RequestParams();
        try {
            params.put("uploadfile"+System.currentTimeMillis(), file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ;
        }

        final String url = key;

//        UiTool.showLog("请求地址：" + url + "/n  请求参数：" + JSONObject.toJSONString(baseReq)); // 土司弹出

        client.setTimeout(60000);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String str = new String(bytes, "UTF-8");
                    System.out.println("11111111111111" + str);
//                    UiTool.showLog("请求地址：" + url + "/n  返回参数：" + str);
                    System.out.println("请求地址：" + url + "/n  返回参数：" + str);
                    processListener.onDone(key, str);
                } catch (UnsupportedEncodingException e) {
                    String str = "{\"data\":null,\"error_code\":\"-1\",\"msg\":\"暂无网络！\"}";//
                    processListener.onDone(key, str);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                String str = "{\"data\":null,\"error_code\":\"-1\",\"msg\":\"暂无网络！\"}";//
                processListener.onDone(key, str);
            }
        });
    }



    // 上传图片uploadPic
    public static void uploadPic(File file) {
            Log.d("====","图片路径"+ file.getPath());
            RequestParams params = new RequestParams();
            try {
                params.put("uploadfile"+System.currentTimeMillis(), file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ;
            }
            client.post("http://qzmoo.cn/rqapi/apiwx/formuploadimg", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String a = null;
                    try {
                        a = new String(responseBody, "UTF-8");
                        Log.d("====上传图片成功", a);
                        System.out.print("====失败" + a);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    String a = responseBody.toString();
                    System.out.print("====失败" + a);
                }
            });
        }



    //javabean转requestparams
    private static RequestParams getReq(Object javaBean) {

        RequestParams requestParams = new RequestParams();

        Method[] methods = javaBean.getClass().getDeclaredMethods();  //反射

        for (Method method : methods)
        {
            try
            {
                if (method.getName().startsWith("get"))
                {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[])null);
                    requestParams.put(field, null == value ? "" : value.toString());
                }
            }
            catch (Exception e)
            {
            }
        }

        if (!javaBean.getClass().equals(LoginReqData.class)) {
            Method[] methods2 = LoginReqData.class.getDeclaredMethods();
            for (Method method : methods2)
            {
                try
                {
                    if (method.getName().startsWith("get"))
                    {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);

                        Object value = method.invoke(javaBean, (Object[])null);
                        requestParams.put(field, null == value ? "" : value.toString());
                    }
                }
                catch (Exception e)
                {
                }
            }
        }

        return requestParams;
    }
}
