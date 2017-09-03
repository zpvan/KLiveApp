package com.knox.kyingke.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/3  20:52
 *
 * @desc ${TODD}
 *
 */

class LiveRoomRelativeLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    
    var mHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mHeight == 0)
            mHeight = MeasureSpec.getSize(heightMeasureSpec)

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY))
    }
}