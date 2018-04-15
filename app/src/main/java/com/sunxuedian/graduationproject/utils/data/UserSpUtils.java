package com.sunxuedian.graduationproject.utils.data;

import android.content.Context;
import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import static com.sunxuedian.graduationproject.utils.data.SharedPreferencesUtils.saveString;

/**
 * 使用SharedPreference操作用户的一些信息
 * Created by sunxuedian on 2018/3/23.
 */

public class UserSpUtils {

    private static MyLog logger = LoggerFactory.getLogger(UserSpUtils.class);

    private static UserBean mUserBean = new UserBean();//全局静态数据
    private static boolean mNeedLocalUserFormShared = true;//判断需不需要从中本地加载用户

    public static final String USER_PHONE = "phone";
    public static final String USER_ICON_URL = "user_head_icon_url";
    public static final String USER_NAME = "user_name";
    public static final String USER_SEX = "user_sex";

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
        //判断内存中的用户信息是否存在
        if (!mNeedLocalUserFormShared){
            return mUserBean;//存在则直接返回
        }

        //为空的时候，从内存中读取
        String phone = SharedPreferencesUtils.readString(context, USER_PHONE, "");
        if (TextUtils.isEmpty(phone)){
            return null;
        }

        mNeedLocalUserFormShared = false;//后面不需要从本地加载
        mUserBean.setPhoneNum(phone);
        mUserBean.setToken(SharedPreferencesUtils.readString(context, phone, ""));
        mUserBean.setIconUrl(SharedPreferencesUtils.readString(context, USER_ICON_URL, ""));
        mUserBean.setUserName(SharedPreferencesUtils.readString(context, USER_NAME, ""));
        mUserBean.setSex(SharedPreferencesUtils.readString(context, USER_SEX, ""));

        return mUserBean;
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

        //复制到内存中
        mUserBean.setPhoneNum(userBean.getPhoneNum());
        mUserBean.setToken(userBean.getToken());
        mUserBean.setUserName(userBean.getUserName());
        mUserBean.setSex(userBean.getSex());
        mUserBean.setIconUrl(userBean.getIconUrl());
        logger.d("saveUserToLocal: " + mUserBean.toString());

        boolean result = SharedPreferencesUtils.saveString(context, USER_PHONE, userBean.getPhoneNum());
        result = result && SharedPreferencesUtils.saveString(context, userBean.getPhoneNum(), userBean.getToken());
        result = result && SharedPreferencesUtils.saveString(context, USER_ICON_URL, userBean.getIconUrl());
        result = result && SharedPreferencesUtils.saveString(context, USER_SEX, userBean.getSex());
        result = result && SharedPreferencesUtils.saveString(context, USER_NAME, userBean.getUserName());
        return result;
    }

    /**
     * 退出登录操作，删除本地的缓冲数据
     */
    public static void logout(Context context){
        UserBean userBean = new UserBean();
        saveUserToLocal(context, userBean);
        mNeedLocalUserFormShared = true;//后面登录需要从本地加载
    }

}
