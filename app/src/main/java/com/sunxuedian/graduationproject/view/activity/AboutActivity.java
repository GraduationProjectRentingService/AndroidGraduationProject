package com.sunxuedian.graduationproject.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;

public class AboutActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
