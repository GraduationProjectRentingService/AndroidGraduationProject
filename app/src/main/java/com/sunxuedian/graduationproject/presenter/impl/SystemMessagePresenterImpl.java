package com.sunxuedian.graduationproject.presenter.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.MessageBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.impl.SystemMessageModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.ISystemMessagePresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.view.ISystemMessageView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public class SystemMessagePresenterImpl extends BasePresenter<ISystemMessageView> implements ISystemMessagePresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @Override
    public void getMessageList() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null || TextUtils.isEmpty(userBean.getToken())){
            getView().showErrorMsg("请先登录！");
            getView().onTokenIllegalView();
            return;
        }

        getView().showLoading();
        SystemMessageModelImpl.getInstance().getSystemMessage(userBean, new IModelCallback<List<MessageBean>>() {
            @Override
            public void onSuccess(List<MessageBean> data) {
                getView().stopLoading();
                getView().showMessages(data);
            }

            @Override
            public void onFailure(String msg) {
                getView().stopLoading();
                getView().showErrorMsg(msg);
            }

            @Override
            public void onResultCode(String code) {
                if (TextUtils.equals(code, UrlParamsUtils.TOKEN_ILLEGAL_CODE)){
                    getView().onTokenIllegalView();
                }
            }
        });
    }
}
