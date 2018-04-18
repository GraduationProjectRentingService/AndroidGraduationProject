package com.sunxuedian.graduationproject.presenter.impl;

import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.HouseModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.ILikeHousePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.view.ILikeHouseView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/12.
 */

public class LikeHousePresenterImpl extends BasePresenter<ILikeHouseView> implements ILikeHousePresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void getLikeHouseList() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        if (!getView().isUserLogin()){
            getView().showErrorMsg("请先登录！");
            getView().stopLoading();
            getView().goLoginView();
            return;
        }

        getView().showLoading();
        HouseModelImpl.getInstance().getLikeHouseList(getView().getUser(), new IModelCallback<List<HouseBean>>() {
            @Override
            public void onSuccess(List<HouseBean> data) {
                getView().stopLoading();
                getView().showHouseList(data);
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
