package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by sunxuedian on 2018/3/16.
 */

public class MyScrollView extends ScrollView {

    private OnScrollChangeListener onScrollChangeListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null){
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener){
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener{
        void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
