package com.knox.kyingke.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

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

}