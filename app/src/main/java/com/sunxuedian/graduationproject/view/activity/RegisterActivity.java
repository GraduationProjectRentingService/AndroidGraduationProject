package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.RegisterPresenterImpl;
import com.sunxuedian.graduationproject.utils.AppActivityStackUtils;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IRegisterView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseSwipeBackActivity<IRegisterView, RegisterPresenterImpl> implements IRegisterView {

    private boolean mCheckCodeSuccess = false;//判断是否已经验证手机号码了

    /**
     * 绑定View
     */
    @BindView(R.id.etPhoneNum)
    EditText mEtPhoneNum;
    @BindView(R.id.etCheckCode)
    EditText mEtCheckCode;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.etRePassword)
    EditText mEtRePassword;
    @BindView(R.id.tvGetCode)
    TextView mTvGetCode;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.linePhone)
    LinearLayout mLinePhone;
    @BindView(R.id.lineCheckCode)
    LinearLayout mLineCheckCode;
    @BindView(R.id.linePassword)
    LinearLayout mLinePassword;
    @BindView(R.id.lineRePassword)
    LinearLayout mLineRePassword;

    @OnClick(R.id.ivBack)
    public void goBack(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tvGetCode)
    public void getCode(){
       mPresenter.getCheckCode();
    }

    @OnClick(R.id.button)
    public void onButtonClick(){
        if (mCheckCodeSuccess){
            mPresenter.register();
        }else {
            mPresenter.checkCode();
        }
    }

    private ShapeLoadingDialog mLoadingView;//进度对话框

    //Timer and TimerTask
    private Timer timer;
    private TimerTask timerTask;
    private int left_second = 60;

    private int UPDATE_MESSAGE = 1;
    //Handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == UPDATE_MESSAGE){
                left_second --;
                if(left_second == 0){
                    if(timerTask != null){
                        timerTask.cancel();
                    }
                    mTvGetCode.setEnabled(true);
                    mTvGetCode.setClickable(true);
                    mTvGetCode.setText("重新发送");
                    left_second = 60;
                }else
                    mTvGetCode.setText("重新发送("+left_second+")");
            }
            super.handleMessage(msg);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");
    }

    @Override
    protected RegisterPresenterImpl createPresenter() {
        return new RegisterPresenterImpl(this);
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void stopLoading() {
        mLoadingView.dismiss();
    }

    @Override
    public String getPhoneNum() {
        return mEtPhoneNum.getText().toString().trim();
    }

    @Override
    public String getCheckCode() {
        return mEtCheckCode.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    @Override
    public String getRePassword() {
        return mEtRePassword.getText().toString().trim();
    }

    @Override
    public void onSendCodeSuccess() {
        //开始倒计时，同时将发送按钮设置为不可点击状态
        ToastUtils.showToast("验证码已经发送，请注意查收");
        //设置发送验证码的文本不可点击
        mTvGetCode.setEnabled(false);
        mTvGetCode.setClickable(false);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(UPDATE_MESSAGE);
            }
        };
        if(timer == null)
            timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void onSendCodeFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onCheckCodeSuccess() {
        //验证手机成功，显示输入密码框，同时将按钮改成注册文本
        mLinePhone.setVisibility(View.GONE);
        mLineCheckCode.setVisibility(View.GONE);
        mLinePassword.setVisibility(View.VISIBLE);
        mLineRePassword.setVisibility(View.VISIBLE);
        mButton.setText(R.string.text_register);
        mCheckCodeSuccess = true;
    }

    @Override
    public void onCheckCodeFailure(String msg) {
        mCheckCodeSuccess = false;
        ToastUtils.showToast(msg);
    }

    @Override
    public void onRegisterSuccess(String phone, String token) {
        AppActivityStackUtils.clear();
        UserBean userBean = new UserBean();
        userBean.setPhoneNum(phone);
        userBean.setToken(token);
        UserSpUtils.saveUserToLocal(this, userBean);
        //实现跳转
        finish();
    }

    @Override
    public void onRegisterFailure(String msg) {
        ToastUtils.showToast("注册失败：" + msg);
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
