package com.dragonforest.app.dragonforestworld.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.adapter.MainFragmentPagerAdapter;
import com.dragonforest.app.dragonforestworld.service.MainService;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.NavigationUtil;
import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;

/**
 * 主activity
 * 包含所有项
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private Dialog upgradeDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomDensity(this, getApplication());
        setContentView(R.layout.app_activity_main);
        LogUtil.D(getClass().getName(), "onCreate()");
        initView();
        initData();
//        StatusBarUtil.getInstance().setColor(this,getResources().getColor(R.color.colorBlueForLoginTitle));
        StatusBarUtil.getInstance().setTransparent(this);
        checkUpdate();

        Log.e("MainActivity", "my ClassLoader" + MainActivity.class.getClassLoader());
        Log.e("MainActivity", "my ClassLoader parent" + MainActivity.class.getClassLoader().getParent());


        // 测试
//        NavigationUtil.navigation(this, false, "com.dragonforest.app.dragonforestworld.test.TestOAListActivity");

    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
//        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.taobao.com"));
//        NotificationUtil.getInstance().showNormalNotification(1,NotificationUtil.CHANNEL_ID_MESSAGE,this,"普通通知",android.R.drawable.star_on,R.drawable.app_icon_add,intent);
//        NotificationUtil.getInstance().showFoldNotification(2,NotificationUtil.CHANNEL_ID_MESSAGE,this,"折叠式通知",android.R.drawable.sym_def_app_icon,R.drawable.app_icon_add,intent);
//        NotificationUtil.getInstance().showHangNotification(3,NotificationUtil.CHANNEL_ID_NOTICE,this,"悬挂式通知",android.R.drawable.ic_notification_overlay,android.R.drawable.ic_notification_overlay,intent);
        // 检查更新  ----使用自己编写的库更新
//        if (UpgradeUtil.getInstance(this).checkUpgrade(2)) {
//            showUpgradeDialog();
//        }
        showUpgradeDialog();
    }

    /**
     * 今日头条屏幕适配
     *
     * @param activity
     * @param application
     */
    private void setCustomDensity(Activity activity, Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        float targetDensity = appDisplayMetrics.widthPixels / 360F;
        appDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;

        DisplayMetrics acDisplayMetrics = activity.getResources().getDisplayMetrics();
        acDisplayMetrics.density = acDisplayMetrics.scaledDensity = targetDensity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.D(getClass().getName(), "onResume()");
        initData();
    }

    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        initNavigationView(navigation);
        viewPager = findViewById(R.id.viewPager);
        initViewPager(viewPager);
    }

    private void initNavigationView(BottomNavigationView ng) {
        ng.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViewPager(ViewPager vp) {
        vp.setOnPageChangeListener(mOnPageChangeListener);
        MainFragmentPagerAdapter fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(fragmentPagerAdapter);
    }

    private void initData() {
        Intent intent = new Intent(MainActivity.this, MainService.class);
        startService(intent);
    }

    private void showUpgradeDialog() {
        if (upgradeDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("版本更新");
            builder.setMessage("检测到有新版本，是否立即更新");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    String testUrl = "http://s9.pstatp.com/package/apk/aweme/aweme_aweGW_v8.2.0_443014c.apk";
                    String testUrl = "https://imgaliyuncdn.miaopai.com/apk/201803/22/miaopai_yx_ewm.apk";
                    // 使用自己的库更新
//                    UpgradeUtil.getInstance(getApplicationContext()).startUpgrade(testUrl);
                    // 使用第三方库更新
                    DownloadManager manager = DownloadManager.getInstance(getApplicationContext());
                    UpdateConfiguration updateConfiguration=new UpdateConfiguration();
                    manager.setApkName("appupdate.apk")
                            .setApkUrl(testUrl)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAuthorities("com.dragonforest.app.dragonforestworld")
                            //可设置，可不设置
//                            .setConfiguration()
                            .download();
                    dialog.dismiss();
                }
            });
            upgradeDialog = builder.show();
        } else {
            upgradeDialog.show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MainActivity.this, MainService.class);
        stopService(intent);
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
                case R.id.navigation_community:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(3);
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
                    case 3:
                        navigation.setSelectedItemId(navigation.getMenu().getItem(3).getItemId());
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
