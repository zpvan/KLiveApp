package com.knox.kyingke.viewholder

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.knox.kyingke.R
import com.knox.kyingke.utils.KSimpleUtil

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  17:34
 *
 * @desc ${TODD}
 *
 */

class KRvViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun setText(vId: Int, s: String) {
        itemView.findViewById<TextView>(vId).text = s
    }

    fun setLabel(vId: Int, strs: MutableList<String>) {
        val linearLayout = itemView.findViewById<LinearLayout>(vId)
        val params = LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, KSimpleUtil.Kdp2px(8f), 0)
        linearLayout.removeAllViews()
        for (str in strs) {
            val textView = TextView(itemView.context)
            textView.text = str
            textView.textSize = 10f
            textView.setBackgroundResource(R.drawable.bg_tag)
            textView.setPadding(KSimpleUtil.Kdp2px(10f), KSimpleUtil.Kdp2px(5f),
                    KSimpleUtil.Kdp2px(10f), KSimpleUtil.Kdp2px(5f))
            textView.gravity = Gravity.CENTER
            linearLayout.addView(textView, params)
        }
    }

}