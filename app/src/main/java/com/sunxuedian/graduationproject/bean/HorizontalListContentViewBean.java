package com.sunxuedian.graduationproject.bean;

/**
 * Created by sunxuedian on 2018/3/18.
 */

public class HorizontalListContentViewBean {

    private String imgUrl;
    private String title;

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

    @Override
    public String toString() {
        return "HorizontalListContentViewBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
