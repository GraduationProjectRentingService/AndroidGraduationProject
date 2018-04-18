package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/26.
 */

public interface IHouseDetailView extends IProgressView , ITokenIllegalView{
    void showBannerImages(List<BannerViewBean> list);
    void showErrorMsg(String msg);
    HouseBean getHouseBean();
    UserBean getUser();
    void goLogin();//前往登录
    void showAddLikeSuccess();
    void showRemoveHouseFromLikeSuccess();
    void showLikeStatus(boolean isLike);
}
