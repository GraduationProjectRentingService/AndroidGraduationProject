package com.sunxuedian.graduationproject.model.impl;

import android.text.TextUtils;

import com.sunxuedian.graduationproject.bean.BannerViewBean;
import com.sunxuedian.graduationproject.bean.DestinationBean;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.bean.HouseDto;
import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.bean.UserBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;
import com.sunxuedian.graduationproject.model.IHouseModel;
import com.sunxuedian.graduationproject.utils.JsonUtils;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;
import com.sunxuedian.graduationproject.utils.OkHttpUtils;
import com.sunxuedian.graduationproject.utils.UrlParamsUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by sunxuedian on 2018/1/23.
 */

public class HouseModelImpl implements IHouseModel {
    private MyLog logger = LoggerFactory.getLogger(getClass());

    private static HouseModelImpl mInstance;

    private List<HouseBean> mLikeHouseList = new ArrayList<>();

    private HouseModelImpl(){}

    public static HouseModelImpl getInstance(){
        if (mInstance == null){
            synchronized (HouseModelImpl.class){
                if (mInstance == null){
                    mInstance = new HouseModelImpl();
                }
            }
        }
        return mInstance;
    }

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
    public void getHouseData(final int typeOfHouseData, final IModelCallback<List<HouseBean>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("managementId", "Admin");
        params.put("password", "123456");
        params.put("haveReviewed",1);
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_GET_ALL_HOUSE, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (TextUtils.equals(responseBean.getCode(),UrlParamsUtils.SUCCESS_CODE)){
                    try {
                        List<HouseDto> list = JsonUtils.getListByJSONArray(HouseDto.class, responseBean.getContent().getJSONArray("list"));
                        switch (typeOfHouseData){
                            case HOUSE_TYPE_ALL:
                                callback.onSuccess(HouseBean.transList(list));
                                break;
                            case HOUSE_TYPE_TOP10:
                                callback.onSuccess(HouseBean.transList(list.subList(0, list.size()/3)));
                                break;
                            case HOUSE_TYPE_THEME:
                                callback.onSuccess(HouseBean.transList(list.subList(list.size()/3, 2*list.size()/3)));
                                break;
                            case HOUSE_TYPE_STORY_AND_HUMANTOUCH:
                                callback.onSuccess(HouseBean.transList(list.subList(2*list.size()/3, list.size())));
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    callback.onFailure(responseBean.getMessage());
                }
            }
        });
    }


    private void testData(IModelCallback<List<HouseBean>> callback){
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

        int moneyOfEachNight[]  = {300, 100, 199, 677};

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

        String imgUrls[] = {"chaozhou.jpg", "beijing.jpg","daocheng.jpg","shanghai.jpg", "wuhan.jpg"};


        for (String imgUrl : imgUrls) {
            DestinationBean bean = new DestinationBean();
            bean.setImgUrl("http://47.106.77.184:8099/milu/img/" +imgUrl);
            list.add(bean);
        }

        callback.onSuccess(list);
    }

    @Override
    public void addHouseToLike(UserBean userBean, HouseBean houseBean, IModelCallback<String> callback) {
        //模拟
        if (!mLikeHouseList.contains(houseBean)){
            houseBean.setLike(true);
            mLikeHouseList.add(houseBean);
        }
        callback.onSuccess("添加成功");
    }

    @Override
    public void getLikeHouseList(UserBean userBean, IModelCallback<List<HouseBean>> callback) {
        callback.onSuccess(mLikeHouseList);
    }

}
