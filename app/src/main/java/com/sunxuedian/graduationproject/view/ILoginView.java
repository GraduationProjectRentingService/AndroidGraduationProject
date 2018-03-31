package com.sunxuedian.graduationproject.view;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public interface ILoginView extends IProgressView{
    String getPhoneNum();
    String getPassword();
    void onLoginSuccess(String phoneNum, String token);
    void onLoginFailure(String msg);
}
