package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public interface IUserModel {

    void login(String phone, String password, IModelCallback<String> iModelCallback);//登录操作
    void register(String phone, String password, IModelCallback<String> iModelCallback);//注册操作
    void getUserInfo(UserBean userBean, IModelCallback<UserBean> callback);//获取用户信息
    void saveUserInfo(UserBean userBean, IModelCallback<UserBean> callback);//保存用户信息

}
