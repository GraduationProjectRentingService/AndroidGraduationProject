package com.sunxuedian.graduationproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunxuedian on 2018/3/15.
 */

public class HouseBean implements Parcelable{

    public static String TAG = "HouseBean";

    private static MyLog logger = LoggerFactory.getLogger(HouseBean.class);

    private long id;
    private String imgUrl;
    private String title;
    private String hostPhoneNum;//房东电话
    private String homeownerIconUrl;//房东头像
    private List<String> houseImgUrls = new ArrayList<>();//多组房源照片
    private String description;
    private boolean isLike;
    private int moneyOfEachNight;//每晚的价格
    private int deposit;//押金
    private String location;//房源位置
    private String rentalTypeText;//出租类型
    private int peopleNum;//适合人数
    private int bedNum;//床数
    private int leastDay;//至少多少天
    private int mostDay;//最多多少天
    private String houseInfo;//内部情况

    public HouseBean(){}

    protected HouseBean(Parcel in) {
        id = in.readLong();
        imgUrl = in.readString();
        title = in.readString();
        hostPhoneNum = in.readString();
        homeownerIconUrl = in.readString();
        houseImgUrls = in.createStringArrayList();
        description = in.readString();
        isLike = in.readByte() != 0;
        moneyOfEachNight = in.readInt();
        deposit = in.readInt();
        location = in.readString();
        rentalTypeText = in.readString();
        peopleNum = in.readInt();
        bedNum = in.readInt();
        leastDay = in.readInt();
        mostDay = in.readInt();
        houseInfo = in.readString();
    }

    public static final Creator<HouseBean> CREATOR = new Creator<HouseBean>() {
        @Override
        public HouseBean createFromParcel(Parcel in) {
            return new HouseBean(in);
        }

        @Override
        public HouseBean[] newArray(int size) {
            return new HouseBean[size];
        }
    };

    /**
     * 将HouseDto的list转换为HouseBean的list
     * @param list
     * @return
     */
    public static List<HouseBean> transList(List<HouseDto> list){
        if (list == null || list.size() == 0){
            return new ArrayList<>();
        }

        List<HouseBean> resultList = new ArrayList<>();
        for (HouseDto dto:list) {
            resultList.add(createHouseBean(dto));
        }
        return resultList;
    }
    /**
     * 通过HouseDto构造一个HouseBean对象
     * @param houseDto
     * @return
     */
    public static HouseBean createHouseBean(HouseDto houseDto){
        if (houseDto == null){
            return new HouseBean();
        }
        HouseBean houseBean = new HouseBean();
        houseBean.setId(houseDto.getId());
        houseBean.setImgUrl(houseDto.getPicOne());
        houseBean.setTitle(houseDto.getTitle());
        houseBean.setHostPhoneNum(houseDto.getHostId());
        houseBean.setHomeownerIconUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1224306677,1730366661&fm=27&gp=0.jpg");
        String picAll = houseDto.getPicAll();
        picAll = picAll.substring(1, picAll.length() - 1);
//        logger.e(picAll);
        String[] ary = picAll.split(",");
        List<String> picList = new ArrayList<>();
        for (String i:ary) {
            i = i.substring(1, i.length() - 1);
            picList.add(i);
        }
        houseBean.setHouseImgUrls(picList);
        houseBean.setDeposit((int) houseDto.getDeposit());
        houseBean.setDescription(houseDto.getDescription());
        houseBean.setHouseInfo(houseDto.getHouseInfo());
        houseBean.setMoneyOfEachNight((int) houseDto.getDailyPrice());
        houseBean.setBedNum(1);
        houseBean.setRentalTypeText(houseDto.getRentalTypeText());
        houseBean.setPeopleNum((int) houseDto.getPeopleNum());
        houseBean.setLeastDay((int) houseDto.getLeastDay());
        houseBean.setMostDay((int) houseDto.getMostDay());
        houseBean.setLocation(houseDto.getAddress());
        return houseBean;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHostPhoneNum() {
        return hostPhoneNum;
    }

    public void setHostPhoneNum(String hostPhoneNum) {
        this.hostPhoneNum = hostPhoneNum;
    }

    public String getHomeownerIconUrl() {
        return homeownerIconUrl;
    }

    public void setHomeownerIconUrl(String homeownerIconUrl) {
        this.homeownerIconUrl = homeownerIconUrl;
    }

    public List<String> getHouseImgUrls() {
        return houseImgUrls;
    }

    public void setHouseImgUrls(List<String> houseImgUrls) {
        this.houseImgUrls = houseImgUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getMoneyOfEachNight() {
        return moneyOfEachNight;
    }

    public void setMoneyOfEachNight(int moneyOfEachNight) {
        this.moneyOfEachNight = moneyOfEachNight;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRentalTypeText() {
        return rentalTypeText;
    }

    public void setRentalTypeText(String rentalTypeText) {
        this.rentalTypeText = rentalTypeText;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getBedNum() {
        return bedNum;
    }

    public void setBedNum(int bedNum) {
        this.bedNum = bedNum;
    }

    public int getLeastDay() {
        return leastDay;
    }

    public void setLeastDay(int leastDay) {
        this.leastDay = leastDay;
    }

    public int getMostDay() {
        return mostDay;
    }

    public void setMostDay(int mostDay) {
        this.mostDay = mostDay;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(imgUrl);
        dest.writeString(title);
        dest.writeString(hostPhoneNum);
        dest.writeString(homeownerIconUrl);
        dest.writeStringList(houseImgUrls);
        dest.writeString(description);
        dest.writeByte((byte) (isLike ? 1 : 0));
        dest.writeInt(moneyOfEachNight);
        dest.writeInt(deposit);
        dest.writeString(location);
        dest.writeString(rentalTypeText);
        dest.writeInt(peopleNum);
        dest.writeInt(bedNum);
        dest.writeInt(leastDay);
        dest.writeInt(mostDay);
        dest.writeString(houseInfo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HouseBean){
            return ((HouseBean) obj).getId() == id;
        }
        return super.equals(obj);
    }
}
