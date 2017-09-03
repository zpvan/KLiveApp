package com.knox.kyingke.event

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/3  17:23
 *
 * @desc ${TODD}
 *
 */

class WidgetEvent(var msg: Int) {
    companion object {
        val EVENT_HIDE = 1
        val EVENT_SHOW = 2
    }
}