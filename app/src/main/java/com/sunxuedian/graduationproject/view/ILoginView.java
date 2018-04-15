package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.UserBean;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public interface ILoginView extends IProgressView, INetworkErrorView{
    String getPhoneNum();
    String getPassword();
    void onLoginSuccess();
    void onLoginFailure(String msg);
}
