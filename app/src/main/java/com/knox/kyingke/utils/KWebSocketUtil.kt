package com.knox.kyingke.utils

import android.util.Log
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/4  9:17
 *
 * @desc 好处:保持一个客户端跟一个服务器只有一个连接.
 * 坏处:因为我没有给出傻瓜式的封装, 调用者仍然需要关心okhttp, 因为他拿到的是WebSocket, 但我不完全认为这点是坏处,
 * 一是没有阉割WebSocket的任何方法, 若封装多一层, 则会. 二是这个Util的优势是保证一个客户端跟一个服务器只有一个连接, 并专注这一点.
 * 服务器对应的是url
 * 客户端对应的是WebSocket
 * 如果其他人call conn(url)给我一个url,
 * 判断有没有对应的WebSocket, 如果有, 判断是连接有没有断, 断了就重新连, 再更新对应的WebSocket, 并返回给调用者
 * 如果没有, 就重新连接, 并保存WebSocket且返回给调用者
 * 保存url跟WebSocket有对应关系是用hashMap来保存, 如何保证这个hashMap只有一份呢?
 * 1. 用static来声明 --> 缺点: 1. 如果没有使用到websocket, 内存也会存在static变量, 相对可能占用多一些内存和低效一些
 * 2. KWebSocketUtil是单例的, hashMap是成员变量
 * <p>
 * compile 'com.squareup.okhttp3:okhttp:3.8.1'
 * <p>
 * 使用说明:
 * 1.conn(给我Url, 给我handler的实例)
 * 2.handler实例拿到回调的WebSocket去做自己的事
 * 3.WebSocket.send
 * 4.WebSocket.close
 *
 */

class KWebSocketUtil private constructor() {

    /*单例模式*/
    companion object {
        val TAG: String = "KWebSocketUtil"
        val instance: KWebSocketUtil by lazy { KWebSocketUtil() }
    }

    private object Holder {
        val INSTANCE = KWebSocketUtil()
    }

    interface IKWebSocketHandler {
        fun onOpen(webSocket: WebSocket?)
        fun onMessage(text: String?)
        fun onFail(t: Throwable?, response: Response?)
    }

    val mMapUS: MutableMap<String, WebSocket> = mutableMapOf()
    val mMapUH: MutableMap<String, IKWebSocketHandler> = mutableMapOf()
    var mListener: KWebSocketListener? = null

    class KWebSocketListener(var util: KWebSocketUtil) : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket?, response: Response?) {
            super.onOpen(webSocket, response)
            util.mMapUS.put(webSocket?.request()?.url()?.url().toString(), webSocket!!)
            util.mMapUH.get(webSocket?.request()?.url()?.url().toString())?.onOpen(webSocket)
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            super.onMessage(webSocket, text)
            util.mMapUH.get(webSocket?.request()?.url()?.url().toString())?.onMessage(text)
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
            super.onFailure(webSocket, t, response)
            util.mMapUH.get(webSocket?.request()?.url()?.url().toString())?.onFail(t, response)
        }

        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
            super.onClosed(webSocket, code, reason)
            util.mMapUH.remove(webSocket?.request()?.url()?.url().toString())
        }
    }

    fun conn(wsUrl: String, handler: IKWebSocketHandler) {
        //同一个客户端, 由于handler不存在了, 重新给一个handler我, 又将已经连接了的wsUrl给我, 我认为他想换一个handler
        mMapUH.put(wsUrl.replace("ws://", "http://"), handler)
        //查询HashMap中的数据
        var webSocket: WebSocket? = mMapUS.get(wsUrl.replace("ws://", "http://"))
        if (webSocket == null) {
            val client: OkHttpClient = OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build()
            val request: Request = Request.Builder().url(wsUrl).build()
            if (mListener == null) {
                mListener = KWebSocketListener(instance)
            }
            client.newWebSocket(request, mListener)
            client.dispatcher().executorService().shutdown()
        } else {
            handler.onOpen(webSocket)
        }
    }
}