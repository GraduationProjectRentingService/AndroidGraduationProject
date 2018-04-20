package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.HouseModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IHouseListPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.view.IHouseListView;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public class HouseListPresenterImpl extends BasePresenter<IHouseListView> implements IHouseListPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private IHouseModel mHouseModel;

    public HouseListPresenterImpl(){
        mHouseModel = HouseModelImpl.getInstance();
    }

    @Override
    public void getHouseList() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        getView().showLoading();

        mHouseModel.getHouseData(IHouseModel.HOUSE_TYPE_ALL, new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showHouseList(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }

            @Override
            public void onResultCode(String code) {

            }
        });
    }

    @Override
    public void searchHouse(int type, String key, boolean isRefresh) {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        logger.d("type: " + type + "key：" + key);
        getView().showLoading();

        mHouseModel.searchHouse(type, key, isRefresh, new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showHouseList(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }

            @Override
            public void onResultCode(String code) {

            }
        });
    }
}
