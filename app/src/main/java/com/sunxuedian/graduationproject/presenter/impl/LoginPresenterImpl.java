package com.sunxuedian.graduationproject.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;
import com.sunxuedian.graduationproject.model.impl.UserModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.ILoginPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.utils.NetworkUtils;
import com.sunxuedian.graduationproject.view.ILoginView;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private Context mContext;
    private IUserModel mUserModel;

    public LoginPresenterImpl(Context context){
        mContext = context;
        mUserModel = new UserModelImpl();
    }

    @Override
    public void login() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(mContext)){
            logger.e("network is not available!");
            getView().showNetworkError();
            return;
        }

        final String phone = getView().getPhoneNum();
        String password = getView().getPassword();

        if (TextUtils.isEmpty(phone)){
            getView().onLoginFailure("手机号不能为空！");
            return;
        }

        if (TextUtils.isEmpty(password)){
            getView().onLoginFailure("密码不能为空！");
            return;
        }



        getView().showLoading();
        mUserModel.login(phone, password, new IModelCallback<String>() {
            @Override
            public void onSuccess(String token) {
                getView().stopLoading();
                getView().onLoginSuccess(phone, token);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().onLoginFailure(msg);
            }
        });
    }
}
