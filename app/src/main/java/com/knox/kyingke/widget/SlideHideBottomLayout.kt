package com.knox.kyingke.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import java.util.jar.Attributes

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  8:35
 *
 * @desc 向上滑动, bottom导航栏缩下去, 向下滑动, bottom导航栏伸上来的自定义RelativeLayout
 *
 */

class SlideHideBottomLayout : RelativeLayout {

    companion object {
        val TAG: String = "SlideHideBottomLayout"
    }

    var mHeight = 0
    var mBottomWidget: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        /*测量bottom的height*/
        /*测试发现只要前面call super.onMeasure(), 不需要call measureChildren()也能拿到measuredHeight*/
//        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (mBottomWidget == null)
            mBottomWidget = getChildAt(1)
        if (mHeight == 0) {
            mHeight = mBottomWidget!!.measuredHeight
            Log.e(TAG, "onMeasure: mHeight " + mHeight)
        }

    }


    var mStartY: Float = 0f
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev != null && mBottomWidget != null) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    /*获取初始按下位置*/
                    mStartY = ev.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val moveY = ev.y
                    var dy = moveY - mStartY

                    /*控制mBottomWidget不超过边界*/
                    val scrollY = mBottomWidget!!.scrollY
                    if (scrollY + dy < -mHeight) {
                        dy = (-mHeight - scrollY).toFloat()
                    } else if (scrollY + dy > 0) {
                        dy = (0 - scrollY).toFloat()
                    }

                    mBottomWidget!!.scrollBy(0, (dy + 0.5f).toInt())

                    mStartY = moveY
                }

                MotionEvent.ACTION_UP -> {
                    /*放开手时, 如果mBottomWidget露出大于一半就弹出来, 如果露出小于一半就缩进去*/
                    //TODO 加动画效果, 慢慢弹出来, 慢慢缩进去
                    val scrollY = mBottomWidget!!.scrollY
                    if (scrollY > -mHeight / 2) {
                        mBottomWidget!!.scrollTo(0,0)
                    } else {
                        mBottomWidget!!.scrollTo(0, -mHeight)
                    }
                }
            }
        }

        /*不用修改分发逻辑, 只想获取滑动距离去移动bottm导航栏, 所以不动返回值*/
        return super.dispatchTouchEvent(ev)
    }

}