package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.ICheckInPeopleModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.utils.JsonUtils;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public class CheckInPeopleModelImpl implements ICheckInPeopleModel {

    private static CheckInPeopleModelImpl mInstance;

    private CheckInPeopleModelImpl(){}

    public static CheckInPeopleModelImpl getInstance(){
        if (mInstance == null){
            synchronized (CheckInPeopleModelImpl.class){
                if (mInstance == null){
                    mInstance = new CheckInPeopleModelImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void getCheckInPeoples(String phoneNum, String token, final IModelCallback<List<CheckInPeopleUserInfo>> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, phoneNum);
        params.put(UrlParamsUtils.TOKEN, token);
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_GET_ALL_CHECK_IN_PEOPLE, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(UrlParamsUtils.SUCCESS_CODE, responseBean.getCode())){
                    List<CheckInPeopleUserInfo> list = JsonUtils.getListByJSONArray(CheckInPeopleUserInfo.class, responseBean.getContent().optJSONArray("list"));
                    iModelCallback.onSuccess(list);
                }else {
                    iModelCallback.onResultCode(responseBean.getCode());
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void addCheckInPeople(String phoneNum, String token, final CheckInPeopleUserInfo info, final IModelCallback<CheckInPeopleUserInfo> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, phoneNum);
        params.put(UrlParamsUtils.TOKEN, token);
        params.put(UrlParamsUtils.CHECK_IN_PEOPLE_PHONE, info.getPhone());
        params.put(UrlParamsUtils.USER_NAME, info.getName());
        params.put(UrlParamsUtils.ID_CARD, info.getIdCard());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_CREATE_CHECK_IN_PEOPLE, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(UrlParamsUtils.SUCCESS_CODE, responseBean.getCode())){
                    iModelCallback.onSuccess(info);
                }else {
                    iModelCallback.onResultCode(responseBean.getCode());
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void updateCheckInPeople(UserBean userBean, CheckInPeopleUserInfo info,final IModelCallback<String> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());
        params.put(UrlParamsUtils.CHECK_IN_PEOPLE_PHONE, info.getPhone());
        params.put(UrlParamsUtils.USER_NAME, info.getName());
        params.put(UrlParamsUtils.ID_CARD, info.getIdCard());
        params.put("id", info.getId());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_UPDATE_CHECK_IN_PEOPLE, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(UrlParamsUtils.SUCCESS_CODE, responseBean.getCode())){
                    iModelCallback.onSuccess("");
                }else {
                    iModelCallback.onResultCode(responseBean.getCode());
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteCheckInPeople(UserBean userBean, CheckInPeopleUserInfo info,final IModelCallback<String> iModelCallback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());
        params.put("id", info.getId());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_DELETE_CHECK_IN_PEOPLE, params, iModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(UrlParamsUtils.SUCCESS_CODE, responseBean.getCode())){
                    iModelCallback.onSuccess("");
                }else {
                    iModelCallback.onResultCode(responseBean.getCode());
                    iModelCallback.onFailure(responseBean.getMessage());
                }
            }
        });
    }
}
