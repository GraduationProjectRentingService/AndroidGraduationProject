package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.OrderModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IOrderListPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.view.IOrderListView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public class OrderListPresenterImpl extends BasePresenter<IOrderListView> implements IOrderListPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void getOrderList() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        if (!getView().isUserLogin()){
            getView().showErrorMsg("请重新登录!");
            getView().stopLoading();
            getView().goLogin();
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null){
            getView().showErrorMsg("请重新登录!");
            getView().stopLoading();
            return;
        }

        getView().showLoading();
        OrderModelImpl.getInstance().getOrderList(userBean, new IModelCallback<List<OrderBean>>() {
            @Override
            public void onSuccess(List<OrderBean> data) {
                getView().stopLoading();
                getView().showOrderList(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }
}
