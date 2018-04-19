package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.MessageBean;

import java.util.List;

import static android.R.id.list;

/**
 * Created by sunxuedian on 2018/4/19.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageBean> mData;
    private Context mContext;
    private RecyclerViewHolder.OnItemClickListener mOnItemClickListener;

    public MessageAdapter(Context context, List<MessageBean> list){
        mContext = context;
        mData = list;
    }

    public void setOnItemClickListener(RecyclerViewHolder.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, viewGroup, false);
        return new ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MessageBean messageBean = mData.get(i);
        viewHolder.mTvMessage.setText(messageBean.getMessageContent());
        viewHolder.mTvTime.setText(messageBean.getTime());
        viewHolder.mTvTitle.setText(messageBean.getMessageTitle());
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerViewHolder{
        TextView mTvTitle;
        TextView mTvTime;
        TextView mTvMessage;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvTime = itemView.findViewById(R.id.tvTime);
            mTvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
