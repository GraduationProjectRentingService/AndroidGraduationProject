package com.sunxuedian.graduationproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HorizontalListContentViewBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.ViewSizeBean;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.presenter.impl.HomepagePresenterImpl;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.ToastUtils;
import com.sunxuedian.graduationproject.utils.ViewUtils;
import com.sunxuedian.graduationproject.view.activity.BrowserActivity;
import com.sunxuedian.graduationproject.view.activity.HouseDetailActivity;
import com.sunxuedian.graduationproject.view.activity.HouseListActivity;
import com.sunxuedian.graduationproject.view.base.BaseFragment;
import com.sunxuedian.graduationproject.view.IHomepageView;
import com.sunxuedian.graduationproject.widgets.BannerView;
import com.sunxuedian.graduationproject.widgets.HorizontalListContentView;
import com.sunxuedian.graduationproject.widgets.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomepageFragment extends BaseFragment<IHomepageView, HomepagePresenterImpl> implements IHomepageView{

    private MyLog logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 绑定View
     */
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//SwipeRefreshLayout
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;//ScrollView
    @BindView(R.id.bannerView)
    BannerView mBannerView;//BannerView
    @BindView(R.id.rlSearchContent)
    RelativeLayout mRlSearchContent;//searchContent
    @BindView(R.id.bgViewSearchBar)
    View mBgViewSearchBar;
    @BindView(R.id.listContentViewOfTop10)
    HorizontalListContentView mListContentViewOfTop10;
    @BindView(R.id.listContentViewOfTheme)
    HorizontalListContentView mListContentViewOfTheme;
    @BindView(R.id.listContentViewOfHotDestination)
    HorizontalListContentView mListContentViewOfHotDestination;
    @BindView(R.id.listContentViewOfStory)
    HorizontalListContentView mListContentViewOfStory;

    private ViewSizeBean mViewSizeOfBannerView;
    private ViewSizeBean mViewSizeOfSearchContent;
    private int heightOfSearchBar;
    private int marginOfSearchBar;
    private int maxYOfSearchBar;
    private boolean isSearchContentHasBorder = false;//判断搜索框是否有边框

    private List<HouseBean> mTop10HouseData = new ArrayList<>();//top10的房源数据
    private List<HouseBean> mThemeHouseData = new ArrayList<>();//主题房源
    private List<HouseBean> mStoryHouseData = new ArrayList<>();//故事房源
    private List<DestinationBean> mDestinationData = new ArrayList<>();//热门地方

    /**
     * 点击了搜索栏
    */
    @OnClick(R.id.rlSearchContent)
    void onSearchClick(){
        Intent intent = new Intent(getActivity(), HouseListActivity.class);
        startActivity(intent);
    }

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
        loadData();
        return view;
    }

    private void initView(){

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                handler.sendEmptyMessageDelayed(0, 2000);
//                ToastUtils.showToast("刷新");
                loadData();
            }
        });

        mBannerView.setOnLoadImageListener(new BannerView.OnLoadImageListener() {
            @Override
            public void onLoadImage(ImageView imageView, String url) {
                ImageLoader.showImage(HomepageFragment.this, imageView, url);
            }
        });
        mBannerView.setOnBannerViewClickListener(new BannerView.OnBannerViewClickListener() {
            @Override
            public void onClick(BannerViewBean item) {
//                ToastUtils.showToast(index + "openUrl: " + list.get(index).getWebUrl());
                Intent intent = new Intent(getActivity(), BrowserActivity.class);
                intent.putExtra("webUrl", item.getWebUrl());
                startActivity(intent);

            }
        });

        mScrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener(){

            @Override
            public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //当BannerView整个显示的时候
                if (scrollY <= 0){
                    mRlSearchContent.setY(maxYOfSearchBar);
                    return;
                }

                mBgViewSearchBar.setAlpha(0);
                if (scrollY <= mViewSizeOfBannerView.getHeight() - mViewSizeOfSearchContent.getHeight() - 2 * marginOfSearchBar){
                    float mRlSearchNewY = mRlSearchContent.getY() - scrollY + oldScrollY;
                    //在bannerView区间
                    if (mRlSearchNewY <= maxYOfSearchBar && mRlSearchNewY >= marginOfSearchBar){
                        mRlSearchContent.setY(mRlSearchNewY);
                        //设置SearchBar的透明度
                        if (mRlSearchNewY <= heightOfSearchBar){
                            float alpha = 1.0f - (mRlSearchNewY-marginOfSearchBar)/(heightOfSearchBar-marginOfSearchBar);
                            logger.d("alpha: " + alpha);
                            mBgViewSearchBar.setAlpha(alpha);
                        }
                    }

                    //当整个searchContent进入searchBar中，将searchContent的背景换成有边框的
                    if (mRlSearchNewY < 2 * marginOfSearchBar){
                        if (!isSearchContentHasBorder){
                            mRlSearchContent.setBackgroundResource(R.drawable.bg_white_border_et_search);
                            isSearchContentHasBorder = true;
                            logger.d("换成有边框的");
                        }
                    }else {
                        if (isSearchContentHasBorder){
                            mRlSearchContent.setBackgroundResource(R.drawable.bg_white_no_border_et_search);
                            isSearchContentHasBorder = false;
                            logger.d("换成没有边框的");
                        }
                    }
                }else {
                    //当BannerView已经滚动消失了
                    mRlSearchContent.setY(marginOfSearchBar);
                    mBgViewSearchBar.setAlpha(1.0f);
                }

            }
        });

        mListContentViewOfTop10.setTitle(R.string.text_top10_title);
        mListContentViewOfTop10.setRecyclerViewItemClickListener(new HorizontalListContentView.OnItemClickListener() {
            @Override
            public void onItemClick(HorizontalListContentViewBean item, int pos) {
                Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra(HouseBean.TAG, mTop10HouseData.get(pos));
                startActivity(intent);
            }
        });
        mListContentViewOfTop10.setGoMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HouseListActivity.class);
                intent.putParcelableArrayListExtra(HouseBean.TAG, (ArrayList<? extends Parcelable>) mTop10HouseData);
                startActivity(intent);
            }
        });

        mListContentViewOfTheme.setTitle(R.string.text_theme_title);
        mListContentViewOfTheme.setRecyclerViewItemClickListener(new HorizontalListContentView.OnItemClickListener() {
            @Override
            public void onItemClick(HorizontalListContentViewBean item, int pos) {
//                ToastUtils.showToast("pos: " + pos + "item: " + item.getTitle());
                Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra(HouseBean.TAG, mThemeHouseData.get(pos));
                startActivity(intent);
            }
        });
        mListContentViewOfTheme.setGoMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.obtainBannerView();
                Intent intent = new Intent(getActivity(), HouseListActivity.class);
                intent.putParcelableArrayListExtra(HouseBean.TAG, (ArrayList<? extends Parcelable>) mThemeHouseData);
                startActivity(intent);
            }
        });

        mListContentViewOfHotDestination.dismissRecyclerViewTitle();
        mListContentViewOfHotDestination.setTitle(R.string.text_hot_destination_title);
        mListContentViewOfHotDestination.setRecyclerViewItemClickListener(new HorizontalListContentView.OnItemClickListener() {
            @Override
            public void onItemClick(HorizontalListContentViewBean item, int pos) {
//                ToastUtils.showToast("pos: " + pos + "item: " + item.getTitle());
                Intent intent = new Intent(getActivity(), HouseListActivity.class);
                intent.putExtra("text", mDestinationData.get(pos).getTitle());
                intent.putExtra("type", IHouseModel.SEARCH_TYPE_MAP);
                intent.putExtra("search", true);
                startActivity(intent);
            }
        });
        mListContentViewOfHotDestination.setGoMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.obtainBannerView();
                ToastUtils.showToast("没有更多~");
            }
        });

        mListContentViewOfStory.setTitle(R.string.text_story_title);
        mListContentViewOfStory.setRecyclerViewItemClickListener(new HorizontalListContentView.OnItemClickListener() {
            @Override
            public void onItemClick(HorizontalListContentViewBean item, int pos) {
//                ToastUtils.showToast("pos: " + pos + "item: " + item.getTitle());
                Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra(HouseBean.TAG, mStoryHouseData.get(pos));
                startActivity(intent);
            }
        });
        mListContentViewOfStory.setGoMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.obtainBannerView();
                Intent intent = new Intent(getActivity(), HouseListActivity.class);
                intent.putParcelableArrayListExtra(HouseBean.TAG, (ArrayList<? extends Parcelable>) mStoryHouseData);
                startActivity(intent);
            }
        });

        mViewSizeOfBannerView = ViewUtils.getViewSize(mBannerView);
        mViewSizeOfSearchContent = ViewUtils.getViewSize(mRlSearchContent);
        heightOfSearchBar = (int) getResources().getDimension(R.dimen.heightOfSearchBar);
        marginOfSearchBar = (int) getResources().getDimension(R.dimen.marginLeftOfRightOfSearchBar);
        maxYOfSearchBar = mViewSizeOfBannerView.getHeight() - mViewSizeOfSearchContent.getHeight() - marginOfSearchBar;

    }

    /**
     * 调用Presenter加载所有页面数据
     */
    private void loadData(){
        //获取数据
        mPresenter.obtainBannerView();
        mPresenter.obtainTop10House();
        mPresenter.obtainThemeHouse();
        mPresenter.obtainHotDestination();
        mPresenter.obtainStoryAndHumanTouchHouse();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBannerView.startBannerScrollTask(2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerView.stopBannerScrollTask();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showBannerView(final List<BannerViewBean> list) {
        mBannerView.setBannerViewData(list);
        mBannerView.startBannerScrollTask(2000);
    }

    /**
     * 显示推荐的Top10房源
     * @param list
     */
    @Override
    public void showTop10House(List<HouseBean> list) {
        List<HorizontalListContentViewBean> data = new ArrayList<>();
        int i = 0;
        for (HouseBean houseBean: list){
            if (i == 5){
                break;
            }
            HorizontalListContentViewBean bean = new HorizontalListContentViewBean();
            bean.setTitle(houseBean.getTitle());
            bean.setImgUrl(houseBean.getImgUrl());
            data.add(bean);
            ++i;
        }
        mTop10HouseData.clear();
        mTop10HouseData.addAll(list);
        mListContentViewOfTop10.setData(data);
    }

    /**
     * 显示主题房源
     * @param list
     */
    @Override
    public void showThemeHouse(List<HouseBean> list) {
        List<HorizontalListContentViewBean> data = new ArrayList<>();
        int i = 0;
        for (HouseBean houseBean: list){
            if (i == 5){
                break;
            }
            i ++;
            HorizontalListContentViewBean bean = new HorizontalListContentViewBean();
            bean.setTitle(houseBean.getTitle());
            bean.setImgUrl(houseBean.getImgUrl());
            data.add(bean);
        }
        mThemeHouseData.clear();
        mThemeHouseData.addAll(list);
        mListContentViewOfTheme.setData(data);
    }

    /**
     * 显示热门目的地信息
     * @param list
     */
    @Override
    public void showHotDestination(List<DestinationBean> list) {
        List<HorizontalListContentViewBean> data = new ArrayList<>();
        for (DestinationBean destinationBean: list){
            HorizontalListContentViewBean bean = new HorizontalListContentViewBean();
            bean.setTitle(destinationBean.getTitle());
            bean.setImgUrl(destinationBean.getImgUrl());
            data.add(bean);
        }
        mDestinationData.clear();
        mDestinationData.addAll(list);
        mListContentViewOfHotDestination.setData(data);
    }

    /**
     * 显示故事和人情味的房源信息
     * @param list
     */
    @Override
    public void showStoryAndHumanTouchHouse(List<HouseBean> list) {
        List<HorizontalListContentViewBean> data = new ArrayList<>();
        int i = 0;
        for (HouseBean houseBean: list){
            if (i == 5){
                break;
            }
            i ++;

            HorizontalListContentViewBean bean = new HorizontalListContentViewBean();
            bean.setTitle(houseBean.getTitle());
            bean.setImgUrl(houseBean.getImgUrl());
            data.add(bean);
        }
        mStoryHouseData.clear();
        mStoryHouseData.addAll(list);
        mListContentViewOfStory.setData(data);
    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout.isRefreshing()){
            return;
        }

        logger.d("showLoading");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        if (mSwipeRefreshLayout.isRefreshing()){
            logger.d("stopLoading");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
