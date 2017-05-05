package com.viking.myframe.http;

import android.util.ArrayMap;

/**
 * 请求类，封装了Http请求头
 * Created by 周正一 on 2017/5/5.
 */

public class Request {
    private ArrayMap mRequestHeader;

    public Request(){
        mRequestHeader=new ArrayMap();
    }

    /**
     * 设置Action
     * */
    public Request setAction(String action){
        mRequestHeader.put("action",action);
        return this;
    }

    /**
     * 设置method
     * */
    public Request setMethod(String method){
        mRequestHeader.put("method",method);
        return this;
    }

    /**
     * 提供添加头方法
     * */
    public Request addHeader(String key,Object value){
        mRequestHeader.put(key,value);
        return this;
    }

    /**
     * 设置是否加密
     * */
    public Request setEncrpy(boolean isEncrpy){
        if (isEncrpy) {
           return  addHeader("encrpy", 1);
        } else {
           return addHeader("encrpy", 0);
        }
    }

    /**
     * 是否进行ZIP压缩
     * */
    public Request setZip(boolean isZip){
        if (isZip) {
            return addHeader("zip", 1);
        } else {
            return addHeader("zip", 0);
        }
    }
}
