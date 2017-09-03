package com.knox.kyingke.fragment.liveroom

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.knox.kyingke.R
import com.knox.kyingke.adapter.liveroom.GiftRvAdapter
import com.knox.kyingke.adapter.liveroom.GiftShopVpAdapter
import com.knox.kyingke.bean.liveroom.GiftBean
import com.knox.kyingke.bean.liveroom.GiftListBean
import com.knox.kyingke.event.WidgetEvent
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
import com.knox.kyingke.utils.KSimpleUtil
import kotlinx.android.synthetic.main.frag_gift_shop.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/2  21:14
 *
 * @desc ${TODD}
 *
 */

class GiftShopFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG: String = "GiftShopFragment"
    }

    var mService = KRetrofic.getConnection(IRetrofitConnect::class.java)
    var giftShopVpAdapter: GiftShopVpAdapter? = null
    val itemPerPage = 8

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_gift_shop, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initGiftShopVp()
        requstGifts()
    }

    private fun initListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                selectIndicator(position)
            }
        })

        back.setOnClickListener(this)
    }

    private fun selectIndicator(position: Int) {
        val count = ll_dots.childCount
        var index = 0
        while (index < count) {
            if (index == position) {
                /*选中的*/
                ll_dots.getChildAt(index).setBackgroundResource(R.drawable.white_dot)
            } else {
                /*非选中的*/
                ll_dots.getChildAt(index).setBackgroundResource(R.drawable.gray_dot)
            }
            index++
        }
    }

    private fun initGiftShopVp() {
        giftShopVpAdapter = GiftShopVpAdapter()
        viewPager.adapter = giftShopVpAdapter
    }

    private fun initIndicator(count: Int) {
        var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, KSimpleUtil.Kdp2px(5f), 0)
        var index = 0
        while (index < count) {
            val dot = ImageView(context)
            dot.setBackgroundResource(R.drawable.gray_dot)
            ll_dots.addView(dot, params)
            index++
        }
    }

    private fun requstGifts() {
        val call = mService.getGiftAll()
        call.enqueue(object : Callback<GiftListBean> {
            override fun onFailure(call: Call<GiftListBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getGiftAll失败")
            }

            override fun onResponse(call: Call<GiftListBean>?, response: Response<GiftListBean>?) {
                val body = response?.body()
                handleGiftsBean(body?.gifts)
            }

        })
    }

    private fun handleGiftsBean(giftBeans: MutableList<GiftBean>?) {
        /*计算gifts需要多少页才能展示完*/
        var pageNum = (giftBeans?.size!! + itemPerPage - 1) / itemPerPage

        /*制作指示器, 需要知道有多少页才知道制作多少个小点点*/
        initIndicator(pageNum)

        /*制作recyclerView集合*/
        val giftRvs: MutableList<RecyclerView> = mutableListOf()
        var rIndex = 0
        while (rIndex < pageNum) {
            val giftRv = RecyclerView(context)
            giftRv.layoutManager = GridLayoutManager(context, itemPerPage / 2)
            val giftRvAdapter = GiftRvAdapter()
            giftRv.adapter = giftRvAdapter
            val gifts: MutableList<GiftBean> = mutableListOf()
            var gIndex = 0
            while (gIndex < itemPerPage) {
                /*计算真正的index*/
                val index = rIndex * itemPerPage + gIndex
                var gift = GiftBean()
                if (index < giftBeans.size) {
                    gift = giftBeans[index]
                }
                gifts.add(gift)
                gIndex++
            }
            giftRvAdapter.setDatas(gifts)
            giftRvs.add(giftRv)
            rIndex++
        }
        giftShopVpAdapter?.setDatas(giftRvs)

        /*指示器默认选中当前选择的item*/
        selectIndicator(viewPager.currentItem)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                /*如果礼物栏可见就隐藏*/
                if (ll_content.visibility == View.VISIBLE)
                    switchContent()
            }
        }
    }

    fun switchContent() {
        ll_content.visibility = if (ll_content.visibility == View.GONE) View.VISIBLE else View.GONE
        EventBus.getDefault().post(WidgetEvent(if (ll_content.visibility == View.VISIBLE) WidgetEvent.EVENT_HIDE else WidgetEvent.EVENT_SHOW))
    }
}