package com.viking.myframe.http;

import android.util.ArrayMap;

/**
 * 请求类，封装了Http请求头,请求数据
 * Created by 周正一 on 2017/5/5.
 */
public class Request {
    private ArrayMap requestHeader;//请求头
    private ArrayMap requestDate;//请求数据

    /**
     * Instantiates a new Request.
     */
    public Request(){
        requestHeader =new ArrayMap();
        requestDate =new ArrayMap();
    }

    /**
     * 设置Action
     *
     * @param action the action
     * @return the request
     */
    public Request setAction(String action){
        requestHeader.put("action",action);
        return this;
    }

    /**
     * 设置method
     *
     * @param method the method
     * @return the request
     */
    public Request setMethod(String method){
        requestHeader.put("method",method);
        return this;
    }

    /**
     * 提供添加头方法
     *
     * @param key   the key
     * @param value the value
     * @return the request
     */
    public Request addHeader(String key,Object value){
        requestHeader.put(key,value);
        return this;
    }

    /**
     * 设置是否加密
     *
     * @param isEncrpy the is encrpy
     * @return the request
     */
    public Request setEncrpy(boolean isEncrpy){
        if (isEncrpy) {
           return  addHeader("encrpy", 1);
        } else {
           return addHeader("encrpy", 0);
        }
    }

    /**
     * 是否进行ZIP压缩
     *
     * @param isZip the is zip
     * @return the request
     */
    public Request setZip(boolean isZip){
        if (isZip) {
            return addHeader("zip", 1);
        } else {
            return addHeader("zip", 0);
        }
    }

    /**
     * 添加请求数据
     *
     * @param key   the key
     * @param value the value
     * @return the request
     */
    public Request putParams(String key,Object value){
        requestDate.put(key,value);
        return this;
    }


    /**
     * Gets request date.
     *
     * @return the request date
     */
    public ArrayMap getRequestDate() {
        return requestDate;
    }

    /**
     * 是否采用zip压缩
     *
     * @return the boolean
     */
    public boolean isZip() {
        int zip= requestHeader.get("zip")==null?0: (int) requestHeader.get("zip");
        return zip== 1;
    }


    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return (String) requestHeader.get("action");
    }


    /**
     * Gets method.
     *
     * @return the method
     */
    public String getMethod() {
        return (String) requestHeader.get("method");
    }
}
