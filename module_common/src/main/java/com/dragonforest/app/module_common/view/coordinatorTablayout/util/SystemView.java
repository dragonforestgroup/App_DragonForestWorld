package com.dragonforest.app.module_common.view.coordinatorTablayout.util;


import android.content.Context;

/**
 * @author 韩龙林
 * @date 2019/8/14 12:36
 */

public class SystemView {
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}