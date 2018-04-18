package com.sunxuedian.graduationproject.presenter;

/**
 * Created by sunxuedian on 2018/3/26.
 */

public interface IHouseDetailPresenter {
    void getBannerImages();
    void addHouseToLike();//将该房源收藏起来
    void removeHouseFromLike();//将房源移除收藏列表
    void isHouseInLike();//判断房源是否在收藏列表中
}
