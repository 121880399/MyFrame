package com.viking.myframe.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by 周正一 on 2017/5/8.
 */

public class BaseApplication  extends Application {
    //App是否退出
    public static boolean isExit=false;
    private static BaseApplication mBaseApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication=this;
    }

    /**
     * 返回Application
     * */
    public static BaseApplication getBaseApplication(){
        return mBaseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化MultiDex
        MultiDex.install(this);
    }

}
