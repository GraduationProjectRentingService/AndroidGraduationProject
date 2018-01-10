package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * The banner view is design to show some images cyclically.
 * Created by sunxuedian on 2018/1/7.
 */

public class BannerView extends RelativeLayout{

    private ViewPager viewPager;//viewpager，用于显示循环显示图片


    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){

    }


}
