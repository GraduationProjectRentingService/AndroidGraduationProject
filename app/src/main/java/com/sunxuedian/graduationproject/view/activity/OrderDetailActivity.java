package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.CheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.ListViewUtil;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends SwipeBackActivity {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    @BindView(R.id.ivStatusIcon)
    ImageView mIvStatusIcon;
    @BindView(R.id.tvStatus)
    TextView mTvStatus;
    @BindView(R.id.ivHouse)
    ImageView mIvHouse;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvDayDetail)
    TextView mTvDayDetail;//入住时段
    @BindView(R.id.tvRentalType)
    TextView mTvRentalType;
    @BindView(R.id.tvHostPhone)
    TextView mTvHostPhone;//房东手机号码
    @BindView(R.id.tvHouseLocation)
    TextView mTvHouseLocation;
    @BindView(R.id.tvHouseTotalMoney)
    TextView mTvHouseTotalMoney;//房费
    @BindView(R.id.tvDeposit)
    TextView mTvDeposit;//押金
    @BindView(R.id.lvCheckInPeople)
    ListView mLvCheckInPeople;//入住人信息列表
    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvUserPhone)
    TextView mTvUserPhone;
    @BindView(R.id.ivPayWayIcon)
    ImageView mIvPayWayIcon;
    @BindView(R.id.tvPayWay)//支付方式
    TextView mTvPayWay;
    @BindView(R.id.tvTotalMoney)
    TextView mTvTotalMoney;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    private CheckInPeopleListAdapter mAdapter;
    private List<CheckInPeopleUserInfo> mCheckInPeopleUserInfoList = new ArrayList<>();
    private OrderBean mOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        getIntentData();
        initView();
    }

    private void getIntentData(){
        Intent data = getIntent();
        if (data == null){
            logger.e("the intent data is null!");
            return;
        }

        mOrderBean = (OrderBean) data.getSerializableExtra(OrderBean.TAG);
    }

    private void initView(){
        if (mOrderBean != null){
            //判断是取消还是完成
            if (mOrderBean.getStatus() == OrderBean.STATUS_CANCEL){
                mIvStatusIcon.setImageResource(R.drawable.unhappy);
                mTvStatus.setText("已取消");
                mTvStatus.setTextColor(getResources().getColor(R.color.colorOfRed));
            }else {
                mIvStatusIcon.setImageResource(R.drawable.happy);
                mTvStatus.setText("已完成");
                mTvStatus.setTextColor(getResources().getColor(R.color.colorOfGreen));
            }
            if (mOrderBean.getPayWayCode() == OrderBean.ALI_PAY_CODE){
                mIvPayWayIcon.setImageResource(R.drawable.alipay);
            }else {
                mIvPayWayIcon.setImageResource(R.drawable.wxpay);
            }
            mTvPayWay.setText(mOrderBean.getPayWay());
            //显示入住日期新
            Timestamp inDay = new Timestamp(mOrderBean.getCheckInDate());
            Timestamp outDay = new Timestamp(mOrderBean.getCheckOutDate());
            mTvDayDetail.setText(String.format("入住时段：\n%d月%d号-%d月%d号 共%d晚", inDay.getMonth() + 1, inDay.getDate(), outDay.getMonth() + 1, outDay.getDate(), mOrderBean.getDayNum()));
            mTvTitle.setText(mOrderBean.getHouseTitle());
            mTvHostPhone.setText(mOrderBean.getHostPhone());
            mTvHouseLocation.setText(mOrderBean.getHouseLocation());
            if (!TextUtils.isEmpty(mOrderBean.getHouseImgUrl())){
                ImageLoader.showImage(this, mIvHouse, mOrderBean.getHouseImgUrl());
            }
            if (!TextUtils.isEmpty(mOrderBean.getUserName())){
                mTvUserName.setText(mOrderBean.getUserName());
            }else {
                mTvUserName.setText("SUN");
            }
            //显示用户信息和订单价格信息
            mTvUserName.setText(mOrderBean.getUserName());
            mTvUserPhone.setText(mOrderBean.getUserPhone());
            mTvHouseTotalMoney.setText("￥ " + mOrderBean.getTotalHouseMoney());
            mTvTotalMoney.setText("￥ " + mOrderBean.getTotalMoney());
            mTvDeposit.setText("￥ " + mOrderBean.getDeposit());
            mCheckInPeopleUserInfoList.addAll(mOrderBean.getCheckInPeopleUserInfoList());
        }
        //显示入住人信息的列表适配器
        mAdapter = new CheckInPeopleListAdapter(mCheckInPeopleUserInfoList, this);
        mAdapter.hideDeleteButton();
        mLvCheckInPeople.setAdapter(mAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
    }
}
