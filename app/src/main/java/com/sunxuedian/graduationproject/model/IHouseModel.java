package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public interface IHouseModel {

    int HOUSE_TYPE_TOP10 = 0;//获取top10房源的数据
    int HOUSE_TYPE_THEME = 1;//获取主题房源的数据
    int HOUSE_TYPE_STORY_AND_HUMANTOUCH = 2;//获取故事及人情味的房源数据
    int HOUSE_TYPE_ALL = 3;//获取所有数据

    void getBannerViewData(IModelCallback<List<BannerViewBean>> callback);//获取轮播图数据
    void getHouseData(int typeOfHouseData, IModelCallback<List<HouseBean>> callback);//获取房源信息
    void getHotDestination(IModelCallback<List<DestinationBean>> callback);//获取热门目的地

}
