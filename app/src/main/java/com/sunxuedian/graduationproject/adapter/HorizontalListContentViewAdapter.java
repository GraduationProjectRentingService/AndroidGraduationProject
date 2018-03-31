package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.HorizontalListContentViewBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.List;

/**
 * HorizontalListContentView中的RecycleView中Item的适配器
 * Created by sunxuedian on 2018/3/18.
 */

public class HorizontalListContentViewAdapter extends RecyclerView.Adapter<HorizontalListContentViewAdapter.HouseItemViewHolder> {

    private MyLog logger = LoggerFactory.getLogger(getClass(), false);

    private Context mContext;
    private List<HorizontalListContentViewBean> mData;
    
    private RecyclerViewHolder.OnItemClickListener mOnItemClickListener;

    private boolean mIsShowTitle = true;

    public HorizontalListContentViewAdapter(Context context){
        mContext = context;
    }

    public void setData(List<HorizontalListContentViewBean> data){
        mData = data;
    }
    
    public void setOnItemClickListener(RecyclerViewHolder.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public HouseItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        logger.d("onCreateViewHolder: pos" + i);
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_view_item, viewGroup, false);
        return new HouseItemViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(HouseItemViewHolder houseItemViewHolder, int index) {
        HorizontalListContentViewBean item = mData.get(index);
        ImageLoader.showImage(mContext, houseItemViewHolder.mImageView, item.getImgUrl());
        if (mIsShowTitle){
            houseItemViewHolder.mTextView.setText(item.getTitle());
        }else {
            houseItemViewHolder.mTextView.setVisibility(View.GONE);
        }

        logger.d("onBindViewHolder: pos: " + index + " item: " + item.toString());
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    /**
     * 设置没有标题
     */
    public void setNotTitle(){
        mIsShowTitle = false;
    }


    class HouseItemViewHolder extends RecyclerViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public HouseItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);
        }
        
    }
}
