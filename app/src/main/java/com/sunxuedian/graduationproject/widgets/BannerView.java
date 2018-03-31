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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.BannerViewPagerAdapter;
import com.sunxuedian.graduationproject.bean.BannerViewBean;
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

    private MyLog logger = LoggerFactory.getLogger(BannerView.class, true);

    private static final int UPDATE_VIEW_CODE = 1;//定时器更新View的code
    public static final int TIME_SPACE_MAX = 10 * 1000;//最大的时间间隔为10s
    public static final int TIME_SPACE_MIN = 1 * 1000;//最小的时间间隔为1s

    private ViewPager mViewPager;//viewpager，用于显示循环显示图片
    private BannerViewPagerAdapter mBannerViewPagerAdapter;//viewPager的适配器
    private Context mContext;
    private List<View> mViewList = new ArrayList<>();//循环显示图片的View列表
    private List<BannerViewBean> mBannerViewBeanList = new ArrayList<>();//存储显示Banner的数据源
    private int mCurrentViewPosition;//当前显示图对应的下标
    private boolean isScrollTaskRunning = false;//是否在滚动

    private OnBannerViewClickListener mOnBannerViewClickListener;//点击回调
    private OnLoadImageListener mOnLoadImageListener;//加载图片的回调，供外部实现

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
                    logger.i("BannerView update view, current position is " + mCurrentViewPosition % mViewList.size());
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
        mBannerViewPagerAdapter = new BannerViewPagerAdapter();
        mViewPager.setAdapter(mBannerViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    public void setOnLoadImageListener(OnLoadImageListener onLoadImageListener){
        mOnLoadImageListener = onLoadImageListener;
    }

    public void setOnBannerViewClickListener(OnBannerViewClickListener mOnBannerViewClickListener){
        this.mOnBannerViewClickListener = mOnBannerViewClickListener;
    }

    /**
     * 设置数据源
     * @param list
     */
    public void setBannerViewData(List<BannerViewBean> list){
        if (mOnLoadImageListener == null){
            logger.e("onLoadImageListener is null, please impl the listener to load image");
            return;
        }

        if (list == null || list.isEmpty()){
            logger.e("setBannerViewData is fail, because the param is null or empty");
            return;
        }

        if (list.size() > 1 && list.size() < 4){
            if (list.size() == 2){
                list.addAll(list);
            }else if (list.size() == 3){
                list.add(list.get(0));
            }
            logger.d("处理后的list的长度为： " + list.size());
        }

        mBannerViewBeanList = list;
        setImageViews();
    }

    /**
     * 外部设置需要显示的图片
     */
    private void setImageViews(){

        boolean hasClickListener = true;
        if (mOnBannerViewClickListener == null){
            logger.d("mOnBannerViewClickListener is null!");
            hasClickListener = false;
        }
        List<View> data = new ArrayList<>(mBannerViewBeanList.size());
        ImageView imageView = null;
        for (final BannerViewBean bannerViewBean: mBannerViewBeanList){
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mOnLoadImageListener.onLoadImage(imageView, bannerViewBean.getImgUrl());//加载图片
            if (hasClickListener){
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBannerViewClickListener.onClick(bannerViewBean);
                    }
                });
            }
            data.add(imageView);
        }
        setViewList(data);
    }

    /**
     * 设置显示的ViewList
     * @param data
     */
    private void setViewList(List<View> data){
        if (data == null || data.isEmpty()){
            logger.e("setViewList is fail, because the param is null or empty");
            return;
        }

        logger.i("setViewList, the view size is " + data.size());
        mViewList = data;
        stopBannerScrollTask();//暂停轮播
        updateBannerView();
    }

    /**
     * 更新视图
     */
    private void updateBannerView(){
        mBannerViewPagerAdapter = new BannerViewPagerAdapter();
        mViewPager.setAdapter(mBannerViewPagerAdapter);
        mBannerViewPagerAdapter.setViewList(mViewList);
        mBannerViewPagerAdapter.notifyDataSetChanged();
        mCurrentViewPosition = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mViewList.size();
//        mCurrentViewPosition = 0;//// TODO: 2018/3/19 test
        mViewPager.setCurrentItem(mCurrentViewPosition);
    }

    /**
     * 开始滚动任务
     *  @param timeSpace 轮播的时间间隔
     */
    public void startBannerScrollTask(int timeSpace){
        logger.d("startBannerScrollTask");
        if (mViewList == null || mViewList.size() <= 0){
            return;
        }

        if (isScrollTaskRunning){
            logger.e("the banner scroll Task has running");
            return;
        }

        isScrollTaskRunning = true;

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
            isScrollTaskRunning = false;
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
        void onClick(BannerViewBean bannerViewBean);
    }

    /**
     * 自定义加载图片的方法
     */
    public interface OnLoadImageListener{
        void onLoadImage(ImageView imageView, String url);
    }

}
