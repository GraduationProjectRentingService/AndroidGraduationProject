package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public interface ICheckInPeoplesView extends INetworkErrorView, IProgressView {
    void showAllCheckInPeoples(List<CheckInPeopleUserInfo> list);
    void showError(String msg);
}
