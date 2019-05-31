package co.ryit.activity.statusbar

import android.graphics.ColorSpace
import android.os.Bundle
import android.view.View
import co.baselib.utils.StatusBarUtil
import co.ryit.R
import co.ryit.base.BaseActivity

class StatusBarWriteActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statusbarwrite)
        setBackGb(R.color.colorPrimary)
        setCtenterTitle("白色通知栏")
        titleBar.setLeftImage(R.mipmap.fanhuibai)
    }

    override fun setStatusBar() {
        StatusBarUtil.setPaddingSmart(this, titleBar)
        StatusBarUtil.immersive(this,R.color.colorPrimary)
    }


    fun goBlack(v: View) {
        mIntent(this, StatusBarDarkActivity::class.java)
    }
}