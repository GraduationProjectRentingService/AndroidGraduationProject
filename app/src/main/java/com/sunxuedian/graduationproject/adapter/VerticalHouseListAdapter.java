package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.HouseBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunxuedian on 2018/3/22.
 */

public class VerticalHouseListAdapter extends RecyclerView.Adapter<VerticalHouseListAdapter.MyViewHolder> {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private List<HouseBean> mData;
    private Context mContext;
    private RecyclerViewHolder.OnItemClickListener mOnItemClickListener;

    public VerticalHouseListAdapter(Context context, RecyclerViewHolder.OnItemClickListener onItemClickListener){
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<HouseBean> list){
        mData = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.house_vertical_list_item, viewGroup, false);
        return new MyViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        HouseBean houseBean = mData.get(i);
        myViewHolder.mIvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        myViewHolder.mTvTitle.setText(houseBean.getTitle());
        myViewHolder.mTvDescription.setText(houseBean.getDescription());
        myViewHolder.mTvMoney.setText(String.format("￥ %s", houseBean.getMoneyOfEachNight()));
        ImageLoader.showImage(mContext, myViewHolder.mIvHouse, houseBean.getImgUrl());
        ImageLoader.showImage(mContext, myViewHolder.mCircleImageView, houseBean.getHomeownerIconUrl());
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerViewHolder {

        private ImageView mIvHouse;
        private ImageView mIvLike;
        private CircleImageView mCircleImageView;
        private TextView mTvMoney;
        private TextView mTvTitle;
        private TextView mTvDescription;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
            mIvHouse = itemView.findViewById(R.id.ivHouse);
            mIvLike = itemView.findViewById(R.id.ivLike);
            mCircleImageView = itemView.findViewById(R.id.circleImageView);
            mTvMoney = itemView.findViewById(R.id.tvMoney);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
