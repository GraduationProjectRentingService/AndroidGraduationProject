package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public interface IHomepageView extends IProgressView{

    void showErrorMsg(String msg);//显示失败信息
    void showBannerView(List<BannerViewBean> list);//显示轮播图
    void showTop10House(List<HouseBean> list);//显示推荐的Top10房源
    void showThemeHouse(List<HouseBean> list);//显示主题房源
    void showHotDestination(List<DestinationBean> list);//显示热门目的地
    void showStoryAndHumanTouchHouse(List<HouseBean> list);//显示故事，人情味主题的房源
}