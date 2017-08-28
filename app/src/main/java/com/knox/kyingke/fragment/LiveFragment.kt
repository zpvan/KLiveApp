package com.knox.kyingke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.LiveVpAdapter
import com.knox.kyingke.utils.KSimpleUtil
import kotlinx.android.synthetic.main.frag_live.*

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  15:06
 *
 * @desc ${TODD}
 *
 */

class LiveFragment : Fragment() {

    companion object {
        val TAG: String = "LiveFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_live, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        val strArray = KSimpleUtil.KGetStringArrayFromRes(R.array.title)
        val fragList = mutableListOf<Fragment>()
        var i: Int = 0
        while (i < strArray.size) {
            if (i == 0) fragList.add(FocusFragment())
            else if (i == 1) fragList.add(HotFragment())
            else if (i == 2) fragList.add(NearFragment())
            else fragList.add(EmptyFragment())
            i++
        }
        vp_live.adapter = LiveVpAdapter(childFragmentManager, fragList)
    }
}