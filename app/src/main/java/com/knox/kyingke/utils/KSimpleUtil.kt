package com.knox.kyingke.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
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

    /*展示输入法软键盘*/
    fun KshowInputKeyboard(activity: Activity, currentFocusedView: View) {
        if (activity == null) {
            return
        }
        val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentFocusedView, InputMethodManager.SHOW_FORCED)
    }

    /*隐藏输入法软键盘*/
    fun KcloseInputKeyboard(activity: Activity) {
        if (activity == null) {
            return
        }
        val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /*监听键盘的弹出收回*/
    interface KeyboardListener {
        fun onKeyboardShow(distance: Int)
        fun onKeyboardHide(distance: Int)
    }

    fun setOnKeyboardListener(activity: Activity, listener: KeyboardListener) {
        val decorView = activity.window.decorView
        val viewTreeObserver = decorView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            var height = 0
            override fun onGlobalLayout() {
                val rect = Rect()
                decorView.getWindowVisibleDisplayFrame(rect)
                if (height == 0) {
                    height = rect.height()
                    return
                }

                if (rect.height() < height) {
                    /*表示键盘弹出来了*/
                    if (listener != null)
                        listener.onKeyboardShow(height - rect.height())
                } else if (rect.height() > height) {
                    /*表示键盘缩回去了*/
                    if (listener != null)
                        listener.onKeyboardHide(rect.height() - height)
                }
                /*当前的高度需要做改变*/
                height = rect.height()
            }
        })
    }
}