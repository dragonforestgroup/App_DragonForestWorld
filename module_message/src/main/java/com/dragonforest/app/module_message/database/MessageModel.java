package com.dragonforest.app.module_message.database;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DragonForest
 * @date 2019/10/29 16:27
 */
public class MessageModel extends LitePalSupport implements Serializable {
    /**
     * 消息接收时间
     */
    Date date;
    /**
     * 监听主题
     */
    String topic;
    /**
     * 消息id
     */
    int id;
    /**
     * 消息主体
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
