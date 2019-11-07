package com.dragonforest.app.module_message.database;

import com.google.gson.Gson;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @author DragonForest
 * @date 2019/11/7 14:33
 */
public class MessageDetail extends LitePalSupport implements Serializable {
    /**
     * 发送人id
     */
    String sendClientID;
    /**
     * 发送时间
     */
    String sendDate;
    /**
     * 消息类型 0公告/1工作通知/2私聊/3群聊
     */
    int type;
    /**
     * 消息内容
     */
    String content;

    public MessageDetail() {
    }

    public MessageDetail(String sendClientID, String sendDate, int type, String content) {
        this.sendClientID = sendClientID;
        this.sendDate = sendDate;
        this.type = type;
        this.content = content;
    }

    public String getSendClientID() {
        return sendClientID;
    }

    public void setSendClientID(String sendClientID) {
        this.sendClientID = sendClientID;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static MessageDetail createFromJson(String json){
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, MessageDetail.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
