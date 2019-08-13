package com.dragonforest.app.module_common.beans;

/**
 * 用户信息类
 *
 * @author 韩龙林
 * @date 2019/8/2 10:39
 */
public class UserInfo {
    /**
     * 用户名
     */
    String userName;
    /**
     * 用户id
     */
    String userId;
    /**
     * 邮箱
     */
    String userEmail;

    /**
     * 用户等级
     */
    int userLevel;
    /**
     * 用户密码
     */
    String userPassword;

    public UserInfo(String userName, String userId, String userEmail, int userLevel, String userPassword) {
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userLevel = userLevel;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
