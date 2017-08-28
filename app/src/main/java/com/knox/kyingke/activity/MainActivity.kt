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

    override fun onClick(v: View?) {
        Log.e(TAG, "onClick: ")
        when (v?.id) {
            R.id.iv_live -> showLive()
            R.id.iv_mine -> showMine()
        }
    }

    private fun showMine() {
        /*开始fragment事务*/
        val trans = fragmentManager.beginTransaction()
        /*给FrameLayout设置MineFragment*/
        trans.replace(R.id.fl_main, MineFragment())
        /*提交事务*/
        trans.commit()
    }

    private fun showLive() {
        /*开始fragment事务*/
        val trans = fragmentManager.beginTransaction()
        /*给FrameLayout设置LiveFragment*/
        trans.replace(R.id.fl_main, LiveFragment())
        /*提交事务*/
        trans.commit()
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


}
