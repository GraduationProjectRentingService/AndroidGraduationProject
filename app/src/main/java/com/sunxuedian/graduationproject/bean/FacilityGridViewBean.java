package com.sunxuedian.graduationproject.bean;

/**
 * 显示房源显示中的基础设施信息
 * Created by sunxuedian on 2018/4/1.
 */

public class FacilityGridViewBean {

    private String text;
    private Integer resId;
    private boolean isTextGray;//字体是否为灰色

    public FacilityGridViewBean() {
    }

    public FacilityGridViewBean(String text, Integer resId, boolean isTextGray) {
        this.text = text;
        this.resId = resId;
        this.isTextGray = isTextGray;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }


    public boolean isTextGray() {
        return isTextGray;
    }

    public void setTextGray(boolean textGray) {
        isTextGray = textGray;
    }
}
