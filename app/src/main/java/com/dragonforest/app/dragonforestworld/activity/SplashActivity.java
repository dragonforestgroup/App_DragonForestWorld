package com.dragonforest.app.dragonforestworld.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.welcome.WelcomeActivity;
import com.dragonforest.app.module_common.utils.LoginUtil;
import com.dragonforest.app.module_common.utils.NavigationUtil;
import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;


public class SplashActivity extends Activity {

    private int ALL_SECONDS = 5;
    private int lastSeconds = ALL_SECONDS;

    private TextView tv_seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_splash);
        initStatusBar();
        initView();
        StatusBarUtil.getInstance().setTransparent(this);

        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        countTime();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        finish();
                    }
                });


    }

    private void countTime() {
        long appLastOpenTime = LoginUtil.getAppLastOpenTime(this);
        long currentOpenTime = System.currentTimeMillis();
        LoginUtil.recordAppOpenTime(this);
//        if (appLastOpenTime != 0
////                && ((currentOpenTime - appLastOpenTime) < (1000 * 60))) {
////            // 1分钟以内 闪现
////            handler.sendEmptyMessage(1);
////        } else if (appLastOpenTime != 0
////                && ((currentOpenTime - appLastOpenTime) < (1000 * 60 * 60))) {
////            // 1小时以内 等待1s
////            handler.sendEmptyMessageDelayed(1, 1000);
////        } else {
////            // 1小时以上 等待5s
////            handler.sendEmptyMessage(0);
////        }

        // 测试 等待5s
        handler.sendEmptyMessage(0);
    }


    private void initStatusBar() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null)
//            actionBar.hide();
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
                        tv_seconds.setText("跳 过 " + lastSeconds + " s");
                    } else {
                        forwardActivity();
                    }
                    break;
                case 1:
                    forwardActivity();
                    break;
            }
        }
    };

    /**
     * 页面跳转规则
     * 1.是否首次打开app
     * 是：进入引导页
     * 否：进入2
     * 2.是否已经登录：
     * 是：进入MainActivity
     * 否：进入登录
     */
    private void forwardActivity() {
        if (LoginUtil.isFirstOpenApp(this)) {
            // 首次打开app， 打开引导页面
            LoginUtil.recordAppOpened(this);
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        } else {
            if (LoginUtil.getCacheUserInfo(this) != null) {
                // 已经登录过，进入Main
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                // 没有登录过, 进入登录
                NavigationUtil.navigation(this, true, "com.dragonforest.app.module_login.LoginActivity");
            }
        }
    }
}
