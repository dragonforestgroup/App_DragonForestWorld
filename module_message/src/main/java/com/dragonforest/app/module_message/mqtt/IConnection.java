package com.dragonforest.app.module_message.mqtt;

/**
 * @author DragonForest
 * @date 2019/10/29 15:05
 */
public interface IConnection {
    boolean connect();
    boolean disconnect();
    boolean isConnected();
}
