package com.viking.myframe.inter;

/**
 * RecyclerView 多布局类型支持接口
 * Created by 周正一 on 2017/5/3.
 */

public interface MutipleTypeSupport<T>{
    //根据当前item返回布局
    int getLayoutId(T t);
}
