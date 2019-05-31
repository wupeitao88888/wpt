package co.ryit.activity.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import co.baselib.utils.PImageLoaderUtils
import co.ryit.R
import co.ryit.activity.download.DownloadActivity
import co.ryit.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_me.view.*
import kotlinx.android.synthetic.main.fragnemtt_home.view.*

/***
 * 我的
 */
class FragmentMe : BaseFragment() {

    override fun initView(): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.fragment_me, null)
        inflate.nice_iv0.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }

        PImageLoaderUtils.getInstance().displayIMG(R.mipmap.hand, inflate.nice_iv0, context)
        return inflate
    }


}