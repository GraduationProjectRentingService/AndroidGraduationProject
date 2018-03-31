package com.sunxuedian.graduationproject.model.impl;

import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class UserModelImpl implements IUserModel {

    @Override
    public void login(String phone, String password, IModelCallback<String> iModelCallback) {
        // TODO: 2018/3/23 暂时使用模拟访问
        iModelCallback.onSuccess(phone + password);
    }

    @Override
    public void register(String phone, String password, IModelCallback<String> iModelCallback) {
        // TODO: 2018/3/23 模拟注册成功
        iModelCallback.onSuccess(phone + password);
    }
}
