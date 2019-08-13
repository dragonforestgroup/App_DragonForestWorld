package com.dragonforest.app.dragonforestworld.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.module_common.utils.LoginUtil;

public class SplashActivity extends AppCompatActivity {

    private int ALL_SECONDS = 5;
    private int lastSeconds = ALL_SECONDS;

    private TextView tv_seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initStatusBar();
        initView();
        long appLastOpenTime = LoginUtil.getAppLastOpenTime(this);
        long currentOpenTime = System.currentTimeMillis();
        if (appLastOpenTime != 0
//                && ((appLastOpenTime - System.currentTimeMillis()) < (1000 * 60 * 60 * 6))) {
                && ((currentOpenTime - appLastOpenTime) < (1000))) {
            // 当距离上一次打开app相隔6个小时以内时，不显示splash
            LoginUtil.recordAppOpenTime(this);
            forwardActivity();
        } else {
            LoginUtil.recordAppOpenTime(this);
            handler.sendEmptyMessage(0);
        }
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
        tv_seconds = findViewById(R.id.tv_seconds);
        tv_seconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(0);
                forwardActivity();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (lastSeconds > 0) {
                        lastSeconds--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                        tv_seconds.setText("跳过" + lastSeconds + "s");
                    } else {
                        forwardActivity();
                    }
                    break;
            }
        }
    };

    /**
     * 页面跳转规则
     * 1.是否首次打开app
     *      是：进入引导页
     *      否：进入2
     * 2.是否已经登录：
     *      是：进入MainActivity
     *      否：进入登录
     */
    private void forwardActivity() {
        if (LoginUtil.isFirstOpenApp(this)) {
            // 首次打开app， 打开引导页面
            LoginUtil.recordAppOpened(this);
            startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
            finish();
        } else {
            if (LoginUtil.getCacheUserInfo(this) != null) {
                // 已经登录过，进入Main
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                // 没有登录过, 进入登录
                try {
                    Class<?> aClass = Class.forName("com.dragonforest.app.module_login.LoginActivity");
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, aClass);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "登录模块缺失", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
