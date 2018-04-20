package com.sunxuedian.graduationproject.view.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.ViewUtils;
import com.sunxuedian.graduationproject.view.fragment.HomepageFragment;
import com.sunxuedian.graduationproject.view.fragment.LikeHouseFragment;
import com.sunxuedian.graduationproject.view.fragment.MineFragment;
import com.sunxuedian.graduationproject.view.fragment.OrderManagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MyLog logger = LoggerFactory.getLogger(MainActivity.class);

    private HomepageFragment mHomepageFragment;//推荐主页模块Fragment
    private LikeHouseFragment mLikeHouseFragment;//房源收藏模块Fragment
    private OrderManagerFragment mOrderManagerFragment;//订单管理模块
    private MineFragment mMineFragment;//个人中心



    private long keyDownTime = 0;//返回时记录的系统时间
    private long timeInterval = 2000;//双击退出的时间间隔

    /**
     * 绑定View
     */
    @BindView(R.id.ivMain)//推荐模块
    ImageView mIvMain;
    @BindView(R.id.ivReservation)//收藏模块
    ImageView mIvReservation;
    @BindView(R.id.ivOrder)//订单模块
    ImageView mIvOrder;
    @BindView(R.id.ivMine)//个人中心模块
    ImageView mIvMine;
    @BindView(R.id.tvMain)
    TextView mTvMain;
    @BindView(R.id.tvReservation)
    TextView mTvReservation;
    @BindView(R.id.tvOrder)
    TextView mTvOrder;
    @BindView(R.id.tvMine)
    TextView mTvMine;


    /**
     * 底部按钮的点击事件
     * @param view
     */
    @OnClick({R.id.rlMain, R.id.rlReservation, R.id.rlOrder, R.id.rlMine})
    void navigationClick(View view){
        setAllTagUnselected();//设置所有tag都处于未选中状态
        changeFragment(view.getId());//通过id进行区分切换Fragment
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        changeFragment(R.id.rlMain);//初始化是展示推荐主界面
    }

    /**
     * 设置导航栏所有tag都处于未选中状态
     */
    private void setAllTagUnselected(){
        mIvMain.setSelected(false);
        mIvReservation.setSelected(false);
        mIvOrder.setSelected(false);
        mIvMine.setSelected(false);
        mTvMain.setSelected(false);
        mTvReservation.setSelected(false);
        mTvOrder.setSelected(false);
        mTvMine.setSelected(false);
    }

    /**
     * 通过底部菜单栏不同模块的id来判断并切换
     * @param rlId
     */
    private void changeFragment(int rlId){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction, false);//隐藏所有Fragment，但不需要提交
        switch (rlId){
            case R.id.rlMain:
                mIvMain.setSelected(true);
                mTvMain.setSelected(true);
                if (mHomepageFragment == null){
                    mHomepageFragment = new HomepageFragment();
                    fragmentTransaction.add(R.id.frameMain, mHomepageFragment);
                }else {
                    fragmentTransaction.show(mHomepageFragment);
                }
                break;
            case R.id.rlReservation:
                mIvReservation.setSelected(true);
                mTvReservation.setSelected(true);
                if (mLikeHouseFragment == null){
                    mLikeHouseFragment = new LikeHouseFragment();
                    fragmentTransaction.add(R.id.frameMain, mLikeHouseFragment);
                }else {
                    fragmentTransaction.show(mLikeHouseFragment);
                }
                break;
            case R.id.rlOrder:
                mIvOrder.setSelected(true);
                mTvOrder.setSelected(true);
                if (mOrderManagerFragment == null){
                    mOrderManagerFragment = new OrderManagerFragment();
                    fragmentTransaction.add(R.id.frameMain, mOrderManagerFragment);
                }else {
                    fragmentTransaction.show(mOrderManagerFragment);
                }
                break;
            case R.id.rlMine:
                mIvMine.setSelected(true);
                mTvMine.setSelected(true);
                if (mMineFragment == null){
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.frameMain, mMineFragment);
                }else {
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有已显示的Fragment
     * @param fragmentTransaction
     * @param needCommit 是否需要提交
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction, boolean needCommit){
        if (mHomepageFragment != null){
            fragmentTransaction.hide(mHomepageFragment);
        }

        if (mLikeHouseFragment != null){
            fragmentTransaction.hide(mLikeHouseFragment);
        }

        if (mOrderManagerFragment != null){
            fragmentTransaction.hide(mOrderManagerFragment);
        }

        if (mMineFragment != null){
            fragmentTransaction.hide(mMineFragment);
        }

        if (needCommit){
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if (currentTime - keyDownTime < timeInterval){
                finish();
            }else {
                ToastUtils.showToast("再按一次，退出程序");
                keyDownTime = currentTime;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.d("onDestroy");
    }
}
