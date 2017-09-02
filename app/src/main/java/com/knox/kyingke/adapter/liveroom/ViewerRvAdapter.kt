package com.knox.kyingke.adapter.liveroom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.KRvAdapter
import com.knox.kyingke.bean.liveroom.UserBean
import com.knox.kyingke.viewholder.KRvViewHolder

/**
 * @author Knox.Tsang
 *
 * @time 2017/9/2  14:05
 *
 * @desc ${TODD}
 *
 */

class ViewerRvAdapter: KRvAdapter<UserBean>() {
    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        holder?.setImgUrl(R.id.sDraweeView_bg, mList[position].ext.light)
        holder?.setImgUrl(R.id.sDraweeView_viewer_icon, mList[position].portrait)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        return KRvViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_viewer_icon, parent, false))
    }
}