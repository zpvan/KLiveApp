package com.knox.kyingke.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.bean.hot.HotItemBean
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

    var mList = mutableListOf<HotItemBean>()

    fun setDatas(list: MutableList<HotItemBean>?) {
        if (list != null) {
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_hot, parent, false)
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder(holder: KRvViewHolder?, position: Int) {
        val item = mList[position]
        holder?.setText(R.id.name, item.creator.nick)
        holder?.setText(R.id.viewCount, item.online_users.toString())

        /*标签数据*/
        val lables = item.extra.label
        val strs = mutableListOf<String>()
        var index = 0
        while (index < lables.size) {
            strs.add(lables[index].tab_key)
            index++
        }
        holder?.setLabel(R.id.ll_tag, strs)
    }
}