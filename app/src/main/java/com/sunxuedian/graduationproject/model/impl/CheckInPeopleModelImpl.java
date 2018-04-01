package com.sunxuedian.graduationproject.model.impl;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.model.ICheckInPeopleModel;
import com.sunxuedian.graduationproject.model.IModelCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public class CheckInPeopleModelImpl implements ICheckInPeopleModel {

    static List<CheckInPeopleUserInfo> mData = new ArrayList<>();

    @Override
    public void getCheckInPeoples(String phoneNum, String token, IModelCallback<List<CheckInPeopleUserInfo>> iModelCallback) {
        iModelCallback.onSuccess(mData);
    }

    @Override
    public void addCheckInPeople(String phoneNum, String token, CheckInPeopleUserInfo info, IModelCallback<CheckInPeopleUserInfo> iModelCallback) {
        // TODO: 2018/4/2 测试
        mData.add(info);
        iModelCallback.onSuccess(info);
    }
}
