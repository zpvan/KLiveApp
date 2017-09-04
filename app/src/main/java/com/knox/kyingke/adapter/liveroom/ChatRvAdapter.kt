package com.knox.kyingke.adapter.liveroom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.KRvAdapter
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/4  11:16
 *
 * @desc ${TODD}
 *
 */

class ChatRvAdapter: KRvAdapter<String>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_msg, parent, false)
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        holder?.setText(R.id.tv_chat, mList[position])
    }

    fun addAtLast(string: String) {
        mList.add(mList.size, string)
        notifyDataSetChanged()
    }

    fun clearDatas() {
        mList.clear()
        notifyDataSetChanged()
    }
}