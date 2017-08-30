package com.knox.kyingke.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.knox.kyingke.R
import com.knox.kyingke.adapter.SearchResultAdapter
import com.knox.kyingke.adapter.SearchRvAdapter
import com.knox.kyingke.bean.search.SearchListBean
import com.knox.kyingke.bean.search.SearchResultBean
import com.knox.kyingke.http.IRetrofitConnect
import com.knox.kyingke.http.KRetrofic
import com.knox.kyingke.utils.KInKeUrlUtil
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_clean -> {
                search.getText().clear()
                search.clearFocus()
            }
            R.id.tv_cancel -> finish()
        }
    }

    companion object {
        val TAG: String = "SearchActivity"
    }

    var conn = KRetrofic.getConnection(IRetrofitConnect::class.java)
    val searchRvAdapter = SearchRvAdapter()
    val searchResultAdapter = SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initAdapter()
        initListener()
        refreshData()
    }

    private fun initListener() {
        search.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    rv_search_all.visibility = View.GONE
                    rv_search_res.visibility = View.VISIBLE
                    iv_clean.visibility = View.VISIBLE
                } else {
                    iv_clean.visibility = View.GONE
                    rv_search_res.visibility = View.GONE
                    rv_search_all.visibility = View.VISIBLE
                }
            }
        })

        search.addTextChangedListener(object : TextWatcher {

            private var tmp: CharSequence? = null
            private var selectionStart: Int = 0
            private var selectionEnd: Int = 0

            override fun afterTextChanged(s: Editable?) {
                if (s == null)
                    return

                selectionStart = search.selectionStart
                selectionEnd = search.selectionEnd
                if (tmp!!.length > 30) {
                    Toast.makeText(this@SearchActivity, "只能输入30个字",
                            Toast.LENGTH_SHORT).show()
                    s.delete(selectionStart - 1, selectionEnd)
                    val tempSelection = selectionEnd
                    search.setText(s)
                    search.setSelection(tempSelection)
                }

                /*请求搜索数据*/
                requestResultData(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                tmp = s
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        iv_clean.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }

    private fun requestResultData(keyWord: String) {
        val call = conn.getSearchResult(KInKeUrlUtil.getSearchKeyword(keyWord))
        call.enqueue(object : Callback<SearchResultBean> {
            override fun onFailure(call: Call<SearchResultBean>?, t: Throwable?) {
                Log.e(TAG, "onFailure: 请求getSearchResult失败")
            }

            override fun onResponse(call: Call<SearchResultBean>?, response: Response<SearchResultBean>?) {
                val body = response!!.body()
                searchResultAdapter.setResultDatas(body?.users)
            }
        })
    }

    private fun initAdapter() {
        rv_search_all.layoutManager = LinearLayoutManager(this)
        rv_search_all.adapter = searchRvAdapter

        rv_search_res.layoutManager = LinearLayoutManager(this)
        rv_search_res.adapter = searchResultAdapter
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
