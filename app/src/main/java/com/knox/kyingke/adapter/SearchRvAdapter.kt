package com.knox.kyingke.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.bean.KTypeBean
import com.knox.kyingke.bean.search.LiveNodeBean
import com.knox.kyingke.bean.search.TitleBean
import com.knox.kyingke.bean.search.UserBean
import com.knox.kyingke.utils.KSimpleUtil
import com.knox.kyingke.viewholder.KRvViewHolder
import java.util.ArrayList

/**
 * @author Knox.Tsang
 *
 * @time 2017/8/30  14:16
 *
 * @desc ${TODD}
 *
 */

class SearchRvAdapter : KRvAdapter<KTypeBean>() {

    /*item类型组成
    * type item
    * title item
    * recommend item
    * */

    fun setTypeDatas(lives: MutableList<LiveNodeBean>) {
        mList.addAll(0, lives)
        notifyDataSetChanged()
    }

    fun setRecommendDatas(users: MutableList<UserBean>) {
        val addTitleList : MutableList<KTypeBean> = mutableListOf()
        addTitleList.addAll(users)
        addTitleList.add(0, TitleBean("今日推荐"))
        mList.addAll(addTitleList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KRvViewHolder {
        var view: View? = null
        when (viewType) {
            KTypeBean.TypeSearchNode -> view = LayoutInflater.from(parent?.context).inflate(R.layout.item_search_type, parent, false)
            KTypeBean.TypeSearchTitle -> view = LayoutInflater.from(parent?.context).inflate(R.layout.item_search_title, parent, false)
            KTypeBean.TypeSearchRecommend -> view = LayoutInflater.from(parent?.context).inflate(R.layout.item_search_recommend, parent, false)
        }
        return KRvViewHolder(view)
    }

    override fun onBindViewHolder2(holder: KRvViewHolder?, position: Int) {
        val kTypeBean = mList[position]
        when (kTypeBean.getType()) {
            KTypeBean.TypeSearchNode -> showSearchNode(holder, kTypeBean as LiveNodeBean)
            KTypeBean.TypeSearchTitle -> showSearchTitle(holder, kTypeBean as TitleBean)
            KTypeBean.TypeSearchRecommend -> showSearchRecommend(holder, kTypeBean as UserBean)
        }
    }

    private fun showSearchNode(holder: KRvViewHolder?, liveNode: LiveNodeBean) {
        if (holder == null)
            return

        holder.setText(R.id.title, liveNode.title)

        /*设置主播图片和观看人数*/
        val lives = liveNode.lives
        for (i in lives.indices) {
            val liveBean = lives.get(i)
            if (i == 0) {
                holder.setImgUrl(R.id.one, liveBean.creator.portrait)
                holder.setText(R.id.one_number, liveBean.online_users.toString())
            } else if (i == 1) {
                holder.setImgUrl(R.id.two, liveBean.creator.portrait)
                holder.setText(R.id.two_number, liveBean.online_users.toString())
            } else if (i == 2) {
                holder.setImgUrl(R.id.three, liveBean.creator.portrait)
                holder.setText(R.id.three_number, liveBean.online_users.toString())
            }
        }
    }

    private fun showSearchTitle(holder: KRvViewHolder?, titleBean: TitleBean) {
        //不用做什么, 布局文件已经hardcode了
    }

    private fun showSearchRecommend(holder: KRvViewHolder?, userBean: UserBean) {
        if (holder == null)
            return

        holder.setText(R.id.type, userBean.reason)

        val userItemBean = userBean.user
        holder.setImgUrl(R.id.icon, userItemBean.portrait)
        holder.setText(R.id.name, userItemBean.nick)
        holder.setImgRes(R.id.sex, if (userItemBean.gender === 0)
            R.drawable.global_icon_female
        else
            R.drawable.global_icon_male)

        holder.setImgRes(R.id.rank,
                KSimpleUtil.kGetApplicationContext().getResources().getIdentifier("rank_" + userItemBean.level, "drawable", "com.knox.kyingke"))
    }


}