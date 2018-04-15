package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public interface ICheckInPeopleModel {
    void getCheckInPeoples(String phoneNum, String token, IModelCallback<List<CheckInPeopleUserInfo>> iModelCallback);//获取所有入住人的信息
    void addCheckInPeople(String phoneNum, String token, CheckInPeopleUserInfo info, IModelCallback<CheckInPeopleUserInfo> iModelCallback);//添加一个入住人信息
}
