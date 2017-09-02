package com.knox.kyingke.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.knox.kyingke.R
import com.knox.kyingke.bean.hot.HotItemBean
import com.knox.kyingke.utils.KSimpleUtil

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  18:14
 *
 * @desc ${TODD}
 *
 */

class LiveRoomAdapter(var mList: MutableList<HotItemBean>?) : PagerAdapter() {

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val layout = RelativeLayout(KSimpleUtil.kGetApplicationContext())
        layout.setBackgroundResource(R.drawable.room_change_bg)
        container!!.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View?)
    }

    fun getItem(currentItem: Int) : HotItemBean? {
        if (mList != null)
            return mList!![currentItem]
        else
            return null
    }
}