package com.dragonforest.app.dragonforestworld.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.welcome.adapter.WelcomeFragmentAdater;
import com.dragonforest.app.dragonforestworld.welcome.fragment.WelcomeFragment;
import com.dragonforest.app.module_login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private TextView tv_start;
    private TextView tv_finish;
    private ImageView img_left;
    private ImageView img_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_welcome);
        initStatusBar();
        initView();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }
    private void initView() {
        viewPager=findViewById(R.id.viewPager_welcome);
        initViewPager(viewPager);
        tv_start=findViewById(R.id.tv_start);
        tv_finish=findViewById(R.id.tv_finish);
        img_left=findViewById(R.id.img_left);
        img_right=findViewById(R.id.img_right);

        tv_start.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        img_left.setOnClickListener(this);
        img_right.setOnClickListener(this);
    }

    private void initViewPager(ViewPager vp) {
        List<Fragment> list=new ArrayList<>();
        list.add(WelcomeFragment.newInstance(R.drawable.app_welcome_bg1,R.drawable.app_welcome_logo1,"This is a megical world","here you can find what you want to konw,and find the direction."));
        list.add(WelcomeFragment.newInstance(R.drawable.app_welcome_bg2,R.drawable.app_welcome_logo2,"Who am I?","I am DragonForest,like all of us,I have a dream,that is become powerful"));
        list.add(WelcomeFragment.newInstance(R.drawable.app_welcome_bg3,R.drawable.app_welcome_logo3,"How to improve ourself?","I collect the best article whitch can make everyone understand the konwledge."));
        WelcomeFragmentAdater adater=new WelcomeFragmentAdater(getSupportFragmentManager(),list);
        vp.setAdapter(adater);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    switch (viewPager.getCurrentItem()) {
                        case 0:
                            tv_start.setVisibility(View.VISIBLE);
                            tv_finish.setVisibility(View.GONE);
                            img_left.setVisibility(View.GONE);
                            img_right.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            tv_start.setVisibility(View.GONE);
                            tv_finish.setVisibility(View.GONE);
                            img_left.setVisibility(View.VISIBLE);
                            img_right.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            tv_start.setVisibility(View.GONE);
                            tv_finish.setVisibility(View.VISIBLE);
                            img_left.setVisibility(View.VISIBLE);
                            img_right.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start:
                break;
            case R.id.tv_finish:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.img_left:
                scrollLast();
                break;
            case R.id.img_right:
                scrollNext();
                break;
        }
    }

    private void scrollLast() {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem-1);
    }

    private void scrollNext() {
        int currentItem=viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem+1);
    }
}
