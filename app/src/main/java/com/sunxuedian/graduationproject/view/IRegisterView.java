package com.sunxuedian.graduationproject.view;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public interface IRegisterView extends IProgressView, INetworkErrorView{
    String getPhoneNum();//获取手机号码
    String getCheckCode();//获取验证码
    String getPassword();//获取密码
    String getRePassword();//获取确认验证码
    void onSendCodeSuccess();//发送短信验证码成功
    void onSendCodeFailure(String msg);//发送验证码失败
    void onCheckCodeSuccess();//验证手机成功
    void onCheckCodeFailure(String msg);//验证手机失败
    void onRegisterSuccess(String phone, String token);//注册成功
    void onRegisterFailure(String msg);//注册失败
}
