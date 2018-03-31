package com.sunxuedian.graduationproject.bean;

/**
 * Created by sunxuedian on 2018/3/14.
 */

public class BannerViewBean {

    private String imgUrl;
    private String webUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "BannerViewBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
