package com.dragonforest.app.module_message.mqtt;

/**
 * @author DragonForest
 * @date 2019/11/6 17:04
 */
public class MqttConfig {
    private static String serverURI = "tcp://172.16.17.248:1884";
    private static String clientID = "com.dragonforest.app.module_message";
    private static String[] topics={"hanlonglin"};
    private static int[] qoss={1};

    public static void setClientID(String clientID) {
        MqttConfig.clientID = clientID;
    }

    public static String getClientID() {
        return clientID;
    }

    public static void setServerURI(String serverURI) {
        MqttConfig.serverURI = serverURI;
    }

    public static String getServerURI() {
        return serverURI;
    }

    public static void setTopics(String[] topics) {
        MqttConfig.topics = topics;
    }

    public static String[] getTopics() {
        return topics;
    }

    public static void setQoss(int[] qoss) {
        MqttConfig.qoss = qoss;
    }

    public static int[] getQoss() {
        return qoss;
    }
}
