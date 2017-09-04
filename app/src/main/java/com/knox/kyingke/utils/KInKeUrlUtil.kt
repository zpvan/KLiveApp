package com.knox.kyingke.utils

import android.util.Log

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  17:54
 *
 * @desc ${TODD}
 *
 */

object KInKeUrlUtil {
    val BASE_URL_IP = "http://218.11.0.112/"
    //热门页面的所有数据
    const val INDEX_LIVE_ALL_DATE = "api/live/simpleall?uid=260095067"
    //热门页面轮播图
    const val INDEX_BANNER = "api/live/ticker"
    //搜索所有
    const val SEARCH_ALL = "api/recommend/aggregate"
    //关键词主播房间搜索
    const val SEARCH_KEYWORD = "api/user/search?mtxid=FCAA14EFE150&devi=864394102521707&uid=278794547&sid=20apIKRuUve4iOLo7oSi2i0Coi2ZKfLDhW6OzONeysOV8i2cQFaIWk&conn=WIFI&imei=864394102521707&mtid=0acd9551fb5aa92442069df37393691c&aid=FCAA14EFE1500000&cv=IK3.4.20_Android&smid=DuNRd%2FUxsbeK1L9tQw0TNRvGvuiTGcC08%2FFDr9KEdjdfkXbds8BheQpVHHOYmMkTErodalrJMOW1OTVu4Hamaemw&osversion=android_19&proto=4&logid=&vv=1.0.3-201610291749.android&icc=89860081012521701423&ua=LENOVOLenovoS898t%2B&lc=3000000000034000&imsi=460072521701423&cc=GF10000&count=10&start=0&keyword=_@keyword@_&r_c=1215378052&s_sg=5372685bd13f113826e5f18004f2daf5&s_sc=100&s_st=1478678705"
    //获得房间内的所有观众头像等信息
    const val GET_ROOM_VIEWERS = "api/live/users"
    //礼物商店
    const val GIFT_ALL = "api/resource/gift_info?type=2"
    //websocket服务器地址
    const val WSURL = "ws://47.93.30.78:8080/WebSocketYK/websocket"

    fun getScaledImgUrl(imgUrl: String, width: Int, height: Int): String {
        var imgUrl = imgUrl
        // 图片地址抓包数据示例
        // http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img2.inke.cn/MTQ5MjU2MzgwNzQxNiMxMzcjanBn.jpg&w=100&h=100&s=80&c=0&o=0
        val scaleUrl = "http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=%1s&w=%2s&h=%3s&s=80"
        imgUrl = getEscapeImgUrl(imgUrl)
        val format = String.format(scaleUrl, imgUrl, width.toString(), height.toString())
        return format
    }

    //对Url中的特殊符号做转义，规避加载图片时因为特殊符号不识别而失败
    //    /	    分隔目录和子目录	        %2F（忽略）
    //    ? 	分隔实际的URL和参数	        %3F（忽略）
    //    % 	指定特殊字符	            %25（忽略）
    //    +     URL中+号表示空格	        %2B
    //    # 	表示书签	                %23
    //    & 	URL中指定的参数间的分隔符	%26
    //    = 	URL中指定参数的值	        %3D
    //    * 	URL中指定参数的值	        %2A
    fun getEscapeImgUrl(imgUrl: String): String {
        var imgUrl = imgUrl
        if (!imgUrl.contains("http:")) {
            imgUrl = "http://img2.inke.cn/" + imgUrl
        }
        imgUrl = imgUrl.replace("\\+".toRegex(), "%2B")
                .replace("\\=".toRegex(), "%3D")
                .replace("\\=".toRegex(), "%2A")
                .replace("\\#".toRegex(), "%23")
        return imgUrl
    }

    fun getSearchKeyword(parms: String): String {
        return SEARCH_KEYWORD.replace("_@keyword@_".toRegex(), parms)
    }
}