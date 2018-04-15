package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunxuedian.graduationproject.R;

/**
 * Created by sunxuedian on 2018/4/12.
 */

public class LikeHouseListAdapter extends VerticalHouseListAdapter {

    public LikeHouseListAdapter(Context context, RecyclerViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.like_house_list_item, viewGroup, false);
        return new MyViewHolder(view, mOnItemClickListener);
    }
}
