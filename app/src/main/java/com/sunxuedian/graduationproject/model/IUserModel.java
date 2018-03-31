package com.sunxuedian.graduationproject.model;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public interface IUserModel {

    void login(String phone, String password, IModelCallback<String> iModelCallback);//登录操作
    void register(String phone, String password, IModelCallback<String> iModelCallback);//注册操作
}
