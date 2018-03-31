package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.HouseModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IHomepagePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.view.IHomepageView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public class HomepagePresenterImpl extends BasePresenter<IHomepageView> implements IHomepagePresenter{

    private MyLog logger = LoggerFactory.getLogger(this.getClass());
    private IHouseModel mHouseModel;

    public HomepagePresenterImpl(){
        mHouseModel = new HouseModelImpl();
    }

    @Override
    public void obtainBannerView() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getBannerViewData(new IModelCallback<List<BannerViewBean>>() {
            @Override
            public void onSuccess(List<BannerViewBean> data) {
                logger.e("onSuccess: " + Thread.currentThread().getName());
                getView().showBannerView(data);
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
    public void obtainTop10House() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getHouseData(IHouseModel.HOUSE_TYPE_TOP10, new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showTop10House(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }

    @Override
    public void obtainThemeHouse() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getHouseData(IHouseModel.HOUSE_TYPE_THEME, new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showThemeHouse(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }

    @Override
    public void obtainHotDestination() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getHotDestination(new IModelCallback<List<DestinationBean>>() {
            @Override
            public void onSuccess(List<DestinationBean> data) {
                getView().stopLoading();
                getView().showHotDestination(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }

    @Override
    public void obtainStoryAndHumanTouchHouse() {

        if (!isViewAttached()){
            logger.e("the view is not attached！");
            return;
        }

        getView().showLoading();

        mHouseModel.getHouseData(IHouseModel.HOUSE_TYPE_STORY_AND_HUMANTOUCH, new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showStoryAndHumanTouchHouse(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }
        });
    }

}
