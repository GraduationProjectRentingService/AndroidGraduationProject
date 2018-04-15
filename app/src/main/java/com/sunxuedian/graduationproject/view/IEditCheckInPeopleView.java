package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.UserBean;

/**
 * Created by sunxuedian on 2018/4/15.
 */

public interface IEditCheckInPeopleView extends IProgressView{
    void onUpdateCheckInPeopleSuccess();
    void onDeleteCheckInPeopleSuccess();
    void showErrorMsg(String msg);
    UserBean getUser();
    CheckInPeopleUserInfo getCheckInPeople();
    String getName();
    String getPhone();
    String getIdCard();
}
