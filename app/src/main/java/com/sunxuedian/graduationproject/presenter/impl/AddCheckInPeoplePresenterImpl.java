package com.sunxuedian.graduationproject.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.ICheckInPeopleModel;
import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.CheckInPeopleModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IAddCheckInPeoplePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.MyTextUtils;
import com.sunxuedian.graduationproject.utils.NetworkUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IAddCheckInPeopleView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public class AddCheckInPeoplePresenterImpl extends BasePresenter<IAddCheckInPeopleView> implements IAddCheckInPeoplePresenter {

    MyLog logger = LoggerFactory.getLogger(getClass());
    private Context mContext;
    private ICheckInPeopleModel mCheckInPeopleModel;

    public AddCheckInPeoplePresenterImpl(Context mContext) {
        this.mContext = mContext;
        mCheckInPeopleModel = new CheckInPeopleModelImpl();
    }

    @Override
    public void addCheckInPeople() {

        if (!isViewAttached()){
            logger.e("the View is not attached！");
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(mContext)){
            logger.e("network is not available!");
            getView().showNetworkError();
            return;
        }

        UserBean userBean = UserSpUtils.readLocalUser(mContext);
        if (userBean == null){
            logger.e("the user is null !");
            getView().onAddFailure("登录信息异常！请重新登录！");
            return;
        }

        String name = getView().getPeopleName();
        String idCard = getView().getIdCard();
        String phone = getView().getPhoneNum();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(idCard) || TextUtils.isEmpty(phone)){
            logger.e("不能为空！");
            getView().onAddFailure("姓名、身份证、手机号码不能为空！");
            return;
        }

        if (!MyTextUtils.isIDCardLegitimate(idCard)){
            getView().onAddFailure("身份证格式不对，请检查后再添加！");
            return;
        }

        if (!MyTextUtils.isPhoneNumLegitimate(phone)){
            getView().onAddFailure("手机号码格式不正常，请检查后再添加！");
            return;
        }

        CheckInPeopleUserInfo checkInPeopleUserInfo = new CheckInPeopleUserInfo(name, idCard, phone);

        getView().showLoading();
        mCheckInPeopleModel.addCheckInPeople(userBean.getPhoneNum(), userBean.getToken(), checkInPeopleUserInfo, new IModelCallback<CheckInPeopleUserInfo>() {
            @Override
            public void onSuccess(CheckInPeopleUserInfo data) {
                getView().stopLoading();
                getView().onAddSuccess(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().onAddFailure(msg);
            }
        });
    }
}
