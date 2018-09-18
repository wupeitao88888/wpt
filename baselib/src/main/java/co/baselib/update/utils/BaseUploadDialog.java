package co.baselib.update.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import co.baselib.model.OnUpdataLoadDownLoadListener;
import co.baselib.nohttp.DownloadCallback;
import co.baselib.utils.L;
import co.baselib.utils.PFileUtils;


/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期:	2015-4-21
 * 编写人员:	 吴培涛
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BaseUploadDialog extends Dialog {
    Context context;
    private View view;
    public boolean isback = false;
    private String uploadUrl;
    DownloadQueue queue;

    public BaseUploadDialog(Context context) {
        super(context);
    }

    public BaseUploadDialog(Context context, View view) {
        super(context); // TODO Auto-generated constructor stub
        this.context = context;
        this.view = view;
        queue = NoHttp.newDownloadQueue();
    }

    public BaseUploadDialog(Context context, int theme, View view) {
        super(context, theme);
        this.context = context;
        this.view = view;
        queue = NoHttp.newDownloadQueue();
    }

    private int animations = -1;
    private int gravity = -1;

    public BaseUploadDialog(Context context, int theme, View view, int animations, int gravity) {
        super(context, theme);// TODO Auto-generated constructor stub
        this.context = context;
        this.view = view;
        this.animations = animations;
        this.gravity = gravity;
        queue = NoHttp.newDownloadQueue();
    }

    public BaseUploadDialog(Context context, int theme, View view, int animations) {
        super(context, theme); // TODO Auto-generated constructor stub
        this.context = context;
        this.view = view;
        this.animations = animations;
        queue = NoHttp.newDownloadQueue();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO Auto-generated

        super.onCreate(savedInstanceState);
        this.setContentView(view);
        if (isback) {
            this.setCancelable(false);// 设置点击屏幕Dialog不消失
        }

        if (animations != -1) {
            Window window = getWindow();
            if (gravity != -1) {
                window.setGravity(gravity); // 此处可以设置dialog显示的位置
            } else {
                window.setGravity(Gravity.CENTER); // 默认居中
            }
            window.setWindowAnimations(animations); // 添加动画
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                stopDownload();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isback) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    /***
     * 是否能返回
     * @param isback
     */
    public void setIsback(boolean isback) {
        this.isback = isback;
    }


    DownloadRequest request;

    /***
     * 设置下载地址
     */
    public void uploadUrl(String url) {
        this.uploadUrl = url;
    }

    private final int HTTPWHAT = 10;

    /***
     * 开始下载
     */
    public void startUpdate(String filename) {
        if (request == null)
            request = new DownloadRequest(uploadUrl, RequestMethod.GET, PFileUtils.getAppPath(), filename, true, true);

        queue.add(HTTPWHAT, request, new DownloadCallback(context) {
            @Override
            public void onException(String message) {
                if (onUpdataLoadDownLoadListener != null)
                    onUpdataLoadDownLoadListener.onDownloadError(message);
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                if (onUpdataLoadDownLoadListener != null)
                    onUpdataLoadDownLoadListener.onStart(what, isResume, rangeSize, responseHeaders, allCount);
            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {
                L.e("下载进度："+progress);
                if (onUpdataLoadDownLoadListener != null)
                    onUpdataLoadDownLoadListener.onProgress(what, progress, fileCount, speed);
            }

            @Override
            public void onFinish(int what, String filePath) {
                if (onUpdataLoadDownLoadListener != null)
                    onUpdataLoadDownLoadListener.onFinish(what, filePath);
            }

            @Override
            public void onCancel(int what) {
                if (onUpdataLoadDownLoadListener != null)
                    onUpdataLoadDownLoadListener.onCancel(what);
            }
        });
    }


    // 暂停或者取消一个下载。
    public void stopDownload() {
        if (request != null)
            request.cancel();
    }

    private OnUpdataLoadDownLoadListener onUpdataLoadDownLoadListener;

    public void setOnUpdataLoadDownLoadListener(OnUpdataLoadDownLoadListener onUpdataLoadDownLoadListener) {
        this.onUpdataLoadDownLoadListener = onUpdataLoadDownLoadListener;
    }

}

