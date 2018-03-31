package com.sunxuedian.graduationproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunxuedian.graduationproject.widgets.HorizontalListContentView;

/**
 * Created by sunxuedian on 2018/3/17.
 */

public abstract class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);
        if (onItemClickListener != null){
            //设置每一项的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, getPosition());
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

}
