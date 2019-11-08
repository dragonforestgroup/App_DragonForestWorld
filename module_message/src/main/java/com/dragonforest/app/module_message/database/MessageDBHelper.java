package com.dragonforest.app.module_message.database;

import android.database.Cursor;

import com.dragonforest.app.module_message.messageOuter.bean.MessageOuterModel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author DragonForest
 * @date 2019/11/6 16:27
 */
public class MessageDBHelper {
    /**
     * 根据时间排序 获取全部消息
     *
     * @param sendClientID 发送人id
     * @param status       状态 0未读  1已读
     * @return
     */
    public static List<MessageModel> getMessageOrderByDate(int type, String sendClientID, int status) {
        return LitePal.where("sendClientID = ? and status = ? and type=?", sendClientID, status + "", type + "").order("date desc").find(MessageModel.class);
    }

    /**
     * @param sendClientID
     * @return
     */
    public static List<MessageModel> getMessageOrderByDate(int type, String sendClientID) {
        return LitePal.where("sendClientID = ? and type=?", sendClientID, type + "").order("date desc").find(MessageModel.class);
    }

    /**
     * 获取类型下的所有sendClientID
     *
     * @param type
     * @return
     */
    public static List<String> getAllSendClientIDs(int type) {
        String sql = "select sendclientid,max(date) as mdate from messageModel where type=" + type + " group by sendclientid order by mdate desc";
        List<String> clientIds = new ArrayList<>();
        Cursor cursor = LitePal.findBySQL(sql);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String clientId = cursor.getString(0);
            clientIds.add(clientId);
            cursor.moveToNext();
        }
        cursor.close();
        return clientIds;
    }

    /**
     * 获取所有外层message
     *
     * @return
     */
    public static LinkedList<MessageOuterModel> getAllMessageOuterModels() {
        return getAllMessageOuterModels(-1);
    }

    /**
     * 获取所有外层message
     *
     * @return
     */
    public static LinkedList<MessageOuterModel> getAllMessageOuterModels(int mtype) {
        LinkedList<MessageOuterModel> messageOuterModels = new LinkedList<>();
        String sql = "";
        if (mtype != -1) {
            sql = "select sendclientid,type,max(date) as mdate from messageModel where type=" + mtype + " group by sendclientid,type order by mdate desc";
        } else {
            sql = "select sendclientid,type,max(date) as mdate from messageModel group by sendclientid,type order by mdate desc";
        }
        Cursor cursor = LitePal.findBySQL(sql);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String clientId = cursor.getString(0);
            int type = cursor.getInt(1);
            MessageModel newestMessage = getNewestMessage(type, clientId);
            if (newestMessage != null) {
                int unReadMessageNum = getUnReadMessageNum(type, clientId);
                MessageOuterModel messageOuterModel = new MessageOuterModel(type, clientId, newestMessage, unReadMessageNum);
                messageOuterModels.add(messageOuterModel);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return messageOuterModels;
    }


    /**
     * 获取未读消息数量
     *
     * @param sendClientID
     * @return
     */
    public static int getUnReadMessageNum(int type, String sendClientID) {
        int num = 0;
        Cursor cursor = LitePal.findBySQL("select count(*) from MessageModel where sendclientid='" + sendClientID + "' and type=" + type + " and status=0");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            num = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return num;
    }

    /**
     * 获取最新的一条消息
     *
     * @param sendClientID
     */
    public static MessageModel getNewestMessage(int type, String sendClientID) {
        return LitePal.where("sendclientid=? and type=?", sendClientID, type + "").findLast(MessageModel.class);
    }

    /**
     * 更新指定sendClientID下的所有消息的状态
     *
     * @param sendClientID
     * @param status
     */
    public static void updateMessageStatus(int type, String sendClientID, int status) {
        MessageModel messageModel = new MessageModel();
        messageModel.setStatus(status);
        messageModel.updateAll("sendclientid=? and type=?", sendClientID, type + "");
    }

    public static MessageModel saveMessage(String topic, String messageJson) {
        try {
            MessageModel messageModel = new MessageModel();
            // 设置外层信息
            messageModel.setTopic(topic);
            messageModel.setDate(new Date());
            messageModel.setStatus(0);
            // 设置内层信息
            MessageDetail messageDetail = MessageDetail.createFromJson(messageJson);
            if (messageDetail != null) {
                messageModel.setType(messageDetail.getType());
                messageModel.setMessage(messageDetail.getContent());
                messageModel.setSendDate(messageDetail.getSendDate());
                messageModel.setSendClientID(messageDetail.getSendClientID());
                messageModel.save();
                return messageModel;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
