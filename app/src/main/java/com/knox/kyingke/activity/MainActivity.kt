package com.knox.kyingke.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.knox.kyingke.R
import com.knox.kyingke.fragment.LiveFragment
import com.knox.kyingke.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_bottom.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val TAG: String = "MainActivity"
    }

    val fragmentManager = supportFragmentManager
    val TAG_LIVE = "tagLiveFragment"
    val TAG_MINE = "tagMineFragment"

    override fun onClick(v: View?) {
        Log.e(TAG, "onClick: ")
        when (v?.id) {
            R.id.iv_live -> clickBtn(TAG_LIVE)
            R.id.iv_mine -> clickBtn(TAG_MINE)
        }
    }

    private fun clickBtn(tag: String) {
        iv_live.isSelected = tag == TAG_LIVE
        iv_mine.isSelected = tag == TAG_MINE
        hideFragment(if (tag == TAG_LIVE) TAG_MINE else TAG_LIVE)
        showFragment(if (tag == TAG_LIVE) TAG_LIVE else TAG_MINE)
    }

    private fun showFragment(tag: String) {
        val fragByTag = fragmentManager.findFragmentByTag(tag)
        val trans = fragmentManager.beginTransaction()
        if (fragByTag == null) {
            trans.add(R.id.fl_main, if (tag == TAG_LIVE) LiveFragment() else MineFragment(), tag)
        } else {
            trans.show(fragByTag)
        }
        trans.commit()
    }

    private fun hideFragment(tag: String) {
        val fragByTag = fragmentManager.findFragmentByTag(tag)
        if (fragByTag != null) {
            val trans = fragmentManager.beginTransaction()
            trans.hide(fragByTag)
            trans.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()

    }

    private fun initListener() {
        iv_live.setOnClickListener(this)
        iv_mine.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        clickBtn(TAG_LIVE)
    }
}
