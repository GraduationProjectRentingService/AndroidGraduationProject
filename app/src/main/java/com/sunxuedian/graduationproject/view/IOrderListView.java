package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public interface IOrderListView extends IProgressView,ITokenIllegalView{
    UserBean getUser();
    void showOrderList(List<OrderBean> list);
    void showErrorMsg(String msg);
    void goLogin();
    boolean isUserLogin();
}
