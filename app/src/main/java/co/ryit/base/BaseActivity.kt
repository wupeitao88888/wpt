package co.ryit.base

import android.text.TextUtils
import co.baselib.R
import co.baselib.base.ActivitySupport
import co.baselib.nohttp.HttpV2ResponseListener
import co.ryit.global.HttpTaskID
import co.baselib.utils.L
import co.ryit.bean.BaseModel
import com.yanzhenjie.nohttp.error.*
import com.yanzhenjie.nohttp.rest.Response

open class BaseActivity : ActivitySupport() {


    override fun onSucceedBase(what: Int, response: Response<*>?, request: String?, modelClass: Class<*>?) {
        super.onSucceedBase(what, response, request, modelClass)
        onSucceedV2(what, modelClass)
        onSucceedV2(what, request, modelClass)
    }


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

}