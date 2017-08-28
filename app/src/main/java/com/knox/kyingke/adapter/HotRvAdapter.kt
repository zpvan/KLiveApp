package com.knox.kyingke.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  17:31
 *
 * @desc ${TODD}
 *
 */

class HotRvAdapter : RecyclerView.Adapter<KRvViewHolder>() {

    var mList = mutableListOf<String>()

    fun setDatas(list : MutableList<String>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_hot, parent, false)
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder(holder: KRvViewHolder?, position: Int) {
        val s = mList[position]
        holder?.setText(R.id.tv_content, s)
    }


}