package com.sunxuedian.graduationproject.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.widgets.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomepageFragment extends Fragment {

    private Context mContext;

    /**
     * View
     */
    @BindView(R.id.bannerView)
    BannerView mBannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        List<View> list = new ArrayList<>();
        int[] resIds = {R.drawable.test_picture_1, R.drawable.test_picture_2, R.drawable.test_picture_3, R.drawable.test_picture_4, R.drawable.test_picture_5};
        for (Integer id: resIds){
            ImageView view = new ImageView(mContext);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(id);
            list.add(view);
        }
        mBannerView.setViewData(list);
        mBannerView.updateView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
