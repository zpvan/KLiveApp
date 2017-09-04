package com.knox.kyingke.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knox.kyingke.utils.KGsonUtil.gson

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/4  10:46
 *
 * @desc ${TODD}
 *
 */

object KGsonUtil {

    val gson: Gson = Gson()

    fun b2s(any: Any): String = gson.toJson(any)

    fun <T> s2b(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)

}