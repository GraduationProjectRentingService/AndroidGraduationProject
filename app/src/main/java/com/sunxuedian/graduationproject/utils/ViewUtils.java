package com.sunxuedian.graduationproject.utils;

import android.view.View;

import com.sunxuedian.graduationproject.bean.ViewSizeBean;

/**
 * Created by sunxuedian on 2018/3/16.
 */

public class ViewUtils {

    /**
     * 获取View的尺寸
     * @param view
     * @return
     */
    public static ViewSizeBean getViewSize(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        ViewSizeBean bean = new ViewSizeBean();
        bean.setHeight(view.getMeasuredHeight());
        bean.setWidth(view.getMeasuredWidth());
        return bean;
    }
}
