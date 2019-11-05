package com.dragonforest.app.dragonforestworld.biz.mine.activity.message.bean;

import com.dragonforest.app.dragonforestworld.R;

import java.util.Date;

/**
 * @author 韩龙林
 * @date 2019/10/9 09:17
 */
public class MMessage {
    public final static int STATUS_READED = 1;
    public final static int STATUS_UNREADED = 0;
    int head;
    String from;
    String to;
    String content;
    Date date;
    int status;

    public MMessage() {
        head= R.drawable.app_bg_java;
    }

    public MMessage(String from, String to, String content, Date date, int status) {
        head= R.drawable.app_bg_java;
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
        this.status = status;
    }

    public static int getStatusReaded() {
        return STATUS_READED;
    }

    public static int getStatusUnreaded() {
        return STATUS_UNREADED;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getHead() {
        return head;
    }
}
