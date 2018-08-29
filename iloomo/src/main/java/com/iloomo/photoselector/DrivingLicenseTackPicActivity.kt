package com.iloomo.photoselector

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.iloomo.base.ActivitySupport
import com.iloomo.paysdk.R
import com.iloomo.photoselector.model.OnTakePicListener
import com.iloomo.photoselector.widget.JCameraView
import com.iloomo.threadpool.MyThreadPool
import com.iloomo.utils.AppUtil
import com.iloomo.utils.BigDecimalUtil
import com.iloomo.utils.L
import com.iloomo.utils.PImageLoaderUtils
import kotlinx.android.synthetic.main.activity_drivinglicense.*


open class DrivingLicenseTackPicActivity : ActivitySupport() {

    var dslMarker: JCameraView? = null
    var skipv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivinglicense)
        setRemoveTitle()

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels// 屏幕宽度（像素）

        val layoutParams = re_idview.layoutParams as ViewGroup.LayoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        L.e("屏幕：$width")
        val dis = width.toDouble() - AppUtil.dip2px(context, 52f)
        L.e("屏幕减去尺寸：$dis")
        val div = BigDecimalUtil.div(dis, 345.0)
        L.e("屏幕与图片的比例：$div")
        val mul = BigDecimalUtil.mul(div, 238.0)
        L.e("图片的尺寸：$mul")

        layoutParams.height = mul.toInt()
        re_idview.layoutParams = layoutParams
        jCameraView.ischangeVisible(View.GONE)


        PImageLoaderUtils.getInstance().displayIMG(R.drawable.biankuang, biankuang, context)
        PImageLoaderUtils.getInstance().displayIMG(R.drawable.paizhao, paizhao, context)
        PImageLoaderUtils.getInstance().displayIMG(R.drawable.quxiao, quxiaoimg, context)
        quxiaoimg.setOnClickListener { finish() }
        paizhao.setOnClickListener { takePic() }
        dslMarker = jCameraView
        skipv = skip;
    }

    override fun onStart() {
        super.onStart()
        //全屏显示
//        if (Build.VERSION.SDK_INT >= 19) {
//            val decorView = window.decorView
//            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//        } else {
//            val decorView = window.decorView
//            val option = View.SYSTEM_UI_FLAG_FULLSCREEN
//            decorView.systemUiVisibility = option
//        }
    }

    var istake: Boolean = false
    fun takePic() {
        if (istake)
            return
        istake = true
        jCameraView.capture()
    }

    override fun onResume() {
        super.onResume()
        MyThreadPool.getInstance().submit {
            jCameraView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        jCameraView.onPause()
    }

    open fun onSkip(view: View) {
        L.e("跳过")
    }
}