package com.sunxuedian.graduationproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.presenter.impl.UserInfoPresenterImpl;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.data.UserSpUtils;
import com.sunxuedian.graduationproject.view.IUserInfoView;
import com.sunxuedian.graduationproject.view.base.BaseSwipeBackActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.O;

public class UserInfoActivity extends BaseSwipeBackActivity<IUserInfoView, UserInfoPresenterImpl> implements IUserInfoView {

    private MyLog logger = LoggerFactory.getLogger(getClass());
    public static final int GET_IMAGE = 11;//获取图片
    public static final int SET_USER_NAME = 12;//设置用户姓名

    @BindView(R.id.cIvHeadIcon)
    CircleImageView mCIVHeadIcon;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvUserPhone)
    TextView mTvUserPhone;
    @BindView(R.id.tvUserSex)
    TextView mTvUserSex;

    private PopupWindow mPopChooseIcon;
    private PopupWindow mPopChooseSex;

    private ShapeLoadingDialog mLoadingView;//进度对话框

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    /**
     * 点击显示头像选择框
     */
    @OnClick(R.id.rlUserHead)
    public void chooseUserHeadIcon(){
        if (!mPopChooseIcon.isShowing()){
            mPopChooseIcon.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 点击显示性别选择框
     */
    @OnClick(R.id.rlUserSex)
    public void chooseUserSex(){
        if (!mPopChooseSex.isShowing()){
            mPopChooseSex.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    @OnClick(R.id.rlUserName)
    public void changeUserName(){
        Intent intent = new Intent(this, SetUserNameActivity.class);
        intent.putExtra("userName", mUserBean.getUserName());
        startActivityForResult(intent, SET_USER_NAME);
    }

    private AlertDialog.Builder mLogoutDialog;
    @OnClick(R.id.btnLogout)
    public void logout(){
        if (mLogoutDialog == null){
            mLogoutDialog = new AlertDialog.Builder(this)
                    .setPositiveButton("取消", null)
                    .setNegativeButton("退出登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserSpUtils.logout(UserInfoActivity.this);
                            finish();
                        }
                    })
                    .setMessage("您确定要退出当前账号吗？");
        }
        mLogoutDialog.show();
    }

    private UserBean mUserBean;
    private String mImagePath;//选中的本地图片地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected UserInfoPresenterImpl createPresenter() {
        return new UserInfoPresenterImpl(this);
    }

    private void initView() {

        mUserBean = UserSpUtils.readLocalUser(this);
        ImageLoader.showImage(this, mCIVHeadIcon, mUserBean.getIconUrl(), R.drawable.test_p_5);
        if (TextUtils.isEmpty(mUserBean.getUserName())){
            mTvUserName.setText("未设置");
        }else {
            mTvUserName.setText(mUserBean.getUserName());
        }

        if (TextUtils.isEmpty(mUserBean.getPhoneNum())){
            mTvUserPhone.setText("------");
        }else {
            mTvUserPhone.setText(mUserBean.getPhoneNum());
        }

        if (TextUtils.isEmpty(mUserBean.getSex())){
            mTvUserSex.setText("未设置");
        }else {
            mTvUserSex.setText(mUserBean.getSex());
        }

        //初始化加载对话框
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");

        /**
         * 显示选择照片的菜单栏
         */
        View chooseIconView = LayoutInflater.from(this).inflate(R.layout.pop_bottom_layout, null);
        TextView tvTackPic = chooseIconView.findViewById(R.id.popItem1);
        tvTackPic.setText("拍照");
        tvTackPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopChooseIcon.dismiss();
            }
        });
        TextView tvChoosePic = chooseIconView.findViewById(R.id.popItem2);
        tvChoosePic.setText("从手机相册选择");
        tvChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用相册选取照片
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_IMAGE);
                mPopChooseIcon.dismiss();
            }
        });
        TextView tvCancelPic = chooseIconView.findViewById(R.id.popItem3);
        tvCancelPic.setText("取消");
        tvCancelPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopChooseIcon.dismiss();
            }
        });
        mPopChooseIcon = new PopupWindow(chooseIconView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopChooseIcon.setOutsideTouchable(false);
        //设置显示与消失样式
        mPopChooseIcon.setAnimationStyle(R.style.mypopwindow_anim_go_style);

        /**
         * 选择性别菜单
         */
        View chooseSexView = LayoutInflater.from(this).inflate(R.layout.pop_bottom_layout, null);
        TextView tvBoy = chooseSexView.findViewById(R.id.popItem1);
        tvBoy.setText("男");
        tvBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvUserSex.setText("男");
                mUserBean.setSex("男");
                mPresenter.updateUserInfo();//更新用户信息
                mPopChooseSex.dismiss();
            }
        });
        TextView tvGirl = chooseSexView.findViewById(R.id.popItem2);
        tvGirl.setText("女");
        tvGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvUserSex.setText("女");
                mUserBean.setSex("女");
                mPresenter.updateUserInfo();
                mPopChooseSex.dismiss();
            }
        });
        TextView tvCancelSex = chooseSexView.findViewById(R.id.popItem3);
        tvCancelSex.setText("取消");
        tvCancelSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopChooseSex.dismiss();
            }
        });
        mPopChooseSex = new PopupWindow(chooseSexView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopChooseSex.setOutsideTouchable(false);
        //设置显示与消失样式
        mPopChooseSex.setAnimationStyle(R.style.mypopwindow_anim_go_style);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case GET_IMAGE:
                    //获取到图片
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mImagePath = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    logger.d("path: " + mImagePath);
                    //调用Presenter上传图片
                    mPresenter.uploadImg();
                    break;
                case SET_USER_NAME:
                    String userName = data.getStringExtra("userName");
                    mTvUserName.setText(userName);
                    mUserBean.setUserName(userName);
                    mPresenter.updateUserInfo();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFileUploadSuccess(String fileUrl) {
        logger.d("上传成功：url：" + fileUrl);
        ImageLoader.showImageFromFilePath(this, mCIVHeadIcon, mImagePath , R.drawable.test_p_5);
        mUserBean.setIconUrl(fileUrl);
        mPresenter.updateUserInfo();//更新用户信息
    }

    @Override
    public void onFileUploadFailed(String msg) {
        ToastUtils.showToast(msg);
        logger.e("上传文件失败：" + msg);
    }

    @Override
    public void showFileUploadProgress(Integer value) {

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
    public void onUpdateUserInfoSuccess(UserBean userBean) {
        ToastUtils.showToast("更新用户信息成功！");
        UserSpUtils.saveUserToLocal(this, userBean);
    }
    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public String getChooseImgLocalPath() {
        return mImagePath;
    }

    @Override
    public UserBean getUser() {
        return mUserBean;
    }
}
