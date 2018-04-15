package com.sunxuedian.graduationproject.bean;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class UserBean {

    private String userName = "";
    private String iconUrl = "";
    private String phoneNum = "";
    private String sex = "";
    private String token = "";


    public String getPhoneNum() {
        if (phoneNum == null){
            return "";
        }
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getToken() {
        if (token == null){
            return "";
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        if (userName == null){
            return "";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIconUrl() {
        if (iconUrl == null){
            return "";
        }
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSex() {
        if (sex == null){
            return "";
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
