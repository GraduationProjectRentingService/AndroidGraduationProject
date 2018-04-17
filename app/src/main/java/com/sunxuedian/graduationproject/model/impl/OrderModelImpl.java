package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.IOrderModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.utils.JsonUtils;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public class OrderModelImpl implements IOrderModel {

    private MyLog logger = LoggerFactory.getLogger(getClass());
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
    public void createOrder(UserBean userBean, OrderBean orderBean, final IModelCallback<OrderBean> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put(UrlParamsUtils.USER_PHONE, userBean.getPhoneNum());
        params.put(UrlParamsUtils.TOKEN, userBean.getToken());
        params.put("houseId", orderBean.getHouseId());
        params.put("checkInDate", orderBean.getCheckInDate().toString().substring(0,10));
        params.put("checkOutDate", orderBean.getCheckOutDate().toString().substring(0, 10));
        params.put("checkInPeopleIdList", orderBean.getCheckInPeopleIdList());
        params.put("order", orderBean);
        logger.d(params.toString());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_CREATE_ORDER, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(), UrlParamsUtils.SUCCESS_CODE)){
                    try {
                        OrderBean order = JsonUtils.fromJson(OrderBean.class, responseBean.getContent().optJSONObject("order"));
                        order.setCheckInPeopleUserInfoList(JsonUtils.getListByJSONArray(CheckInPeopleUserInfo.class, responseBean.getContent().optJSONObject("order").getJSONArray("checkInPeopleUserInfoList")));
                        callback.onSuccess(order);
                    }catch (Exception e){
                        e.printStackTrace();
                        callback.onFailure("内部错误！");
                    }
                }else{
                    callback.onFailure(responseBean.getMessage());
                }
            }
        });
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
