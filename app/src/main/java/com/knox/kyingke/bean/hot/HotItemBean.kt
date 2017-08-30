package com.knox.kyingke.bean.hot

import com.knox.kyingke.bean.CreatorBean
import com.knox.kyingke.bean.ExtraBean
import com.knox.kyingke.bean.KTypeBean


/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/26  18:47
 * *
 * @desc ${TODD}
 */


class HotItemBean : KTypeBean{
    var act_info: HotActInfoBean = HotActInfoBean()
    var creator: CreatorBean = CreatorBean()
    var extra: ExtraBean = ExtraBean()
    var id: String = ""
    var name: String = ""
    var online_users: Int = 0
    var room_id: Long = 0
    var stream_addr: String = ""

    override fun getType(): Int {
        return KTypeBean.TypeHotItem
    }
}
