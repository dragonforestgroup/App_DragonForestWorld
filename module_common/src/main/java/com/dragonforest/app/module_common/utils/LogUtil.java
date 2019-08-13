package com.dragonforest.app.module_common.utils;

import android.util.Log;

/**
 * @author 韩龙林
 * @date 2019/8/2 16:28
 */
public class LogUtil {
    private static boolean isDebug = true;

    public static void E(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void D(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void I(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }
}
