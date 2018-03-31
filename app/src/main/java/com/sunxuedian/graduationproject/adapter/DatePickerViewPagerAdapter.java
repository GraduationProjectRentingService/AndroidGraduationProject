package com.sunxuedian.graduationproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/30.
 */

public class DatePickerViewPagerAdapter extends PagerAdapter {

    private List<View> mData;

    public DatePickerViewPagerAdapter() {
    }

    public DatePickerViewPagerAdapter(List<View> mData) {
        this.mData = mData;
    }

    public void setViewData(List<View> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mData.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mData.get(position);
        container.addView(view);
        return view;
    }
}
