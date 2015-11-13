package com.simplepageindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by renan on 12/11/15.
 */
public class PageViewIndicator extends LinearLayout {

    private int currentPage = 0;
    private int pageQuantity = 1;
    private int primaryColor = 0;
    private int secondaryColor = 0;
    private int indicadorWidth = 0;
    private int indicadorHeight = 0;
    private Context mContext;

    public PageViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PageViewIndicator, 0, 0);
        try {
            //get attrs
            this.primaryColor = a.getInteger(R.styleable.PageViewIndicator_indicatorPrimaryColor, 0);
            this.secondaryColor = a.getInteger(R.styleable.PageViewIndicator_indicatorSecondaryColor, 0);
            this.indicadorWidth = a.getDimensionPixelSize(R.styleable.PageViewIndicator_indicatorWidth, 5);
            this.indicadorHeight = a.getDimensionPixelSize(R.styleable.PageViewIndicator_indicatorHeight, 5);
            this.pageQuantity = a.getInteger(R.styleable.PageViewIndicator_pageQuantity, 1);
            layoutWidthAdjust();
        } finally {
            a.recycle();
        }
        setPageQuantity(pageQuantity);
    }

    private void layoutWidthAdjust() {
        int width =  mContext.getResources().getDisplayMetrics().widthPixels;
        if((indicadorWidth * pageQuantity) >= width){
            this.indicadorWidth = width / pageQuantity ;
            this.indicadorHeight = width / pageQuantity ;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void nextPage(){
        clearPageIndicator(currentPage);
        if(currentPage < pageQuantity -1)
            currentPage++;
        else currentPage = 0;
        setCurrentPage(currentPage);
    }

    public void previousPage() {
        clearPageIndicator(currentPage);
        if(currentPage > 0)
            currentPage--;
        else currentPage = pageQuantity -1;
        setCurrentPage(currentPage);
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage >= pageQuantity || currentPage < 0 ) return;
        ViewGroup mViewGroup = this;
        this.currentPage = currentPage;
        Indicator  indicator = (Indicator) mViewGroup.getChildAt(currentPage);
        indicator.setCircleColor(primaryColor);
    }

    public void clearPageIndicator(int currentPage){
        ViewGroup mViewGroup = this;
        Indicator indicator = (Indicator) mViewGroup.getChildAt(this.currentPage);
        indicator.setCircleColor(secondaryColor);
    }

    public void setPageQuantity(int quantity) {
        ViewGroup mViewGroup = this;
        this.pageQuantity = quantity;
        for (int i = 0; i < this.pageQuantity; i++) {
            Log.wtf("PageViewIndicator", "addView !!!");
            Indicator indicator = new Indicator(getContext());
            indicator.setLayoutParams(new LayoutParams(indicadorWidth, indicadorHeight));
            if (currentPage == i) {
                indicator.setCircleColor(primaryColor);
            } else {
                indicator.setCircleColor(secondaryColor);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mViewGroup.addView(indicator, i);
        }
    }

}
