package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public interface IPayOrderView extends IProgressView{
    UserBean getUser();
    OrderBean getOrderBean();
    int getPayWay();
    void onPaySuccess(OrderBean orderBean);
    void onCancelOrderSuccess(OrderBean orderBean);
    void showErrorMsg(String msg);
}
