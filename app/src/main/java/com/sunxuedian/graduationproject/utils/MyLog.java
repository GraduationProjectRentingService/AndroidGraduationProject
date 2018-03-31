package com.sunxuedian.graduationproject.utils;

import android.util.Log;

/**
 * Created by 37 on 2018/1/11.
 */

public class MyLog {
    private String mTag;
    private boolean debug = true;//是否处于调试模式

    public MyLog(String tag, boolean debug){
        mTag = tag;
        this.debug = debug;
    }

    public void d(String msg){
        if (debug)
            Log.d(mTag, msg);
    }

    public void e(String msg){
        Log.e(mTag, msg);
    }

    public void i(String msg){
        if (debug)
        Log.i(mTag, msg);
    }

}
