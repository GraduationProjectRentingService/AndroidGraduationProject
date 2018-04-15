package com.sunxuedian.graduationproject.presenter.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.CheckInPeopleModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IEditCheckInPeoplePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.view.IEditCheckInPeopleView;

import java.util.List;

import static cn.smssdk.utils.a.c;

/**
 * Created by sunxuedian on 2018/4/15.
 */

public class EditCheckInPeoplePresenterImpl extends BasePresenter<IEditCheckInPeopleView> implements IEditCheckInPeoplePresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void updateCheckInPeopleInfo() {

        if (!isViewAttached()){
            logger.e("the view is not attached! ");
            return;
        }

        CheckInPeopleUserInfo checkInPeopleUserInfo = getView().getCheckInPeople();
        UserBean userBean = getView().getUser();

        String name = getView().getName();
        if (TextUtils.isEmpty(name)){
            getView().showErrorMsg("姓名不能为空！");
            return;
        }

        String phone = getView().getPhone();
        if (!MyTextUtils.isPhoneNumLegitimate(phone)){
            getView().showErrorMsg("手机格式有误！");
            return;
        }

        String idCard = getView().getIdCard();
        if (!MyTextUtils.isIDCardLegitimate(idCard)){
            getView().showErrorMsg("身份证格式有误！");
            return;
        }

        checkInPeopleUserInfo.setName(name);
        checkInPeopleUserInfo.setPhone(phone);
        checkInPeopleUserInfo.setIdCard(idCard);

        getView().showLoading();
        CheckInPeopleModelImpl.getInstance().updateCheckInPeople(userBean, checkInPeopleUserInfo, new IModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().onUpdateCheckInPeopleSuccess();
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }

    @Override
    public void deleteCheckInPeople() {
        if (!isViewAttached()){
            logger.e("the view is not attached! ");
            return;
        }

        CheckInPeopleUserInfo checkInPeopleUserInfo = getView().getCheckInPeople();
        UserBean userBean = getView().getUser();

        getView().showLoading();
        CheckInPeopleModelImpl.getInstance().deleteCheckInPeople(userBean, checkInPeopleUserInfo, new IModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().onDeleteCheckInPeopleSuccess();
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }
}
