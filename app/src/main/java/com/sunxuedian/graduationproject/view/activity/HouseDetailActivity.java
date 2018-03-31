package com.sunxuedian.graduationproject.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.FacilityGridViewAdapter;
import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.FacilityGridViewBean;
import com.sunxuedian.graduationproject.presenter.impl.HouseDetailPresenterImpl;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.IHouseDetailView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;
import com.sunxuedian.graduationproject.widgets.BannerView;
import com.sunxuedian.graduationproject.widgets.MyScrollView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static cn.smssdk.utils.a.e;
import static cn.smssdk.utils.a.f;

public class HouseDetailActivity extends BaseSwipeBackActivity<IHouseDetailView, HouseDetailPresenterImpl> implements IHouseDetailView {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @BindView(R.id.scrollView)
    MyScrollView mScrollView;
    @BindView(R.id.rlTopBarBg)
    RelativeLayout mRlTopBarBg;//顶部栏的背景控件
    @BindView(R.id.bannerView)
    BannerView mBannerView;
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.ivLike)
    ImageView mIvLike;
    @BindView(R.id.ivShare)
    ImageView mIvShare;
    @BindView(R.id.tvTopBarTitle)
    TextView mTvTopBarTitle;
    @BindView(R.id.calendarView)
    MaterialCalendarView mMaterialCalendarView;//显示选择日期的空间
    @BindView(R.id.gvFacility)
    GridView mGvFacility;

    private float mHeightOfBannerView;//轮播图的高
    private float mHeightOfTopBar;//顶部栏的高

    private CalendarDay mCurrentDay = new CalendarDay(new Date(System.currentTimeMillis()));//当前日期
    private List<CalendarDay> mHasRentedHouseDates;//已经被订房的日期
    private List<CalendarDay> mChooseDates = new ArrayList<>();//选中的订房日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    @Override
    protected HouseDetailPresenterImpl createPresenter() {
        return new HouseDetailPresenterImpl();
    }

    private void initView() {
        mBannerView.setOnLoadImageListener(new BannerView.OnLoadImageListener() {
            @Override
            public void onLoadImage(ImageView imageView, String url) {
                ImageLoader.showImage(HouseDetailActivity.this, imageView, url);
            }
        });

        mHeightOfBannerView = getResources().getDimension(R.dimen.heightBannerView);
        mHeightOfTopBar = getResources().getDimension(R.dimen.heightOfTopBar);

        mScrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > mHeightOfBannerView - mHeightOfTopBar){
                    mIvBack.setImageResource(R.drawable.back);
                    mIvLike.setImageResource(R.drawable.like_gray_icon_selector);
                    mIvShare.setImageResource(R.drawable.share);
                    mTvTopBarTitle.setVisibility(View.VISIBLE);
                    if (scrollY <= mHeightOfBannerView){
                        float alpha = 1.0f - (mHeightOfBannerView - scrollY)/ (mHeightOfBannerView - mHeightOfTopBar);
                        mRlTopBarBg.setAlpha(alpha);
                    }else {
                        mRlTopBarBg.setAlpha(1);
                    }
                }else {
                    mIvBack.setImageResource(R.drawable.back_white);
                    mIvLike.setImageResource(R.drawable.like_white_icon_selector);
                    mIvShare.setImageResource(R.drawable.share_white);
                    mTvTopBarTitle.setVisibility(View.INVISIBLE);
                    mRlTopBarBg.setAlpha(0);
                }
            }
        });

        mHasRentedHouseDates = new ArrayList<>();
        mHasRentedHouseDates.add(new CalendarDay(2018,3,2));
        mHasRentedHouseDates.add(new CalendarDay(2018,3,6));
        mHasRentedHouseDates.add(new CalendarDay(2018,3,7));

        mMaterialCalendarView.setAllowClickDaysOutsideCurrentMonth(true);
        mMaterialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return mHasRentedHouseDates.contains(day) || day.isBefore(mCurrentDay);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setDaysDisabled(true);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_of_calendar_date_disabled));
            }
        });
        mMaterialCalendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                if (!checkRangeSelectedDatesCanChoose(dates)){
                    ToastUtils.showToast("选择的日期范围中有日期已经被预定了！请重新选择！");
                    //将已选择的日期取消选择
                    for (CalendarDay day: dates){
                        widget.setDateSelected(day, false);
                    }
                    mChooseDates.clear();
                    return;
                }

                mChooseDates = dates;
            }
        });
        mMaterialCalendarView.setDynamicHeightEnabled(true);
        mMaterialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        mMaterialCalendarView.setDateSelected(new Date(System.currentTimeMillis()), true);
        mMaterialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                logger.d("data: " + date.toString() + " selected：" + selected);
            }
        });
        mMaterialCalendarView.setDayFormatter(new DayFormatter() {
            @NonNull
            @Override
            public String format(@NonNull CalendarDay day) {
                if (mHasRentedHouseDates.contains(day)){
                    return day.getDay() + "\n已租";
                }

                if (day.isBefore(mCurrentDay)){
                    return day.getDay()+"";
                }
                return day.getDay() + "\n￥100";
            }
        });

        List<FacilityGridViewBean> list = new ArrayList<>();
        String texts[] =  {"热水淋浴", "无线网络", "空调", "电视", "洗衣机", "冰箱", "停车位", "饮水设备", "有线网络", "拖鞋", "纸巾", "洗发水", "香皂", "电梯", "门禁系统", "浴缸", "暖气", "毛巾"};
        Integer resIds[] = {R.drawable.bath, R.drawable.wifi, R.drawable.air_condition, R.drawable.television, R.drawable.washer, R.drawable.icebox, R.drawable.car_park, R.drawable.water,
                R.drawable.wired_network, R.drawable.slipper, R.drawable.paper, R.drawable.shampoo, R.drawable.soap, R.drawable.elevator, R.drawable.access_control, R.drawable.bathtub, R.drawable.sun, R.drawable.bath_towel_icon};
        for (int i = 0; i < texts.length; ++ i){
            FacilityGridViewBean facilityGridViewBean = new FacilityGridViewBean();
            facilityGridViewBean.setText(texts[i]);
            facilityGridViewBean.setResId(resIds[i]);
            facilityGridViewBean.setTextGray(i > 12);
            list.add(facilityGridViewBean);
        }
        FacilityGridViewAdapter adapter = new FacilityGridViewAdapter(list, this);
        mGvFacility.setAdapter(adapter);
    }

    /**
     * 检测选择的连续日期是否可以预定，如果连续日期中出现了被预定的日期，则返回false，否则返回true
     * @param rangeSelectedDates
     * @return
     */
    private boolean checkRangeSelectedDatesCanChoose(List<CalendarDay> rangeSelectedDates){
        for (CalendarDay rangeDate: rangeSelectedDates){
            if (mHasRentedHouseDates.contains(rangeDate)){
                return false;
            }
        }
        return true;
    }

    private void loadData(){
        mPresenter.getBannerImages();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }


    @Override
    public void showBannerImages(List<BannerViewBean> list) {
        mBannerView.setBannerViewData(list);
        mBannerView.startBannerScrollTask(2000);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

}
