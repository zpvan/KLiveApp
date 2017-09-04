package com.knox.kyingke.bean.websocket

import com.knox.kyingke.bean.liveroom.GiftBean

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/4  10:06
 *
 * @desc ${TODD}
 *
 */

class WebSocketUserBean {

    companion object {
        val TYPE_LOGIN = 1; //上线
        val TYPE_SEND_MSG = 2; //消息
        val TYPE_SEND_GIFT = 3; //礼物
    }

    var type: Int = 0; //1.代表返回的是首次登录 2.代表是聊天信息 3.代表的是礼物
    var group: String = ""; //房间号
    var userId: String = ""; //用户id, U123456
    var msg: String = ""
    var gift: GiftBean = GiftBean()

    constructor(type: Int): this() {
        this.type = type
    }

    constructor()
}