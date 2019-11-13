package co.baselib.base


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout

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
import co.baselib.utils.ByDialogManger
import co.baselib.utils.ByDialogUtil
import co.baselib.utils.ByL
import co.baselib.utils.BySharedPreferencesHelper
import co.baselib.utils.ByStrUtil
import co.baselib.utils.ByToastUtil
import co.baselib.utils.ByUnicodeUtils


/**
 * Created by wupeitao on 16/1/7.
 */
open class ByFragmentSupport : Fragment() {
    open var title: String? = null

   open var rootView: View? = null// 缓存Fragment view
   open internal var all_super: View? = null
    open internal var content_layout: LinearLayout? = null

    open var sharedPreferencesHelper: BySharedPreferencesHelper? = null

   open internal var layout_error: RelativeLayout? = null
   open var gson: Gson? = null

   open internal var layout_parent: RelativeLayout? = null

    /**
     * 用来标记取消。
     */
    var cancelTag = Any()

    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    open fun noData(blean: Boolean, onClickListener: View.OnClickListener) {
        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        layout_parent!!.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        ByActivityErrorUtils.getInstance().NoDate(blean, onClickListener)

    }

    open fun noData(viewGroup: ViewGroup, blean: Boolean) {

        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        viewGroup.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(viewGroup, context)

        ByActivityErrorUtils.getInstance().NoDate(blean) { }

    }

    open fun noData(viewGroup: ViewGroup, blean: Boolean, onClickListener: View.OnClickListener) {
        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        viewGroup.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(viewGroup, context)
        ByActivityErrorUtils.getInstance().NoDate(blean, onClickListener)

    }

    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    open fun netError(blean: Boolean, onClickListener: View.OnClickListener) {
        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        layout_parent!!.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(layout_parent, context)
        ByActivityErrorUtils.getInstance().netError(blean, onClickListener)
    }

    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    open fun netError(viewGroup: ViewGroup, blean: Boolean) {
        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        viewGroup.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(viewGroup, context)

        ByActivityErrorUtils.getInstance().netError(blean) { }
    }

    open fun netError(viewGroup: ViewGroup, blean: Boolean, onClickListener: View.OnClickListener) {
        layout_error = LayoutInflater.from(context).inflate(
                R.layout.by_layout_error, null) as RelativeLayout
        viewGroup.addView(layout_error)
        ByActivityErrorUtils.getInstance().initNetStatus(viewGroup, context)
        ByActivityErrorUtils.getInstance().netError(blean, onClickListener)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        sharedPreferencesHelper = BySharedPreferencesHelper(context,
                BySharedPreferencesHelper.SHARED_PATH)
        if (rootView == null) {
            viewInit()
            all_super = LayoutInflater.from(context).inflate(
                    R.layout.by_layout_fragmentsuper, null)

            content_layout = all_super!!.findViewById<View>(R.id.content_layout) as LinearLayout
            layout_parent = all_super!!.findViewById<View>(R.id.content_layout_parent) as RelativeLayout
            // 自己的布局
            val customContentView = initView()
            content_layout!!.addView(customContentView)
            rootView = all_super
        }

        return setTitleBar(rootView!!)
    }

    open fun viewInit() {
        gson = Gson()
    }


    open  fun findViewById(view: View, id: Int): View {
        return view.findViewById(id)
    }




    override fun onResume() {
        super.onResume()
        /**
         * 友盟统计
         */
        if (title != null) {
            if (TextUtils.isEmpty(title))
                title = "Fragment"
        }
    }


    override fun onPause() {
        super.onPause()
        /**
         * 友盟统计
         */
        if (title != null) {
            if (TextUtils.isEmpty(title))
                title = "Fragment"
        }

    }

    override fun onDestroy() {
        RequestManager.getInstance().cancel(cancelTag)
        ByDialogManger.getInstance().cancelsDialogs()
        super.onDestroy()
        //Acitvity 释放子view资源
        ByActivityPageManager.unbindReferences(all_super)
        all_super = null
        ByActivityPageManager.unbindReferences(rootView)
        rootView = null
    }


    open fun setContent(layout: Int) {
        this.rootView = LayoutInflater.from(context).inflate(layout, null)
    }

    open  fun initView(): View? {
        return rootView
    }

    open fun setTitleBar(view: View): View {
        return view
    }

    open fun mString(string: Int): String {
        // TODO Auto-generated method stub
        return resources.getString(string)
    }

    /**
     * 设置titleb中间
     */
    @Deprecated("")
    protected fun setRemoveTitle() {

    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    @Deprecated("")
    protected fun setCtenterTitle(title: String) {

    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    @Deprecated("")
    protected fun setCtenterTitle(title: Int) {

    }


    /**
     * 设置右边标题内容
     */
    @Deprecated("")
    protected fun setRightTitle(msg: String) {

    }

    /**
     * 设置右边标题内容字体颜色
     */
    @Deprecated("")
    protected fun setRightTitle(msg: Int) {
    }

    /**
     * 设置右边图片内容
     */
    @Deprecated("")
    protected fun setRightTitleRes(msg: Int) {

    }

    /**
     * 设置右边标题内容
     */
    @Deprecated("")
    protected fun setRightTitleListener(l: View.OnClickListener) {

    }

    /**
     * 设置右边图片内容
     */
    @Deprecated("")
    protected fun setRightTitleImageListener(l: View.OnClickListener) {
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    /***
     * 显示提示语
     *
     * 替代方法   showToastSuccess  和  showToastFiled
     * @param msg
     */
    open fun showToastBig(msg: String) {
        ByToastUtil.showShort(context, msg)
    }


    /***
     * 显示成功提示语
     * @param msg
     */
    open fun showToastSuccess(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示失败提示语
     * @param msg
     */
    open fun showToastFiled(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示成功提示语,并且关闭Activity
     * @param msg
     */
    open fun showToastSuccessFinish(msg: String) {
        ByToastUtil.showShort(context, msg)
    }


    /***
     * 显示失败提示语,并且关闭Activity
     * @param msg
     */
    open fun showToastFiledFinish(msg: String) {
        ByToastUtil.showShort(context, msg)
    }

    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    open fun showToastFinish(msg: String, blean: Boolean, isSuccess: Boolean) {

    }

    /***
     * 自定义Toast才会回调
     */
    open fun toastFinish() {

    }

    /***
     * 自定义Toast才会回调
     */
    open fun toastOver() {

    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     */
    open fun request(what: Int, url: String, stringStringMap: Map<String, String>,
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
    open fun requestV2(what: Int, url: String, stringStringMap: Map<String, String>,
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
     * 网络返回错误原因
     * @param what
     * @param modelClass
     */
    open fun onFailedV2(what: Int, modelClass: Any) {

    }

    /***
     * 网络返回成功对象
     * @param what
     * @param modelClass
     */
    open fun onSucceedV2(what: Int, modelClass: Any) {

    }

    /***
     * 网络返回成功对象+原数据
     * @param what
     * @param result
     * @param modelClass
     */
    open fun onSucceedV2(what: Int, result: String, modelClass: Any) {

    }

    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    open fun getResString(resId: Int): String {
        return resources.getText(resId).toString()
    }

    /***
     * 判断网络是否连接
     * @param context
     * @return
     */
    open fun isNetworkConnected(context: Context?): Boolean {
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
