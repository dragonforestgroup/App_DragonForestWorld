package com.dragonforest.app.dragonforestworld.application;

import com.dragonforest.app.module_common.application.CommonApplication;

/**
 * @author 韩龙林
 * @date 2019/8/15 16:59
 */
public class AppApplication extends CommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initShare();
    }

    private void initShare() {
        try {
            com.dragonforest.app.module_share.application.ShareApplication.getInstance().initJShare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
