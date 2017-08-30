package com.knox.kyingke.bean

import com.knox.kyingke.bean.hot.HotLabelBean
import java.io.Serializable

/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/26  18:52
 * *
 * @desc ${TODD}
 */


class ExtraBean : Serializable {
    var label: MutableList<HotLabelBean> = mutableListOf()
}
