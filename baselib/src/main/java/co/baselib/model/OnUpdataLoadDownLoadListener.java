package co.baselib.model;

import com.yanzhenjie.nohttp.Headers;

public interface OnUpdataLoadDownLoadListener {
    void onDownloadError(String message);

    void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount);

    void onProgress(int what, int progress, long fileCount, long speed);

    void onFinish(int what, String filePath);

    void onCancel(int what);
}
