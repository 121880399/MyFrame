package com.viking.myframe.models;

import android.support.v7.widget.RecyclerView;

/**
 * Created by adm on 2017/5/1.
 */

public class RecyclerModel<T> {
    private Integer layoutId;//布局ID
    private Integer variableId;//变量ID
    private T data;//数据

    public RecyclerModel(){

    }

    public RecyclerModel(Integer layoutId, Integer variableId, T data) {
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.data = data;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public Integer getVariableId() {
        return variableId;
    }

    public void setVariableId(Integer variableId) {
        this.variableId = variableId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
