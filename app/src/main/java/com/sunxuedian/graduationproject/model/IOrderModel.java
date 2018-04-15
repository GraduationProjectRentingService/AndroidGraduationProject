package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public interface IOrderModel {
    void createOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback);//创建订单
    void payOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback);//支付订单
    void cancelOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback);//取消订单
    void getOrderList(UserBean userBean, IModelCallback<List<OrderBean>> callback);//获取订单列表
}
