package com.sunxuedian.graduationproject.presenter.impl;

import android.content.Context;

import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.ICheckInPeopleModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.CheckInPeopleModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.ICheckInPeoplesPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.NetworkUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.ICheckInPeoplesView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/2.
 */

public class CheckInPeoplesPresenterImpl extends BasePresenter<ICheckInPeoplesView> implements ICheckInPeoplesPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());
    private Context mContext;
    private ICheckInPeopleModel mCheckInPeopleModel;

    public CheckInPeoplesPresenterImpl(Context mContext) {
        this.mContext = mContext;
        mCheckInPeopleModel = CheckInPeopleModelImpl.getInstance();
    }

    @Override
    public void getAllCheckInPeoples() {

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
            getView().showError("登录信息异常！请重新登录！");
            return;
        }

        getView().showLoading();
        mCheckInPeopleModel.getCheckInPeoples(userBean.getPhoneNum(), userBean.getToken(), new IModelCallback<List<CheckInPeopleUserInfo>>() {
            @Override
            public void onSuccess(List<CheckInPeopleUserInfo> data) {
                getView().stopLoading();
                getView().showAllCheckInPeoples(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (UrlParamsUtils.TOKEN_ILLEGAL_CODE.equals(code)){
                    getView().onTokenIllegalView();
                }
            }
        });
    }
}
