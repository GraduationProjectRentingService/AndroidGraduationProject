package com.sunxuedian.graduationproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于ViewPager中的滚动轮播效果
 * Created by 37 on 2018/1/11.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mViewList = new ArrayList<>();

    /**
     * 设置视图数据
     * @param mViewList
     */
    public void setViewList(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        //使用最大整数来作为view的个数，实现无限边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mViewList.size() > 3){
            container.removeView(mViewList.get(position % mViewList.size()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position % mViewList.size());
        container.addView(view, 0);
        return view;
    }

}
