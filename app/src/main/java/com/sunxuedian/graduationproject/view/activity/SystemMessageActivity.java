package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.MessageAdapter;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.bean.MessageBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.SystemMessagePresenterImpl;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.ISystemMessageView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemMessageActivity extends BaseSwipeBackActivity<ISystemMessageView, SystemMessagePresenterImpl> implements ISystemMessageView {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvMessages)
    RecyclerView mRvMessages;
    @BindView(R.id.tvHint)
    TextView mTvHint;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    private MessageAdapter mAdapter;
    private List<MessageBean> mMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAdapter = new MessageAdapter(this, mMessages);
        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(SystemMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("title", mMessages.get(pos).getMessageTitle());
                intent.putExtra("message", mMessages.get(pos).getMessageContent());
                startActivity(intent);
            }
        });
        mRvMessages.setAdapter(mAdapter);
        mRvMessages.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorOfBlue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMessageList();
            }
        });

        //解决recycleView和refreshLayout的滑动冲突
        mRvMessages.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMessageList();
    }

    @Override
    protected SystemMessagePresenterImpl createPresenter() {
        return new SystemMessagePresenterImpl();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void stopLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onTokenIllegalView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessages(List<MessageBean> list) {
        if (list != null){
            if (list.size() > 0){
                mMessages.clear();
                mMessages.addAll(list);
                mAdapter.notifyDataSetChanged();
                mTvHint.setVisibility(View.INVISIBLE);
            }else {
                mMessages.clear();
                mAdapter.notifyDataSetChanged();
                mTvHint.setVisibility(View.VISIBLE);
            }
        }else {
            mTvHint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(this);
    }
}
