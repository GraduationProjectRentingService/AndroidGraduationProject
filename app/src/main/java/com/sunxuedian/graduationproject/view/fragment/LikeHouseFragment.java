package com.sunxuedian.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.LikeHouseListAdapter;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.LikeHousePresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.ILikeHouseView;
import com.sunxuedian.graduationproject.view.activity.HouseDetailActivity;
import com.sunxuedian.graduationproject.view.activity.LoginActivity;
import com.sunxuedian.graduationproject.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunxuedian on 2018/4/4.
 */

public class LikeHouseFragment extends BaseFragment<ILikeHouseView, LikeHousePresenterImpl> implements ILikeHouseView{

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvLikeHouses)
    RecyclerView mRvLikeHouses;
    @BindView(R.id.tvHint)
    TextView mTvHint;
    @BindView(R.id.ivBack)
    ImageView mIvBack;

    @OnClick(R.id.ivBack)
    public void goBack(){
        getActivity().finish();
    }

    private LikeHouseListAdapter mAdapter;
    private List<HouseBean> mLikeHouses = new ArrayList<>();
    private boolean mCanGoBack = true;

    public LikeHouseFragment(boolean canBack){
        mCanGoBack = canBack;//设置是否可以返回
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_house, container, false);
        ButterKnife.bind(this, view);
        initView();
        mPresenter.getLikeHouseList();
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            if (!isUserLogin()){
                goLoginView();
            }
        }
        super.onHiddenChanged(hidden);
    }

    private void initView(){

        if (mCanGoBack){
            mIvBack.setVisibility(View.VISIBLE);
        }else {
            mIvBack.setVisibility(View.GONE);
            mIvBack.setClickable(false);
        }

        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorOfBlue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getLikeHouseList();
            }
        });

        mAdapter = new LikeHouseListAdapter(getActivity(), new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra(HouseBean.TAG, mLikeHouses.get(pos));
                startActivity(intent);
            }
        });
        mAdapter.setData(mLikeHouses);
        mRvLikeHouses.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvLikeHouses.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        mRvLikeHouses.setAdapter(mAdapter);
    }

    @Override
    public boolean isUserLogin() {
        return UserSpUtils.isUserLogin(getActivity());
    }

    @Override
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(getActivity());
    }

    @Override
    public void showHouseList(List<HouseBean> list) {
        if (list != null && list.size() > 0){
            mTvHint.setVisibility(View.GONE);
            mLikeHouses.clear();
            mLikeHouses.addAll(list);
            mAdapter.notifyDataSetChanged();
        }else {
            mTvHint.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void goLoginView() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public LikeHousePresenterImpl createPresenter() {
        return new LikeHousePresenterImpl();
    }

    @Override
    public void showLoading() {
        if (!mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(true);
        }

    }

    @Override
    public void stopLoading() {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
    }
}
