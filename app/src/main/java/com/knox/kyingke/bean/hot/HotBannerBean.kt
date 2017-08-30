package com.knox.kyingke.bean.hot

import com.knox.kyingke.bean.KTypeBean


/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/26  19:49
 * *
 * @desc ${TODD}
 */


class HotBannerBean : KTypeBean{
    override fun getType(): Int {
        return KTypeBean.TypeHotBanner
    }

    var ticker: MutableList<BannerItemBean> = mutableListOf()
}
