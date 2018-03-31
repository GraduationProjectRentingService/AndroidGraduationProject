package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends SwipeBackActivity {

    @BindView(R.id.linearLayout)
    LinearLayout mBaseView;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        Intent data = getIntent();
        if (data != null){
            String webUrl = data.getStringExtra("webUrl");
            if (!TextUtils.isEmpty(webUrl)){
                mWebView = new WebView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mWebView.setLayoutParams(layoutParams);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.loadUrl(webUrl);
                mBaseView.addView(mWebView);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebView != null && mWebView.canGoBack()){
                mWebView.goBack();
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null){
            mBaseView.removeView(mWebView);
        }
    }
}
