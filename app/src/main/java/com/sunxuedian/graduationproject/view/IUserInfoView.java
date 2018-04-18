package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.UserBean;

/**
 * Created by sunxuedian on 2018/4/14.
 */

public interface IUserInfoView extends IUploadFileView, IProgressView, ITokenIllegalView {
    void onUpdateUserInfoSuccess(UserBean userBean);
    void showErrorMsg(String msg);
    String getChooseImgLocalPath();//获取选中的图片路径
    UserBean getUser();//获取选中的性别
}
