package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.OrderBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;

import java.util.Date;
import java.util.List;


/**
 * Created by sunxuedian on 2018/4/13.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<OrderBean> mData;
    protected Context mContext;
    protected RecyclerViewHolder.OnItemClickListener mOnItemClickListener;

    public OrderListAdapter(Context mContext, List<OrderBean> mData, RecyclerViewHolder.OnItemClickListener mOnItemClickListener) {
        this.mData = mData;
        this.mContext = mContext;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, viewGroup, false);
        return new MyViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int pos) {
        OrderBean orderBean = mData.get(pos);
        myViewHolder.mTvTitle.setText(orderBean.getHouseTitle());
        Date inDate = new Date(orderBean.getCheckInDate());
        Date outDate = new Date(orderBean.getCheckOutDate());
        myViewHolder.mTvDayDetail.setText(String.format("%d月%d日-%d月%d日 共%d晚", inDate.getMonth()+1, inDate.getDate()
        , outDate.getMonth() + 1, outDate.getDate(), orderBean.getDayNum()));

        myViewHolder.mTvRentalType.setText(orderBean.getHouseRentalType());
        myViewHolder.mTvTotalMoney.setText("订单总额：￥" + orderBean.getTotalMoney());
        ImageLoader.showImage(mContext, myViewHolder.mIvHouse, orderBean.getHouseImgUrl());
        String status = "";
        switch (orderBean.getStatus()){
            case OrderBean.STATUS_CANCEL:
                status = "已取消";
                break;
            case OrderBean.STATUS_FINISH:
                status = "已完成";
                break;
            case OrderBean.STATUS_UNPAY:
                status = "未支付";
                break;
        }
        myViewHolder.mTvOrderStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerViewHolder{

        TextView mTvTitle;
        TextView mTvOrderStatus;
        ImageView mIvHouse;
        TextView mTvDayDetail;
        TextView mTvRentalType;
        TextView mTvTotalMoney;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            mIvHouse = itemView.findViewById(R.id.ivHouse);
            mTvDayDetail = itemView.findViewById(R.id.tvDayDetail);
            mTvRentalType = itemView.findViewById(R.id.tvRentalType);
            mTvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
        }
    }
}
