package com.dragonforest.app.module_message.event;

/**
 * 用来通知消息状态变化
 *
 * @author DragonForest
 * @date 2019/11/7 10:20
 */
public class MessageStatusEvent {
    public final static int ACTION_ADD = 0;
    public final static int ACTION_DELETE = 1;
    public final static int ACTION_UPDATE = 2;

    /**
     * 消息类型
     */
    int type;
    /**
     * 发送人id
     */
    String sendClientID;
    /**
     * 消息操作 增/删/改
     */
    int action;

    public MessageStatusEvent(String sendClientID, int type, int action) {
        this.type = type;
        this.sendClientID = sendClientID;
        this.action = action;
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

    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
