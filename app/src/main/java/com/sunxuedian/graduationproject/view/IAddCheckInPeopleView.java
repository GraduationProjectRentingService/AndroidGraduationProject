package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public interface IAddCheckInPeopleView extends INetworkErrorView, IProgressView, ITokenIllegalView{
    void onAddSuccess(CheckInPeopleUserInfo info);
    void onAddFailure(String msg);
    String getPeopleName();
    String getIdCard();
    String getPhoneNum();
}
