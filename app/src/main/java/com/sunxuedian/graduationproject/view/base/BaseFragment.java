package com.sunxuedian.graduationproject.view.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.sunxuedian.graduationproject.presenter.BasePresenter;

/**
 * Created by sunxuedian on 2018/2/27.
 */

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
