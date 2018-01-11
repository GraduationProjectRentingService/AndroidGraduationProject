package com.sunxuedian.graduationproject.utils;


/**
 * Created by 37 on 2018/1/11.
 */

public class LoggerFactory {

    /**
     * 工厂模式新建Log类
     * @param clazz
     * @return
     */
    public static MyLog getLogger(Class clazz){
        return getLogger(clazz, true);
    }

    /**
     * 工厂模式新建Log类
     * @param clazz
     * @param debug 是否调试
     * @return
     */
    public static MyLog getLogger(Class clazz, boolean debug){
        return new MyLog(clazz.getSimpleName(), debug);
    }
}
