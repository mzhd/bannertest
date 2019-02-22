package com.example.administrator.bannertest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class BannerViewPager extends ViewPager {
    public BannerViewPager(@NonNull Context context) {
        super(context);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int height=0;
        for(int i=0;i<childCount;i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
            int measuredHeight = childView.getMeasuredHeight();
            if(height<measuredHeight){
                height=measuredHeight;
            }
        }
       // System.out.println("高度："+height);

        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
       // super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
