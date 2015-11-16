package com.simplepageindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by renan on 12/11/15.
 */
public class Indicator extends View {
    //circle and text colors
    private int indicatorCol = 0;
    //paint for drawing custom view
    private Paint circlePaint;

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //paint object for drawing in onDraw
        circlePaint = new Paint();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Indicator, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            indicatorCol = a.getInteger(R.styleable.Indicator_indicatorColor, 0);//0 is default
        } finally {
            a.recycle();
        }
    }

    public Indicator(Context context) {
        super(context);
        //paint object for drawing in onDraw
        indicatorCol = Color.BLACK;
        circlePaint = new Paint();

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;
        Log.wtf("viewWidthHalf:", String.valueOf(this.getMeasuredWidth() / 2));
        Log.wtf("viewWidthHalf:", String.valueOf(this.getMeasuredHeight() / 2));


        Log.wtf("viewWidthHalf:", String.valueOf(this.getMeasuredHeight() / 2));

        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        //set the paint color using the circle color specified
        circlePaint.setColor(indicatorCol);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, viewWidthHalf/2, circlePaint);
    }

    public int getIndicatorCol() {
        return indicatorCol;
    }

    public void setIndicatorCol(int indicatorCol) {
        this.indicatorCol = indicatorCol;
    }

    public void setCircleColor(int newColor){
        //update the instance variable
        this.indicatorCol = newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        this.indicatorCol = newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
}
