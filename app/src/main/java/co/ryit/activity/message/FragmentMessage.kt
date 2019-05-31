package co.ryit.activity.message

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import co.baselib.utils.ToastUtil
import co.ryit.R
import co.ryit.activity.download.DownloadActivity
import co.ryit.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_message.view.*
import kotlinx.android.synthetic.main.fragnemtt_home.view.*

/**
 * Created by admin on 2018/8/29.
 */
class FragmentMessage : BaseFragment() {
    override fun initView(): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        inflate.login.setOnClickListener {
            JMessageClient.login("zantuvip", "123456", object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {
                    if (p0 == 0) {
                        inflate.userinfo.text = "登陆成功"
                    } else {
                        ToastUtil.showShort(context, p1);
                    }
                }
            });

        }
        return inflate;
    }
}