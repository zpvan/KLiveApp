package com.knox.kyingke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  15:10
 *
 * @desc ${TODD}
 *
 */

class EmptyFragment : Fragment(){

    companion object {
        val TAG:String = "EmptyFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(context)
        textView.text = javaClass.name
        return textView
    }
}