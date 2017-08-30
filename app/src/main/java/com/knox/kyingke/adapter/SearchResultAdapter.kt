package com.knox.kyingke.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.bean.search.UserBean
import com.knox.kyingke.utils.KSimpleUtil
import com.knox.kyingke.viewholder.KRvViewHolder
import java.util.ArrayList

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  15:41
 *
 * @desc ${TODD}
 *
 */

class SearchResultAdapter : KRvAdapter<UserBean>() {

    /*只有一种数据类型*/
    fun setResultDatas(users: MutableList<UserBean>?) {
        mList.clear()
        if (users != null) {
            mList.addAll(users)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        if (holder == null)
            return

        val userBean = mList[position]
        val userItemBean = userBean.user
        holder.setImgUrl(R.id.icon, userItemBean.portrait)
        holder.setText(R.id.name, userItemBean.nick)
        holder.setText(R.id.describe, userItemBean.description)
        holder.setImgRes(R.id.sex, if (userItemBean.gender === 0)
            R.drawable.global_icon_female
        else
            R.drawable.global_icon_male)

        holder.setImgRes(R.id.rank,
                KSimpleUtil.kGetApplicationContext().getResources().getIdentifier("rank_" + userItemBean.level, "drawable", "com.knox.kyingke"))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_search_result, parent, false)
        return KRvViewHolder(view)
    }
}