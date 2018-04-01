package com.sunxuedian.graduationproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.CheckInPeopleListAdapter;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.utils.ListViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预定房源
 */
public class ReverseHouseActivity extends SwipeBackActivity {

    public static final int CODE_GET_CHECK_IN_PEOPLE = 11;

    @BindView(R.id.lvCheckInPeople)
    ListView mLvCheckInPeople;//入住人列表

    @OnClick(R.id.ivBack)
    public void gpBack(){
        finish();
    }

    private CheckInPeopleListAdapter mAdapter;
    private List<CheckInPeopleUserInfo> mCheckInPeopleUserInfoList = new ArrayList<>();

    @OnClick(R.id.btnAddPeople)
    public void addCheckInPeople(){
        Intent intent = new Intent(this, ChooseCheckInPeopleActivity.class);
        startActivityForResult(intent, CODE_GET_CHECK_IN_PEOPLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_house);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mAdapter = new CheckInPeopleListAdapter(mCheckInPeopleUserInfoList, this);
        mAdapter.setOnDeleteLisenter(new CheckInPeopleListAdapter.OnDeleteListener() {
            @Override
            public void onDeleted(int pos) {
                mAdapter.removeItem(pos);
                mAdapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
            }
        });
        mLvCheckInPeople.setAdapter(mAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_GET_CHECK_IN_PEOPLE && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<CheckInPeopleUserInfo> list = data.getParcelableArrayListExtra(CheckInPeopleUserInfo.TAG);
                if (list != null && list.size() > 0) {
                    mCheckInPeopleUserInfoList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    ListViewUtil.setListViewHeightBasedOnChildren(mLvCheckInPeople);
                }
            }
        }
    }
}
