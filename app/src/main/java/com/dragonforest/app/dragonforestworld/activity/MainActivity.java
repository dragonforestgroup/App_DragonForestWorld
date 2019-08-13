package com.dragonforest.app.dragonforestworld.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.adapter.MyFragmentPagerAdapter;
import com.dragonforest.app.module_common.utils.LogUtil;

/**
 * 主activity
 * 包含所有项
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(mOnPageChangeListener);
    }

    private void initData() {
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
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
}
