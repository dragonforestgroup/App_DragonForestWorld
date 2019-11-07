package com.dragonforest.app.module_message.database;

import com.google.gson.Gson;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DragonForest
 * @date 2019/10/29 16:27
 */


public class MessageModel extends LitePalSupport implements Serializable {
    // ========================== 外层信息
    /**
     * 消息接收时间
     */
    Date date;
    /**
     * 监听主题
     */
    String topic;
    /**
     * 消息状态  已读/未读
     */
    int status;

    // ========================== 内层信息
    /**
     * 消息类型 0公告/1工作通知/2私聊/3群聊
     */
    int type;
    /**
     * 发送人id
     */
    String sendClientID;
    /**
     * 发送时间
     */
    String sendDate;
    /**
     * 消息信息
     */
    String message;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setSendClientID(String sendClientID) {
        this.sendClientID = sendClientID;
    }

    public String getSendClientID() {
        return sendClientID;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
