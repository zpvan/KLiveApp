package com.knox.kyingke.activity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knox.kyingke.R
import com.knox.kyingke.adapter.LiveRoomAdapter
import com.knox.kyingke.bean.hot.HotItemBean
import com.knox.kyingke.fragment.liveroom.LiveRoomFragment
import com.knox.kyingke.widget.media.IjkVideoView
import kotlinx.android.synthetic.main.activity_live_room.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class LiveRoomActivity : AppCompatActivity() {

    companion object {
        val TAG: String = "LiveRoomActivity"
        val KEY_ITEM = "LiveRoomActivity::KEY_ITEM"
        val KEY_INDEX = "LiveRoomActivity::KEY_INDEX"
    }

    var mAdapter: LiveRoomAdapter? = null
    /*初始值给-1, 不给正值, 是因为第一次进来currentItem可能就跟它一样, 从而不去加载mVideoView*/
    var mLastItem = -1
    var mVideoView: IjkVideoView? = null
    /*视频控件, 里边包括一个用来播放的View, 还有一个展示其它信息的FrameLayout*/
    var view: View? = null
    val TAG_LIVEROOMFRAGMENT = "LiveRoomActivity::TAG_LIVEROOMFRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_room)

        val intent = intent
        if (intent != null) {
            val extra = intent.getSerializableExtra(KEY_ITEM)
            val toMutableList = (extra as Array<HotItemBean>).toMutableList()

            val index = intent.getIntExtra(KEY_INDEX, 0)

            initVideoComponent()
            initViewPager(toMutableList)
            initListener(index)

            /*ViewPager切换到当前点击的item*/
            vvp_lr.setCurrentItem(index)
        }
    }

    private fun initVideoComponent() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        view = LayoutInflater.from(this).inflate(R.layout.item_live_room, null, false)
        mVideoView = view!!.findViewById<IjkVideoView>(R.id.video_view)

        /*默认是使用surfaceView, 稍微一滑动屏幕, video都变得全白了, 改成用Texture渲染就没有这个问题.滑动屏幕时, video跟着走*/
        mVideoView!!.setRender(IjkVideoView.RENDER_TEXTURE_VIEW)

        /*原本video size的按比例缩放AR_ASPECT_FIT_PARENT, 切换一次就变成全屏AR_ASPECT_FILL_PARENT*/
        mVideoView!!.toggleAspectRatio()
    }

    private fun initListener(index: Int) {
        vvp_lr.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(page: View?, position: Float) {
                val currentItem = vvp_lr.getCurrentItem()
                if (position == 0f && currentItem != mLastItem) {
                    /*position == 0, 说明该view在屏幕正中了, 占满屏了, 要将视频控件绑到这个view上*/
                    /*还需要判断当前正中央的view是否还有之前那个, 换言之, 用户划来划去, 并没有切换直播间, 要保存当前Item的序号*/
                    mLastItem = currentItem;
                    val parent = view!!.parent
                    if (parent != null) {
                        /*视频控件如果已经绑定了父控件, 解绑*/
                        (parent as ViewGroup).removeView(view)
                    }
                    (page as ViewGroup).addView(view)


                    loadNewLiveStream(mAdapter!!.getItem(currentItem)!!.stream_addr)
                    loadNewLiveInfo(mAdapter?.getItem(currentItem))
                }
            }
        })
    }

    private fun loadNewLiveInfo(item: HotItemBean?) {
        /*LiveInfo展示在LiveRoomFragment上, LiveRoomFragment绑定在FrameLayout上, 而FrameLayout在item_live_room.xml中,
        * item_live_room是在fun initVideoComponent()里边炸出来的, 为什么不在initVideoComponent()里边绑定LiveRoomFragment?
        * 因为那样会造成Exception, view还没绑定到该activity的布局中时, FrameLayout也就不在activity的布局中, 所以不能绑*/
        var liveRoomFragment = supportFragmentManager.findFragmentByTag(TAG_LIVEROOMFRAGMENT)
        if (liveRoomFragment == null) {
            /*只需要一个fragment就好, 切换直播间时, 它也像mVideoView一样, 是跟着跳转的, 到时只需要更新数据*/
            liveRoomFragment = LiveRoomFragment()
            supportFragmentManager.beginTransaction().add(R.id.fl_lr, liveRoomFragment, TAG_LIVEROOMFRAGMENT).commit()
            /*由于Fragment不是加载完才返回, fragment事务是非阻塞的, 所以这里直接call LiveRoomFragment的updateInfo方法行不通*/
            val bundle = Bundle()
            bundle.putSerializable(LiveRoomFragment.KEY_BUNDLEITEM, item)
            liveRoomFragment.setArguments(bundle)
        } else {
            /*直接更新数据*/
            if (item != null)
                (liveRoomFragment as LiveRoomFragment).fillLiveRoomInfo(item)
        }

    }

    /*先关掉前一个, 再设置新的url, 再起播*/
    private fun loadNewLiveStream(stream_addr: String) {
        mVideoView!!.stopPlayback()
        mVideoView!!.setVideoURI(Uri.parse(stream_addr))
        mVideoView!!.start()
    }

    private fun initViewPager(list: MutableList<HotItemBean>) {
        mAdapter = LiveRoomAdapter(list)
        vvp_lr.adapter = mAdapter
    }

    override fun onStop() {
        super.onStop()

        mVideoView!!.stopPlayback()
        mVideoView!!.release(true)
        IjkMediaPlayer.native_profileEnd()
    }
}
