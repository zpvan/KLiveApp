package com.knox.kyingke.bean.search

import com.knox.kyingke.bean.KTypeBean

import java.util.ArrayList

/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/28  9:28
 * *
 * @desc ${TODD}
 */


class LiveNodeBean : KTypeBean {

    var title: String = ""
    var lives: MutableList<LiveBean> = mutableListOf()

    override fun getType(): Int {
        return KTypeBean.TypeSearchNode
    }
}
