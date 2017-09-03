package com.knox.kyingke.adapter.liveroom

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.KRvAdapter
import com.knox.kyingke.bean.liveroom.GiftBean
import com.knox.kyingke.utils.KInKeUrlUtil
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/3  10:05
 *
 * @desc ${TODD}
 *
 */

class GiftRvAdapter: KRvAdapter<GiftBean>() {
    companion object {
        val TAG:String = "GiftRvAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_gift, parent, false)
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        /*有可能遇到伪造数据*/
        if (TextUtils.isEmpty(mList[position].icon))
            return


        holder?.setImgUrl(R.id.sdv_gift, KInKeUrlUtil.getEscapeImgUrl(mList[position].icon))
        holder?.setText(R.id.tv_price, mList[position].gold.toString())
        holder?.setText(R.id.tv_name, mList[position].name)
    }
}