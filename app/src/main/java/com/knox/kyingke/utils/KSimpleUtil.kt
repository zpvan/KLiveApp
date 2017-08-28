package com.knox.kyingke.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import com.knox.kyingke.KInKeApplication

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  8:30
 *
 * @desc ${TODD}
 *
 */

object KSimpleUtil {
    /*获取Context*/
    fun kGetApplicationContext() : Context {
        return KInKeApplication.mContext!!
    }

    /*从strings资源中取出里边的字符串数组*/
    fun KGetStringArrayFromRes(resId: Int): Array<String> {
        return kGetApplicationContext().getResources().getStringArray(resId)
    }

    /*在主线程中运行runnable*/
    fun KRunOnUIThread(runnable: Runnable) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(runnable)
    }

    /*dp转px*/
    fun Kdp2px(dpVal : Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                kGetApplicationContext().resources.displayMetrics).toInt()
    }
}