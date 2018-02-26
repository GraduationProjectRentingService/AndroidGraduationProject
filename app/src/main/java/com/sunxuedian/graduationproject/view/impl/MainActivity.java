package com.sunxuedian.graduationproject.view.impl;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MyLog logger = LoggerFactory.getLogger(MainActivity.class);

    private HomepageFragment mHomepageFragment;

    @OnClick({R.id.textViewMain})
    void navigationClick(View view){
        switch (view.getId()){
            case R.id.textViewMain:
                ToastUtils.showToast("切换为Main");
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
    }
}