package com.sunxuedian.graduationproject.bean;

/**
 * Created by sunxuedian on 2018/3/14.
 */

public class BannerViewBean {

    private String imgUrl;//图片显示的url
    private String webUrl;//轮播图点击之后跳转的页面url

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
