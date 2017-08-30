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
    const val SEARCH_ALL = "api/recommend/aggregate";

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
    private fun getEscapeImgUrl(imgUrl: String): String {
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
}