package com.dragonforest.app.module_common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dragonforest.app.module_common.beans.UserInfo;
import com.google.gson.Gson;

/**
 * 登录工具
 *
 * @author 韩龙林
 * @date 2019/8/2 10:39
 */
public class LoginUtil {

    /**
     * 用户信息的sp文件
     */
    private static final String FILE_USER = "dragonforest_user";
    /**
     * 用户信息key
     */
    private static final String KEY_USER = "userInfo";
    /**
     * 是否首次打开app
     */
    private static final String KEY_IS_FIRST = "isFirstOpen";
    /**
     * app打开时间
     */
    private static final String KEY_APP_OPEN_TIME = "openTime";

    /**
     * 获取缓存的用户信息
     *
     * @return
     */
    public static UserInfo getCacheUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        String userInfoStr = sp.getString(KEY_USER, "");
        if (userInfoStr.equals(""))
            return null;
        try {
            UserInfo userInfo = new Gson().fromJson(userInfoStr, UserInfo.class);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    public static void saveUserInfo(Context context, UserInfo userInfo) {
        String userInfoStr = "";
        try {
            userInfoStr = new Gson().toJson(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_USER, userInfoStr);
        edit.commit();
    }

    public static void clearUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
    }

    /**
     * 是否首次打开
     *
     * @return
     */
    public static boolean isFirstOpenApp(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean(KEY_IS_FIRST, true);
        return isFirst;
    }

    /**
     * 记录app已经打开过
     */
    public static void recordAppOpened(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(KEY_IS_FIRST, false);
        edit.commit();
    }

    /**
     * 记录app的打开时间
     *
     * @param context
     */
    public static void recordAppOpenTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(KEY_APP_OPEN_TIME, System.currentTimeMillis());
        edit.commit();
    }

    /**
     * 获取app上一次打开时间
     *
     * @return
     */
    public static long getAppLastOpenTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        long lastOpenTime = sp.getLong(KEY_APP_OPEN_TIME, 0);
        return lastOpenTime;
    }
}
