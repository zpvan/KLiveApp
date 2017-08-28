package com.knox.kyingke.http

import com.knox.kyingke.utils.KInKeUrlUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  21:55
 *
 * @desc ${TODD}
 *
 */

object KRetrofic {

    private var sRetrofit: Retrofit? = null

    fun <T> getConnection(clazz: Class<T>): T {
        if (sRetrofit == null) {
            synchronized(KRetrofic::class.java) {
                if (sRetrofit == null) {
                    sRetrofit = Retrofit.Builder()
                            .baseUrl(KInKeUrlUtil.BASE_URL_IP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        val t = sRetrofit!!.create(clazz)
        return t
    }

}