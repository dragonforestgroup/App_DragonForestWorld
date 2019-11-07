package com.dragonforest.app.module_message.messageOuter.bean;

import com.dragonforest.app.module_message.database.MessageModel;

import java.io.Serializable;

/**
 * 外层消息
 *
 * @author DragonForest
 * @date 2019/11/6 17:26
 */
public class MessageOuterModel implements Serializable {
    int type;
    String sendClientID;
    MessageModel lastMessage;
    int unReadNum;

    public MessageOuterModel() {
    }

    public MessageOuterModel(int type,String sendClientID,MessageModel lastMessage, int unReadNum) {
        this.type=type;
        this.lastMessage = lastMessage;
        this.unReadNum = unReadNum;
        this.sendClientID=sendClientID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public MessageModel getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageModel lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public void setSendClientID(String sendClientID) {
        this.sendClientID = sendClientID;
    }

    public String getSendClientID() {
        return sendClientID;
    }
}
