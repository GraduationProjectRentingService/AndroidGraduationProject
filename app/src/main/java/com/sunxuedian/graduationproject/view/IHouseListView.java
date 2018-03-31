package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.HouseBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public interface IHouseListView extends IProgressView{
    void showHouseList(List<HouseBean> list);//显示从网络获取的房源列表
    void showError(String msg);//显示错误提示
}
