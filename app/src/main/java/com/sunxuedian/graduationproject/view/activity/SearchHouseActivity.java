package com.sunxuedian.graduationproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchHouseActivity extends AppCompatActivity {

    public static final int SEARCH_TYPE_TITLE = 1;//搜索类型为标题
    public static final int SEARCH_TYPE_MAP = 2;//搜索类型为位置
    public static final int SEARCH_TYPE_NAME = 3;//搜索类型为房东昵称


    @BindView(R.id.etSearch)
    EditText mEtSearch;
    @BindView(R.id.llHostName)
    LinearLayout mLLHostName;
    @BindView(R.id.llMap)
    LinearLayout mLLMap;
    @BindView(R.id.llTitle)
    LinearLayout mLLTitle;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvHostName)
    TextView mTvHostName;
    @BindView(R.id.tvMap)
    TextView mTvMap;

    @OnClick({R.id.llTitle, R.id.llHostName, R.id.llMap})
    public void chooseSearch(View view){
        Intent data = new Intent();
        int type = SEARCH_TYPE_TITLE;
        switch (view.getId()){
            case R.id.llTitle:
                type = SEARCH_TYPE_TITLE;
                break;
            case R.id.llMap:
                type = SEARCH_TYPE_MAP;
                break;
            case R.id.llHostName:
                type = SEARCH_TYPE_NAME;
                break;
        }
        data.putExtra("type", type);
        data.putExtra("text", mEtSearch.getText().toString().trim());
        setResult(RESULT_OK, data);
        finish();
    }

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_house);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

        mLLHostName.setVisibility(View.INVISIBLE);
        mLLMap.setVisibility(View.INVISIBLE);
        mLLTitle.setVisibility(View.INVISIBLE);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    mLLHostName.setVisibility(View.INVISIBLE);
                    mLLMap.setVisibility(View.INVISIBLE);
                    mLLTitle.setVisibility(View.INVISIBLE);
                }else {
                    mLLHostName.setVisibility(View.VISIBLE);
                    mLLMap.setVisibility(View.VISIBLE);
                    mLLTitle.setVisibility(View.VISIBLE);
                    mTvHostName.setText(s);
                    mTvMap.setText(s);
                    mTvTitle.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
