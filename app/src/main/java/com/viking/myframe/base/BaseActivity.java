package com.viking.myframe.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * Created by adm on 2017/4/30.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected ActionBar mActionBar;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBeforeLayout();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext=this;
        if(getLayoutId()!=0){
            setContentView(getLayoutId());
            initView();
            initData();
            setActionBar();
            setListener();
        }
    }

    /**
     * 设置ActionBar
     * */
    protected abstract void setActionBar();

    /**
     * 布局加载器的操作
     * */
    protected abstract  void setBeforeLayout();

    /**
     * 初始化View
     * */
    protected abstract void initView();

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    /**
     * 得到布局ID
     * */
    protected abstract int getLayoutId();

    /**
     * 设置监听
     * */
    protected abstract void setListener();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取控件 Id (避免类型转换的繁琐，抽取 findViewById())
     * */
    public <T extends View> T obtainView(int resId){
        return (T) findViewById(resId);
    }

    /**
     * 启动Activity的方法，无参数的时候使用
     * */
    public void invoke(Context context,Class clz){
        startActivity(new Intent(context,clz));
    }

}
