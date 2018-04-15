package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.CheckInPeopleUserInfo;
import com.sunxuedian.graduationproject.view.activity.EditCheckInPeopleActivity;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by sunxuedian on 2018/4/1.
 */

public class ChooseCheckInPeopleListAdapter extends Adapter<ChooseCheckInPeopleListAdapter.MyViewHolder> {

    private List<CheckInPeopleUserInfo> mData;
    private Context mContext;

    public ChooseCheckInPeopleListAdapter(List<CheckInPeopleUserInfo> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_check_in_people_list_item, viewGroup, false);
        return new MyViewHolder(view, null);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int pos) {
        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final CheckInPeopleUserInfo info = mData.get(pos);
        myViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setCheck(myViewHolder.mCheckBox.isChecked());
            }
        });
        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCheckInPeopleActivity.class);
                intent.putExtra(CheckInPeopleUserInfo.TAG, (Parcelable) info);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.tvName.setText(info.getName());
        myViewHolder.tvIdCard.setText("身份证：" + info.getIdCard());
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }


    static class MyViewHolder extends RecyclerViewHolder{

        CheckBox mCheckBox;
        TextView tvName;
        TextView tvIdCard;
        ImageView ivEdit;

        MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
            mCheckBox = itemView.findViewById(R.id.checkbox);
            tvName = itemView.findViewById(R.id.tvName);
            tvIdCard= itemView.findViewById(R.id.tvIdCard);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    /**
     * 获取所有选中的入住人信息列表
     * @return
     */
    public ArrayList<CheckInPeopleUserInfo> getCheckedUserInfoList(){
        ArrayList<CheckInPeopleUserInfo> result = new ArrayList<>();
        for (CheckInPeopleUserInfo info: mData){
            //如果选中，则添加到list中
            if (info.isCheck()){
                result.add(info);
            }
        }
        return result;
    }

}
