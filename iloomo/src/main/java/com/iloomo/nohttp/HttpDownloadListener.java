package com.iloomo.nohttp;

import android.content.Context;

import com.iloomo.paysdk.R;
import com.iloomo.utils.ToastUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by wupeitao on 2017/12/1.
 */

public class HttpDownloadListener implements DownloadListener {

    Context context;

    public HttpDownloadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {

    }

    @Override
    public void onDownloadError(int what, Exception exception) {
        Logger.e(exception);
        String message = context.getResources().getString(R.string.download_error);
        String messageContent;
        if (exception instanceof ServerError) {
            messageContent = context.getResources().getString(R.string.download_error_server);
        } else if (exception instanceof NetworkError) {
            messageContent = context.getResources().getString(R.string.download_error_network);
        } else if (exception instanceof StorageReadWriteError) {
            messageContent = context.getResources().getString(R.string.download_error_storage);
        } else if (exception instanceof StorageSpaceNotEnoughError) {
            messageContent = context.getResources().getString(R.string.download_error_space);
        } else if (exception instanceof TimeoutError) {
            messageContent = context.getResources().getString(R.string.download_error_timeout);
        } else if (exception instanceof UnKnownHostError) {
            messageContent = context.getResources().getString(R.string.download_error_un_know_host);
        } else if (exception instanceof URLError) {
            messageContent = context.getResources().getString(R.string.download_error_url);
        } else {
            messageContent = context.getResources().getString(R.string.download_error_un);
        }
        message = String.format(Locale.getDefault(), message, messageContent);
        ToastUtil.showShort(context, message);
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {

    }

    @Override
    public void onFinish(int what, String filePath) {
        Logger.d("Download finish, file path: " + filePath);

    }

    @Override
    public void onCancel(int what) {

    }

}
