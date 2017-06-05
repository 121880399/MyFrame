package com.viking.myframe.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.viking.myframe.R;
import com.viking.myframe.activity.test.ToolbarTestActivity;
import com.viking.myframe.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button toolbarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setBeforeLayout() {

    }

    @Override
    protected void initView() {
        toolbarBtn= (Button) findViewById(R.id.btn_toolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener() {
        toolbarBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //连续按两次back键退出APP
        dealAppBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toolbar:
                invoke(this, ToolbarTestActivity.class);
                break;
        }
    }
}
