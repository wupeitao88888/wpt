package co.ryit.activity.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import co.baselib.model.OnUpdataLoadDownLoadListener
import co.baselib.nohttp.DownloadCallback
import co.baselib.update.utils.BaseUploadDialog
import co.baselib.utils.AppUtil
import co.baselib.utils.L
import co.baselib.utils.PFileUtils
import co.baselib.utils.ToastUtil
import co.ryit.R
import co.ryit.base.BaseActivity
import com.yanzhenjie.nohttp.Headers
import com.yanzhenjie.nohttp.NoHttp
import com.yanzhenjie.nohttp.RequestMethod
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.dialog_download.view.*
import java.lang.Exception

class DownloadActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        setCtenterTitle("网络模块")
    }


    var isDownload: Boolean = false;

    /***
     * 下载更更新
     */
    fun onUpdateDownLoad(view: View) {
        val dialogDownload = LayoutInflater.from(context).inflate(R.layout.dialog_download, null)
        val baseUploadDialog = BaseUploadDialog(this, R.style.PgDialog, dialogDownload)
        updateDialog(baseUploadDialog, dialogDownload)
        baseUploadDialog.show()
        dialogDownload.cancel.setOnClickListener {
            if (baseUploadDialog.isShowing)
                baseUploadDialog.dismiss()
            baseUploadDialog.stopDownload()
        }
        dialogDownload.stop.setOnClickListener {
            if (isDownload) {
                baseUploadDialog.stopDownload()
                dialogDownload.stop.text = "开始"
                isDownload = false;
            } else {
                baseUploadDialog.startUpdate("updata.apk")
                dialogDownload.stop.text = "暂停"
                isDownload = true
            }
        }
    }


    private fun updateDialog(baseUploadDialog: BaseUploadDialog, dialogDownload: View) {
        dialogDownload.progressBar.max = 100;
        baseUploadDialog.setOnUpdataLoadDownLoadListener(object : OnUpdataLoadDownLoadListener {
            override fun onDownloadError(message: String?) {
                L.e("错误：$message")
            }

            override fun onStart(what: Int, isResume: Boolean, rangeSize: Long, responseHeaders: Headers?, allCount: Long) {

            }

            override fun onProgress(what: Int, progress: Int, fileCount: Long, speed: Long) {
                L.e("progress:$progress  fileCount:$fileCount  speed:$speed")
                dialogDownload.progressBar.progress = progress
            }

            override fun onFinish(what: Int, filePath: String?) {
                if (baseUploadDialog.isShowing)
                    baseUploadDialog.dismiss()
                ToastUtil.showLong(context, filePath)
            }

            override fun onCancel(what: Int) {

            }
        })
        dialogDownload.cancel.setOnClickListener {

        }
        dialogDownload.stop.setOnClickListener {

        }
        baseUploadDialog.uploadUrl("http://img.test.ryit.co/appversion/2018/07/04/ryMJbe15306746989173500.apk")
        baseUploadDialog.startUpdate("updata.apk")
        isDownload = true;
        dialogDownload.stop.text = "暂停"
    }

    var queue = -1
    var isDown: Boolean = false

    fun onDownLoad(view: View) {
        if (queue == -1) {
            queue = downLoad(1001, "http://img.test.ryit.co/appversion/2018/07/04/ryMJbe15306746989173500.apk", PFileUtils.getAppPathAPK(), "new1.apk", object : DownloadCallback(context) {
                override fun onFinish(what: Int, filePath: String?) {
                    download.text = "下载完成：$filePath"
                }

                override fun onCancel(what: Int) {

                }

                override fun onException(message: String?) {
                    download.text = "下载错误：$message"
                }

                override fun onProgress(what: Int, progress: Int, fileCount: Long, speed: Long) {
                    download.text = "暂停--下载中：$progress%"
                }

                override fun onStart(what: Int, isResume: Boolean, rangeSize: Long, responseHeaders: Headers?, allCount: Long) {

                }
            })
            isDown = true
        } else {
            if (isDown) {
                downLoadStop(queue)
                isDown = false
                download.text = "下载"
            } else {
                downLoadRestart(queue)
                isDown = true
            }
        }
    }


//

    var queue2 = -1
    var isDown2: Boolean = false

    fun onDownLoads(view: View) {
        if (queue2 == -1) {
            queue2 = downLoad(1002, "http://dl2.smartisan.cn/shuaji/other_phone/N5/20160623/smartisanos-2.6.3-2016062204-user-hammerhead.zip", PFileUtils.getAppPathAPK(), "new2.apk", object : DownloadCallback(context) {
                override fun onFinish(what: Int, filePath: String?) {
                    download2.text = "下载完成：$filePath"
                }

                override fun onCancel(what: Int) {

                }

                override fun onException(message: String?) {
                    download2.text = "下载错误：$message"
                }

                override fun onProgress(what: Int, progress: Int, fileCount: Long, speed: Long) {
                    download2.text = "暂停2--下载中：$progress%"
                }

                override fun onStart(what: Int, isResume: Boolean, rangeSize: Long, responseHeaders: Headers?, allCount: Long) {

                }
            })
            isDown2 = true
        } else {
            if (isDown2) {
                downLoadStop(queue2)
                isDown2 = false
                download2.text = "下载2"
            } else {
                downLoadRestart(queue2)
                isDown2 = true
            }
        }
    }


    fun onGet(view: View) {
        val httpsRequest = NoHttp.createStringRequest("http://www.baidu.com", RequestMethod.GET)
        requestV2(1002, httpsRequest, false, false, String::class.java)
    }

    fun onPost(view: View) {
        val httpsRequest = NoHttp.createStringRequest("", RequestMethod.POST)
        val hashMap = HashMap<String, Any>()
        httpsRequest.add(hashMap)
        requestV2(1003, httpsRequest, false, false, String::class.java)
    }


    fun onUpload(view: View) {

    }


}