package com.dragonforest.app.dragonforestworld.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.adapter.MainFragmentPagerAdapter;
import com.dragonforest.app.dragonforestworld.test.MyFactory;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;

/**
 * 主activity
 * 包含所有项
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        testReplaceText();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.D(getClass().getName(),"onCreate()");
        initView();
    }

    private void testReplaceText() {
        MyFactory myFactory=new MyFactory();
        myFactory.setmDelegate(getDelegate());
        LayoutInflater.from(this).setFactory2(myFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.D(getClass().getName(),"onResume()");
        initData();
    }

    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewPager);
        initViewPager(viewPager);
    }

    private void initViewPager(ViewPager vp){
        vp.setOnPageChangeListener(mOnPageChangeListener);
        MainFragmentPagerAdapter fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(fragmentPagerAdapter);
    }

    private void initData() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            LogUtil.E("MainActvity", "onPageScrolled()" + i + "," + i1);
        }

        @Override
        public void onPageSelected(int i) {
            LogUtil.E("MainActvity", "onPageSelected()" + i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            LogUtil.E("MainActvity", "onPageScrollStateChanged()" + i);

            if (i == 0) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());
                        break;
                    case 1:
                        navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());
                        break;
                    case 2:
                        navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());
                        break;
                }
            }
        }
    };

    // 处理返回键
    long lastClickBackTime = 0;

    @Override
    public void onBackPressed() {
        long currentClickBackTime = System.currentTimeMillis();
        if ((currentClickBackTime - lastClickBackTime) > 2000) {
            lastClickBackTime = currentClickBackTime;
            ToastUtils.show("再按一次退出", this);
        } else {
            super.onBackPressed();
        }
    }
}
