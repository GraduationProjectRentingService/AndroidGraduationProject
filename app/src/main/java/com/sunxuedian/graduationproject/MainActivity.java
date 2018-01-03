package com.sunxuedian.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sunxuedian.graduationproject.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick({R.id.textViewMain})
    void navigationClick(View view){
        switch (view.getId()){
            case R.id.textViewMain:
                ToastUtils.showToast("点击了main");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


}
