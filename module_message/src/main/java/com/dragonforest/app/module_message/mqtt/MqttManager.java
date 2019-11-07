package com.dragonforest.app.module_message.mqtt;

import android.content.Context;
import android.content.Intent;

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
        startMqttService(context);
    }

    private void startMqttService(Context context){
        Intent intent = new Intent(context, MqttService.class);
        context.startService(intent);
    }
}
