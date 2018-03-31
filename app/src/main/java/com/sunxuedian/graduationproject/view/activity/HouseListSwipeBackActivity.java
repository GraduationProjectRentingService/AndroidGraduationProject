package com.sunxuedian.graduationproject.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.adapter.VerticalHouseListAdapter;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.presenter.impl.HouseListPresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.IHouseListView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseListSwipeBackActivity extends BaseSwipeBackActivity<IHouseListView, HouseListPresenterImpl> implements IHouseListView {

    private View mLastClickFilterView;

    private VerticalHouseListAdapter mAdapter;

    /**
     * 绑定View
     */
    @BindView(R.id.rvHouseList)
    RecyclerView mRVHouseList;

    @OnClick({R.id.tvSearchFilterCheckInTime, R.id.tvSearchFilterHouseArea, R.id.tvSearchFilterMore, R.id.tvSearchFilterSort})
    public void onFilterClick(View view){
        if (mLastClickFilterView != null){
            mLastClickFilterView.setSelected(false);
        }
        view.setSelected(true);
        mLastClickFilterView = view;
        switch (view.getId()){
            case R.id.tvSearchFilterCheckInTime:
                break;
            case R.id.tvSearchFilterHouseArea:
                break;
            case R.id.tvSearchFilterMore:
                break;
            case R.id.tvSearchFilterSort:
                break;
        }
    }
    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData(){
        mPresenter.getHouseList();
    }

    private void initView(){
        mAdapter = new VerticalHouseListAdapter(this, new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                ToastUtils.showToast("pos: " + pos);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRVHouseList.setLayoutManager(layoutManager);
        mRVHouseList.setAdapter(mAdapter);
    }

    @Override
    protected HouseListPresenterImpl createPresenter() {
        return new HouseListPresenterImpl();
    }

    @Override
    public void showHouseList(List<HouseBean> list) {
        if (list != null && list.size() > 0){
            mAdapter.setData(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
