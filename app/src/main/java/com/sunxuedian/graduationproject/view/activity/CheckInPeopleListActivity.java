package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.CheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.adapter.ChooseCheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.presenter.impl.CheckInPeoplesPresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.ICheckInPeoplesView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckInPeopleListActivity extends BaseSwipeBackActivity<ICheckInPeoplesView, CheckInPeoplesPresenterImpl> implements ICheckInPeoplesView{

    @BindView(R.id.lvCheckInPeople)
    ListView mLvCheckInPeople;
    private ShapeLoadingDialog mLoadingView;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnAddPeople)
    public void addPeople(){
        Intent intent = new Intent(this, AddCheckInPeopleActivity.class);
        startActivity(intent);
    }

    private CheckInPeopleListAdapter mAdapter;
    private List<CheckInPeopleUserInfo> mCheckInPeopleUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_people_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllCheckInPeoples();
    }

    @Override
    protected CheckInPeoplesPresenterImpl createPresenter() {
        return new CheckInPeoplesPresenterImpl(this);
    }

    private void initView(){
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        mAdapter = new CheckInPeopleListAdapter(mCheckInPeopleUsers, this);
        mAdapter.hideDeleteButton();
        mLvCheckInPeople.setAdapter(mAdapter);
        mLvCheckInPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CheckInPeopleListActivity.this, EditCheckInPeopleActivity.class);
                intent.putExtra(CheckInPeopleUserInfo.TAG, (Parcelable) mCheckInPeopleUsers.get(position));
                startActivity(intent);
            }
        });
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
    public void showNetworkError() {
        ToastUtils.showToast("网络错误！");
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
