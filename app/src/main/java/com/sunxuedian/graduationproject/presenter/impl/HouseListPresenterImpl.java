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

        mHouseModel.getHouseData(0, new IModelCallback<List<HouseBean>>() {
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
        });
    }
}
