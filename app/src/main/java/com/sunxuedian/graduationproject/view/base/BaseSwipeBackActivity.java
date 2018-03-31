package com.sunxuedian.graduationproject.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.presenter.BasePresenter;

/**
 * Created by sunxuedian on 2018/1/28.
 */

public abstract class BaseSwipeBackActivity<V, P extends BasePresenter> extends SwipeBackActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);//View和Presenter建立关联
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解除View和Presenter的关联，避免内存泄漏
    }
}
