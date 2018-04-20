package com.sunxuedian.graduationproject.view.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aitangba.swipeback.SwipeBackActivity;
import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.view.fragment.OrderManagerFragment;

public class OrderListActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        OrderManagerFragment orderManagerFragment = new OrderManagerFragment();
        transaction.add(R.id.mainFragment, orderManagerFragment);
        transaction.commit();
    }
}
