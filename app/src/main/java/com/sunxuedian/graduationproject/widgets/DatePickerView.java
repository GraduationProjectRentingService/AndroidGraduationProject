package com.sunxuedian.graduationproject.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunxuedian.graduationproject.R;

/**
 * 选择日期的控件
 * Created by sunxuedian on 2018/3/30.
 */

public class DatePickerView extends RelativeLayout{

    private Context mContext;

    private ImageView mIvGoLeft;//切换到下一个月
    private ImageView mIvGoRight;//切换到前一个月
    private TextView mTvCurrentMonth;//当前月
    private ViewPager mVpDateCalendar;//显示日历的viewpager

    public DatePickerView(Context context) {
        super(context);
        mContext = context;
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.date_picker_layout, this, true);
        mIvGoLeft = findViewById(R.id.ivGoLeft);
        mIvGoRight = findViewById(R.id.ivGoRight);
        mTvCurrentMonth = findViewById(R.id.tvCurrentMonth);
        mVpDateCalendar = findViewById(R.id.vpDateMain);
    }



}
