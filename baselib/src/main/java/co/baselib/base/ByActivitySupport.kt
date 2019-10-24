package co.baselib.base


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout

import androidx.appcompat.app.AppCompatActivity

import com.google.gson.Gson
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.simple.RequestManager
import com.yanzhenjie.kalle.simple.SimpleCallback
import com.yanzhenjie.kalle.simple.SimpleResponse

import co.baselib.R
import co.baselib.global.ByAppController
import co.baselib.utils.ByActivityErrorUtils
import co.baselib.utils.ByActivityPageManager
import co.baselib.utils.ByDateUtil
import co.baselib.utils.ByDialogUtil
import co.baselib.utils.ByL
import co.baselib.utils.BySharedPreferencesHelper
import co.baselib.utils.ByStrUtil
import co.baselib.utils.ByToastUtil
import co.baselib.utils.ByUnicodeUtils
import co.baselib.widget.ByChildClickableRelativeLayout
import co.baselib.widget.ByTitleBar

/**
 * Actity 基类
 *
 * @author wpt
 */
@SuppressLint("NewApi")
open class ByActivitySupport : AppCompatActivity() {


    protected var context: Activity? = null
    protected var sharedPreferencesHelper: BySharedPreferencesHelper? = null
    open var titleBar: ByTitleBar? = null
    var all_super: ViewGroup? = null
    internal var layout_parent: RelativeLayout? = null
    /**
     * 用来标记取消。
     */
    var cancelTag = Any()

    var gson: Gson? = null

    private var ishide = false

    var layout_error: RelativeLayout? = null

    internal var customtitle: ViewGroup? = null
    internal var childclick: ByChildClickableRelativeLayout? = null


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeInput()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ByActivityPageManager.getInstance().addActivity(this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// 不显示标题
        super.onCreate(savedInstanceState)
        initDate()
        gson = Gson()
    }


    /**
     * 是否隐藏状态栏
     */
    fun isHideStatusBar(ishide: Boolean) {
        this.ishide = ishide
    }


    /**
     * Activity用户行为 子类进行重写
     *
     * @return
     */
    protected fun getBehaviour(bundle: Bundle): Bundle {
        return bundle
    }


    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    fun getResCharSequence(resId: Int): CharSequence {
        return resources.getText(resId)
    }

    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    fun getResString(resId: Int): String {
        return resources.getText(resId).toString()
    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    fun netError(blean: Boolean, onClickListener: OnClickListener) {
        if (layout_error == null) {
            layout_error = LayoutInflater.from(context).inflate(
                    R.layout.by_layout_error, null) as RelativeLayout
            layout_parent!!.addView(layout_error)
            ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        }
        ByActivityErrorUtils.getInstance().netError(blean, onClickListener)
    }


    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    fun noData(blean: Boolean, onClickListener: OnClickListener) {
        if (layout_error == null) {
            layout_error = LayoutInflater.from(context).inflate(
                    R.layout.by_layout_error, null) as RelativeLayout
            layout_parent!!.addView(layout_error)
            ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        }
        ByActivityErrorUtils.getInstance().NoDate(blean, onClickListener)

    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    fun netError(viewGroup: ViewGroup, blean: Boolean) {
        if (layout_error == null) {
            layout_error = LayoutInflater.from(context).inflate(
                    R.layout.by_layout_error, null) as RelativeLayout
            viewGroup.addView(layout_error)
            ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        }
        ByActivityErrorUtils.getInstance().netError(blean) { }
    }

    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    fun noData(viewGroup: ViewGroup, blean: Boolean) {
        if (layout_error == null) {
            layout_error = LayoutInflater.from(context).inflate(
                    R.layout.by_layout_error, null) as RelativeLayout
            viewGroup.addView(layout_error)
            ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        }
        ByActivityErrorUtils.getInstance().NoDate(blean) { }

    }


    private fun initDate() {
        // TODO Auto-generated method stub
        context = this
        sharedPreferencesHelper = BySharedPreferencesHelper(context,
                BySharedPreferencesHelper.SHARED_PATH)

    }


    override fun setContentView(layoutResID: Int) {
        // TODO Auto-generated method stub
        all_super = LayoutInflater.from(context).inflate(
                R.layout.by_layout_super, null) as ViewGroup


        titleBar = LayoutInflater.from(this).inflate(
                R.layout.by_layout_activitytitle, null) as ByTitleBar
        layout_parent = LayoutInflater.from(context).inflate(
                R.layout.by_layout_parent, null) as RelativeLayout
        childclick = layout_parent!!.findViewById<View>(R.id.childclick) as ByChildClickableRelativeLayout
        //        // 自己的布局
        customtitle = LayoutInflater.from(this).inflate(layoutResID, null) as ViewGroup
        childclick!!.addView(customtitle)
        all_super!!.addView(titleBar, 0)
        all_super!!.addView(layout_parent)

        super.setContentView(all_super)
        setStatusBar()
    }


    override fun setContentView(view: View) {
        // TODO Auto-generated method stub
        all_super = LayoutInflater.from(context).inflate(
                R.layout.by_layout_super, null) as ViewGroup

        titleBar = LayoutInflater.from(this).inflate(
                R.layout.by_layout_activitytitle, null) as ByTitleBar
        layout_parent = LayoutInflater.from(context).inflate(
                R.layout.by_layout_parent, null) as RelativeLayout
        childclick = layout_parent!!.findViewById<View>(R.id.childclick) as ByChildClickableRelativeLayout

        childclick!!.addView(view)

        all_super!!.addView(titleBar, 0)
        all_super!!.addView(layout_parent)

        super.setContentView(all_super)
        setStatusBar()

    }


    /****
     * 设置状态栏
     */
    fun setStatusBar() {
        //状态栏透明和间距处理
    }

    /***
     * 设置所有布局子布局是否可以点击
     * @param isclick
     */
    fun allViewIsClick(isclick: Boolean) {
        childclick!!.setChildClickable(isclick)
    }

    /***
     * 显示提示语
     *
     * 替代方法   showToastSuccess  和  showToastFiled
     * @param msg
     */
    fun showToastBig(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示成功提示语
     * @param msg
     */
    fun showToastSuccess(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示失败提示语
     * @param msg
     */
    fun showToastFiled(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示成功提示语,并且关闭Activity
     * @param msg
     */
    fun showToastSuccessFinish(msg: String) {
        ByToastUtil.showShort(context, msg)
    }


    /***
     * 显示失败提示语,并且关闭Activity
     * @param msg
     */
    fun showToastFiledFinish(msg: String) {
        ByToastUtil.showShort(context, msg)
    }


    /***
     * 设置APP背景
     * @param color
     */
    fun setBackgroundColor(color: Int) {
        all_super!!.setBackgroundColor(color)
    }


    /***
     * 快速调转方法
     * @param packageContext
     * @param cls
     */
    protected fun mIntent(packageContext: Context, cls: Class<*>) {
        val intent = Intent(packageContext, cls)
        packageContext.startActivity(intent)
    }


    /**
     * 设置titleb中间
     *
     * @param
     */
    protected fun setRemoveTitle() {
        all_super!!.removeView(titleBar)
    }

    protected fun setTitle() {
        titleBar!!.visibility = View.VISIBLE
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected fun setCtenterTitle(title: String) {
        titleBar!!.centerTitle = title
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected fun setCtenterTitle(title: Int) {
        titleBar!!.centerTitle = mString(title)
    }

    /**
     * 设置左边的是否显示
     */
    protected fun isLeftVisibility(isVisibit: Boolean) {
        titleBar!!.isLeftVisibility(isVisibit)
    }

    /**
     * 设置中间标题是否显示
     */
    protected fun isCenterVisibility(isVisibit: Boolean) {
        titleBar!!.isCenterVisibility(isVisibit)
    }

    /**
     * 设置右边标题内容
     */
    protected fun setRightTitle(msg: String) {
        titleBar!!.setRightTitle(msg)
    }

    /**
     * 设置右边第二个标题内容
     */
    protected fun setRightSecondTitle(msg: String) {
        titleBar!!.setRightSecondTitle(msg)
    }


    /**
     * 设置右边标题内容字体颜色
     */
    protected fun setRightTitle(msg: Int) {
        titleBar!!.setRightTitle(msg)
    }


    /**
     * 设置右边标题内容
     */
    protected fun setRightTitleListener(l: OnClickListener) {
        titleBar!!.setRightTitleListener(l)
    }

    /**
     * 设置右边标题内容
     */
    protected fun setRightSecondTitleListener(l: OnClickListener) {
        titleBar!!.setRightSecondTitleListener(l)
    }


    /**
     * 设置title背景颜色
     */
    fun setBackGb(color: Int) {
        titleBar!!.setBackGb(color)
    }


    fun setTitleBarBackground(color: Int) {
        titleBar!!.setBackGb(color)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        activityOnResume()
    }

    override fun onPause() {
        super.onPause()
        activityOnPause()
    }

    fun activityOnResume() {
        /**
         * 友盟统计
         */
        if (titleBar != null) {
            var centerTitle = titleBar!!.centerTitle
            if (TextUtils.isEmpty(centerTitle))
                centerTitle = "Activity"
        }
    }


    fun activityOnPause() {
        /**
         * 友盟统计
         */
        if (titleBar != null) {
            var centerTitle = titleBar!!.centerTitle
            if (TextUtils.isEmpty(centerTitle))
                centerTitle = "Activity"
        }
    }


    override fun onStop() {
        super.onStop()
    }


    override fun onDestroy() {
        RequestManager.getInstance().cancel(cancelTag)
        super.onDestroy()
        //可以取消同一个tag的网络请求
        //Acitvity 释放子view资源
        ByActivityPageManager.unbindReferences(all_super)
        ByActivityPageManager.getInstance().removeActivity(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState)

    }

    override fun onRestart() {
        // TODO Auto-generated method stub
        super.onRestart()
    }


    /**
     * 描述：tuichu
     *
     * @param
     */
    fun isExit() {
        ByActivityPageManager.getInstance().exit(this)
    }


    fun checkMemoryCard() {
        if (Environment.MEDIA_MOUNTED != Environment
                        .getExternalStorageState()) {
            val intent = Intent(Settings.ACTION_SETTINGS)
            context!!.startActivity(intent)
        }
    }

    /***
     * 打开网络设置
     */
    fun openWirelessSet() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        context!!.startActivity(intent)
    }

    /**
     * 显示toast
     *
     * @param text
     * @author wpt
     * @update 2012-6-28 下午3:46:18
     */
    fun showToast(text: String) {
        showToastFiled(text)
    }

    override fun onBackPressed() {
        closeInput()
        super.onBackPressed()
    }

    /**
     * 关闭键盘事件
     *
     * @author wpt
     * @update 2012-7-4 下午2:34:34
     */
    fun closeInput() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && this.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!
                    .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun getContext(): Context? {
        return context
    }


    fun mString(string: Int): String {
        // TODO Auto-generated method stub
        return resources.getString(string)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig)
    }


    /**
     * 存储当前时间和服务器时间对比时间差
     *
     * @param serveTimesTamp 服务器返回时间戳
     */
    fun setTimeSync(serveTimesTamp: String) {
        if (!TextUtils.isEmpty(serveTimesTamp) || "0" != serveTimesTamp) {
            val currentDate = ByDateUtil.getCurrentDate(ByDateUtil.dateFormatYMDHMS)
            //当前时间转换为时间戳
            val currentStamp = ByDateUtil.date2TimeStamp(currentDate, ByDateUtil.dateFormatYMDHMS)
            //系统时间戳格式转换
            val parseIntCurrentStamp = ByStrUtil.parseInt(currentStamp)
            //服务起时间格式转换
            val parseDoubleServeTimesTamp = ByStrUtil.parseInt(serveTimesTamp)
            //服务器时间戳-系统时间戳=时间差
            val timeDifference = parseDoubleServeTimesTamp - parseIntCurrentStamp
            //存储时间差
            val lcSharedPreferencesHelper = BySharedPreferencesHelper(ByAppController.getInstance().context, BySharedPreferencesHelper.SHARED_PATH)
            lcSharedPreferencesHelper.putInt(BySharedPreferencesHelper.TIME_SYNC, timeDifference)
        }
    }

    /***
     * 判断网络是否连接
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     */
    fun request(what: Int, url: String, stringStringMap: Map<String, String>,
                canCancel: Boolean, isLoading: Boolean, modelClass: Class<*>) {
        if (isLoading) {
            ByDialogUtil.startDialogLoading(context, canCancel)
        }
        val spi = Kalle.post(url)
        spi.tag(cancelTag)
        val it = stringStringMap.keys.iterator()
        while (it.hasNext()) {
            val keys = it.next()
            spi.param(keys, stringStringMap[keys])
        }

        spi.perform(object : SimpleCallback<String>() {
            override fun onResponse(response: SimpleResponse<String, String>) {
                ByDialogUtil.stopDialogLoading(context)
                if (response.isSucceed) {
                    var result = ""
                    result = ByUnicodeUtils.decodeUnicode(response.succeed())
                    ByL.e("请求：url=$url\n返回值：$result")
                    onSucceedBase(what, result, modelClass)
                } else {
                    onFildBase(what, response.failed(), modelClass)
                }
            }
        })


    }


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     */
    fun requestV2(what: Int, url: String, stringStringMap: Map<String, String>,
                  canCancel: Boolean, isLoading: Boolean, modelClass: Class<*>) {
        if (isLoading) {
            ByDialogUtil.startDialogLoading(context, canCancel)
        }
        val spi = Kalle.post(url)
        spi.tag(cancelTag)
        val it = stringStringMap.keys.iterator()
        while (it.hasNext()) {
            val keys = it.next()
            spi.param(keys, stringStringMap[keys])
        }
        spi.perform(object : SimpleCallback<String>() {
            override fun onResponse(response: SimpleResponse<String, String>) {
                ByDialogUtil.stopDialogLoading(context)
                if (response.isSucceed) {
                    var result = ""
                    result = ByUnicodeUtils.decodeUnicode(response.succeed())
                    ByL.e("请求：url=$url\n返回值：$result")
                    onSucceedBase(what, result, modelClass)
                } else {
                    onFildBase(what, response.failed(), modelClass)
                }
            }
        })
    }


    /***
     * 网络返回失败，回调面对activity
     * @param what
     * @param modelClass
     */
    open fun onFailedV2(what: Int, modelClass: Any) {

    }

    /***
     * 需要把这个方法放在onSuccessBase
     * 网络返回成功，原数据
     * @param what
     * @param modelClass
     */
    open fun onSucceedV2(what: Int, modelClass: Any) {

    }

    /***
     * 需要把这个方法放在onSuccessBase
     * 网络返回成功，String数据
     * @param what
     * @param result
     * @param modelClass
     */
    open fun onSucceedV2(what: Int, result: String, modelClass: Any) {

    }


    /***
     * 网络请求成功的回调（父类调用）
     * @param what
     * @param request
     * @param modelClass
     */
    open fun onSucceedBase(what: Int, request: String, modelClass: Class<*>) {

    }

    /***
     * 网络请求失败回调（父类调用）
     * @param what
     * @param modelClass
     */
    open fun onFildBase(what: Int, fild: String, modelClass: Class<*>) {

    }


}
