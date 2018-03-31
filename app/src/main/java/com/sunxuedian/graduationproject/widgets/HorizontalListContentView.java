package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;
import com.sunxuedian.graduationproject.adapter.HorizontalListContentViewAdapter;
import com.sunxuedian.graduationproject.adapter.RecyclerViewHolder;
import com.sunxuedian.graduationproject.bean.HorizontalListContentViewBean;
import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import java.util.List;

/**
 * Created by sunxuedian on 2018/3/17.
 */

public class HorizontalListContentView extends LinearLayout {

    private MyLog logger = LoggerFactory.getLogger(getClass());

    private Context mContext;

    private TextView mTvTitle;
    private RecyclerView mRecyclerView;
    private TextView mTvGoMore;

    private List<HorizontalListContentViewBean> mData;

    private HorizontalListContentViewAdapter mAdapter;

    private boolean mIsShowGoMore = true;
    private boolean mIsShowRecyclerViewTitle = true;

    public HorizontalListContentView(Context context) {
        super(context);
        mContext = context;
    }

    public HorizontalListContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public HorizontalListContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_content_layout, this, true);
        mTvTitle = findViewById(R.id.tvTitle);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTvGoMore = findViewById(R.id.tvGoMore);
        mAdapter = new HorizontalListContentViewAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置显示的数据
     * @param list
     */
    public void setData(List<HorizontalListContentViewBean> list){
        mData = list;
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 隐藏recyclerView的底部标题
     */
    public void dismissRecyclerViewTitle(){
        mIsShowRecyclerViewTitle = false;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.heightOfHouseListViewItemNotTitle));
        int margin = (int) getResources().getDimension(R.dimen.sizeOfMiddleMargin);
        layoutParams.setMargins(margin, 0, margin, 0);
        mRecyclerView.setLayoutParams(layoutParams);
        mAdapter.setNotTitle();
    }

    public void setTitle(String title){

        mTvTitle.setText(title);
    }

    public void setTitle(int resId){

        mTvTitle.setText(resId);
    }

    /**
     * 设置Button可见
     * @param show
     */
    public void showGoMoreButton(boolean show){
        mIsShowGoMore = show;
        if (!show){
            mTvGoMore.setVisibility(GONE);
        }
    }

    /**
     * 设置按钮点击事件
     * @param clickListener
     */
    public void setGoMoreClickListener(OnClickListener clickListener){
        if (!mIsShowGoMore){
            logger.e("the button is not show!!!");
            return;
        }
        mTvGoMore.setOnClickListener(clickListener);
    }

    public void setRecyclerViewItemClickListener(final OnItemClickListener listener){
        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                listener.onItemClick(mData.get(pos), pos);
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(HorizontalListContentViewBean bean, int pos);
    }

}
