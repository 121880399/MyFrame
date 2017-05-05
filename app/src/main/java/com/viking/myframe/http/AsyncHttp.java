package com.viking.myframe.http;


import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 单例类
 * Created by 周正一 on 2017/5/4.
 */

public class AsyncHttp {
    private static AsyncHttp mInstance;

    //初始化操作，设置超时时间
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .build();

    private AsyncHttp() {
        //初始化OkHttpClient
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 得到一个AsyncHttp对象
     */
    public static AsyncHttp getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new AsyncHttp();
                }
            }
        }
        return mInstance;
    }
}

