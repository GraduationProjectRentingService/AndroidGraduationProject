package com.sunxuedian.graduationproject.view.fragment;

import android.app.Fragment;
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
import com.sunxuedian.graduationproject.adapter.OrderListAdapter;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.OrderListPresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IOrderListView;
import com.sunxuedian.graduationproject.view.activity.LoginActivity;
import com.sunxuedian.graduationproject.view.activity.OrderDetailActivity;
import com.sunxuedian.graduationproject.view.activity.PayOrderActivity;
import com.sunxuedian.graduationproject.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunxuedian on 2018/4/4.
 */

public class OrderManagerFragment extends BaseFragment<IOrderListView, OrderListPresenterImpl> implements IOrderListView {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvOrders)
    RecyclerView mRvOrders;
    @BindView(R.id.tvHint)
    TextView mTvHint;
    @BindView(R.id.ivBack)
    ImageView mIvBack;

    @OnClick(R.id.ivBack)
    public void goBack(){
        getActivity().finish();
    }

    private OrderListAdapter mAdapter;
    private List<OrderBean> mOrders = new ArrayList<>();
    private boolean mCanGoBack  = true;

    public OrderManagerFragment(boolean canGoBack){
        mCanGoBack = canGoBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manager, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getOrderList();
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
                mPresenter.getOrderList();
            }
        });
        //解决recycleView和refreshLayout的滑动冲突
        mRvOrders.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mAdapter = new OrderListAdapter(getActivity(), mOrders, new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                OrderBean orderBean = mOrders.get(pos);
                Intent intent = new Intent();
                switch (orderBean.getStatus()){
                    case OrderBean.STATUS_CANCEL:
                    case OrderBean.STATUS_FINISH:
                        intent.setClass(getActivity(), OrderDetailActivity.class);
                        break;
                    case OrderBean.STATUS_UNPAY:
                        intent.setClass(getActivity(), PayOrderActivity.class);
                        break;
                }
                intent.putExtra(OrderBean.TAG, orderBean);//将订单传过到下个界面
                startActivity(intent);
            }
        });
        mRvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOrders.setAdapter(mAdapter);
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

    @Override
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(getActivity());
    }

    @Override
    public void showOrderList(List<OrderBean> list) {
        if (list != null && list.size() > 0 ){
            mOrders.clear();
            mOrders.addAll(list);
            mAdapter.notifyDataSetChanged();
            mTvHint.setVisibility(View.GONE);
        }else {
            mTvHint.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void goLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isUserLogin() {
        return UserSpUtils.isUserLogin(getActivity());
    }

    @Override
    public OrderListPresenterImpl createPresenter() {
        return new OrderListPresenterImpl();
    }

    @Override
    public void onTokenIllegalView() {
        goLogin();
    }
}
