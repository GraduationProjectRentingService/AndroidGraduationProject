package com.sunxuedian.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.bean.FacilityGridViewBean;
import com.sunxuedian.graduationproject.utils.ImageLoader;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/1.
 */

public class FacilityGridViewAdapter extends BaseAdapter{

    private List<FacilityGridViewBean> mData;
    private Context mContext;

    public FacilityGridViewAdapter(List<FacilityGridViewBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public FacilityGridViewBean getItem(int position) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.facility_grid_view, null);
            viewHolder = new ViewHolder();
            //绑定控件
            viewHolder.textView = convertView.findViewById(R.id.tvText);
            viewHolder.imageView = convertView.findViewById(R.id.ivIcon);
            convertView.setTag(viewHolder);//保存复用View
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FacilityGridViewBean item = getItem(position);//获取列表项的数据源
        //显示数据源到列表项中
        ImageLoader.showImage(mContext, viewHolder.imageView, item.getResId());
        viewHolder.textView.setText(item.getText());
        if (item.isTextGray()){
            viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.colorMainGray));
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
