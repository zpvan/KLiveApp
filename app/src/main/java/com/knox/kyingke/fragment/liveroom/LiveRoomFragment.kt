package com.knox.kyingke.fragment.liveroom

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.knox.kyingke.KInKeApplication
import com.knox.kyingke.R
import com.knox.kyingke.R.id.*
import com.knox.kyingke.adapter.liveroom.ViewerRvAdapter
import com.knox.kyingke.bean.hot.HotItemBean
import com.knox.kyingke.bean.liveroom.UserListBean
import com.knox.kyingke.event.WidgetEvent
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
import com.knox.kyingke.utils.KInKeUrlUtil
import com.knox.kyingke.utils.KSimpleUtil
import com.knox.kyingke.utils.KWebSocketUtil
import kotlinx.android.synthetic.main.frag_live_room.*
import okhttp3.WebSocket
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/2  12:45
 *
 * @desc ${TODD}
 *
 */

class LiveRoomFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG: String = "LiveRoomFragment"
        val KEY_BUNDLEITEM = "LiveRoomFragment::KEY_BUNDLEITEM"
    }

    private var viewerRvAdapter: ViewerRvAdapter? = null
    val mService = KRetrofic.getConnection(IRetrofitConnect::class.java)
    var giftShopFragment: GiftShopFragment? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_live_room, container, false)
        /*加载礼物数据*/
        loadGiftShop()
        return view
    }

    private fun initViewerRv() {
        recyclerView_Viewer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewerRvAdapter = ViewerRvAdapter()
        recyclerView_Viewer.adapter = viewerRvAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*如果放在onCreateView里边, 会找不到recyclerView, 造成nullPointerException*/
        initViewerRv()
        initListener()

        val arguments = arguments
        val serializable = arguments.getSerializable(KEY_BUNDLEITEM)
        if (serializable == null)
            return

        val item: HotItemBean = serializable as HotItemBean
        fillLiveRoomInfo(item)
    }

    private fun loadGiftShop() {
        giftShopFragment = GiftShopFragment()
        childFragmentManager.beginTransaction().add(R.id.fl_gift_shop, giftShopFragment).commit()
    }

    private fun initListener() {
        iv_gift_shop.setOnClickListener(this)
        iv_send.setOnClickListener(this)
        tv_send_msg.setOnClickListener(this)

        KSimpleUtil.setOnKeyboardListener(activity, object : KSimpleUtil.KeyboardListener {
            override fun onKeyboardShow(distance: Int) {
                moveRelatedWidget(-distance)
            }

            override fun onKeyboardHide(distance: Int) {
                moveRelatedWidget(0)
            }

        })
    }

    private fun moveRelatedWidget(distance: Int) {
        rl_edit.visibility = if (distance == 0) View.GONE else View.VISIBLE
        rl_bottom.visibility = if (distance == 0) View.VISIBLE else View.GONE

        ll_left.translationY = distance.toFloat()
        recyclerView_Viewer.translationY = distance.toFloat()
        card.translationY = distance.toFloat()
        recyclerView_chat.translationY = distance.toFloat()
        rl_edit.translationY = distance.toFloat()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_gift_shop -> giftShopFragment?.switchContent()
            R.id.iv_send -> {
                /*必须给焦点rl_edit, 键盘才会弹出来*/
                edt.requestFocus()
                KSimpleUtil.KshowInputKeyboard(activity, edt)
            }
            R.id.tv_send_msg -> {
                /*发送文字到WebSocket Server*/
            }
        }
    }

    fun fillLiveRoomInfo(item: HotItemBean) {
        /*主播头像*/
        iv_anchor_icon.setImageURI(KInKeUrlUtil.getScaledImgUrl(item.creator.portrait, 30, 30))
        /*观众人数*/
        tv_online_number.setText(item.online_users.toString())
        /*房间号*/
        tv_gold_number.setText(item.room_id.toString())
        /*主播号*/
        tv_anchor_number.setText("主播号: " + item.creator.id)
        /*观众头像*/
        fillViewerInfo(item.id)
        /*登陆直播间*/
        loginLiveRoom()
    }

    private fun loginLiveRoom() {
        KWebSocketUtil.instance.conn(KInKeUrlUtil.WSURL, object : KWebSocketUtil.IKWebSocketHandler{
            override fun onOpen(webSocket: WebSocket?) {
                Log.e(TAG, "onOpen: 连接成功" + " userId " + KInKeApplication.userId)
            }

            override fun onMessage(text: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFail(t: Throwable?, response: okhttp3.Response?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun fillViewerInfo(id: String) {
        val call = mService.getRoomViewers(id)
        call.enqueue(object : Callback<UserListBean> {
            override fun onFailure(call: Call<UserListBean>?, t: Throwable?) {
                Log.e(TAG, "onResponse: 请求getRoomViewers失败")
            }

            override fun onResponse(call: Call<UserListBean>?, response: Response<UserListBean>?) {
                val body = response?.body()
                viewerRvAdapter?.setDatas(body?.users)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: WidgetEvent) {
        when (event.msg) {
            WidgetEvent.EVENT_HIDE -> rl_bottom.visibility = View.GONE
            WidgetEvent.EVENT_SHOW -> rl_bottom.visibility = View.VISIBLE
        }
    }
}