package com.dragonforest.app.dragonforestworld.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import yanzhikai.textpath.PathAnimatorListener;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.TextPathView;
import yanzhikai.textpath.painter.AsyncPathPainter;
import yanzhikai.textpath.painter.SyncPathPainter;


public class SplashActivity2 extends Activity {

    private int ALL_SECONDS = 5;
    private int lastSeconds = ALL_SECONDS;
    private SyncTextPathView textPathView1;
    private SyncTextPathView textPathView2;

    private TextView tv_seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_splash2);
        initStatusBar();
        initView();


        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        long appLastOpenTime = LoginUtil.getAppLastOpenTime(SplashActivity2.this);
                        long currentOpenTime = System.currentTimeMillis();
                        LoginUtil.recordAppOpenTime(SplashActivity2.this);
                        if (appLastOpenTime != 0
                                && ((currentOpenTime - appLastOpenTime) < (1000 * 60))) {
                            // 1分钟以内 闪现
                            forwardActivity();
                        }
//                        else if (appLastOpenTime != 0
//                                && ((currentOpenTime - appLastOpenTime) < (1000 * 60 * 60))) {
//                            // 1小时以内 等待1s
//                            handler.sendEmptyMessageDelayed(1, 1000);
//                        }
                        else {
                            // 等待动画完成
                            startDrawText();
                        }
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        finish();
                    }
                });


    }


    private void initStatusBar() {
        StatusBarUtil.getInstance().setTransparent(this);
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

        textPathView1 = findViewById(R.id.stpv_dg);
        textPathView2 = findViewById(R.id.stpv_fight);
    }

    private void startDrawText() {
        // 设置显示的画笔的画笔
//        textPathView1.setPathPainter(new SyncPathPainter() {
//            @Override
//            public void onStartAnimation() {
//
//            }
//
//            @Override
//            public void onDrawPaintPath(float x, float y, Path paintPath) {
//                paintPath.addCircle(x,y,6, Path.Direction.CCW);
//            }
//        });
        textPathView1.setFillColor(true);
        textPathView1.getDrawPaint().setColor(Color.WHITE);
        textPathView2.setFillColor(true);
        textPathView2.getDrawPaint().setColor(Color.WHITE);
//        textPathView1.setDuration(3000);
        textPathView1.startAnimation(0, 1);
        textPathView1.setAnimatorListener(new PathAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e("TAG", "textPathView1动画完成");
//                textPathView2.setDuration(5000);
                textPathView2.startAnimation(0, 1);
                textPathView2.setAnimatorListener(new PathAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Log.e("TAG", "textPathView2动画完成");
                        countTime();
                    }
                });
            }
        });

    }

    private void countTime() {
        long appLastOpenTime = LoginUtil.getAppLastOpenTime(this);
        long currentOpenTime = System.currentTimeMillis();
        LoginUtil.recordAppOpenTime(this);

        // 测试 等待5s
        handler.sendEmptyMessage(0);
        tv_seconds.setVisibility(View.VISIBLE);
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
                        tv_seconds.setText("即将开启，剩余  " + lastSeconds + " s");
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
            startActivity(new Intent(SplashActivity2.this, WelcomeActivity.class));
            finish();
        } else {
            if (LoginUtil.getCacheUserInfo(this) != null) {
                // 已经登录过，进入Main
                startActivity(new Intent(SplashActivity2.this, MainActivity.class));
                finish();
            } else {
                // 没有登录过, 进入登录
                NavigationUtil.navigation(this, true, "com.dragonforest.app.module_login.LoginActivity");
            }
        }
    }
}
