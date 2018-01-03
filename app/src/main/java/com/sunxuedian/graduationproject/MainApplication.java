package com.sunxuedian.graduationproject;

import android.app.Application;

import com.sunxuedian.graduationproject.utils.ToastUtils;

/**
 * Created by sunxuedian on 2018/1/4.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}
