package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.mingle.widget.ShapeLoadingDialog;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.EditCheckInPeoplePresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IEditCheckInPeopleView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCheckInPeopleActivity extends BaseSwipeBackActivity<IEditCheckInPeopleView, EditCheckInPeoplePresenterImpl> implements IEditCheckInPeopleView {

    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etIdCard)
    EditText mEtIdCard;
    @BindView(R.id.etPhoneNum)
    EditText mEtPhoneNum;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.tvSure)
    public void onSure(){
        mPresenter.updateCheckInPeopleInfo();//修改信息
    }

    @OnClick(R.id.tvDelete)
    public void deleteCheckInPeople(){
        mPresenter.deleteCheckInPeople();//删除
    }

    private CheckInPeopleUserInfo mCheckInUserInfo;

    private ShapeLoadingDialog mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_check_in_people);
        ButterKnife.bind(this);
        getIntentData();
        initView();
    }

    @Override
    protected EditCheckInPeoplePresenterImpl createPresenter() {
        return new EditCheckInPeoplePresenterImpl();
    }

    private void getIntentData() {
        Intent data = getIntent();
        if (data != null){
            mCheckInUserInfo = data.getParcelableExtra(CheckInPeopleUserInfo.TAG);
            if (mCheckInUserInfo != null){
                mEtName.setText(mCheckInUserInfo.getName());
                mEtName.setSelection(mCheckInUserInfo.getName().length());
                mEtIdCard.setText(mCheckInUserInfo.getIdCard());
                mEtPhoneNum.setText(mCheckInUserInfo.getPhone());
            }
        }
    }

    private void initView(){
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");
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
    public void onUpdateCheckInPeopleSuccess() {
        ToastUtils.showToast("修改成功！");
        finish();
    }

    @Override
    public void onDeleteCheckInPeopleSuccess() {
        ToastUtils.showToast("删除成功！");
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }


    @Override
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(this);
    }

    @Override
    public CheckInPeopleUserInfo getCheckInPeople() {
        return mCheckInUserInfo;
    }

    @Override
    public String getName() {
        return mEtName.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return mEtPhoneNum.getText().toString().trim();
    }

    @Override
    public String getIdCard() {
        return mEtIdCard.getText().toString().trim();
    }

    @Override
    public void onTokenIllegalView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
