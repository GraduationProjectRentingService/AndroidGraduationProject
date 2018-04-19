package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.MessageBean;
import com.sunxuedian.graduationproject.bean.UserBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public interface ISystemMessageView extends IProgressView, ITokenIllegalView {
    void showMessages(List<MessageBean> list);
    void showErrorMsg(String msg);
    UserBean getUser();
}
