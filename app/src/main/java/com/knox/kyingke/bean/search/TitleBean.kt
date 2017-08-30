package com.knox.kyingke.bean.search

import com.knox.kyingke.bean.KTypeBean

/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/28  10:12
 * *
 * @desc ${TODD}
 */


class TitleBean(var title: String) : KTypeBean {

    override fun getType(): Int {
        return KTypeBean.TypeSearchTitle
    }
}
