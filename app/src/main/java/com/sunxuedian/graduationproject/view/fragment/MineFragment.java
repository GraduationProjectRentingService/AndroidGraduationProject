package com.sunxuedian.graduationproject.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.activity.AboutActivity;
import com.sunxuedian.graduationproject.view.activity.CheckInPeopleListActivity;
import com.sunxuedian.graduationproject.view.activity.LikeHouseListActivity;
import com.sunxuedian.graduationproject.view.activity.LoginActivity;
import com.sunxuedian.graduationproject.view.activity.OrderListActivity;
import com.sunxuedian.graduationproject.view.activity.SystemMessageActivity;
import com.sunxuedian.graduationproject.view.activity.UserInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by sunxuedian on 2018/4/4.
 */

public class MineFragment extends Fragment {

    MyLog logger  = LoggerFactory.getLogger(getClass());

    private int GO_LOGIN = 13;

    @BindView(R.id.ivBackground)
    ImageView mIvBackground;
    @BindView(R.id.cIvHeadIcon)
    CircleImageView mIvHeadIcon;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    @OnClick({R.id.rlUserHeadBar, R.id.rlUserInfo})
    public void goUserInfo(){
        if (!UserSpUtils.isUserLogin(getActivity())){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, GO_LOGIN);
        }else {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ivMessage)
    public void goMessageView(){
        Intent intent = new Intent(getActivity(), SystemMessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlOrderInfo)
    public void goOrderInfoView(){
        Intent intent = new Intent(getActivity(), OrderListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlLikeList)
    public void goLikeHouseListView(){
        Intent intent = new Intent(getActivity(), LikeHouseListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlCheckInPeople)
    public void goCheckInPeoles(){
        Intent intent = new Intent(getActivity(), CheckInPeopleListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlAbout)
    public void goAboutView(){
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.e("onResume");
        initView();
    }

    private void initView(){
        UserBean userBean = UserSpUtils.readLocalUser(getActivity());
        if (userBean != null){
            logger.e(userBean.toString());
            if (TextUtils.isEmpty(userBean.getUserName())){
                mTvUserName.setText("未设置");
            }else {
                mTvUserName.setText(userBean.getUserName());
            }

            ImageLoader.showImage(this, mIvHeadIcon, userBean.getIconUrl(), R.drawable.test_p_5);
            ImageLoader.showBlurTransformationImage(getActivity(), mIvBackground, userBean.getIconUrl(), R.color.colorMainGray_45, 22);

        }else {
            //没有登录
            ImageLoader.showBlurTransformationImage(getActivity(), mIvBackground, R.drawable.test_p_5, R.drawable.test_p_5, 22);
            mTvUserName.setText("立即登录");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GO_LOGIN && resultCode == Activity.RESULT_OK && data != null){
            String userName = data.getStringExtra("userName");
            String iconUrl = data.getStringExtra("url");
            if (TextUtils.isEmpty(userName)){
                mTvUserName.setText("未设置");
            }else {
                mTvUserName.setText(userName);
            }

            ImageLoader.showImage(this, mIvHeadIcon, iconUrl, R.drawable.test_p_5);
            ImageLoader.showBlurTransformationImage(getActivity(), mIvBackground, iconUrl, R.color.colorMainGray_45, 22);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
