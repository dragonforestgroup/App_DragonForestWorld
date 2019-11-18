package com.dragonforest.app.module_message.event;

/**
 * 通知消息服务链接状态变化、
 *
 * @author DragonForest
 * @date 2019/10/30 11:43
 */
public class ConnectStatusEvent {
    public final static int STATUS_CONNECTED = 1;
    public final static int STATUS_DISCONNECTED = -1;
    /**
     * 链接状态
     * 1 已连接
     * -1已断开
     */
    int status = 0;
    String message;

    public ConnectStatusEvent(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
