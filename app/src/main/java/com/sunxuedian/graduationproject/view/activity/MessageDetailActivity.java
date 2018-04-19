package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends SwipeBackActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvMessage)
    TextView mTvMessage;
    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        Intent data = getIntent();
        if (data != null){
            mTvTitle.setText(data.getStringExtra("title"));
            mTvMessage.setText(data.getStringExtra("message"));
        }
    }
}
