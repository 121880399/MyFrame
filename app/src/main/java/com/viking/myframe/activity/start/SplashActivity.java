package com.viking.myframe.activity.start;

import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.viking.myframe.R;
import com.viking.myframe.activity.MainActivity;
import com.viking.myframe.base.BaseActivity;

/**
 * Created by adm on 2017/4/30.
 */

public class SplashActivity extends BaseActivity {
    private RelativeLayout mSplashRL;

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setBeforeLayout() {

    }

    @Override
    protected void initView() {
        mSplashRL=obtainView(R.id.rl_splash);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startAnimation();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setListener() {

    }

    private void startAnimation(){
        AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
        animation.setFillAfter(true);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                invoke(SplashActivity.this,MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mSplashRL.startAnimation(animation);
    }
}
