package com.dragonforest.app.module_message.mqtt;

import android.content.Context;
import android.content.Intent;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

/**
 * @author DragonForest
 * @date 2019/11/6 16:11
 */
public class MqttManager {

    public static MqttManager getInstance() {
        return MqttManagerHolder.mqttManager;
    }

    private static class MqttManagerHolder{
        private static final MqttManager mqttManager=new MqttManager();
    }

    public void init(String serverURI,String thisClientID,String[] topics,int[] qoss,Context context){
        MqttConfig.setServerURI(serverURI);
        MqttConfig.setClientID(thisClientID);
        MqttConfig.setTopics(topics);
        MqttConfig.setQoss(qoss);

        // 初始化数据库
        LitePal.initialize(context);
        changeUser(thisClientID);
        // 开启监听service
        startMqttService(context);
    }

    private static void changeUser(String clientID) {
        // 根据用户选择使用数据库
        LitePalDB currentDB = LitePalDB.fromDefault(clientID + "db");
        LitePal.use(currentDB);
    }

    private void startMqttService(Context context){
        Intent intent = new Intent(context, MqttService.class);
        context.startService(intent);
    }
}
