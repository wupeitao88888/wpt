package com.iloomo.photoselector

import android.os.Bundle
import android.view.View
import com.iloomo.base.ActivitySupport
import com.iloomo.paysdk.R
import kotlinx.android.synthetic.main.activity_videoplay.*
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import android.content.pm.ActivityInfo
import com.shuyu.gsyvideoplayer.GSYVideoManager


class PictureVideoPlayActivity : ActivitySupport() {

    var orientationUtils: OrientationUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplay)
        setRemoveTitle()
        val stringExtra = intent.getStringExtra("video_path")

        video_player.setUp(stringExtra, true, "测试视频");
        //增加title
        video_player.titleTextView.visibility = View.GONE
        //设置返回键
        video_player.backButton.visibility = View.VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, video_player)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video_player.fullscreenButton.setOnClickListener(View.OnClickListener { orientationUtils!!.resolveByClick() })
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置返回按键功能
        video_player.backButton.visibility = View.GONE
        video_player.backButton.setOnClickListener(View.OnClickListener { onBackPressed() })
        back_my.setOnClickListener { onBackPressed() }
        video_player.startPlayLogic()

    }


    override fun onPause() {
        super.onPause()
        video_player.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        video_player.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null)
            orientationUtils!!.releaseListener()
    }

    override fun onBackPressed() {
        //先返回正常状态
        if (orientationUtils!!.screenType === ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            video_player.fullscreenButton.performClick()
            return
        }
        //释放所有
        video_player.setVideoAllCallBack(null)
        super.onBackPressed()
    }


}