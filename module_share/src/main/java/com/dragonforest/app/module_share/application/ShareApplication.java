package com.dragonforest.app.module_share.application;

import android.app.Application;
import android.content.Context;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;

/**
 * @author 韩龙林
 * @date 2019/8/15 17:00
 */
public class ShareApplication extends Application {

    private static ShareApplication instance;

    public static ShareApplication getInstance() {
        if(instance==null){
            instance=new ShareApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initJShare(this);
    }

    public void initJShare(Context context) {
        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
                .setWechat("wxc40e16f3ba6ebabc", "dcad950cd0633a27e353477c4ec12e7a")
                .setQQ("1106011004", "YIbPvONmBQBZUGaN")
                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
                .setFacebook("1847959632183996", "JShareDemo")
                .setTwitter("fCm4SUcgYI1wUACGxB2erX5pL", "NAhzwYCgm15FBILWqXYDKxpryiuDlEQWZ5YERnO1D89VBtZO6q")
                .setJchatPro("1847959632183996");
        /**
         * since 1.5.0，1.5.0版本后增加API，支持在代码中设置第三方appKey等信息，当PlatformConfig为null时，或者使用JShareInterface.init(Context)时需要配置assets目录下的JGShareSDK.xml
         **/
        //*
        JShareInterface.init(context, platformConfig);
    }
}
