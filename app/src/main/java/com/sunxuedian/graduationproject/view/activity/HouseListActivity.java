package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mingle.widget.ShapeLoadingDialog;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.adapter.VerticalHouseListAdapter;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.presenter.impl.HouseListPresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.IHouseListView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseListActivity extends BaseSwipeBackActivity<IHouseListView, HouseListPresenterImpl> implements IHouseListView {

    private View mLastClickFilterView;

    private VerticalHouseListAdapter mAdapter;

    /**
     * 绑定View
     */
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvHouseList)
    RecyclerView mRVHouseList;

    private List<HouseBean> mShowHouseList = new ArrayList<>();
    private ShapeLoadingDialog mLoadingView;

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
        Intent data = getIntent();
        if (data != null){
            ArrayList<HouseBean> listExtra = data.getParcelableArrayListExtra(HouseBean.TAG);
            if (listExtra != null && listExtra.size() > 0){
                mShowHouseList.clear();
                mShowHouseList.addAll(listExtra);
                mAdapter.notifyDataSetChanged();
            }else {
                mPresenter.getHouseList();
            }
        }else {
            mPresenter.getHouseList();
        }
    }

    private void initView(){
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHouseList();
            }
        });
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorOfBlue);

        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        mAdapter = new VerticalHouseListAdapter(this, new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(HouseListActivity.this, HouseDetailActivity.class);
                intent.putExtra(HouseBean.TAG, mShowHouseList.get(pos));
                startActivity(intent);
            }
        });
        mAdapter.setData(mShowHouseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRVHouseList.setLayoutManager(layoutManager);
        //解决recycleView和refreshLayout的滑动冲突
        mRVHouseList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        mRVHouseList.setAdapter(mAdapter);
    }

    @Override
    protected HouseListPresenterImpl createPresenter() {
        return new HouseListPresenterImpl();
    }

    @Override
    public void showHouseList(List<HouseBean> list) {
        if (list != null && list.size() > 0){
            mShowHouseList.clear();
            mShowHouseList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void stopLoading() {
        mRefreshLayout.setRefreshing(false);
        mLoadingView.dismiss();
    }
}
