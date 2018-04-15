package com.sunxuedian.graduationproject.model.impl;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.IOrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public class OrderModelImpl implements IOrderModel {

    private static List<OrderBean> mOrderList = new ArrayList<>();//保存订单信息的列表
    private static int mOrderCount = 0;

    private static OrderModelImpl mInstance;
    private OrderModelImpl(){}

    public static OrderModelImpl getInstance(){
        if (mInstance == null){
            synchronized (OrderModelImpl.class){
                if (mInstance == null){
                    mInstance = new OrderModelImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void createOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback) {
        orderBean.setStatus(OrderBean.STATUS_UNPAY);
        orderBean.setOrderId(mOrderCount++);
        mOrderList.add(orderBean);
        callback.onSuccess(orderBean);// TODO: 2018/4/13 测试数据
    }

    @Override
    public void payOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback) {
        if (mOrderList.contains(orderBean)){
            OrderBean resultOrder = mOrderList.get(mOrderList.indexOf(orderBean));
            resultOrder.setPayWayCode(orderBean.getPayWayCode());
            resultOrder.setPayWay(orderBean.getPayWay());
            resultOrder.setStatus(OrderBean.STATUS_FINISH);
        }
        callback.onSuccess(orderBean);
    }

    @Override
    public void cancelOrder(UserBean userBean, OrderBean orderBean, IModelCallback<OrderBean> callback) {
        if (mOrderList.contains(orderBean)){
            orderBean = mOrderList.get(mOrderList.indexOf(orderBean));
            orderBean.setStatus(OrderBean.STATUS_CANCEL);
        }
        callback.onSuccess(orderBean);
    }

    @Override
    public void getOrderList(UserBean userBean, IModelCallback<List<OrderBean>> callback) {
        callback.onSuccess(mOrderList);
    }
}
