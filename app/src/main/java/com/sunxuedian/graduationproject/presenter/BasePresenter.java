package com.sunxuedian.graduationproject.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;

    /**
     * 判断View
     * @param view 视图层对象
     */
    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);//建立关联
    }

    /**
     * 解绑View
     */
    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 获取View对象
     * @return
     */
    protected V getView(){
        return mViewRef.get();
    }

    /**
     * 判断是否已经和View建立关联
     * @return
     */
    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

}
