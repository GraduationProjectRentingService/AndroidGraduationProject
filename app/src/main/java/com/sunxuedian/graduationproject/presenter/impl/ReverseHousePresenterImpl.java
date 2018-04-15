package com.sunxuedian.graduationproject.presenter.impl;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.OrderModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IReverseHousePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.view.IReverseHouseView;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public class ReverseHousePresenterImpl extends BasePresenter<IReverseHouseView> implements IReverseHousePresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void createOrder() {
        if (!isViewAttached()){
            logger.e("the view is not attached");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null){
            getView().showErrorMsg("用户登录信息过期！请重新登录！");
            return;
        }

        HouseBean houseBean = getView().getHouseBean();
        if (houseBean == null){
            logger.e("House 信息为空！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        List<CalendarDay> calendarDays = getView().getCheckInOutDate();
        if (calendarDays == null){
            logger.e("入住日期为空！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        List<CheckInPeopleUserInfo> checkInPeopleUsers = getView().getCheckInPeoples();
        if (checkInPeopleUsers == null){
            logger.e("入住人信息为空！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        OrderBean orderBean = new OrderBean();
        CalendarDay inDay = calendarDays.get(0);
        CalendarDay outDay = calendarDays.get(calendarDays.size() - 1);
        orderBean.setDayNum(calendarDays.size() - 1);//设置入住天数，及入住日期
        orderBean.setCheckInDate(new Timestamp(inDay.getDate().getTime()));
        orderBean.setCheckOutDate(new Timestamp(outDay.getDate().getTime()));
        //设置用户和房东信息
        orderBean.setUserPhone(userBean.getPhoneNum());
        orderBean.setUserName(userBean.getUserName());
        orderBean.setHostPhone(houseBean.getHostPhoneNum());
        //设置入住人信息
        orderBean.setCheckInPeopleUserInfoList(checkInPeopleUsers);
        //设置订单押金和金额
        orderBean.setDeposit(houseBean.getDeposit());
        orderBean.setTotalHouseMoney(houseBean.getMoneyOfEachNight() * orderBean.getDayNum());
        orderBean.setTotalMoney(houseBean.getMoneyOfEachNight() * orderBean.getDayNum() + houseBean.getDeposit());
        //设置房源信息
        orderBean.setHouseTitle(houseBean.getTitle());
        orderBean.setHouseRentalType(houseBean.getRentalTypeText());
        orderBean.setHouseLocation(houseBean.getLocation());
        orderBean.setHouseImgUrl(houseBean.getImgUrl());
        logger.d("创建的订单信息如下：" + orderBean.toString());

        getView().showLoading();
        OrderModelImpl.getInstance().createOrder(userBean, orderBean, new IModelCallback<OrderBean>() {
            @Override
            public void onSuccess(OrderBean data) {
                getView().stopLoading();
                getView().onCreateOrderSuccess(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }
}
