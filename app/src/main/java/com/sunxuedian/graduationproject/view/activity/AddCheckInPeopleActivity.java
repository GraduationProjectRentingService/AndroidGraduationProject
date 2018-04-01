package com.sunxuedian.graduationproject.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.presenter.impl.AddCheckInPeoplePresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.IAddCheckInPeopleView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCheckInPeopleActivity extends BaseSwipeBackActivity<IAddCheckInPeopleView, AddCheckInPeoplePresenterImpl> implements IAddCheckInPeopleView {

    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etIdCard)
    EditText mEtIDCard;
    @BindView(R.id.etPhoneNum)
    EditText mEtPhoneNum;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.tvSureAdd)
    public void addCheckInPeople(){
        mPresenter.addCheckInPeople();//调用Presenter逻辑进行添加
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_in_people);
        ButterKnife.bind(this);
    }

    @Override
    protected AddCheckInPeoplePresenterImpl createPresenter() {
        return new AddCheckInPeoplePresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void onAddSuccess(CheckInPeopleUserInfo info) {
        ToastUtils.showToast("添加成功！");
        goBack();
    }

    @Override
    public void onAddFailure(String msg) {
        ToastUtils.showToast("添加失败：" + msg);
    }

    @Override
    public String getPeopleName() {
        return mEtName.getText().toString().trim();
    }

    @Override
    public String getIdCard() {
        return mEtIDCard.getText().toString().trim();
    }

    @Override
    public String getPhoneNum() {
        return mEtPhoneNum.getText().toString().trim();
    }
}
