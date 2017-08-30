package com.knox.kyingke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.HotRvAdapter
import com.knox.kyingke.bean.KTypeBean
import com.knox.kyingke.bean.hot.HotBannerBean
import com.knox.kyingke.bean.hot.HotListBean
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
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
        initData()
    }

    private fun initRv() {
        rv_hot.layoutManager = LinearLayoutManager(context)
        rv_hot.adapter = hotRvAdapter
    }

    private fun initData() {
        loadHotList()
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

    private fun loadHotList() {
        val call = conn.getHotList()
        call.enqueue(object : Callback<HotListBean> {
            override fun onFailure(call: Call<HotListBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getHotList失败")
            }

            override fun onResponse(call: Call<HotListBean>?, response: Response<HotListBean>?) {
                val body = response?.body()
                hotRvAdapter.loadItemData(body?.lives)
            }
        })
    }
}