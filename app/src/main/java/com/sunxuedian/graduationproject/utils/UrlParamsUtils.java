package com.sunxuedian.graduationproject.utils;

/**
 * 接口地址和访问参数
 * Created by sunxuedian on 2018/4/1.
 */

public class UrlParamsUtils {

    public static final String SUCCESS_CODE = "1";//成功返回的值
    public static final String TOKEN_ILLEGAL_CODE = "11";//token过期

//    public static final String IP = "http://47.106.77.184:8080";//阿里云服务器ip
//    private static final String IP = "http://10.242.67.7:8080";// 校园网本地服务器ip
    private static final String IP = "http://10.4.10.49:8080";//公司本地服务器ip
//    private static final String IP = "http://192.168.43.227:8080";//手机WiFi ip

    public static final String URL_USER_REGISTER = IP + "/user/register";//注册
    public static final String URL_USER_LOGIN = IP + "/user/login";//登录
    public static final String URL_GET_USER_INFO = IP + "/user/getUserInfo";//获取用户信息
    public static final String URL_UPDATE_USER_INFO = IP + "/user/updateUserInfo";//保存用户信息

    public static final String URL_GET_ALL_HOUSE = IP + "/house/user/getHousehaveReviewed";//获取所有房源

    public static final String URL_CREATE_CHECK_IN_PEOPLE = IP + "/checkInPeople/create";//创建入住人信息
    public static final String URL_UPDATE_CHECK_IN_PEOPLE = IP + "/checkInPeople/update";//修改入住人信息
    public static final String URL_DELETE_CHECK_IN_PEOPLE = IP + "/checkInPeople/delete";//删除入住人信息
    public static final String URL_GET_ALL_CHECK_IN_PEOPLE = IP + "/checkInPeople/getAll";//获取所有入住人信息

    public static final String URL_CREATE_ORDER = IP + "/order/createOrder";//创建订单
    public static final String URL_GET_ALL_ORDERS = IP + "/order/getAllOrderByUserPhone";//获取所有订单
    public static final String URL_PAY_ORDER = IP + "/order/payOrder";//支付订单
    public static final String URL_CANCEL_ORDER = IP + "/order/cancelOrder";//取消订单

    public static final String URL_GET_ALL_LIKE_HOUSE = IP + "/like/getAll";//获取所有收藏房源列表
    public static final String URL_ADD_HOUSE_TO_LIKE = IP + "/like/addHouse";//添加房源到收藏列表中
    public static final String URL_REMOVE_HOUSE_FROM_LIKE = IP + "/like/deleteHouse";//将房源从收藏列表中移除

    public static final String USER_PHONE = "phoneNumber";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String TOKEN = "token";
    public static final String SEX = "sex";
    public static final String USER_ICON_URL = "pic";

    public static final String ID_CARD = "idCard";
    public static final String CHECK_IN_PEOPLE_PHONE = "phone";

}
