package com.dragonforest.app.module_message.mqtt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.NotificationUtil;
import com.dragonforest.app.module_message.MessageDetailActivity;
import com.dragonforest.app.module_message.database.MessageModel;
import com.dragonforest.app.module_message.event.ConnectStatusEvent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/22 15:01
 */
public class MqttService extends Service{

    private String TAG = "MqttService";

    String serverURI = "tcp://172.16.17.248:1884";
    String clientID = "com.dragonforest.app.module_message";

    List<MqttConnection> connections = new ArrayList<>();
    MqttBinder myBinder = new MqttBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.E(TAG, "onCreate()");
        LogUtil.E(TAG, "开始监听()");
        startSubscribe();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.E(TAG, "onStartCommand()");
//        // 判断是否已经连接
//        MqttConnection currentConnection = getCurrentConnection(serverURI + "&" + clientID);
//        if (currentConnection != null) {
//            if (currentConnection.isConnected()) {
//                EventBus.getDefault().post(new ConnectStatusEvent(1, "已连接到服务：" + currentConnection.getServerURI()));
//            } else {
////                EventBus.getDefault().post(new ConnectStatusEvent(-1, "连接已断开，请重新连接!"));
//                // 重新连接
//                LogUtil.E(TAG, "onStartCommand()连接已断开，尝试重新连接");
//                startSubscribe();
//            }
//        }else{
//            LogUtil.E(TAG, "onStartCommand()连接为空，尝试重新连接");
//            startSubscribe();
//        }

        if (isCurrentConnect()) {
            LogUtil.E(TAG, "当前已连接");
            EventBus.getDefault().post(new ConnectStatusEvent(1, "已连接到服务：" + serverURI));
        } else {
            // 重新连接
            LogUtil.E(TAG, "当前未连接，开始重新连接...");
            startSubscribe();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.E(TAG, "onBind()");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.E(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    public class MqttBinder extends Binder {
        public List<MqttConnection> getAllConnect() {
            return connections;
        }

        public void disConnect() {
            Iterator<MqttConnection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                MqttConnection next = iterator.next();
                next.disconnect();
                connections.remove(next);
            }
        }

        public void start() {
            startSubscribe();
        }
    }

    private void startSubscribe() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        MqttConnection currentConnection = getCurrentConnection(serverURI + "&" + clientID);
        if (currentConnection == null) {
            currentConnection = new MqttConnection(serverURI, clientID, getApplicationContext());
            currentConnection.setMqttCallback(mqttCallback);
            connections.add(currentConnection);
        }
        try {
            boolean isConnected = currentConnection.connect();
            if (isConnected) {
                EventBus.getDefault().post(new ConnectStatusEvent(1, "已连接到服务：" + currentConnection.getServerURI()));
                currentConnection.getClient().subscribe("hanlonglin", 1);
            } else {
                connections.remove(currentConnection);
                EventBus.getDefault().post(new ConnectStatusEvent(-1, "连接失败!" + serverURI));
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtil.E(TAG, "监听失败......" + e.getMessage());
        }
//            }
//        }).start();
    }

    /**
     * 当前是否已连接
     *
     * @return
     */
    public boolean isCurrentConnect() {
        MqttConnection currentConnection = getCurrentConnection(serverURI + "&" + clientID);
        if (currentConnection != null && currentConnection.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private MqttConnection getCurrentConnection(String clientHandle) {
        for (MqttConnection connection : connections) {
            if (connection.getClientHandle().equals(clientHandle)) {
                return connection;
            }
        }
        return null;
    }

    private MessageModel saveToDatabase(String topic, MqttMessage message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message.toString());
        messageModel.setTopic(topic);
        messageModel.setDate(new Date());
        messageModel.save();
        return messageModel;
    }

    MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
            Log.e(TAG, "connectionLost 连接断开" + cause.getMessage());
            EventBus.getDefault().post(new ConnectStatusEvent(-1, "连接断开!" + cause.getMessage()));
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.e(TAG, "messageArrived 收到消息：" + message.toString() + ",payload:" + new String(message.getPayload(), "ISO-8859-1"));
            /*
                接收到消息后操作：
                1.保存到数据库
                2.显示通知
                3.通知EventBus当前监听者
             */
            MessageModel messageModel = saveToDatabase(topic, message);
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MessageDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("message", messageModel);
            NotificationUtil.getInstance().showNormalNotification(1, NotificationUtil.CHANNEL_ID_MESSAGE, getApplicationContext(), "收到一条消息", android.R.drawable.ic_menu_report_image, android.R.drawable.ic_menu_report_image, intent);
            EventBus.getDefault().post(messageModel);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.e(TAG, "deliveryComplete 消息完成：" + token.toString());
        }
    };

}