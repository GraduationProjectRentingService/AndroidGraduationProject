package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import java.util.List;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public interface IHouseModel {

    int HOUSE_TYPE_TOP10 = 0;//获取top10房源的数据
    int HOUSE_TYPE_THEME = 1;//获取主题房源的数据
    int HOUSE_TYPE_STORY_AND_HUMANTOUCH = 2;//获取故事及人情味的房源数据
    int HOUSE_TYPE_ALL = 3;//获取所有数据

    int SEARCH_TYPE_TITLE = 1;//搜索类型为标题
    int SEARCH_TYPE_MAP = 2;//搜索类型为位置
    int SEARCH_TYPE_HOST_INFO = 3;//搜索类型为房东昵称

    void getBannerViewData(IModelCallback<List<BannerViewBean>> callback);//获取轮播图数据
    void getHouseData(int typeOfHouseData, IModelCallback<List<HouseBean>> callback);//获取房源信息
    void getHotDestination(IModelCallback<List<DestinationBean>> callback);//获取热门目的地

    void addHouseToLike(UserBean userBean, HouseBean houseBean, IModelCallback<String> callback);//添加房源到收藏列表中
    void getLikeHouseList(UserBean userBean, IModelCallback<List<HouseBean>> callback);//获取收藏列表
    void removeHouseFromLike(UserBean userBean, HouseBean houseId, IModelCallback<String> callback);//将房源从收藏列表中移除
    void isHouseInLike(UserBean userBean, HouseBean houseId, IModelCallback<Boolean> callback);//判断该房源是否为收藏状态
    void searchHouse(int type, String text, IModelCallback<List<HouseBean>> callback);//搜索接口
}
