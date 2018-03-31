package com.sunxuedian.graduationproject.view.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.fragment.HomepageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MyLog logger = LoggerFactory.getLogger(MainActivity.class);

    private HomepageFragment mHomepageFragment;


    /**
     * 绑定View
     */
    @BindView(R.id.ivMain)
    ImageView mIvMain;
    @BindView(R.id.ivReservation)
    ImageView mIvReservation;
    @BindView(R.id.ivOrder)
    ImageView mIvOrder;
    @BindView(R.id.ivMine)
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

        switch (view.getId()){
            case R.id.rlMain:
                ToastUtils.showToast("切换为Main");
                mIvMain.setSelected(true);
                mTvMain.setSelected(true);
                break;
            case R.id.rlReservation:
                mIvReservation.setSelected(true);
                mTvReservation.setSelected(true);
                break;
            case R.id.rlOrder:
                mIvOrder.setSelected(true);
                mTvOrder.setSelected(true);
                break;
            case R.id.rlMine:
                mIvMine.setSelected(true);
                mTvMine.setSelected(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mHomepageFragment = new HomepageFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameMain, mHomepageFragment);
        fragmentTransaction.commit();
        setAllTagUnselected();
        mIvMain.setSelected(true);
        mTvMain.setSelected(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.d("onDestroy");
    }
}
