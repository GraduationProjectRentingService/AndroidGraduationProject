package com.sunxuedian.graduationproject.bean;

import java.util.Date;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public class MessageBean {
    private Long id;

    private String messageTitle;

    private String messageContent;

    private long time;

    public String getTime() {
        Date date = new Date(time);
        return date.toLocaleString().split(" ")[0];
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
