package com.viking.myframe.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.viking.myframe.R;
import com.viking.myframe.activity.APPActivityManager;
import com.viking.myframe.views.ActionBarView;

import static com.viking.myframe.base.BaseApplication.isExit;

/**
 * 所有Activity的基类
 * Created by 周正一 on 2017/4/30.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected Context mContext;
    protected ActionBarView topbarView;
    /**
     * 双击退出的消息处理
     */
    public Handler mHandlerExit = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityCommon();
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
     * 设置Activity的通用属性
     * */
    private void setActivityCommon(){
        //每启动一个Activity都加入到Activity管理列表中
        APPActivityManager.getInstance().addActivity(this);
        //设置平面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 处理返回事件，如果在首页 连续按两次back键退出APP
     */
    public void dealAppBack() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息,间隔时间3秒钟，如果3秒内再次按back键，isExit就还为true就会退出APP
            mHandlerExit.sendEmptyMessageDelayed(
                    0, 3000);
        } else {
            finish();
            APPActivityManager.getInstance().finishActivities();
            System.exit(0);
        }
    }


    /**
     * Gets topbar.
     *
     * @return the topbar
     */
    public ActionBarView getTopbar() {
        if (topbarView == null) {
            View view = findViewById(R.id.topbar_view);
            if (view != null) {
                topbarView = new ActionBarView(view);
            }
        }
        return topbarView;
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
