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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.iloomo.base.ActivitySupport
import com.iloomo.paysdk.R
import com.iloomo.photoselector.model.OnTakePicListener
import com.iloomo.utils.AppUtil
import com.iloomo.utils.BigDecimalUtil
import com.iloomo.utils.L
import com.iloomo.utils.PImageLoaderUtils
import kotlinx.android.synthetic.main.activity_idcard_takepic.*


class IdCardTakePicActivity : ActivitySupport() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_idcard_takepic)
        setRemoveTitle()

        if (intent.getBooleanExtra("qianhou", false)) {
            qianhou.text = "（身份证带国徽面）"
        } else {
            qianhou.text = "（身份证带照片面）"
        }

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
        layoutParams.height=mul.toInt()
        re_idview.layoutParams = layoutParams

//        re_idview.systemUiVisibility=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE

        jCameraView.ischangeVisible(View.GONE)

        PImageLoaderUtils.getInstance().displayIMG(R.drawable.paizhao, paizhao, context)
        PImageLoaderUtils.getInstance().displayIMG(R.drawable.quxiao, quxiao_cancel, context)
        quxiao_cancel.setOnClickListener { finish() }
        paizhao.setOnClickListener { takePic() }
        jCameraView.setOnTakePicListener(object : OnTakePicListener {

            override fun takePic(path: String) {
                L.e("拍照地址：$path")
                val intent = Intent()
                intent.putExtra("path", path)
                setResult(RESULT_OK, intent)
                finish()
            }

            override fun filed() {
                showToastFiled("拍照失败！")
            }
        })
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
        jCameraView.onResume()
    }

    override fun onPause() {
        super.onPause()
        jCameraView.onPause()
    }
}