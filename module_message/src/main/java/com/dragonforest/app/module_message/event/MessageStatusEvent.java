package com.dragonforest.app.module_message.event;

/**
 * 用来通知消息状态变化
 *
 * @author DragonForest
 * @date 2019/11/7 10:20
 */
public class MessageStatusEvent {
    int type;
    String sendClientID;

    public MessageStatusEvent(String sendClientID, int type) {
        this.type = type;
        this.sendClientID = sendClientID;
    }

    public void setSendClientID(String sendClientID) {
        this.sendClientID = sendClientID;
    }

    public String getSendClientID() {
        return sendClientID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
