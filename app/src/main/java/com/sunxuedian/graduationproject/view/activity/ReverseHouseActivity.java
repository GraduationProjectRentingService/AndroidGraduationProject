package com.sunxuedian.graduationproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.mingle.widget.ShapeLoadingDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.CheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.ReverseHousePresenterImpl;
import com.sunxuedian.graduationproject.utils.AppActivityStackUtils;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.ListViewUtil;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IReverseHouseView;
import com.sunxuedian.graduationproject.view.base.BaseActivity;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.VERSION_CODES.O;

/**
 * 预定房源
 */
public class ReverseHouseActivity extends BaseActivity<IReverseHouseView, ReverseHousePresenterImpl> implements IReverseHouseView {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    public static final int CODE_GET_CHECK_IN_PEOPLE = 11;

    @BindView(R.id.ivHouse)
    ImageView mIvHouse;//房源缩略图
    @BindView(R.id.tvTitle)
    TextView mTvTitle;//标题
    @BindView(R.id.tvRentalType)
    TextView mTvRentalType;
    @BindView(R.id.tvTotalNight)//总共住的天数
    TextView mTvTotalNight;
    @BindView(R.id.tvCheckInDate)//入住日期
    TextView mTvCheckInDate;
    @BindView(R.id.tvCheckOutDate)//离开日期
    TextView mTvCheckOutDate;
    @BindView(R.id.lvCheckInPeople)
    ListView mLvCheckInPeople;//入住人列表
    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvUserPhone)
    TextView mTvUserPhone;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;//是否同意条约
    @BindView(R.id.tvTotalMoney)
    TextView mTvTotalMoney;

    AlertDialog.Builder mExitDialog;//退出对话框
    private UserBean mUserBean;

    @OnClick(R.id.ivBack)
    public void goBack(){
        if (mExitDialog == null){
            mExitDialog = new AlertDialog.Builder(this)
                    .setMessage("预订流程未完成，您确定要离开预订页面吗？")
                    .setPositiveButton("确认离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            AppActivityStackUtils.popActivity();
                            finish();
                        }
                    })
                    .setNegativeButton("继续预订", null);
        }
        mExitDialog.show();

    }

    private ShapeLoadingDialog mLoadingView;//进度对话框
    private CheckInPeopleListAdapter mAdapter;
    private ArrayList<CheckInPeopleUserInfo> mCheckInPeopleUserInfoList = new ArrayList<>();
    private ArrayList<CalendarDay> mChooseDates;
    private HouseBean mHouseBean;

    @OnClick(R.id.btnAddPeople)
    public void addCheckInPeople(){
        Intent intent = new Intent(this, ChooseCheckInPeopleActivity.class);
        startActivityForResult(intent, CODE_GET_CHECK_IN_PEOPLE);
    }

    /**
     * 提交订单
     */
    @OnClick(R.id.tvSubmitOrder)
    public void onSubmitOrder(){
        if (mCheckInPeopleUserInfoList.size() == 0){
            ToastUtils.showToast("请添加入住人信息！");
            return;
        }

        if (!mCheckBox.isChecked()){
            ToastUtils.showToast("请先同意MiLuHouse条约！");
            return;
        }

        mPresenter.createOrder();//创建订单

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_house);
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
    protected ReverseHousePresenterImpl createPresenter() {
        return new ReverseHousePresenterImpl();
    }

    /**
     * 获取上一个Activity传过来的数据
     */
    private void getIntentData(){
        Intent data = getIntent();
        if (data == null){
            logger.e("the intent data is null!");
            return;
        }

        mChooseDates = data.getParcelableArrayListExtra("chooseDates");
        mHouseBean = data.getParcelableExtra(HouseBean.TAG);
        if (mHouseBean != null){
            if (!TextUtils.isEmpty(mHouseBean.getImgUrl())){
                ImageLoader.showImage(this, mIvHouse, mHouseBean.getImgUrl());
            }
            mTvTitle.setText(mHouseBean.getTitle());
            mTvRentalType.setText(mHouseBean.getRentalTypeText());
            int mTotalMoney = mHouseBean.getDeposit() + mHouseBean.getMoneyOfEachNight() * (mChooseDates.size() - 1);//押金+天数*每天价格
            mTvTotalMoney.setText("￥" + mTotalMoney);
        }

    }

    private void initView(){
        mUserBean = UserSpUtils.readLocalUser(this);
        if (!TextUtils.isEmpty(mUserBean.getUserName())){
            mTvUserName.setText(mUserBean.getUserName());
        }
        mTvUserPhone.setText(mUserBean.getPhoneNum());

        if (mChooseDates != null){
            CalendarDay checkInDate = mChooseDates.get(0);
            CalendarDay checkOutDate = mChooseDates.get(mChooseDates.size() - 1);
            mTvTotalNight.setText(String.format("共%d晚", mChooseDates.size() - 1));
            mTvCheckInDate.setText(String.format("%d月%d号", checkInDate.getMonth() + 1, checkInDate.getDay()));
            mTvCheckOutDate.setText(String.format("%d月%d号", checkOutDate.getMonth() + 1, checkOutDate.getDay()));
        }

        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        mAdapter = new CheckInPeopleListAdapter(mCheckInPeopleUserInfoList, this);
        mAdapter.setOnDeleteListener(new CheckInPeopleListAdapter.OnDeleteListener() {
            @Override
            public void onDeleted(int pos) {
                mAdapter.removeItem(pos);
                mAdapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
            }
        });
        mLvCheckInPeople.setAdapter(mAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是否从 选中入住人Activity中传值过来的
        if (requestCode == CODE_GET_CHECK_IN_PEOPLE && resultCode == RESULT_OK) {
            //如果data不为空，则表明有数据
            if (data != null) {
                ArrayList<CheckInPeopleUserInfo> list = data.getParcelableArrayListExtra(CheckInPeopleUserInfo.TAG);
                //再次做判断，避免空指针异常
                if (list != null && list.size() > 0) {
                    //将选中的入住人信息添加到显示列表中
                    addCheckInPeopleUserInfoList(list);
                    mAdapter.notifyDataSetChanged();
                    //刷新列表的长度
                    ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
                }
            }
        }
    }

    /**
     * 去掉重复的入住人信息
     * @param list
     */
    private void addCheckInPeopleUserInfoList(ArrayList<CheckInPeopleUserInfo> list){
        for (CheckInPeopleUserInfo info: list){
            if (!mCheckInPeopleUserInfoList.contains(info)){
                mCheckInPeopleUserInfoList.add(info);
            }
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
    public UserBean getUser() {
        return mUserBean;
    }

    @Override
    public HouseBean getHouseBean() {
        return mHouseBean;
    }

    @Override
    public List<CalendarDay> getCheckInOutDate() {
        return mChooseDates;
    }

    @Override
    public List<CheckInPeopleUserInfo> getCheckInPeoples() {
        return mCheckInPeopleUserInfoList;
    }

    @Override
    public void onCreateOrderSuccess(OrderBean orderBean) {

        ToastUtils.showToast("创建订单成功，前往支付界面！");
        Intent intent = new Intent(this, PayOrderActivity.class);
//        intent.putParcelableArrayListExtra(CheckInPeopleUserInfo.TAG, mCheckInPeopleUserInfoList);//传入入住人信息列表
//        intent.putParcelableArrayListExtra("chooseDates", mChooseDates);//传入入住日期
//        intent.putExtra(HouseBean.TAG, mHouseBean);//传入房源
        intent.putExtra(OrderBean.TAG, orderBean);
        startActivity(intent);
        AppActivityStackUtils.pushActivity(this);
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
