package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.HouseDto;
import com.sunxuedian.graduationproject.bean.MessageBean;
import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.ISystemMessageModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.utils.JsonUtils;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public class SystemMessageModelImpl implements ISystemMessageModel {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private static SystemMessageModelImpl mInstance;
    private SystemMessageModelImpl(){}

    public static SystemMessageModelImpl getInstance(){
        if (mInstance == null){
            synchronized (SystemMessageModelImpl.class){
                if (mInstance == null){
                    mInstance = new SystemMessageModelImpl();
                }
            }
        }
        return mInstance;
    }
    @Override
    public void getSystemMessage(UserBean userBean, final IModelCallback<List<MessageBean>> callback) {
        Map<String,Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_GET_SYSTEM_MESSAGE, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(UrlParamsUtils.SUCCESS_CODE, responseBean.getCode())){
                    List<MessageBean> list = JsonUtils.getListByJSONArray(MessageBean.class, responseBean.getContent().optJSONArray("list"));
                    callback.onSuccess(list);
                }else {
                    callback.onResultCode(responseBean.getCode());
                    callback.onFailure(responseBean.getMessage());
                }
            }
        });
    }
}
