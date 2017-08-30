package com.knox.kyingke.bean.search

import com.knox.kyingke.bean.KTypeBean

/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/28  9:34
 * *
 * @desc ${TODD}
 */


class UserBean : KTypeBean {

    var reason: String = ""
    var user: UserItemBean = UserItemBean()

    override fun getType(): Int {
        return KTypeBean.TypeSearchRecommend
    }
}
