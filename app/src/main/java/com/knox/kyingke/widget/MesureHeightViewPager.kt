package com.knox.kyingke.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/3  11:39
 *
 * @desc ${TODD}
 *
 */

class MesureHeightViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {

    companion object {
        val TAG:String = "MesureHeightViewPager"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var childHeightMeasureSpec = heightMeasureSpec
        /* ViewPager的子控件是指里边可以滑动的东西, 譬如目前: 里边是5个RecyclerView
        *  如果展示的是第一个, childCount=2, 说明里边有0, 1两个
        *  如果展示的是第二个, childCount=3, 说明里边有0, 1, 2三个
        *  如果展示的是第五个, childCount=2, 说明里边有3, 4两个*/
        if (childCount > 0) {
            /*因为每个子控件的高度是一样的, 所以我只拿第一个就好*/
            measureChildren(widthMeasureSpec, childHeightMeasureSpec)
            val child = getChildAt(0)
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, childHeightMeasureSpec)
    }

}