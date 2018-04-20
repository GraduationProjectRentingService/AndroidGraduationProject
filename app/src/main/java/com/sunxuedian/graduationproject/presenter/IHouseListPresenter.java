package com.sunxuedian.graduationproject.presenter;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public interface IHouseListPresenter {
    void getHouseList();
    void searchHouse(int type, String key, boolean isRefresh);
}
