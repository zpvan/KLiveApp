package com.knox.kyingke.http

import com.knox.kyingke.bean.hot.HotBannerBean
import com.knox.kyingke.bean.hot.HotListBean
import com.knox.kyingke.bean.liveroom.GiftListBean
import com.knox.kyingke.bean.liveroom.UserListBean
import com.knox.kyingke.bean.search.SearchListBean
import com.knox.kyingke.bean.search.SearchResultBean
import com.knox.kyingke.utils.KInKeUrlUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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
    fun getHotList(): Call<HotListBean>

    @GET(KInKeUrlUtil.INDEX_BANNER)
    fun getBanner(): Call<HotBannerBean>

    @GET(KInKeUrlUtil.SEARCH_ALL)
    fun getSearchAll(): Call<SearchListBean>

    @GET
    fun getSearchResult(@Url url: String): Call<SearchResultBean>

    @GET(KInKeUrlUtil.GET_ROOM_VIEWERS)
    fun getRoomViewers(@Query("id") id: String): Call<UserListBean>

    @GET(KInKeUrlUtil.GIFT_ALL)
    fun getGiftAll(): Call<GiftListBean>
}