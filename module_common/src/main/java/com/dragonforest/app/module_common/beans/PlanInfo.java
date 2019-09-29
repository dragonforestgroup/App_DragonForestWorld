package com.dragonforest.app.module_common.beans;

/**
 * @author 韩龙林
 * @date 2019/9/29 18:25
 */
public class PlanInfo {
    String userName;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;

    String thing;
    int level;
    int type;

    public void setDate(int year,int month,int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public void setTime(int hour,int minute,int second){
        this.hour=hour;
        this.minute=minute;
        this.second=second;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
