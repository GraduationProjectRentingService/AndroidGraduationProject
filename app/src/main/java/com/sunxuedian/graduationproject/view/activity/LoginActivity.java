package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.LoginPresenterImpl;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.ILoginView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseSwipeBackActivity<ILoginView, LoginPresenterImpl> implements ILoginView {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    /**
     * 绑定View
     */
    @BindView(R.id.etPhoneNum)
    EditText mEtPhoneNum;
    @BindView(R.id.etPassword)
    EditText mEtPassword;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        mPresenter.login();
    }

    @OnClick(R.id.tvGoRegister)
    public void goRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public String getPhoneNum() {
        return mEtPhoneNum.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    @Override
    public void onLoginSuccess(String phoneNum, String token) {
        UserBean userBean = new UserBean();
        userBean.setPhoneNum(phoneNum);
        userBean.setToken(token);
        UserSpUtils.saveUserToLocal(this, userBean);
        //实现跳转
        finish();
    }

    @Override
    public void onLoginFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showNetworkError() {
        ToastUtils.showToast("网络不给力，请检查网络设置。");
    }
}
