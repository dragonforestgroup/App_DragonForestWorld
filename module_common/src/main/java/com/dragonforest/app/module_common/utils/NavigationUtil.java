package com.dragonforest.app.module_common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 跳转工具类
 *
 * @author 韩龙林
 * @date 2019/8/15 10:14
 */
public class NavigationUtil {
    public static void navigation(Context context, boolean isFinish, String packageName) {
        try {
            Class<?> aClass = Class.forName(packageName);
            Intent intent = new Intent();
            intent.setClass(context, aClass);
            context.startActivity(intent);
            if (isFinish) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(packageName + "模块缺失", context);
        }
    }
}
