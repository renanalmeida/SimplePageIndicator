package com.github.simplepageindicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.simplepageindicator.R

class PageViewIndicator(
    private val mContext: Context,
    attrs: AttributeSet?
) : LinearLayout(mContext, attrs) {
    private var currentPage = 0
    private var pageNumber = 1
    private var primaryColor = 0
    private var secondaryColor = 0
    private var indicatorWidth = 0
    private var indicatorHeight = 0
    private var overflowMode = false

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL
        val typedArray = mContext.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PageViewIndicator, 0, 0
        )
        primaryColor = typedArray.getInteger(R.styleable.PageViewIndicator_indicatorPrimaryColor, 0)
        secondaryColor = typedArray.getInteger(R.styleable.PageViewIndicator_indicatorSecondaryColor, 0)
        indicatorWidth = typedArray.getDimensionPixelSize(R.styleable.PageViewIndicator_indicatorWidth, 5)
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.PageViewIndicator_indicatorHeight, 5)
        pageNumber = typedArray.getInteger(R.styleable.PageViewIndicator_pageNumber, 1)
        overflowMode = typedArray.getBoolean(R.styleable.PageViewIndicator_overflowMode, false)

        typedArray.recycle()
        layoutWidthAdjust()
        setPageNumber(pageNumber)
    }

    private fun layoutWidthAdjust() {
        val width = mContext.resources.displayMetrics.widthPixels
        if (indicatorWidth * pageNumber >= width) {
            indicatorWidth = width / pageNumber
            indicatorHeight = width / pageNumber
        }
    }

    fun setPageNumber(number: Int) {
        removeAllViews()
        pageNumber = number
        layoutWidthAdjust()
        for (i in 0 until pageNumber) {
            val indicator = Indicator(context).apply {
                layoutParams = LayoutParams(indicatorWidth, indicatorHeight)
                setCircleColor(secondaryColor)
            }
            addView(indicator, i)
        }
        setCurrentPage(getCurrentPage())
    }

    fun setCurrentPage(pageIndex: Int) {
        clearPageIndicator()
        if (currentPage >= pageNumber || currentPage < 0) throw  Exception("Invalid page number")
        currentPage = pageIndex
        val indicator = getChildAt(currentPage) as Indicator
        indicator.setCircleColor(primaryColor)
    }

    private fun clearPageIndicator() {
        val indicator = getChildAt(currentPage) as Indicator
        indicator.setCircleColor(secondaryColor)
    }

    fun nextPage() {
        clearPageIndicator()
        if (currentPage < pageNumber - 1) {
            currentPage++
        } else if (overflowMode) {
            currentPage = 0
        }
        setCurrentPage(currentPage)
    }

    fun previousPage() {
        clearPageIndicator()
        if (currentPage > 0) {
            currentPage--
        } else if (overflowMode) {
            currentPage = pageNumber - 1
        }
        setCurrentPage(currentPage)
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

}