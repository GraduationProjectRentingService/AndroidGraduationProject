package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class UserModelImpl implements IUserModel {

    @Override
    public void login(String phone, String password, final IModelCallback<String> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, phone);
        params.put(UrlParamsUtils.USER_PASSWORD, MyTextUtils.MD5(password));
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_USER_LOGIN, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    iModelCallback.onSuccess(responseBean.getContent().optString(UrlParamsUtils.TOKEN));
                }else {
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void register(String phone, String password, final IModelCallback<String> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, phone);
        params.put(UrlParamsUtils.USER_PASSWORD, MyTextUtils.MD5(password));
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_USER_REGISTER, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    iModelCallback.onSuccess(responseBean.getContent().optString(UrlParamsUtils.TOKEN));
                }else {
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });
    }
}
