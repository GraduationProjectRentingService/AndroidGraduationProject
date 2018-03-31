package com.sunxuedian.graduationproject.bean;

/**
 * Created by sunxuedian on 2018/3/15.
 */

public class HouseBean {

    private String imgUrl;
    private String title;
    private String homeownerIconUrl;
    private String description;
    private boolean isLike;
    private String moneyOfEachNight;//每晚的价格

    public String getMoneyOfEachNight() {
        return moneyOfEachNight;
    }

    public void setMoneyOfEachNight(String moneyOfEachNight) {
        this.moneyOfEachNight = moneyOfEachNight;
    }

    public String getHomeownerIconUrl() {
        return homeownerIconUrl;
    }

    public void setHomeownerIconUrl(String homeownerIconUrl) {
        this.homeownerIconUrl = homeownerIconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
