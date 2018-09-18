package co.baselib.bean;

import com.yanzhenjie.nohttp.download.DownloadRequest;

import co.baselib.nohttp.DownloadCallback;

public class DownLoad {
    private DownloadRequest mRequest;
    private int dwhat;
    private DownloadCallback ddownloadCallback;

    public DownloadRequest getmRequest() {
        return mRequest;
    }

    public void setmRequest(DownloadRequest mRequest) {
        this.mRequest = mRequest;
    }

    public int getDwhat() {
        return dwhat;
    }

    public void setDwhat(int dwhat) {
        this.dwhat = dwhat;
    }

    public DownloadCallback getDdownloadCallback() {
        return ddownloadCallback;
    }

    public void setDdownloadCallback(DownloadCallback ddownloadCallback) {
        this.ddownloadCallback = ddownloadCallback;
    }
}
