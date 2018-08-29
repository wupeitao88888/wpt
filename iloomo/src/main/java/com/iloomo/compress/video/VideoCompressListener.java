package com.iloomo.compress.video;

/**
 * Created by caizhiming on 2016/11/8.
 */

public interface VideoCompressListener {
    public void onSuccess(String outputFile);
    public void onFail(String reason);
    public void onProgress(String progress);
}
