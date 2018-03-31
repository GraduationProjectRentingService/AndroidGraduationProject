package com.sunxuedian.graduationproject.view;

import com.sunxuedian.graduationproject.bean.BannerViewBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/26.
 */

public interface IHouseDetailView extends IProgressView {
    void showBannerImages(List<BannerViewBean> list);
    void showErrorMsg(String msg);
}
