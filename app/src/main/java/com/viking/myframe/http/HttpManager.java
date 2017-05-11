package com.viking.myframe.http;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.viking.myframe.base.BaseApplication;
import com.viking.myframe.commons.SystemConfig;
import com.viking.myframe.http.exception.HttpTrowable;
import com.viking.myframe.utils.GsonUtil;
import com.viking.myframe.utils.LogGloble;
import com.viking.myframe.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * 单例类
 * Created by 周正一 on 2017/5/4.
 */

public class HttpManager {
    /**
     * 成功标识
     */
    public static final String SUCCESS_STATUS = "10000";
    private static HttpManager mInstance;
    //初始化操作，设置超时时间
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .build();

    private HttpManager() {
        //初始化OkHttpClient
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 得到一个AsyncHttp对象
     */
    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用于post提交
     *
     * @param callBack 回调接口
     */
    public void doPost(final Request request, final HttpCallBack callBack) {
        if(!isNetworkAvailable(BaseApplication.getBaseApplication())){
            ToastUtil.getInstance().toastInCenter(BaseApplication.getBaseApplication(),"网络异常");
            return;
        }
        String url = SystemConfig.BASEURL;

        //暂时没有实现Gzip压缩
        if(request.isZip()){
            //StringUtil.zip(gson.toJson(request.getRequestDate()));
        }else {
            //request.getRequest().setParams(MyJSON.toJSONString(request.getRequest().getParamsMap()));
        }
        //if(request.isEncrpy()){
        //}else{
        //}
        String requestStr = GsonUtil.GsonString(request);
        LogGloble.d("http",requestStr);
        OkHttpUtils
                .post()
                .url(url)
                .addParams("data", requestStr)
                .addHeader("charset","utf-8")
                .tag(this)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
                        return response.body().string();
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        HttpTrowable httpTrowable=new HttpTrowable(e.getMessage(),"999999");
                        callBack.doFaild(httpTrowable, request.getRequestDate(), request.getAction(), request.getMethod());
                    }

                    @Override
                    public void onResponse(Object result, int id) {
                        LogGloble.d("http：", "url--" + request.getAction() + "--methed--" + request.getMethod() + "--result--" + result);
                        Response response = null;
                        try {
                            response = GsonUtil.GsonToBean((String) result, Response.class);
                            if (!httpCalllBackPreFilter(response, request.getAction())) {
                                callBack.doSuccess(response, request.getAction(), request.getMethod());
                            }else{
                                HttpTrowable httpTrowable=new HttpTrowable(response.getResponseDate().getMessage(),response.getResponseDate().getStatus());
                                callBack.doFaild(httpTrowable, request.getRequestDate(), request.getAction(), request.getMethod());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            HttpTrowable httpTrowable=new HttpTrowable(e.getMessage(),"99999");
                            callBack.doFaild(httpTrowable, request.getRequestDate(), request.getAction(), request.getMethod());
                        }
                    }
                });

    }

    /**
     * 文件上传的接口请求
     */
    public void upLoadOneFile(final Request request, final Map<String, File> fileMap, final HttpCallBack callBack) {
        if(!isNetworkAvailable((Activity)callBack)){
            //网络未连接处理
            return;
        }
        String url = SystemConfig.BASEURL;
//        if(request.isZip()){
//            request.getRequest().setParams(StringUtil.zip(MyJSON.toJSONString(request.getRequest().getParamsMap())));
//        }else {
//            request.getRequest().setParams(MyJSON.toJSONString(request.getRequest().getParamsMap()));
//        }
//        if(request.isEncrpy()){
//        }else{
//
//        }
        String requestStr = GsonUtil.GsonString(request);
        LogGloble.d("http",requestStr);
        // 使用multipart表单上传文件
        PostFormBuilder builder= OkHttpUtils.post();
        for (String key : fileMap.keySet()) {
            //userid+字段名称用base64编码+.jpg
            String filename= new String(Base64.encode((key).getBytes(),Base64.DEFAULT))+".jpg";
            builder.addFile(key,filename,fileMap.get(key));
        }
        builder.url(url)
                .addHeader("charset","utf-8")
                .addParams("data",requestStr)
                .build()//
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        ToastUtil.getInstance().toastInCenter((Context) callBack,"服务器异常");
                        HttpTrowable httpTrowable=new HttpTrowable(e.getMessage(),"99999");
                        callBack.doFaild(httpTrowable, request.getRequestDate(), request.getAction(), request.getMethod());
                    }

                    @Override
                    public void onResponse(Object result, int id) {
                        LogGloble.d("http：", "url--" + request.getAction() + "--methed--" + request.getMethod() + "--result--" + result);
                        Response response = null;
                        try {
                            response = GsonUtil.GsonToBean((String) result, Response.class);
                            if (!httpCalllBackPreFilter(response, request.getAction())) {
                                callBack.doSuccess(response, request.getAction(), request.getMethod());
                            }else{
                                HttpTrowable httpTrowable=new HttpTrowable(response.getResponseDate().getMessage(),response.getResponseDate().getStatus());
                                callBack.doFaild(httpTrowable,request.getRequestDate(), request.getAction(), request.getMethod());
                            }
                            ToastUtil.getInstance().toastInCenter((Context) callBack,response.getResponseDate().getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogGloble.d("http",e.getMessage());
                        }
                    }
                });


    }

    /**
     * 统一过滤
     *
     * @param response
     * @param method
     * @return
     */
    public static boolean httpCalllBackPreFilter(Response response, String method) {
        if(response.getResponseDate().getStatus().equals(SUCCESS_STATUS)){
            return false;
        }
        ToastUtil.getInstance().toastInCenter(BaseApplication.getBaseApplication(),response.getResponseDate().getMessage());
        return true;
    }

    // 检测网络
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

