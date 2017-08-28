package com.knox.kyingke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.HotRvAdapter
import kotlinx.android.synthetic.main.frag_hot.*

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

    val hotRvAdapter = HotRvAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_hot, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRv()
        /*给一些假数据RvAdapter, 验证效果*/
        initData()
    }

    private fun initRv() {
        rv_hot.layoutManager = LinearLayoutManager(context)
        rv_hot.adapter = hotRvAdapter
    }

    private fun initData() {

        val list = mutableListOf<String>()
        var index = 0
        while (index < 60) {
            list.add(index.toString())
            index++
        }
        hotRvAdapter.setDatas(list)
    }
}