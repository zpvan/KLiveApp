package com.knox.kyingke.bean

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  11:09
 *
 * @desc ${TODD}
 *
 */

interface KTypeBean {

    companion object {
        val TypeHotBanner: Int = 0
        val TypeHotItem: Int = 1
        val TypeSearchNode: Int = 2
        val TypeSearchRecommend: Int = 3
        val TypeSearchTitle: Int = 4
    }

    fun getType(): Int
}