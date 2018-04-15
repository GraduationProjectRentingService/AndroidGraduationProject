package com.sunxuedian.graduationproject.bean;

import org.json.JSONArray;

import java.util.ArrayList;

public class HouseDto {

    private Long id;

    private String hostId;

    private String title;

    private String description;

    private String houseInfo;

    private String trafficCondition;

    private String surroundCondition;

    private String bathroom;

    private String electricAppliances;

    private String facility;

    private String claim;

    private long dailyPrice;

    private long deposit;

    private long addGuest;

    private String tradingRules;

    private String breakContact;

    private long leastDay;

    private long mostDay;

    private long receiveOutside;

    private String otherRequirement;

    private String tip;

    private String address;

    private long rentalType;

    private String rentalTypeText;

    private long peopleNum;

    private long roomNum;

    private long hallNum;

    private long bathroomNum;

    private long kitchenNum;

    private long balconyNum;

    private long roomArea;

    private long liveWithOwner;

    private long haveReviewed;

    private long type;

    private String picOne;

    public String getPicAll() {
        return picAll;
    }

    public void setPicAll(String picAll) {
        this.picAll = picAll;
    }

    private String picAll;

    private String bathroomType;

    public String getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public String getPicOne() {
        return picOne;
    }

    public void setPicOne(String picOne) {
        this.picOne = picOne;
    }


    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getHaveReviewed() {
        return haveReviewed;
    }

    public void setHaveReviewed(long haveReviewed) {
        this.haveReviewed = haveReviewed;
    }

    public long getBalconyNum() {
        return balconyNum;
    }

    public void setBalconyNum(long balconyNum) {
        this.balconyNum = balconyNum;
    }

    public long getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(long roomArea) {
        this.roomArea = roomArea;
    }

    public long getLiveWithOwner() {
        return liveWithOwner;
    }

    public void setLiveWithOwner(long liveWithOwner) {
        this.liveWithOwner = liveWithOwner;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getReplaceBedSheet() {
        return replaceBedSheet;
    }

    public void setReplaceBedSheet(String replaceBedSheet) {
        this.replaceBedSheet = replaceBedSheet;
    }

    private String bed;

    private String replaceBedSheet;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRentalType() {
        return rentalType;
    }

    public void setRentalType(long rentalType) {
        this.rentalType = rentalType;
    }

    public String getRentalTypeText() {
        return rentalTypeText;
    }

    public void setRentalTypeText(String rentalTypeText) {
        this.rentalTypeText = rentalTypeText;
    }

    public long getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(long peopleNum) {
        this.peopleNum = peopleNum;
    }

    public long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(long roomNum) {
        this.roomNum = roomNum;
    }

    public long getHallNum() {
        return hallNum;
    }

    public void setHallNum(long hallNum) {
        this.hallNum = hallNum;
    }

    public long getBathroomNum() {
        return bathroomNum;
    }

    public void setBathroomNum(long bathroomNum) {
        this.bathroomNum = bathroomNum;
    }

    public long getKitchenNum() {
        return kitchenNum;
    }

    public void setKitchenNum(long kitchenNum) {
        this.kitchenNum = kitchenNum;
    }

    public String getTradingRules() {
        return tradingRules;
    }

    public void setTradingRules(String tradingRules) {
        this.tradingRules = tradingRules;
    }

    public String getBreakContact() {
        return breakContact;
    }

    public void setBreakContact(String breakContact) {
        this.breakContact = breakContact;
    }

    public long getLeastDay() {
        return leastDay;
    }

    public void setLeastDay(long leastDay) {
        this.leastDay = leastDay;
    }

    public long getMostDay() {
        return mostDay;
    }

    public void setMostDay(long mostDay) {
        this.mostDay = mostDay;
    }

    public long getReceiveOutside() {
        return receiveOutside;
    }

    public void setReceiveOutside(long receiveOutside) {
        this.receiveOutside = receiveOutside;
    }

    public String getOtherRequirement() {
        return otherRequirement;
    }

    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public long getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(long dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public long getAddGuest() {
        return addGuest;
    }

    public void setAddGuest(long addGuest) {
        this.addGuest = addGuest;
    }

    public String getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(String otherPrice) {
        this.otherPrice = otherPrice;
    }

    private String otherPrice;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getTrafficCondition() {
        return trafficCondition;
    }

    public void setTrafficCondition(String trafficCondition) {
        this.trafficCondition = trafficCondition;
    }

    public String getSurroundCondition() {
        return surroundCondition;
    }

    public void setSurroundCondition(String surroundCondition) {
        this.surroundCondition = surroundCondition;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getElectricAppliances() {
        return electricAppliances;
    }

    public void setElectricAppliances(String electricAppliances) {
        this.electricAppliances = electricAppliances;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @Override
    public String toString() {
        return "HouseDto{" +
                "id=" + id +
                ", hostId='" + hostId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", houseInfo='" + houseInfo + '\'' +
                ", trafficCondition='" + trafficCondition + '\'' +
                ", surroundCondition='" + surroundCondition + '\'' +
                ", bathroom='" + bathroom + '\'' +
                ", electricAppliances='" + electricAppliances + '\'' +
                ", facility='" + facility + '\'' +
                ", claim='" + claim + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", deposit=" + deposit +
                ", addGuest=" + addGuest +
                ", tradingRules='" + tradingRules + '\'' +
                ", breakContact='" + breakContact + '\'' +
                ", leastDay=" + leastDay +
                ", mostDay=" + mostDay +
                ", receiveOutside=" + receiveOutside +
                ", otherRequirement='" + otherRequirement + '\'' +
                ", tip='" + tip + '\'' +
                ", address='" + address + '\'' +
                ", rentalType=" + rentalType +
                ", rentalTypeText='" + rentalTypeText + '\'' +
                ", peopleNum=" + peopleNum +
                ", roomNum=" + roomNum +
                ", hallNum=" + hallNum +
                ", bathroomNum=" + bathroomNum +
                ", kitchenNum=" + kitchenNum +
                ", balconyNum=" + balconyNum +
                ", roomArea=" + roomArea +
                ", liveWithOwner=" + liveWithOwner +
                ", haveReviewed=" + haveReviewed +
                ", type=" + type +
                ", picOne='" + picOne + '\'' +
                ", picAll='" + picAll + '\'' +
                ", bathroomType='" + bathroomType + '\'' +
                ", bed='" + bed + '\'' +
                ", replaceBedSheet='" + replaceBedSheet + '\'' +
                ", otherPrice='" + otherPrice + '\'' +
                '}';
    }
}
