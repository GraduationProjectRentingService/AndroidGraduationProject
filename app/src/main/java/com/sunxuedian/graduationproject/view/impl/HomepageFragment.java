package com.sunxuedian.graduationproject.view.impl;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.presenter.impl.HomepagePresenterImpl;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.view.BaseFragment;
import com.sunxuedian.graduationproject.view.IHomepageView;
import com.sunxuedian.graduationproject.widgets.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.x;


public class HomepageFragment extends BaseFragment<IHomepageView, HomepagePresenterImpl> implements IHomepageView{

    private MyLog logger = LoggerFactory.getLogger(this.getClass());

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
    public HomepagePresenterImpl createPresenter() {
        return new HomepagePresenterImpl();
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
        int[] resIds = {R.drawable.test_p_1, R.drawable.test_p_2, R.drawable.test_p_3, R.drawable.test_p_4, R.drawable.test_p_5,R.drawable.test_p_6};
        for (Integer id: resIds){
            ImageView view = new ImageView(getActivity());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(id);
            list.add(view);
        }
        mBannerView.setViewList(list);
        mBannerView.setOnBannerViewClickListener(new BannerView.OnBannerViewClickListener() {
            @Override
            public void onClick(int index) {
                ToastUtils.showToast("index: " + index);
            }
        });
        mBannerView.startBannerScrollTask(2000);
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
