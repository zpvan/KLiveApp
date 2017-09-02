package com.knox.kyingke.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.knox.kyingke.listener.IKRvAdapterListener
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  14:17
 *
 * @desc 抽取HotRvAdapter跟SearchRvAdapter的共同特性出来, 譬如mList用T泛型来做, item的点击事件也在这里做
 *
 */

abstract class KRvAdapter<T> : RecyclerView.Adapter<KRvViewHolder>() {

    var mList = mutableListOf<T>()
    var mListener: IKRvAdapterListener? = null

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setDatas(list: MutableList<T>?) {
        mList.clear()
        if (list != null)
            mList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: KRvViewHolder?, position: Int) {
        holder?.itemView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mListener?.onClick(v, position)
            }
        })
        onBindViewHolder2(holder, position)
    }

    abstract fun onBindViewHolder2(holder: KRvViewHolder?, position: Int)

    fun setKRvAdapterListener(listener: IKRvAdapterListener) {
        mListener = listener
    }
}