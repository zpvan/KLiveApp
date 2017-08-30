package com.knox.kyingke.http

import com.knox.kyingke.bean.hot.HotBannerBean
import com.knox.kyingke.bean.hot.HotListBean
import com.knox.kyingke.utils.KInKeUrlUtil
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  21:36
 *
 * @desc ${TODD}
 *
 */

interface IRetrofitConnect {
    @GET(KInKeUrlUtil.INDEX_LIVE_ALL_DATE)
    fun getHotList() : Call<HotListBean>

    @GET(KInKeUrlUtil.INDEX_BANNER)
    fun getBanner() : Call<HotBannerBean>
}