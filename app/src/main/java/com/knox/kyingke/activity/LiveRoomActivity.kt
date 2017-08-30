package com.knox.kyingke.activity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import com.knox.kyingke.R
import com.knox.kyingke.adapter.LiveRoomAdapter
import com.knox.kyingke.bean.hot.HotItemBean
import com.knox.kyingke.widget.media.IjkVideoView
import fr.castorflex.android.verticalviewpager.VerticalViewPager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_room)

        val intent = intent
        if (intent != null) {
            val extra = intent.getSerializableExtra(KEY_ITEM)
            val toMutableList = (extra as Array<HotItemBean>).toMutableList()
            Log.e(TAG, "onCreate: list " + toMutableList.size + " toMutableList " + toMutableList)

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

        val view = LayoutInflater.from(this).inflate(R.layout.item_live_room, null, false)
        mVideoView = view.findViewById<IjkVideoView>(R.id.video_view)

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
                    Log.e(TAG, "transformPage: currentItem " + currentItem + " url " + mAdapter!!.getUrl(currentItem));
                    val parent = mVideoView!!.parent
                    if (parent != null) {
                        /*视频控件如果已经绑定了父控件, 解绑*/
                        (parent as ViewGroup).removeView(mVideoView);
                    }
                    (page as ViewGroup).addView(mVideoView);

                    /*先关掉前一个, 再设置新的url, 再起播*/
                    mVideoView!!.stopPlayback();
                    mVideoView!!.setVideoURI(Uri.parse(mAdapter!!.getUrl(currentItem)));
                    mVideoView!!.start();
                }
            }
        })
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
