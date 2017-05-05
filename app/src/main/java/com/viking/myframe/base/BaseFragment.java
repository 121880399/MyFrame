package com.viking.myframe.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSeekBar;

/**
 * Created by adm on 2017/4/30.
 */

public  abstract  class BaseFragment extends Fragment {

    protected View mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getLayoutId()!=0){
            mRootView=inflater.inflate(getLayoutId(),container,false);
            initView(mRootView);
            setUserVisibleHint(true);
            initData();
            setListener();
        }
        return mRootView;
    }

    /**
     * 初始化ActionBar
     * */
    public void initActionBar(Activity activity){}

    /**
     * 初始化界面
     * */
    protected abstract void initView(View rootView);

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    /**
     * 得到布局Id
     * **/
    protected abstract int getLayoutId();

    /**
     * 设置监听器
     * */
    protected abstract void setListener();

    /**
     * 返回操作
     * */
    public void onBackPressed(){}

    /**
     * 通过id得到控件
     * */
    public <T extends View> T obtainView(int resId){
        return  (T)mRootView.findViewById(resId);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当Fragment显示的时候会调用该方法
     * */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }
        super.onHiddenChanged(hidden);
    }

    /**
     * Fragment UI是否对用户可见，可以在此实现懒加载
     * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
