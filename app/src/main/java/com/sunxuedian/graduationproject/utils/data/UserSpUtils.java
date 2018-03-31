package com.sunxuedian.graduationproject.utils.data;

import android.content.Context;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

/**
 * 使用SharedPreference操作用户的一些信息
 * Created by sunxuedian on 2018/3/23.
 */

public class UserSpUtils {

    private static MyLog logger = LoggerFactory.getLogger(UserSpUtils.class);

    public static final String USER_PHONE = "phone";

    /**
     * 判断用户是否已经登录；
     * @param context
     * @return
     */
    public static boolean isUserLogin(Context context) {
        UserBean userBean = readLocalUser(context);
        return userBean != null && !TextUtils.isEmpty(userBean.getToken());
    }

    /**
     * 获取保存到本地的用户信息
     * @param context
     * @return
     */
    public static UserBean readLocalUser(Context context){
        String phone = SharedPreferencesUtils.readString(context, USER_PHONE, "");
        if (TextUtils.isEmpty(phone)){
            return null;
        }

        UserBean userBean = new UserBean();
        userBean.setPhoneNum(phone);
        userBean.setToken(SharedPreferencesUtils.readString(context, phone, ""));
        return userBean;
    }

    /**
     * 保存用户信息到本地
     * @param context
     * @param userBean
     * @return
     */
    public static boolean saveUserToLocal(Context context, UserBean userBean){
        if (userBean == null){
            logger.e("the user is null! ");
            return false;
        }

        return SharedPreferencesUtils.saveString(context, USER_PHONE, userBean.getPhoneNum())
                && SharedPreferencesUtils.saveString(context, userBean.getPhoneNum(), userBean.getToken());
    }


}
