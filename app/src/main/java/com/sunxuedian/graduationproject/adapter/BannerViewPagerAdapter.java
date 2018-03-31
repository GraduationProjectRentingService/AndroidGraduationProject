package com.sunxuedian.graduationproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于ViewPager中的滚动轮播效果
 * Created by 37 on 2018/1/11.
 */

public class BannerViewPagerAdapter extends PagerAdapter {

    private MyLog logger = LoggerFactory.getLogger(BannerViewPagerAdapter.class, false);
    private List<View> mViewList = new ArrayList<>();

    /**
     * 设置视图数据
     * @param viewList
     */
    public void setViewList(List<View> viewList) {
        if (viewList == null){
            logger.e("the viewList is null!");
            return;
        }

        mViewList = viewList;
    }

    @Override
    public int getCount() {
        //使用最大整数来作为view的个数，实现无限边界
        if (mViewList != null && mViewList.size() > 0){
            if (mViewList.size() == 1){
                return 1;
            }else {
                return Integer.MAX_VALUE;
            }
        }
        return 0;

//        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        logger.d("isViewFromObject");
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        View view = mViewList.get(position % mViewList.size());
        logger.d("destroyItem index: " +  position % mViewList.size());
//        container.removeView(view);
        if (mViewList.size() > 3){
            container.removeView(mViewList.get(position % mViewList.size()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        logger.d("instantiateItem position: " + position);
        View view = null;
        if (mViewList.size() != 0){
            view = mViewList.get(position % mViewList.size());
            logger.d("index: " + position % mViewList.size());
            logger.d("view: " + view.toString());
            container.addView(view);
        }
        return view;
    }

}
