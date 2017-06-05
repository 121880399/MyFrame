package com.viking.myframe.activity.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.viking.myframe.R;
import com.viking.myframe.utils.ToastUtil;

import java.lang.reflect.Method;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/6/5
 */

public class ToolbarTestActivity extends AppCompatActivity{
    //1.继承AppCompatActivity
    //2.在styles.xml中将AppTheme改为Theme.AppCompat.Light.NoActionBar
    //3.在AndroidManifest中设置ToolbarTestActivity为android:theme="@style/AppTheme"
    //4.在布局中添加 Toolbar
    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_test);
        mToolBar= (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle("ToolBar");
        mToolBar.setTitleTextColor(Color.WHITE);
       // mToolBar.setSubtitle("test");
        mToolBar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(mToolBar);
        //监听事件一定要放在setSupportActionBar后面，否则无效
        // 原因是因为在setSupportActionBar的时候系统重新设置了点击事件，把之前设置的点击事件顶掉了。
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //用actionBar设置返回图标
//        ActionBar supportActionBar = getSupportActionBar();
//        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 设置ToolBar上面的menu
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider shareActionProvider= (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareActionProvider.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 处理menu被选择事件
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                ToastUtil.getInstance().toastInCenter(this,"设置");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在这里可以通过反射设置Menu中的item可以设置android:icon属性
     * */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
