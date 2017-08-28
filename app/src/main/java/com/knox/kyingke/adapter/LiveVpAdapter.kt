package com.knox.kyingke.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  16:12
 *
 * @desc ${TODD}
 *
 */

class LiveVpAdapter(fm: FragmentManager, val fragList: MutableList<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragList.get(position)
    }

    override fun getCount(): Int {
        /*三种解决方案, 有什么区别
        * 1. 返回值 Int?
        * 2. !!
        * 3. as Int*/
        return fragList.size
    }
}