package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUserNameActivity extends SwipeBackActivity {

    @BindView(R.id.etUserName)
    EditText mEtUserName;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnSure)
    public void onSure(){
        String userName = mEtUserName.getText().toString();
        if (TextUtils.isEmpty(userName)){
            ToastUtils.showToast("用户名不能为空！");
            return;
        }

        //将数据传到上一个界面
        Intent result = new Intent();
        result.putExtra("userName", userName);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_name);
        ButterKnife.bind(this);
        getIntentData();
    }

    public void getIntentData() {
        Intent data = getIntent();
        if (data != null){
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)){
                mEtUserName.setText(userName);
                mEtUserName.setSelection(userName.length());
            }
        }
    }
}
