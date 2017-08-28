package com.knox.kyingke

import android.app.Application
import android.content.Context

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  8:31
 *
 * @desc ${TODD}
 *
 */

class KInKeApplication : Application() {

    companion object {
        var mContext: Context ?= null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}