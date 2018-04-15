package com.sunxuedian.graduationproject.model.impl;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.model.ICheckInPeopleModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public class CheckInPeopleModelImpl implements ICheckInPeopleModel {

    static List<CheckInPeopleUserInfo> mData = new ArrayList<>();

    static {
        mData.add(new CheckInPeopleUserInfo("张三", "445122189004015212", "15512345678"));
    }

    @Override
    public void getCheckInPeoples(String phoneNum, String token, IModelCallback<List<CheckInPeopleUserInfo>> iModelCallback) {
        for (CheckInPeopleUserInfo info: mData){
            info.setCheck(false);
        }
        iModelCallback.onSuccess(mData);
    }

    @Override
    public void addCheckInPeople(String phoneNum, String token, CheckInPeopleUserInfo info, IModelCallback<CheckInPeopleUserInfo> iModelCallback) {
        // TODO: 2018/4/2 测试
        mData.add(info);
        iModelCallback.onSuccess(info);
    }
}
