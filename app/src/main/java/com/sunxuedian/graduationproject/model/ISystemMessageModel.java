package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.MessageBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public interface ISystemMessageModel {
    void getSystemMessage(UserBean userBean, IModelCallback<List<MessageBean>> callback);
}
