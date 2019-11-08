package com.dragonforest.app.module_message.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * @author DragonForest
 * @date 2019/10/29 14:56
 */
public class MqttConnection implements IConnection {
    private String TAG = "MqttConnection";
    private MqttClient client;
    private String serverURI;
    private String clientID;
    private Context context;
    private MqttConnectOptions connOpts;
    private MqttCallback mqttCallback;
    private MemoryPersistence persistence = new MemoryPersistence();
    /**
     * 连接的唯一标识
     */
    private String clientHandle;

    public MqttConnection(String serverURI, String clientID, Context context, MqttCallback mqttCallback) {
        this.serverURI = serverURI;
        this.clientID = clientID;
        this.context = context;
        this.mqttCallback = mqttCallback;
        this.clientHandle = serverURI + "&" + clientID;
        init();
    }

    public MqttConnection(String serverURI, String clientID, Context context) {
        this.serverURI = serverURI;
        this.clientID = clientID;
        this.context = context;
        this.clientHandle = serverURI + "&" + clientID;
        this.mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "connectionLost 连接断开" + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e(TAG, "messageArrived 收到消息：" + message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "deliveryComplete 消息完成：" + token.toString());
            }
        };
        init();
    }

    public void init() {
        try {
            client = new MqttClient(serverURI, clientID, persistence);
            connOpts = new MqttConnectOptions();
            // 设置心跳时间间隔
            // 注意：当客户端断网重连后，如果此值过大则服务端要过重连时间周期才能重新连上，所以不宜过大
            connOpts.setKeepAliveInterval(10);
            // 设置保持之前的回话，再次连接可以获取之前的消息
            connOpts.setCleanSession(false);  // 设置为false 可以收到离线后推送的消息
            // 设置遗嘱消息，在客户端异常断开之后会向相应topic发送消息
            connOpts.setWill("connection", "close".getBytes(), 1, true);
            // 设置回调
            client.setCallback(mqttCallback);
        } catch (MqttException e) {
            Log.e(TAG, "client 初始化失败：" + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public boolean connect() {
        try {
            if (!client.isConnected()) {
                client.connect(connOpts);
            } else {
                Log.e(TAG, "client是已连接状态");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "client连接失败：" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean disconnect() {
        try {
            client.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "client断开连接失败：" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isConnected() {
        if (client != null)
            return client.isConnected();
        return false;
    }

    public void release() {
        try {
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Set


    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MqttConnectOptions getConnOpts() {
        return connOpts;
    }

    public void setConnOpts(MqttConnectOptions connOpts) {
        this.connOpts = connOpts;
    }

    public MqttCallback getMqttCallback() {
        return mqttCallback;
    }

    public void setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        this.client.setCallback(mqttCallback);
    }

    public String getClientHandle() {
        return clientHandle;
    }

    public void setClientHandle(String clientHandle) {
        this.clientHandle = clientHandle;
    }
}
