package com.knox.kyingke.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.bean.KTypeBean
import com.knox.kyingke.bean.hot.HotBannerBean
import com.knox.kyingke.bean.hot.HotItemBean
import com.knox.kyingke.utils.KInKeUrlUtil
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/28  17:31
 *
 * @desc ${TODD}
 *
 */

class HotRvAdapter : KRvAdapter<KTypeBean>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        var view: View? = null
        if (viewType == KTypeBean.TypeHotBanner)
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_hot_banner, parent, false)
        else if (viewType == KTypeBean.TypeHotItem)
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_hot, parent, false)
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        val kTypeBean = mList[position]
        if (kTypeBean.getType() == KTypeBean.TypeHotBanner) {
            /*加载轮播图*/
            val strs: MutableList<String> = mutableListOf()
            val item = kTypeBean as HotBannerBean
            val ticker = item.ticker

            val iterator = ticker.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                strs.add(next.image)
            }

            holder?.setBanner(R.id.banner, strs)
        } else if (kTypeBean.getType() == KTypeBean.TypeHotItem) {
            val item = kTypeBean as HotItemBean
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

            /*头像加载*/
            holder?.setImgUrl(R.id.icon, KInKeUrlUtil.getScaledImgUrl(item.creator.portrait, 100, 100))

            /*形象照加载*/
            holder?.setImgUrl(R.id.src, item.creator.portrait)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].getType()
    }

    fun loadItemData(list: MutableList<HotItemBean>?) {
        if (list != null || list!!.size == 0) {
            /*删除原来保存的item数据*/
            val iterator = mList.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next.getType() != KTypeBean.TypeHotBanner) {
                    iterator.remove()
                }
            }
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadBannerData(banner: HotBannerBean?) {
        if (banner != null) {
            if (mList.size > 0) {
                val kTypeBean = mList[0]
                if (kTypeBean.getType() == KTypeBean.TypeHotBanner) {
                    mList.set(0, banner)
                } else {
                    mList.add(0, banner)
                }
            } else {
                mList.add(0, banner)
            }
        }
    }
}