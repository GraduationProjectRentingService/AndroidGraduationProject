package com.sunxuedian.graduationproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
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
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.HouseDetailPresenterImpl;
import com.sunxuedian.graduationproject.utils.AppActivityStackUtils;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IHouseDetailView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;
import com.sunxuedian.graduationproject.widgets.BannerView;
import com.sunxuedian.graduationproject.widgets.MyScrollView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    GridView mGvFacility;//基础设施列表
    @BindView(R.id.tvTitle)
    TextView mTvTitle;//房源标题
    @BindView(R.id.tvMoney)
    TextView mTvMoney;//每晚的价格
    @BindView(R.id.tvRentalType)
    TextView mTvRentalType;//出租类型 整套/单间
    @BindView(R.id.tvPeopleNum)
    TextView mTvPeopleNum;//宜住的人数
    @BindView(R.id.tvBedNum)
    TextView mTvBedNum;//床的个数
    @BindView(R.id.tvHouseDescription)
    TextView mTvHouseDescription;//房源描述
    @BindView(R.id.tvHouseInfo)
    TextView mTvHouseInfo;//房源内部情况
    @BindView(R.id.tvHouseLocation)
    TextView mTvHouseLocation;//房源位置
    @BindView(R.id.tvDayNum)
    TextView mTvDayNum;//入住天数规则
    @BindView(R.id.tvDeposit)
    TextView mTvDeposit;//定金
    @BindView(R.id.tvCheckInInstructions)
    TextView mTvCheckInInstructions;//入住须知
    @BindView(R.id.tvOtherRequirement)
    TextView mTvOtherRequirement;//其他要求
    @BindView(R.id.tvOtherTip)
    TextView mTvOtherTip;//其他贴士


    private float mHeightOfBannerView;//轮播图的高
    private float mHeightOfTopBar;//顶部栏的高

    private CalendarDay mCurrentDay = new CalendarDay(new Date(System.currentTimeMillis()));//当前日期
    private List<CalendarDay> mHasRentedHouseDates;//已经被订房的日期
    private List<CalendarDay> mChooseDates = new ArrayList<>();//选中的订房日期
    private HouseBean mHouseBean;//当前HouseBean的对象
    private ShapeLoadingDialog mLoadingView;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.ivLike)
    public void addHouseToLike(){
        mPresenter.addHouseToLike();//将房源添加到收藏列表中
    }

    /**
     * 调用分享接口
     */
    @OnClick(R.id.ivShare)
    public void shareHouse(){
//        ToastUtils.showToast("暂未开通该功能！");
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, mHouseBean.getTitle());
        startActivity(Intent.createChooser(textIntent, "分享"));
    }

    /**
     * 联系房东
     */
    AlertDialog.Builder mContactHostDialog;
    @OnClick(R.id.tvContactHost)
    public void contactHost(){
        if (mContactHostDialog == null){
            mContactHostDialog = new AlertDialog.Builder(this)
                    .setMessage("拨打电话给房东？")
                    .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + mHouseBean.getHostPhoneNum()  );
                            intent.setData(data);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", null);
        }
        mContactHostDialog.show();
    }
    /**
     * 点击立即订房
     */
    @OnClick(R.id.tvReserveRoom)
    public void reserveRoom(){
        if (mChooseDates.size() == 0){
            ToastUtils.showToast("请先选择入住的日期！");
            return;
        }
        //判断用户是否登录
        if (UserSpUtils.isUserLogin(this)){
            //跳转到订房界面
            Intent intent = new Intent(this, ReverseHouseActivity.class);
            //将选中的预定日期传到预订Activity界面中
            intent.putParcelableArrayListExtra("chooseDates", (ArrayList<? extends Parcelable>) mChooseDates);
            intent.putExtra(HouseBean.TAG, mHouseBean);
            startActivity(intent);//启动Activity
            AppActivityStackUtils.clear();//清空当前的Activity
            AppActivityStackUtils.pushActivity(this);//将当前的Activity添加到栈中
        }else {
            ToastUtils.showToast("用户未登录，请先登录！");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        ButterKnife.bind(this);
        getIntentData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerView.startBannerScrollTask(2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerView.stopBannerScrollTask();
    }

    @Override
    protected HouseDetailPresenterImpl createPresenter() {
        return new HouseDetailPresenterImpl();
    }

    private void initView() {
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        mBannerView.setOnLoadImageListener(new BannerView.OnLoadImageListener() {
            @Override
            public void onLoadImage(ImageView imageView, String url) {
                ImageLoader.showImage(HouseDetailActivity.this, imageView, url);
            }
        });

        if (mHouseBean != null){
            mTvTitle.setText(mHouseBean.getTitle());
            mTvRentalType.setText(mHouseBean.getRentalTypeText());
            mTvBedNum.setText(mHouseBean.getBedNum()+"张床");
            mTvDeposit.setText("￥ " + mHouseBean.getDeposit());
            mTvHouseDescription.setText(mHouseBean.getDescription());
            mTvPeopleNum.setText("宜住" + mHouseBean.getPeopleNum() + "人");
            mTvHouseInfo.setText(mHouseBean.getHouseInfo());
            mTvHouseLocation.setText(mHouseBean.getLocation());
            mTvDayNum.setText(String.format("至少%d天，最多%d天", mHouseBean.getLeastDay(), mHouseBean.getMostDay()));
            mTvMoney.setText("￥" + mHouseBean.getMoneyOfEachNight());
            showBannerView(mHouseBean.getHouseImgUrls());
        }

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
//        mMaterialCalendarView.setDateSelected(new Date(System.currentTimeMillis()), true);
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
                return day.getDay() + "\n￥" + mHouseBean.getMoneyOfEachNight();
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

    /**
     * 获取intent数据
     */
    private void getIntentData(){
        Intent data = getIntent();
        if (data != null){
            mHouseBean = data.getParcelableExtra(HouseBean.TAG);
        }
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void stopLoading() {
        mLoadingView.dismiss();
    }


    @Override
    public void showBannerImages(List<BannerViewBean> list) {
        mBannerView.setBannerViewData(list);
        mBannerView.startBannerScrollTask(2000);
    }


    private void showBannerView(List<String> list){
        List<BannerViewBean> bannerViewDatas = new ArrayList<>();
        for (String url: list){
            BannerViewBean bannerViewBean = new BannerViewBean();
            bannerViewBean.setImgUrl(url);
            bannerViewDatas.add(bannerViewBean);
        }
        showBannerImages(bannerViewDatas);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public HouseBean getHouseBean() {
        return mHouseBean;
    }

    @Override
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(this);
    }

    @Override
    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAddLikeSuccess() {
        ToastUtils.showToast("收藏房源成功！");
        mIvLike.setSelected(true);
    }

}
