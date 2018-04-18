package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/12.
 */

public interface ILikeHouseView extends IProgressView,ITokenIllegalView{
    boolean isUserLogin();
    UserBean getUser();
    void showHouseList(List<HouseBean> list);
    void goLoginView();//跳转到登录界面
    void showErrorMsg(String msg);//显示错误信息
}
