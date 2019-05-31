package co.ryit.activity.statusbar

import android.os.Bundle
import co.baselib.utils.StatusBarUtil
import co.ryit.R
import co.ryit.base.BaseActivity

class StatusBarDarkActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statusbardark)
        setCtenterTitle("白色通知栏")
    }

    override fun setStatusBar() {
        StatusBarUtil.setPaddingSmart(this, titleBar)
        StatusBarUtil.darkMode(this)
    }
}