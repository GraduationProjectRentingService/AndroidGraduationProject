package com.sunxuedian.graduationproject.utils;

/**
 * 错误码的工具类
 * Created by SUN on 2017/4/26.
 */

public class ResultCodeUtils {
    /**
     * Bmob 对应的错误代码
     */
    public static final int BMOB_APP_KEY_NULL = 9001;//application id为空
    public static final int BMOB_PARSE_DATA_ERROR = 9002;//解析返回的数据出错
    public static final int BMOB_UPLOAD_FILE_FAILURE = 9004;//上传文件失败
    public static final int BMOB_UPLOAD_FILE_ERROR =  9003;//上传文件出错
    public static final int BMOB_UPLOAE_FILE_NO_EXIST = 9008;//上传的文件不存在
    public static final int BMOB_TIME_OUT = 9010;//网络超时
    public static final int BMOB_NOT_NETWORK = 9016;//无网络连接
    public static final int BMOB_OBJECT_EXIST = 401;//对象已经存在，也是唯一键存在
}
