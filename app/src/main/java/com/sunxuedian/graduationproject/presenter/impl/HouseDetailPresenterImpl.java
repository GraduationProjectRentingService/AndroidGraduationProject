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

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getBannerViewData(new IModelCallback<List<BannerViewBean>>() {
            @Override
            public void onSuccess(List<BannerViewBean> data) {
                logger.e("onSuccess: " + Thread.currentThread().getName());
                getView().showBannerImages(data);
                getView().stopLoading();
            }

            @Override
            public void onFailure(String msg) {
                getView().showErrorMsg(msg);
                getView().stopLoading();
            }
        });
    }

    @Override
    public void addHouseToLike() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null || TextUtils.isEmpty(userBean.getToken())){
            getView().showErrorMsg("请先登录！");
            getView().goLogin();//前往登录
            return;
        }

        HouseBean houseBean = getView().getHouseBean();
        if (houseBean == null){
            getView().showErrorMsg("当前房源信息为空！");
            return;
        }

        mHouseModel.addHouseToLike(userBean, houseBean, new IModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().showAddLikeSuccess();
            }

            @Override
            public void onFailure(String msg) {
                getView().showErrorMsg(msg);
            }
        });
    }
}
