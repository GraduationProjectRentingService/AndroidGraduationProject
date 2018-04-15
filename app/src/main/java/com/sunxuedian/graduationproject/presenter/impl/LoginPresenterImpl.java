package com.sunxuedian.graduationproject.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.IUserModel;
import com.sunxuedian.graduationproject.model.impl.UserModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.ILoginPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.NetworkUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
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
        mUserModel = UserModelImpl.getInstance();
    }

    @Override
    public void login() {
        //判断Presenter和View是否绑定在一起
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }
        //判断网络是否可用
        if (!NetworkUtils.isNetworkAvailable(mContext)){
            logger.e("network is not available!");
            getView().showNetworkError();
            return;
        }
        //从view中获取用户输入的账号和密码
        final String phone = getView().getPhoneNum();
        String password = getView().getPassword();
        //判断账号和密码的合理性
        if (TextUtils.isEmpty(phone)){
            getView().onLoginFailure("手机号不能为空！");
            return;
        }

        if (TextUtils.isEmpty(password)){
            getView().onLoginFailure("密码不能为空！");
            return;
        }
        //显示进度条
        getView().showLoading();
        //调用Model层的login接口实现访问服务器进行登录操作
        mUserModel.login(phone, password, new IModelCallback<String>() {
            @Override
            public void onSuccess(String token) {
                getUserInfo(phone, token);
            }

            @Override
            public void onFailure(String msg) {
                //提示View界面登录失败
                getView().stopLoading();
                getView().onLoginFailure(msg);
            }
        });
    }

    @Override
    public void getUserInfo(String phone, String token) {
        UserBean userBean = new UserBean();
        userBean.setPhoneNum(phone);
        userBean.setToken(token);
        mUserModel.getUserInfo(userBean, new IModelCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean data) {
                UserSpUtils.saveUserToLocal(mContext, data);
                //将登录成功回调到View界面
                getView().stopLoading();
                getView().onLoginSuccess();
            }

            @Override
            public void onFailure(String msg) {
                logger.e(msg);
                //提示View界面登录失败
                getView().stopLoading();
                getView().onLoginFailure(msg);
            }
        });
    }
}
