package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;
import android.util.Log;

import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.bean.UserDto;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;
import com.sunxuedian.graduationproject.utils.JsonUtils;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class UserModelImpl implements IUserModel {

    private static UserModelImpl mInstance;

    private UserModelImpl(){}

    public static UserModelImpl getInstance(){
        if (mInstance == null){
            synchronized (UserModelImpl.class){
                if (mInstance == null){
                    mInstance = new UserModelImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void login(String phone, String password, final IModelCallback<String> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        //将参数传递到map中
        params.put(UrlParamsUtils.USER_PHONE, phone);
        //将密码使用MD5加密，避免明文传输
        params.put(UrlParamsUtils.USER_PASSWORD, MyTextUtils.MD5(password));
        //调用OKHttpUtils的网络访问方法
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_USER_LOGIN, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                //判断服务器接口的返回接口是否成功
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    //回调到Presenter层
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

    @Override
    public void getUserInfo(final UserBean userBean, final IModelCallback<UserBean> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_GET_USER_INFO, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    UserDto userDto = JsonUtils.fromJson(UserDto.class, responseBean.getContent().optString("user"));
                    if (userDto == null){
                        Log.e("user", "userDto为空！");
                    }else {
                        userBean.setUserName(userDto.getName());
                        userBean.setIconUrl(userDto.getPic());
                        userBean.setSex(userDto.getSex());
                    }
                    callback.onSuccess(userBean);
                }else {
                    callback.onFailure(responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void saveUserInfo(final UserBean userBean, final IModelCallback<UserBean> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());//用户手机号码
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());//登录获取到的Token
        params.put(UrlParamsUtils.USER_NAME, userBean.getUserName());//用户名
        params.put(UrlParamsUtils.SEX, userBean.getSex());//用户性别
        params.put(UrlParamsUtils.USER_ICON_URL, userBean.getIconUrl());//用户头像
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_UPDATE_USER_INFO, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    callback.onSuccess(userBean);
                }else {
                    callback.onFailure(responseBean.getMessage());
                }
            }
        });
    }
}
