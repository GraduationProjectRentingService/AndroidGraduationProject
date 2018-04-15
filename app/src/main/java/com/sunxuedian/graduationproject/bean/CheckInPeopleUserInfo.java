package com.sunxuedian.graduationproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 入住人信息类
 * Created by sunxuedian on 2018/4/1.
 */

public class CheckInPeopleUserInfo implements Parcelable, Serializable{
    public static final String TAG = "CheckInPeopleUserInfo";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private String idCard;
    private String phone;
    private boolean isCheck;

    protected CheckInPeopleUserInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        idCard = in.readString();
        phone = in.readString();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<CheckInPeopleUserInfo> CREATOR = new Creator<CheckInPeopleUserInfo>() {
        @Override
        public CheckInPeopleUserInfo createFromParcel(Parcel in) {
            return new CheckInPeopleUserInfo(in);
        }

        @Override
        public CheckInPeopleUserInfo[] newArray(int size) {
            return new CheckInPeopleUserInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CheckInPeopleUserInfo() {

    }

    public CheckInPeopleUserInfo(String name, String idCard, String phone) {

        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(idCard);
        dest.writeString(phone);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckInPeopleUserInfo){
            CheckInPeopleUserInfo info = (CheckInPeopleUserInfo) obj;
            return info.getIdCard().equals(idCard)&&info.getName().equals(name);
        }
        return super.equals(obj);
    }
}
