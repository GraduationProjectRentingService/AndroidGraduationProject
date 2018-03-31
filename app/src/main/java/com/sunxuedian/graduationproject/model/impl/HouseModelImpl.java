package com.sunxuedian.graduationproject.model.impl;

import android.os.Handler;
import android.os.Looper;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.model.IModelCallback;
import com.sunxuedian.graduationproject.model.IHouseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public class HouseModelImpl implements IHouseModel {

    @Override
    public void getBannerViewData(final IModelCallback<List<BannerViewBean>> callback) {
        final List<BannerViewBean> list = new ArrayList<>();
        String imgUrls[] = {"https://image.xiaozhustatic2.com/00,1600,700/13,0,14,7065,1600,700,8bc11062.jpg",
                            "https://image.xiaozhustatic2.com/00,1600,700/13,0,66,6868,1600,700,cbbd9cee.jpg",
                            "https://image.xiaozhustatic1.com/12/12,0,98,9305,2000,1333,48f5383c.jpg",
                            "https://image.xiaozhustatic1.com/12/12,0,48,28845,1800,1200,0beb510c.jpg"};
        String webUrls[] = {"http://sz.xiaozhu.com/fangzi/23025604703.html",
                            "http://su.xiaozhu.com/fangzi/14865885703.html",
                            "http://cq.xiaozhu.com/fangzi/23975167503.html",
                            "http://cq.xiaozhu.com/fangzi/26375530903.html"};
        for (int i = 0; i < imgUrls.length-2; ++ i){
            BannerViewBean bannerViewBean = new BannerViewBean();
            bannerViewBean.setImgUrl(imgUrls[i]);
            bannerViewBean.setWebUrl(webUrls[i]);
            list.add(bannerViewBean);
        }

        callback.onSuccess(list);

    }

    @Override
    public void getHouseData(int typeOfHouseData, IModelCallback<List<HouseBean>> callback) {
        List<HouseBean> list = new ArrayList<>();
        String imgUrls[] = {"https://image.xiaozhustatic2.com/00,1600,700/13,0,14,7065,1600,700,8bc11062.jpg",
                "https://image.xiaozhustatic2.com/00,1600,700/13,0,66,6868,1600,700,cbbd9cee.jpg",
                "https://image.xiaozhustatic1.com/12/12,0,98,9305,2000,1333,48f5383c.jpg",
                "https://image.xiaozhustatic1.com/12/12,0,48,28845,1800,1200,0beb510c.jpg"};

        String titles[] = {"成都优质房源", "上海优质房源", "广州优质房源", "深圳优质房源"};

        String homeownerIconUrls[] = {"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1192198377,2781673332&fm=58&w=150&h=150&img.JPEG",
        "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1192198377,2781673332&fm=58&w=150&h=150&img.JPEG",
        "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1192198377,2781673332&fm=58&w=150&h=150&img.JPEG",
        "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1192198377,2781673332&fm=58&w=150&h=150&img.JPEG"};

        String descriptions[] = {"整套出租/5张床/适合7人住-5.0分/30点评", "整套出租/5张床/适合7人住-4.0分/30点评","整套出租/6张床/适合7人住-5.0分/30点评","整套出租/5张床/适合7人住-5.0分/30点评"};

        String moneyOfEachNight[]  = {"300", "100", "199", "677"};

        for (int i = 0; i < imgUrls.length; ++ i){
            HouseBean bean = new HouseBean();
            bean.setImgUrl(imgUrls[i]);
            bean.setTitle(titles[i]);
            bean.setHomeownerIconUrl(homeownerIconUrls[i]);
            bean.setDescription(descriptions[i]);
            bean.setMoneyOfEachNight(moneyOfEachNight[i]);
            list.add(bean);
        }

        callback.onSuccess(list);

    }

    @Override
    public void getHotDestination(IModelCallback<List<DestinationBean>> callback) {
        List<DestinationBean> list = new ArrayList<>();

        String imgUrls[] = {"https://image.xiaozhustatic2.com/00,1600,700/13,0,14,7065,1600,700,8bc11062.jpg",
                "https://image.xiaozhustatic2.com/00,1600,700/13,0,66,6868,1600,700,cbbd9cee.jpg",
                "https://image.xiaozhustatic1.com/12/12,0,98,9305,2000,1333,48f5383c.jpg",
                "https://image.xiaozhustatic1.com/12/12,0,48,28845,1800,1200,0beb510c.jpg"};

        for (String imgUrl : imgUrls) {
            DestinationBean bean = new DestinationBean();
            bean.setImgUrl(imgUrl);
            list.add(bean);
        }

        callback.onSuccess(list);
    }

}
