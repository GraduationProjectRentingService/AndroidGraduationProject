package com.sunxuedian.graduationproject.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;
import com.sunxuedian.graduationproject.model.impl.UserModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IRegisterPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.utils.NetworkUtils;
import com.sunxuedian.graduationproject.view.IRegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.R.attr.id;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class RegisterPresenterImpl extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private Activity mContext;
    private String mPhone;
    private IUserModel mUserModel;

    private EventHandler mEventHandler = new EventHandler(){
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!isViewAttached()){
                        logger.e("the View is not attached！");
                        return;
                    }

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            getView().onCheckCodeSuccess();
                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            getView().onSendCodeSuccess();
                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                        }
                    }else{
                        ((Throwable)data).printStackTrace();
                        try {
                            JSONObject object = new JSONObject(((Throwable)data).getMessage());
                            getView().onCheckCodeFailure(object.optString("detail"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().onCheckCodeFailure(((Throwable)data).getMessage());
                        }
                    }
                }
            });
        }
    };

    public RegisterPresenterImpl(Activity activity){
        mContext = activity;
        mUserModel = new UserModelImpl();
        SMSSDK.registerEventHandler(mEventHandler);
    }

    /**
     * 获取验证码
     */
    @Override
    public void getCheckCode() {
        if (!isViewAttached()){
            logger.e("the View is not attached！");
            return;
        }

        String phone = getView().getPhoneNum();

        if (TextUtils.isEmpty(phone)){
            getView().onSendCodeFailure("手机号码不能为空！");
            return;
        }

        if (!MyTextUtils.isPhoneNumLegitimate(phone)){
            getView().onSendCodeFailure("手机号码不合法！");
            return;
        }

        mPhone = phone;
        SMSSDK.getVerificationCode("86", mPhone);

    }

    /**
     * 验证手机验证码
     */
    @Override
    public void checkCode() {
        if (!isViewAttached()){
            logger.e("the View is not attached！");
            return;
        }

        String code = getView().getCheckCode();
        if (TextUtils.isEmpty(code)){
            getView().onCheckCodeFailure("验证码不能为空！");
            return;
        }

        SMSSDK.submitVerificationCode("86", mPhone, code);
    }

    /**
     * 注册
     */
    @Override
    public void register() {
        if (!isViewAttached()){
            logger.e("the View is not attached！");
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(mContext)){
            logger.e("network is not available!");
            getView().showNetworkError();
            return;
        }

        String password = getView().getPassword();
        if (TextUtils.isEmpty(password)){
            getView().onRegisterFailure("密码不能为空！");
            return;
        }
        if (!MyTextUtils.isPasswordLegitimate(password)){
            getView().onRegisterFailure("密码不合理！");
            return;
        }

        String rePassword = getView().getRePassword();
        if (!password.equals(rePassword)){
            getView().onRegisterFailure("前后密码不一致，请再次输入！");
            return;
        }

        getView().showLoading();
        mUserModel.register(mPhone, password, new IModelCallback<String>() {
            @Override
            public void onSuccess(String token) {
                getView().stopLoading();
                getView().onRegisterSuccess(mPhone, token);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().onRegisterFailure(msg);
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
