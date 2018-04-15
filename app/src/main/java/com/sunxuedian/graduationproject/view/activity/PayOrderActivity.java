package com.sunxuedian.graduationproject.view.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.CheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.PayOrderPresenterImpl;
import com.sunxuedian.graduationproject.utils.AppActivityStackUtils;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.ListViewUtil;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IPayOrderView;
import com.sunxuedian.graduationproject.view.base.BaseActivity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.VERSION_CODES.O;

public class PayOrderActivity extends BaseActivity<IPayOrderView, PayOrderPresenterImpl> implements IPayOrderView{
    private MyLog logger = LoggerFactory.getLogger(getClass());

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
    @BindView(R.id.rgPayWay)//选择支付方式
    RadioGroup mRGPayWay;
    @BindView(R.id.tvTotalMoney)
    TextView mTvTotalMoney;

    AlertDialog.Builder mExitDialog;//退出对话框
    private ShapeLoadingDialog mLoadingView;

    @OnClick(R.id.ivBack)
    public void goBack(){
        if (mExitDialog == null){
            mExitDialog = new AlertDialog.Builder(this)
                    .setMessage("订单支付未完成，您确定要离开支付页面吗？")
                    .setPositiveButton("确认离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            AppActivityStackUtils.clear();
                            finish();
                        }
                    })
                    .setNegativeButton("继续支付", null);
        }
        mExitDialog.show();
    }

    AlertDialog.Builder mCancelOrderDialog;
    @OnClick(R.id.tvCancelOrder)
    public void cancelOrder(){
        if (mCancelOrderDialog == null){
            mCancelOrderDialog = new AlertDialog.Builder(this)
                    .setPositiveButton("继续支付", null)
                    .setNegativeButton("确定取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mPresenter.cancelOrder();
                        }
                    })
                    .setMessage("客官，您确定要取消该订单吗？");
        }
        mCancelOrderDialog.show();
    }

    @OnClick(R.id.tvPayOrder)
    public void payOrder(){
        mPresenter.payOrder();
    }

    private int mPayWay = OrderBean.ALI_PAY_CODE;

    private CheckInPeopleListAdapter mAdapter;
    private List<CheckInPeopleUserInfo> mCheckInPeopleUserInfoList = new ArrayList<>();
    private OrderBean mOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        getIntentData();
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected PayOrderPresenterImpl createPresenter() {
        return new PayOrderPresenterImpl();
    }

    private void getIntentData(){
        Intent data = getIntent();
        if (data == null){
            logger.e("the intent data is null!");
            return;
        }

        mOrderBean = (OrderBean) data.getSerializableExtra(OrderBean.TAG);
    }

    private void initView() {

        if (mOrderBean != null){
            logger.d(mOrderBean.toString());
            Timestamp inDay = mOrderBean.getCheckInDate();
            Timestamp outDay = mOrderBean.getCheckOutDate();
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

            mTvUserPhone.setText(mOrderBean.getUserPhone());
            mTvHouseTotalMoney.setText("￥ " + mOrderBean.getTotalHouseMoney());
            mTvTotalMoney.setText("￥ " + mOrderBean.getTotalMoney());
            mTvDeposit.setText("￥ " + mOrderBean.getDeposit());
            mCheckInPeopleUserInfoList.addAll(mOrderBean.getCheckInPeopleUserInfoList());
        }

        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        mAdapter = new CheckInPeopleListAdapter(mCheckInPeopleUserInfoList, this);
        mAdapter.hideDeleteButton();
        mLvCheckInPeople.setAdapter(mAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);

        mRGPayWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbAliPay:
                        mPayWay = OrderBean.ALI_PAY_CODE;
                        break;
                    case R.id.rbWxPay:
                        mPayWay = OrderBean.WX_PAY_CODE;
                        break;
                }
            }
        });
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
    public UserBean getUser() {
        return UserSpUtils.readLocalUser(this);
    }

    @Override
    public OrderBean getOrderBean() {
        return mOrderBean;
    }

    @Override
    public int getPayWay() {
        return mPayWay;
    }

    @Override
    public void onPaySuccess(OrderBean orderBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("支付订单成功！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        AppActivityStackUtils.clear();//清空存起来的Activity
                        finish();
                    }
                })
                .setCancelable(false);
        builder.show();
    }

    @Override
    public void onCancelOrderSuccess(OrderBean orderBean) {
        ToastUtils.showToast("取消订单成功！");
        AppActivityStackUtils.clear();
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }


}
