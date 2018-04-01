package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.ChooseCheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.presenter.impl.ChooseCheckInPeoplePresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.IChooseCheckInPeoplesView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static com.sunxuedian.graduationproject.R.id.recyclerView;

public class ChooseCheckInPeopleActivity extends BaseSwipeBackActivity<IChooseCheckInPeoplesView, ChooseCheckInPeoplePresenterImpl> implements  IChooseCheckInPeoplesView {

    @BindView(R.id.rvCheckInPeople)
    RecyclerView mRvCheckInPeople;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnAddPeople)
    public void addPeople(){
        Intent intent = new Intent(this, AddCheckInPeopleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvSure)
    public void onSure(){
        ArrayList<CheckInPeopleUserInfo> list = mAdapter.getCheckedUserInfoList();
        if (list.size() > 0){
            Intent data = new Intent();
            data.putParcelableArrayListExtra(CheckInPeopleUserInfo.TAG, list);
            setResult(RESULT_OK, data);
            finish();
        }else {
            ToastUtils.showToast("请选择入住人！");
        }
    }

    private ChooseCheckInPeopleListAdapter mAdapter;
    private List<CheckInPeopleUserInfo> mCheckInPeopleUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_check_in_people);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllCheckInPeoples();
    }

    @Override
    protected ChooseCheckInPeoplePresenterImpl createPresenter() {
        return new ChooseCheckInPeoplePresenterImpl(this);
    }

    private void initView(){
        mAdapter = new ChooseCheckInPeopleListAdapter(mCheckInPeopleUsers, this);
        mRvCheckInPeople.setLayoutManager(new LinearLayoutManager(this));
        mRvCheckInPeople.setAdapter(mAdapter);
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
    public void showAllCheckInPeoples(List<CheckInPeopleUserInfo> list) {
        mCheckInPeopleUsers.clear();
        mCheckInPeopleUsers.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }
}
