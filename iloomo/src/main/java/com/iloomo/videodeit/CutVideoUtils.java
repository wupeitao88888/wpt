package com.iloomo.videodeit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.iloomo.model.OnCutListener;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.PFileUtils;
import com.iloomo.utils.StrUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wupeitao on 2018/1/10.
 */

public class CutVideoUtils {


    private static CutVideoUtils instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static CutVideoUtils getInstance() {
        if (instance == null) {
            instance = new CutVideoUtils();
        }
        return instance;
    }


    MediaPlayer mediaPlayer;

    List<String> stringList = new ArrayList<>();
    FFmpeg ffmpeg;

    public void cut(Activity activity, String vurl, String startTime, String length) {
        this.activity = activity;
        stringList.clear();
        DialogUtil.startDialogLoadingText(activity, false, "正在剪辑中...");
        ffmpeg = FFmpeg.getInstance(activity);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    L.e("加载失败————————————————");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            L.e("加载失败————————————————");
        }
        initCut(activity, vurl, startTime, length);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if (onCutListener != null)
                        onCutListener.onFild();
                    break;
                case 1001:
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(getOutPutFileDirPaths);
                        mediaPlayer.prepare();
                        int vLength = mediaPlayer.getDuration() / 1000;
                        if (vLength > 30) {
                            initCut(activity, getOutPutFileDirPaths, "0", length);
                        } else {
                            DialogUtil.stopDialogLoading(activity);
                            if (onCutListener != null)
                                onCutListener.onSuccess(msg.obj.toString(), vLength);
                        }

                    } catch (Exception e) {
                        DialogUtil.stopDialogLoading(activity);
                        if (onCutListener != null)
                            onCutListener.onFild();
                    }
                    break;
            }
        }
    };


    String getOutPutFileDirPaths;
    Activity activity;
    String length;

    /***
     * 初始化剪辑工具并且设置回调
     */
    private void initCut(Activity activity, String vurl, String startTime, String length) {
        L.e("地址：" + vurl + "\n开始时间：" + startTime + "\n时长：" + length);
        this.length = length;

//        /***
//         * 保存后的路径
//         */
        getOutPutFileDirPaths = PFileUtils.getSDPathVideo() + System.currentTimeMillis() + ".mp4";


        String[] strings = new String[]{
                "-ss", startTime, "-t", length, "-accurate_seek", "-i", vurl, "-codec", "copy", "-avoid_negative_ts", "1", getOutPutFileDirPaths
        };


        cut(activity, strings);
    }


    private OnCutListener onCutListener;

    public void setOnCutListener(OnCutListener onCutListener) {
        this.onCutListener = onCutListener;
    }


    public void cut(Context context, String[] cmd) {

        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    L.e("开始裁剪");
                }

                @Override
                public void onProgress(String message) {
                    L.e("FFMPEG__" + message);
                }

                @Override
                public void onFailure(String message) {
                    L.e("裁剪失败：" + message);
                    if (onCutListener != null)
                        onCutListener.onFild();
                }

                @Override
                public void onSuccess(String message) {
                    L.e("裁剪成功：" + message);
                    Message msg = new Message();
                    msg.what = 1001;
                    msg.obj = getOutPutFileDirPaths;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFinish() {
                    L.e("裁剪完成");
                    DialogUtil.stopDialogLoading(context);
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            L.e("FFmpegCommandAlreadyRunningException：" + e.getMessage());
            if (onCutListener != null)
                onCutListener.onFild();
            DialogUtil.stopDialogLoading(context);
        }
    }

}
