package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;

import java.util.List;

/**
 * 入住人列表的适配器
 * Created by sunxuedian on 2018/4/1.
 */

public class CheckInPeopleListAdapter extends BaseAdapter {

    private List<CheckInPeopleUserInfo> mData;
    private Context mContext;
    private OnDeleteListener mOnDeleteListener;

    public CheckInPeopleListAdapter(List<CheckInPeopleUserInfo> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public void setOnDeleteLisenter(OnDeleteListener lisenter){
        mOnDeleteListener = lisenter;
    }

    public void addItem(CheckInPeopleUserInfo inPeopleUserInfo){
        if (mData == null){
            return;
        }

        mData.add(inPeopleUserInfo);
    }
    public void removeItem(int pos){
        if (mData == null || mData.size() <= pos){
            return;
        }

        mData.remove(pos);
    }

    @Override
    public int getCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public CheckInPeopleUserInfo getItem(int position) {
        if (mData != null){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.check_in_people_list_item, null);
        if (mOnDeleteListener != null){
            convertView.findViewById(R.id.ivDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnDeleteListener.onDeleted(position);
                }
            });
        }
        CheckInPeopleUserInfo info = getItem(position);
        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(info.getName());
        TextView tvIdCard = convertView.findViewById(R.id.tvIdCard);
        tvIdCard.setText("身份证：" + info.getIdCard());
        return convertView;
    }

    public interface OnDeleteListener {
        void onDeleted(int pos);
    }
}
