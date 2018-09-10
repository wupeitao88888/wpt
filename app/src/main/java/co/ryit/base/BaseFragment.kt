package co.ryit.base

import android.text.TextUtils
import co.baselib.R
import co.baselib.base.FragmentSupport
import co.baselib.nohttp.HttpV2ResponseListener
import co.ryit.global.HttpTaskID
import co.baselib.utils.L
import co.ryit.bean.BaseModel
import com.yanzhenjie.nohttp.error.*
import com.yanzhenjie.nohttp.rest.Response

open class BaseFragment : FragmentSupport() {


    override fun onFildBase(what: Int, response: Response<*>?, modelClass: Class<*>?) {
        super.onFildBase(what, response, modelClass)
        val exception = response!!.getException()
        val baseModel = BaseModel()

        if (exception is NetworkError) {// 网络不好
            baseModel.errorMessage = getResString(R.string.error_please_check_network)
            baseModel.errorCode = "404"
        } else if (exception is TimeoutError) {// 请求超时
            baseModel.errorMessage = getResString(R.string.error_timeout)
            baseModel.errorCode = "404"
        } else if (exception is UnKnownHostError) {// 找不到服务器
            baseModel.errorMessage = getResString(R.string.error_not_found_server)
            baseModel.errorCode = "404"
        } else if (exception is URLError) {// URL是错的
            baseModel.errorMessage = getResString(R.string.error_url_error)
            baseModel.errorCode = "404"
        } else if (exception is NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
            baseModel.errorMessage = getResString(R.string.error_unknow)
            baseModel.errorCode = "304"
        }
        showToastBig(baseModel.errorMessage)
        onFailedV2(what, baseModel)
    }

    override fun onSucceedBase(what: Int, response: Response<*>?, request: String?, modelClass: Class<*>?) {
        super.onSucceedBase(what, response, request, modelClass)
        try {
            val bmodel = gson.fromJson(request, BaseModel::class.java)
            setTimeSync(bmodel.timestamp)//存储当前时间和服务器时间对比时间差
            if ("0" == bmodel.errorCode) {
                val obj = gson.fromJson(request, modelClass)
                onSucceedV2(what, obj)
                onSucceedV2(what, request, obj)
            } else if ("20004" == bmodel.getErrorCode()) {
                if (HttpV2ResponseListener.onAgainLoginListener != null) {
                    HttpV2ResponseListener.onAgainLoginListener.onOutLogin(bmodel)
                }
                bmodel.setErrorMessage("")
                onFailedV2(what, bmodel)
            } else if ("20010" == bmodel.getErrorCode()) {
                if (HttpV2ResponseListener.onAgainLoginListener != null) {
                    HttpV2ResponseListener.onAgainLoginListener.onOutLogin(bmodel)
                }
                bmodel.setErrorMessage("")
                onFailedV2(what, bmodel)
            } else if ("10010" == bmodel.getErrorCode()) {
                if (HttpV2ResponseListener.onAgainLoginListener != null) {
                    HttpV2ResponseListener.onAgainLoginListener.onOutLogin(bmodel)
                }
                bmodel.setErrorMessage("")
                onFailedV2(what, bmodel)
            } else if ("5000" == bmodel.getErrorCode()) {
                if (HttpV2ResponseListener.onAgainLoginListener != null) {
                    HttpV2ResponseListener.onAgainLoginListener.onOutLogin(bmodel)
                }
                bmodel.setErrorMessage("")
                onFailedV2(what, bmodel)
            } else if ("20018" == bmodel.getErrorCode()) {
                onFailedV2(what, bmodel)
            } else {
                if (what != HttpTaskID.LOGIN) {
                    showToastBig(bmodel.getErrorMessage())
                }
                onFailedV2(what, bmodel)
            }

        } catch (e: Exception) {
            L.e("异常：" + e.message)
            if (TextUtils.isEmpty(e.message)) {
                val baseModel = BaseModel()
                baseModel.setErrorCode("699")
                baseModel.setErrorMessage("异常信息为空！")
                showToastBig(baseModel.getErrorMessage())
                onFailedV2(what, baseModel)
            } else if (e.message == "You cannot start a load for a destroyed activity") {

            } else {
                val baseModel = BaseModel()
                baseModel.setErrorCode("698")
                baseModel.setErrorMessage("数据解析异常！")
                showToastBig(baseModel.getErrorMessage())
                onFailedV2(what, baseModel)
            }
        }

    }
}