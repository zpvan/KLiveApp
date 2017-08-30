package com.knox.kyingke

import android.app.Application
import android.content.Context
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig

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
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this

        val diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setMaxCacheSize((50 * 1024 * 1024).toLong())
                .setBaseDirectoryPath(externalCacheDir) //如果是SD卡路径，要注意权限
                .build()

        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build()

        Fresco.initialize(this, config)
    }
}