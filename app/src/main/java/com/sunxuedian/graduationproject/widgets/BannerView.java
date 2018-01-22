package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.ViewPagerAdapter;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The banner view is design to show some images cyclically.
 * Created by sunxuedian on 2018/1/7.
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener{

    private MyLog logger = LoggerFactory.getLogger(BannerView.class);

    private static final int UPDATE_VIEW_CODE = 1;//定时器更新View的code
    public static final int TIME_SPACE_MAX = 10 * 1000;//最大的时间间隔为10s
    public static final int TIME_SPACE_MIN = 1 * 1000;//最小的时间间隔为1s

    private ViewPager mViewPager;//viewpager，用于显示循环显示图片
    private ViewPagerAdapter mViewPagerAdapter;//viewPager的适配器
    private Context mContext;
    private List<View> mViewList = new ArrayList<>();//循环显示图片的View列表
    private int mCurrentViewPosition;//当前显示图对应的下标

    private OnBannerViewClickListener mOnBannerViewClickListener;//点击回调

    //定时器
    private Timer mTimer;
    private TimerTask mTimerTask;

    //Handler处理线程切换到main
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEW_CODE:
                    mCurrentViewPosition++;
                    mViewPager.setCurrentItem(mCurrentViewPosition);
//                    logger.i("BannerView update view, current position is " + mCurrentViewPosition % mViewList.size());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

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

    public void setOnBannerViewClickListener(OnBannerViewClickListener mOnBannerViewClickListener){
        this.mOnBannerViewClickListener = mOnBannerViewClickListener;
        addViewClickListener();
    }

    /**
     * 设置显示的ViewList
     * @param data
     */
    public void setViewList(List<View> data){
        if (data == null || data.isEmpty()){
            logger.e("setViewList is fail, because the param is null or empty");
            return;
        }

        logger.i("setViewList, the view size is " + data.size());
        mViewList = data;
        updateBannerView();
    }

    /**
     * 设置每张图的点击事件
     */
    private void addViewClickListener(){
        if (mOnBannerViewClickListener == null){
            logger.e("mOnBannerViewClickListener is null");
            return;
        }

        for (int i = 0; i < mViewList.size(); ++ i){
            final int finalI = i;
            mViewList.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBannerViewClickListener.onClick(finalI);
                }
            });
        }
    }

    /**
     * 更新视图
     */
    private void updateBannerView(){
        mViewPagerAdapter.setViewList(mViewList);
        mViewPagerAdapter.notifyDataSetChanged();
        mCurrentViewPosition = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mViewList.size();
        mViewPager.setCurrentItem(mCurrentViewPosition);
    }

    /**
     * 开始滚动任务
     *  @param timeSpace 轮播的时间间隔
     */
    public void startBannerScrollTask(int timeSpace){
        if (timeSpace < TIME_SPACE_MIN){
            timeSpace = TIME_SPACE_MIN;
        }else if (timeSpace > TIME_SPACE_MAX){
            timeSpace = TIME_SPACE_MAX;
        }

        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE_VIEW_CODE);
            }
        };
        mTimer.schedule(mTimerTask, timeSpace, timeSpace);
    }

    /**
     * 暂停滚动任务
     */
    public void stopBannerScrollTask(){
        if (mTimer != null){
            mTimer.cancel();
            logger.i("stopBannerScrollTask");
        }else {
            logger.e("stopBannerScrollTask is fail, timer is null");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        ToastUtils.showToast("onPageSelected " + position);
//        logger.d("onPageSelected->" + position);
        mCurrentViewPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // TODO: 2018/1/23 需处理按下轮播图的时候，关闭定时滚动任务
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        logger.d("onInterceptTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                logger.d("down");
                break;
            case MotionEvent.ACTION_MOVE:
                logger.d("move");
                break;
            case MotionEvent.ACTION_UP:
                logger.d("up");
                break;
            case MotionEvent.ACTION_CANCEL:
                logger.e("cancel");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 接口，提供点击回调
     */
    public interface OnBannerViewClickListener {
        void onClick(int index);
    }

}
