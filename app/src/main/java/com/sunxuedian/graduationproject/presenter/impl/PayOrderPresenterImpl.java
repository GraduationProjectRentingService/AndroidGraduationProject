package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.OrderModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IPayOrderPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.view.IPayOrderView;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public class PayOrderPresenterImpl extends BasePresenter<IPayOrderView> implements IPayOrderPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void payOrder() {

        if (!isViewAttached()){
            logger.e("the view is not attached");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null){
            getView().showErrorMsg("用户登录信息过期！请重新登录！");
            return;
        }

        OrderBean orderBean = getView().getOrderBean();
        if (orderBean == null){
            logger.e("订单出错！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        if (getView().getPayWay() == OrderBean.ALI_PAY_CODE){
            orderBean.setPayWay("支付宝支付");
        }else {
            orderBean.setPayWay("微信支付");
        }
        orderBean.setPayWayCode(getView().getPayWay());

        getView().showLoading();

        OrderModelImpl.getInstance().payOrder(userBean, orderBean, new IModelCallback<OrderBean>() {
            @Override
            public void onSuccess(OrderBean data) {
                getView().stopLoading();
                getView().onPaySuccess(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (UrlParamsUtils.TOKEN_ILLEGAL_CODE.equals(code)){
                    getView().onTokenIllegalView();
                }
            }
        });

    }

    @Override
    public void cancelOrder() {

        if (!isViewAttached()){
            logger.e("the view is not attached");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null){
            getView().showErrorMsg("用户登录信息过期！请重新登录！");
            return;
        }

        OrderBean orderBean = getView().getOrderBean();
        if (orderBean == null){
            logger.e("订单出错！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        getView().showLoading();
        OrderModelImpl.getInstance().cancelOrder(userBean, orderBean, new IModelCallback<OrderBean>() {
            @Override
            public void onSuccess(OrderBean data) {
                getView().stopLoading();
                getView().onCancelOrderSuccess(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (UrlParamsUtils.TOKEN_ILLEGAL_CODE.equals(code)){
                    getView().onTokenIllegalView();
                }
            }
        });

    }

}
