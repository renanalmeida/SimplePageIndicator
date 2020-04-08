package com.github.simplepageindicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.simplepageindicator.R

class Indicator : View {
    private var indicatorColor = 0
    private var circlePaint = Paint()

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val styledAttributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Indicator, 0, 0
        )
        indicatorColor = try {
            styledAttributes.getInteger(R.styleable.Indicator_indicatorColor, 0)
        } finally {
            styledAttributes.recycle()
        }
    }

    constructor(context: Context?) : super(context) {
        indicatorColor = Color.BLACK
        circlePaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val viewWidthHalf = this.measuredWidth / 2
        val viewHeightHalf = this.measuredHeight / 2
        circlePaint.style = Paint.Style.FILL
        circlePaint.isAntiAlias = true
        circlePaint.color = indicatorColor
        canvas.drawCircle(
            viewWidthHalf.toFloat(),
            viewHeightHalf.toFloat(),
            viewWidthHalf / 2.toFloat(),
            circlePaint
        )
    }

    fun setCircleColor(newColor: Int) { //update the instance variable
        indicatorColor = newColor
        invalidate()
        requestLayout()
    }

}