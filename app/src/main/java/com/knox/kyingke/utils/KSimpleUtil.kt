package com.knox.kyingke.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import com.facebook.drawee.backends.pipeline.Fresco
import com.knox.kyingke.KInKeApplication
import com.knox.kyingke.activity.SearchActivity

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
    fun kGetApplicationContext(): Context {
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
    fun Kdp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                kGetApplicationContext().resources.displayMetrics).toInt()
    }

    /*Fresco暂停加载图片*/
    fun KpauseFresco() {
        if (!Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().pause();
        }
    }

    /*Fresco回复加载图片*/
    fun KresumeFresco() {
        if (Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().resume();
        }
    }

    /*启动一个activity*/
    fun KstartActivity(clazz: Class<out Activity>) {
        kGetApplicationContext().startActivity(Intent(kGetApplicationContext(), clazz))
    }
}