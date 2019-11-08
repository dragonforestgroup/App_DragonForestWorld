package com.dragonforest.app.module_message.application;

import android.app.Application;

import com.dragonforest.app.module_message.mqtt.MqttManager;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

/**
 * @author DragonForest
 * @date 2019/10/29 16:24
 */
public class MyApplication extends Application {
    public static String[] clientIds = {"hanlonglin", "hexin", "zhangjinpan"};
    public static String currentClientId;

    //    public static String clientID="hexin";
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化客户端消息监听
//        String[] topics = {"hanlonglin","dragon"};
//        int[] qoss = {2,2};

    }

    public static void changeUser(String clientID) {
        // 根据用户选择使用数据库
        LitePalDB currentDB = LitePalDB.fromDefault(clientID + "db");
        LitePal.use(currentDB);
        currentClientId = clientID;
    }
}
