package com.knox.kyingke.bean.search

import com.knox.kyingke.bean.CreatorBean
import com.knox.kyingke.bean.ExtraBean


/**
 * @author Knox.Tsang
 * *
 * @time 2017/8/28  9:28
 * *
 * @desc ${TODD}
 */


class LiveBean {
    var id: Long = 0
    var name: String = ""
    var online_users: Int = 0
    var room_id: Long = 0
    var stream_addr: String = ""
    var creator: CreatorBean = CreatorBean()
    var extra: ExtraBean = ExtraBean()


}
