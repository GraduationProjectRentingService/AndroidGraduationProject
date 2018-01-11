package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.ViewPagerAdapter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.List;

/**
 * The banner view is design to show some images cyclically.
 * Created by sunxuedian on 2018/1/7.
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener{

    private MyLog logger = LoggerFactory.getLogger(BannerView.class);

    private ViewPager mViewPager;//viewpager，用于显示循环显示图片
    private ViewPagerAdapter mViewPagerAdapter;//viewPager的适配器
    private Context mContext;
    private List<View> mViewData;
    private int mCurrentViewIndex;

    public BannerView(Context context) {
        super(context);
        mContext = context;
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    /**
     * 初始化View
     */
    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_view, null);
        addView(view);//将view添加到视图中

        mViewPager = view.findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 设置显示的视图数据
     * @param data
     */
    public void setViewData(List<View> data){
        this.mViewData = data;
    }

    /**
     * 更新视图
     */
    public void updateView(){
        mViewPagerAdapter.setViewList(mViewData);
        mViewPagerAdapter.notifyDataSetChanged();
        mCurrentViewIndex = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mViewData.size();
        mViewPager.setCurrentItem(mCurrentViewIndex);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        ToastUtils.showToast("onPageScrolled " + position);
    }

    @Override
    public void onPageSelected(int position) {
//        ToastUtils.showToast("onPageSelected " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        ToastUtils.showToast("onPageScrollStateChanged " + state);
    }
}
