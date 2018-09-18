package co.ryit.activity.welcome

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import co.ryit.base.BaseActivity
import co.ryit.R
import co.wpt.LoginUtil
import co.wpt.login.LoginListener
import co.wpt.login.LoginPlatform
import co.wpt.login.LoginResult
import co.wpt.login.result.*
import co.wpt.model.ShareListener
import com.tbruyelle.rxpermissions.RxPermissions
import com.umeng.socialize.UMShareAPI
import android.content.Intent
import co.ryit.activity.IndexActivity
import com.nineoldandroids.animation.Animator
import kotlinx.android.synthetic.main.layout_app.*


class WelcomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_app)
        setRemoveTitle()
        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe { granted ->
                    if (granted!!) { // 在android 6.0之前会默认返回true
                        Log.e("获取权限", "获取权限成功");
                    } else {
                        // 未获取权限
                        Log.e("获取权限", "获取权限失败");
                    }
                    startWelcomPic()
                }
    }

//    fun onDeleteLogin(view: View) {
//        LoginUtil.getInstance().delete(this, LoginPlatform.WEIBO);
//    }


//    fun onlogin(view: View) {
//        LoginUtil.getInstance().login(this, LoginPlatform.WEIBO, object : LoginListener() {
//
//            override fun beforeFetchUserInfo(token: BaseToken) {
//                super.beforeFetchUserInfo(token)
//            }
//
//            override fun loginSuccess(result: LoginResult) {
//                //登录成功， 如果你选择了获取用户信息，可以通过
//
//                // 处理result
//                when (result.getPlatform()) {
//                    LoginPlatform.QQ -> {
//                        val user = result.getUserInfo() as QQUser
//                        val token = result.getToken() as QQToken
//                        Log.e("", "openid:" + token.getOpenid())
//                        val bundle = Bundle()
//                        bundle.putString("type", "2")
//                        bundle.putString("openid", token.getOpenid())
//                        bundle.putString("nick", user.getNickname())
//                        bundle.putString("avatar", user.getHeadImageUrlLarge())
//
//                    }
//                    LoginPlatform.WEIBO -> {
//                        // 处理信息
//                        val Weibouser = result.getUserInfo() as WeiboUser
//                        val Weibotoken = result.getToken() as WeiboToken
//                        val bd = Bundle()
//                        bd.putString("type", "3")
//                        bd.putString("openid", Weibotoken.getOpenid())
//                        if (Weibouser == null) {
//                            bd.putString("nick", "")
//                            bd.putString("avatar", "")
//                        } else {
//                            bd.putString("nick", Weibouser!!.getNickname())
//                            bd.putString("avatar", Weibouser!!.getHeadImageUrlLarge())
//                        }
//
//                    }
//                    LoginPlatform.WX -> {
//                        val wxUser = result.getUserInfo() as WxUser
//                        val wxtoken = result.getToken() as WxToken
//                        Log.e("", "openid:" + wxtoken.getOpenid())
//                        val bds = Bundle()
//                        bds.putString("type", "1")
//                        bds.putString("openid", wxtoken.getOpenid())
//                        bds.putString("nick", wxUser.getNickname())
//                        bds.putString("avatar", wxUser.getHeadImageUrlLarge())
//                    }
//                }
//
//            }
//
//            override fun loginFailure(e: Throwable) {
//                Log.i("TAG", "登录失败$e")
//
//
//            }
//
//            override fun loginCancel() {
//                Log.i("TAG", "登录取消")
//
//
//            }
//        }, object : ShareListener {
//            override fun onSuccess(smsg: String?) {
//
//            }
//
//            override fun onFiled(fmsg: String?) {
//
//            }
//        })
//    }


    /**
     * 启动APP启动动画
     */
    fun startWelcomPic() {
        welcome.setStartPicImage(R.mipmap.welcome)
        welcome.start()
        welcome.setAnimationListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                mIntent(context, IndexActivity::class.java)
                finish()
            }
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
//    }


}