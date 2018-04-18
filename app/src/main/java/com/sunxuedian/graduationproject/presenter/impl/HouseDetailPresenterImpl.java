package com.sunxuedian.graduationproject.presenter.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.HouseModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IHouseDetailPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.view.IHouseDetailView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/26.
 */

public class HouseDetailPresenterImpl extends BasePresenter<IHouseDetailView> implements IHouseDetailPresenter {
    private MyLog logger = LoggerFactory.getLogger(getClass());

    private IHouseModel mHouseModel;

    public HouseDetailPresenterImpl(){
        mHouseModel = HouseModelImpl.getInstance();
    }

    @Override
    public void getBannerImages() {

    }

    private boolean check(){
        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return false;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null || TextUtils.isEmpty(userBean.getToken())){
            getView().showErrorMsg("请先登录！");
            getView().goLogin();//前往登录
            return false;
        }

        HouseBean houseBean = getView().getHouseBean();
        if (houseBean == null){
            getView().showErrorMsg("当前房源信息为空！");
            return false;
        }

        return true;
    }

    @Override
    public void addHouseToLike() {

        if (!check()){
            return;
        }

        getView().showLoading();
        mHouseModel.addHouseToLike(getView().getUser(), getView().getHouseBean(), new IModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().showAddLikeSuccess();
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (UrlParamsUtils.TOKEN_ILLEGAL_CODE.equals(code)){
                    getView().onTokenIllegalView();
                }
            }
        });
    }

    @Override
    public void removeHouseFromLike() {

        if (!check()){
            return;
        }

        getView().showLoading();
        mHouseModel.removeHouseFromLike(getView().getUser(), getView().getHouseBean(), new IModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().showRemoveHouseFromLikeSuccess();
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (UrlParamsUtils.TOKEN_ILLEGAL_CODE.equals(code)){
                    getView().onTokenIllegalView();
                }
            }
        });

    }

    @Override
    public void isHouseInLike() {
        if (!check()){
            return;
        }

        getView().showLoading();
        mHouseModel.isHouseInLike(getView().getUser(), getView().getHouseBean(), new IModelCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                getView().stopLoading();
                getView().showLikeStatus(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
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
