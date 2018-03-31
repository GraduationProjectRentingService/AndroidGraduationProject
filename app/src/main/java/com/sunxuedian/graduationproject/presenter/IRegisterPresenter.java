package com.sunxuedian.graduationproject.presenter;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public interface IRegisterPresenter {
    void getCheckCode();//获取验证码
    void checkCode();//验证验证码
    void register();//注册
}
