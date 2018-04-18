package com.sunxuedian.graduationproject.view;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/13.
 */

public interface IReverseHouseView extends IProgressView, ITokenIllegalView{
    UserBean getUser();
    HouseBean getHouseBean();
    List<CalendarDay> getCheckInOutDate();
    List<CheckInPeopleUserInfo> getCheckInPeoples();
    void onCreateOrderSuccess(OrderBean orderBean);
    void showErrorMsg(String msg);
}
