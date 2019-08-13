package com.dragonforest.app.dragonforestworld.beans;

/**
 * @author 韩龙林
 * @date 2019/8/13 19:06
 */
public class Article {
    String publisher;
    String title;
    String time;
    String imgUrl;
    String content;

    public Article() {
    }

    public Article(String publisher, String title, String time, String imgUrl, String content) {
        this.publisher = publisher;
        this.title = title;
        this.time = time;
        this.imgUrl = imgUrl;
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
