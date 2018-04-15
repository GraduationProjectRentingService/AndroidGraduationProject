package com.sunxuedian.graduationproject.view.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.view.fragment.LikeHouseFragment;

public class LikeHouseListActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_house_list);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LikeHouseFragment likeHouseFragment = new LikeHouseFragment(true);
        fragmentTransaction.add(R.id.mainFragment, likeHouseFragment);
        fragmentTransaction.commit();
    }
}
