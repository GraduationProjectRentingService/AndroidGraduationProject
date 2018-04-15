package com.sunxuedian.graduationproject;

import android.app.Application;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.aitangba.swipeback.SwipeBackActivity;
import com.mob.MobApplication;
import com.sunxuedian.graduationproject.utils.SDKUtils;
import com.sunxuedian.graduationproject.utils.ToastUtils;

import cn.bmob.v3.Bmob;

/**
 * Created by sunxuedian on 2018/1/4.
 */

public class MainApplication extends MobApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
        //初始化Bomb
        Bmob.initialize(this, SDKUtils.BmobApplicationID);
    }
}
