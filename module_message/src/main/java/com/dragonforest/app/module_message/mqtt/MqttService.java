package com.dragonforest.app.module_message.mqtt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.NotificationUtil;
import com.dragonforest.app.module_message.database.MessageDBHelper;
import com.dragonforest.app.module_message.database.MessageModel;
import com.dragonforest.app.module_message.event.ConnectStatusEvent;
import com.dragonforest.app.module_message.event.MessageStatusEvent;
import com.dragonforest.app.module_message.log.MessageLog;
import com.dragonforest.app.module_message.messageInter.MessageDetailActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/22 15:01
 */
public class MqttService extends Service {

    private String TAG = "MqttService";
    /**
     * 重新连接等待时间，默认10s
     */
    private static final int RECONNECT_DELAY = 10;

    List<MqttConnection> connections = new ArrayList<>();
    MqttBinder myBinder = new MqttBinder();
    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.E(TAG, "onCreate()");
//        LogUtil.E(TAG, "开始监听()");
//        startSubscribe();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.E(TAG, "onStartCommand()");
//        // 判断是否已经连接
//        MqttConnection currentConnection = getCurrentConnection(MqttConfig.getServerURI() + "&" + MqttConfig.getClientID());
//        if (currentConnection != null) {
//            if (currentConnection.isConnected()) {
//                EventBus.getDefault().post(new ConnectStatusEvent(1, "已连接到服务：" + currentConnection.getMqttConfig.getServerURI()()));
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

//        if (isCurrentConnect()) {
//            LogUtil.E(TAG, "当前已连接");
//            onServerConnect("已连接到服务：" + MqttConfig.getServerURI());
//        } else {
//            // 重新连接
//            LogUtil.E(TAG, "当前未连接，开始重新连接...");
//            onServerDisconnect("连接失败!" + MqttConfig.getServerURI());
//        }
        LogUtil.E(TAG, "开始监听()");
        startSubscribe();
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

    /**
     * 开始连接并监听
     * 1.判断当前连接是否已连接
     * 2.是，则使用当前连接
     * 3.否，创建连接，并连接，监听
     * 4.连接成功则完成，失败则10s后重试
     */
    private void startSubscribe() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        LogUtil.E(TAG, "startSubscribe() 开始连接并监听");
        // 1.判断当前是否连接
        MqttConnection currentConnection = getCurrentConnection(MqttConfig.getServerURI() + "&" + MqttConfig.getClientID());
        if (currentConnection != null) {
            if (currentConnection.isConnected()) {
                // 2.是，使用当前连接
                LogUtil.E(TAG, "startSubscribe() 当前是已连接状态");
                onServerConnect("已连接到服务：" + currentConnection.getServerURI());
                return;
            } else {
                connections.remove(currentConnection);
            }
        }
        // 3.否创建连接 并监听
        currentConnection = new MqttConnection(MqttConfig.getServerURI(), MqttConfig.getClientID(), getApplicationContext());
        currentConnection.setMqttCallback(mqttCallback);
        connections.add(currentConnection);
        try {
            boolean isConnected = currentConnection.connect();
            if (isConnected) {
                onServerConnect("已连接到服务：" + currentConnection.getServerURI());
                currentConnection.getClient().subscribe(MqttConfig.getTopics(), MqttConfig.getQoss());
            } else {
                connections.remove(currentConnection);
                onServerDisconnect("连接失败!" + MqttConfig.getServerURI());
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtil.E(TAG, "监听失败......" + e.getMessage());
        }
//            }
//        }).start();
    }

    /**
     * 所有连接成功统一调用此方法
     */
    public void onServerConnect(String msg) {
        EventBus.getDefault().post(new ConnectStatusEvent(1, msg));
    }

    /**
     * 所有连接失败统一调用此方法
     */
    public void onServerDisconnect(String msg) {
        EventBus.getDefault().post(new ConnectStatusEvent(-1, msg));
        // 连接失败 5s一次重新连接
        LogUtil.E(TAG, "startSubscribe() 连接失败，" + RECONNECT_DELAY + "s后开始重新连接......");
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startSubscribe();
            }
        }, RECONNECT_DELAY * 1000);
    }

    /**
     * 当前是否已连接
     *
     * @return
     */
    public boolean isCurrentConnect() {
        MqttConnection currentConnection = getCurrentConnection(MqttConfig.getServerURI() + "&" + MqttConfig.getClientID());
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

    MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
            Log.e(TAG, "connectionLost 连接断开" + cause.getMessage().toString());
            // 清理连接
            MqttConnection currentConnection = getCurrentConnection(MqttConfig.getServerURI() + "&" + MqttConfig.getClientID());
            if (currentConnection != null) {
                connections.remove(currentConnection);
                currentConnection.release();
            }
            onServerDisconnect("连接断开!" + cause.getMessage());
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
            MessageModel messageModel = MessageDBHelper.saveMessage(topic, message.toString());
            if (messageModel == null) {
                // 消息有问题,忽略掉
                Log.e(TAG, "消息格式错误，忽略" + message.toString());
                return;
            }
            // 记录日志
            MessageLog.D(getApplicationContext(), messageModel.toString());
            // 显示通知状态栏
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MessageDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("message", messageModel);
            NotificationUtil.getInstance().showNormalNotification(1, NotificationUtil.CHANNEL_ID_MESSAGE, getApplicationContext(), "收到一条消息", android.R.drawable.ic_menu_report_image, android.R.drawable.ic_menu_report_image, intent);
            // 通知新消息
            EventBus.getDefault().post(messageModel);
            // 通知消息状态变化
            EventBus.getDefault().post(new MessageStatusEvent(messageModel.getSendClientID(), messageModel.getType()));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.e(TAG, "deliveryComplete 消息完成：" + token.toString());
        }
    };

}