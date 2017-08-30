package com.knox.kyingke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.knox.kyingke.R
import com.knox.kyingke.adapter.HotRvAdapter
import com.knox.kyingke.bean.KTypeBean
import com.knox.kyingke.bean.hot.HotBannerBean
import com.knox.kyingke.bean.hot.HotListBean
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
import com.knox.kyingke.utils.KSimpleUtil
import kotlinx.android.synthetic.main.frag_hot.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  15:10
 *
 * @desc ${TODD}
 *
 */

class HotFragment : Fragment() {

    companion object {
        val TAG: String = "HotFragment"
    }

    var conn = KRetrofic.getConnection(IRetrofitConnect::class.java)
    val hotRvAdapter = HotRvAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_hot, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRv()
        initListener()
        refreshData(false)
    }

    private fun initListener() {
        rv_hot.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_SETTLING -> KSimpleUtil.pauseFresco()
                    RecyclerView.SCROLL_STATE_IDLE -> KSimpleUtil.resumeFresco()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
            }
        })

        rv_hot.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                Log.e(TAG, "onLoadMore: ")
            }

            override fun onRefresh() {
                refreshData(true)
            }

        })
        /*去掉下拉更多的功能, 因为数据都是一次全部请求完回来的*/
        rv_hot.setLoadingMoreEnabled(false)
    }

    private fun initRv() {
        rv_hot.layoutManager = LinearLayoutManager(context)
        rv_hot.adapter = hotRvAdapter
    }

    private fun refreshData(needNotify: Boolean) {
        loadHotList(needNotify)
        loadBanner()
    }

    private fun loadBanner() {
        val call = conn.getBanner()
        call.enqueue(object : Callback<HotBannerBean> {
            override fun onFailure(call: Call<HotBannerBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getBanner失败")
            }

            override fun onResponse(call: Call<HotBannerBean>?, response: Response<HotBannerBean>?) {
                val body = response?.body()
                hotRvAdapter.loadBannerData(body)
            }
        })
    }

    private fun loadHotList(needNotify: Boolean) {
        val call = conn.getHotList()
        call.enqueue(object : Callback<HotListBean> {
            override fun onFailure(call: Call<HotListBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getHotList失败")
                if (needNotify)
                    rv_hot.refreshComplete()
            }

            override fun onResponse(call: Call<HotListBean>?, response: Response<HotListBean>?) {
                val body = response?.body()
                hotRvAdapter.loadItemData(body?.lives)
                if (needNotify)
                    rv_hot.refreshComplete()
            }
        })
    }
}