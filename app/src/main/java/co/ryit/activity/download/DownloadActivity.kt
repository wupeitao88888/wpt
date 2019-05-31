package co.ryit.activity.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import co.ryit.R
import co.ryit.base.BaseActivity
import kotlinx.android.synthetic.main.dialog_download.view.*
import java.lang.Exception
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.download.Callback


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

    var downLoadurl = "http://img.test.ryit.co/appversion/2018/07/04/ryMJbe15306746989173500.apk";

    private fun updateDialog(baseUploadDialog: BaseUploadDialog, dialogDownload: View) {
        val perform = Kalle.Download.get(downLoadurl)
                .directory("/sdcard")
                .fileName("updata.apk")
                .onProgress { progress, byteCount, speed ->


                }
                .perform(object : Callback {
                    override fun onFinish(path: String?) {

                    }

                    override fun onCancel() {

                    }

                    override fun onException(e: Exception?) {

                    }

                    override fun onEnd() {

                    }

                    override fun onStart() {
                        isDownload = true;
                        dialogDownload.stop.text = "暂停"
                    }
                })

    }

    fun onDownLoad(view: View) {

    }




    fun onGet(view: View) {
        val hashMap = HashMap<String, Any>()
        hashMap["mockProjectId"] = "11"
        val httpsRequest = NoHttp.createStringRequest("http://58.87.124.224:8080/user", RequestMethod.GET)
        httpsRequest.add(hashMap)
        requestV2(1002, httpsRequest, false, false, String::class.java)
    }

    fun onPost(view: View) {
        val hashMap = HashMap<String, Any>()
        hashMap["mockProjectId"] = "11"
        val httpsRequest = NoHttp.createStringRequest("http://58.87.124.224:8080/user", RequestMethod.POST)
        httpsRequest.add(hashMap)
        requestV2(1003, httpsRequest, false, false, String::class.java)
    }


    fun onUpload(view: View) {

    }


    override fun onSucceedV2(what: Int, result: String?, modelClass: Any?) {
        super.onSucceedV2(what, result, modelClass)
        showToastSuccess(result)
    }

}