package co.ryit.activity.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import co.ryit.R
import co.ryit.activity.download.DownloadActivity
import co.ryit.base.BaseFragment
import kotlinx.android.synthetic.main.fragnemtt_home.view.*

/**
 * Created by admin on 2018/8/29.
 */
class FragmentHome : BaseFragment() {
    override fun initView(): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.fragnemtt_home, null);
        /***
         * 下载页面
         */
        inflate.download.setOnClickListener { activity!!.startActivity(Intent(activity, DownloadActivity::class.java)) }

        return inflate;
    }
}