package com.dragonforest.app.module_message.event;

/**
 * @author DragonForest
 * @date 2019/10/30 11:43
 */
public class ConnectStatusEvent {
    int status=0;
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
