package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.model.IModelCallback;
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
        mHouseModel = new HouseModelImpl();
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
}
