package com.knox.kyingke.adapter.liveroom

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/2  21:56
 *
 * @desc ${TODD}
 *
 */

class GiftShopVpAdapter : PagerAdapter() {

    var mList: MutableList<RecyclerView> = mutableListOf()

    fun setDatas(list: MutableList<RecyclerView>) {
        mList.clear()
        if (list != null)
            mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(position: Int): RecyclerView? {
        if (position in 0..(mList.size-1))
            return mList[position]
        else
            return null
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val recyclerView = mList[position]
        container?.addView(recyclerView)
        return recyclerView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View?)
    }
}