package com.knox.kyingke.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.knox.kyingke.R
import com.knox.kyingke.adapter.SearchRvAdapter
import com.knox.kyingke.bean.search.SearchListBean
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    
    companion object {
        val TAG:String = "SearchActivity"
    }

    var conn = KRetrofic.getConnection(IRetrofitConnect::class.java)
    val searchRvAdapter = SearchRvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initAdapter()
        refreshData()
    }

    private fun initAdapter() {
        rv_search_all.layoutManager = LinearLayoutManager(this)
        rv_search_all.adapter = searchRvAdapter
    }

    private fun refreshData() {
        val call = conn.getSearchAll()
        call.enqueue(object : Callback<SearchListBean> {
            override fun onFailure(call: Call<SearchListBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getSearchAll失败")
            }

            override fun onResponse(call: Call<SearchListBean>?, response: Response<SearchListBean>?) {
                /*分离出SearchRvAdapter需要的
                * ArrayList<LiveNodeBean> 和 ArrayList<UserBean>*/
                val searchListBean = response!!.body()
                val lives = searchListBean!!.live_nodes
                searchRvAdapter.setTypeDatas(lives)

                val users = searchListBean.user_nodes.get(0).users
                searchRvAdapter.setRecommendDatas(users)
            }

        })
    }
}
