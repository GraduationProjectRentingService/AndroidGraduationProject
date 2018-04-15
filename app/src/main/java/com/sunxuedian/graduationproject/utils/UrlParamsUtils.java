package com.sunxuedian.graduationproject.utils;

/**
 * 接口地址和访问参数
 * Created by sunxuedian on 2018/4/1.
 */

public class UrlParamsUtils {

    public static final String SUCCESS_CODE = "1";//成功返回的值

    public static final String IP = "http://47.106.77.184:8080";//阿里云服务器ip
//    private static final String IP = "http://10.242.67.7:8080";//本地服务器ip

    public static final String URL_USER_REGISTER = IP + "/user/register";//注册
    public static final String URL_USER_LOGIN = IP + "/user/login";//登录
    public static final String URL_GET_USER_INFO = IP + "/user/getUserInfo";//获取用户信息
    public static final String URL_UPDATE_USER_INFO = IP + "/user/updateUserInfo";//保存用户信息

    public static final String URL_GET_ALL_HOUSE = IP + "/house/user/getHousehaveReviewed";//获取所有房源

    public static final String USER_PHONE = "phoneNumber";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String TOKEN = "token";
    public static final String SEX = "sex";
    public static final String USER_ICON_URL = "pic";

}
