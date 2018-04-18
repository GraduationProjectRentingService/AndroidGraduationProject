package com.sunxuedian.graduationproject.presenter.impl;

import android.content.Context;

import com.sunxuedian.graduationproject.bean.MyFile;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.IUploadFileModel;
import com.sunxuedian.graduationproject.model.callback.IFindListener;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.callback.IUploadFileListener;
import com.sunxuedian.graduationproject.model.impl.UploadFileModelImpl;
import com.sunxuedian.graduationproject.model.impl.UserModelImpl;
import com.sunxuedian.graduationproject.presenter.BasePresenter;
import com.sunxuedian.graduationproject.presenter.IUserInfoPresenter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;
import com.sunxuedian.graduationproject.view.IUserInfoView;

import java.io.File;
import java.util.List;

/**
 * Created by sunxuedian on 2018/4/14.
 */

public class UserInfoPresenterImpl extends BasePresenter<IUserInfoView> implements IUserInfoPresenter {

    private MyLog logger = LoggerFactory.getLogger(getClass());
    private IUploadFileModel mUploadFileModel;

    public UserInfoPresenterImpl(Context context){
        mUploadFileModel = new UploadFileModelImpl(context);
    }

    @Override
    public void uploadImg() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        try{
            getView().showLoading();
            final String filePath = getView().getChooseImgLocalPath();
            File file = new File(filePath);
            mUploadFileModel.searchFile(file.getName(), new IFindListener() {
                @Override
                public void onSuccess(List list) {
                    if (list.size() == 0){
                        onUploadFile(filePath);
                    }else{
                        MyFile item = (MyFile) list.get(0);
                        getView().stopLoading();
                        getView().onFileUploadSuccess(item.getFileUrl());
                    }
                }

                @Override
                public void onFailed(String msg) {
                    getView().stopLoading();
                    getView().onFileUploadFailed(msg);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            getView().stopLoading();
            getView().onFileUploadFailed("上传的文件不存在！");
        }
    }

    /**
     * 上传文件到Bmob云
     * @param filePath
     */
    private void onUploadFile(String filePath){
        mUploadFileModel.uploadFile(filePath, new IUploadFileListener() {
            @Override
            public void onSuccess(String fileUrl) {
                getView().stopLoading();
                getView().onFileUploadSuccess(fileUrl);
            }

            @Override
            public void onProgress(Integer value) {
                getView().stopLoading();
                getView().showFileUploadProgress(value);
            }

            @Override
            public void onFailed(String msg) {
                getView().stopLoading();
                getView().onFileUploadFailed(msg);
            }
        });
    }

    @Override
    public void updateUserInfo() {
        if (!isViewAttached()){
            logger.e("the view is not attached!");
            return;
        }

        UserBean userBean = getView().getUser();
        if (userBean == null){
            logger.e("出错！");
            getView().showErrorMsg("内部错误！请退出重试！");
            return;
        }

        getView().showLoading();
        UserModelImpl.getInstance().saveUserInfo(userBean, new IModelCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean data) {
                getView().stopLoading();
                getView().onUpdateUserInfoSuccess(data);
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
