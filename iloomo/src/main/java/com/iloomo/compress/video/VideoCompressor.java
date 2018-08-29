package com.iloomo.compress.video;

import android.content.Context;
import android.graphics.Bitmap;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.PFileUtils;
import com.iloomo.videodeit.ExtractVideoInfoUtil;

/**
 * Created by caizhiming on 2016/11/8.
 * 视频压缩器
 */

public class VideoCompressor {
    private Context mContext;
    private static String HW = "";

    private VideoCompressor(Context context) {
        mContext = context;
    }

    static FFmpeg ffmpeg;
    static String getOutPutFileDirPaths;

    /**
     * 视频压缩工具方法
     *
     * @param context
     * @param inputFile
     */
    public static void compress(final Context context, final String inputFile) {

        L.e("1宽高比：");

        ExtractVideoInfoUtil extractVideoInfoUtil = new ExtractVideoInfoUtil(inputFile);

        Bitmap resource = extractVideoInfoUtil.extractFrame();


        int bitmapHeight = resource.getHeight();
        int bitmapWidth = resource.getWidth();

        if (bitmapWidth < bitmapHeight) {
            double bili = BigDecimalUtil.div(bitmapHeight, bitmapWidth, 2);

            int vHeight = (int) BigDecimalUtil.mul(bili, 240);
            vHeight = ((int) (vHeight + 10) / 10) * 10;
            HW = 240 + "x" + vHeight;
        } else if (bitmapWidth > bitmapHeight) {
            double bili = BigDecimalUtil.div(bitmapHeight, bitmapWidth, 2);
            int vHeight = (int) BigDecimalUtil.mul(bili, 240);
            vHeight = ((int) (vHeight + 10) / 10) * 10;
            HW = vHeight + "x" + 240;
        } else {
            HW = 240 + "x" + 240;
        }
        getOutPutFileDirPaths = PFileUtils.getSDPathVideo() + System.currentTimeMillis() + ".mp4";
        L.e("宽高比：" + HW);
        String[] cmd = new String[]{"-y", "-i", inputFile, "-strict", "-2", "-vcodec", "libx264", "-preset", "ultrafast", "-acodec", "aac", "-ar", "44100", "-ac", "1", "-b:a", "72k", "-r", "20", "-s", HW, getOutPutFileDirPaths};


        StringBuffer sb = new StringBuffer();
        for (String s : cmd) {
            sb.append(s);
            sb.append(" ");
        }
        L.e("命令：" + sb.toString());

        compressor(context, cmd);

    }


    public static void compressor(Context context, String[] cmd) {
        ffmpeg = FFmpeg.getInstance(context);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    if (listener != null)
                        listener.onFail("压缩失败");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            L.e("加载失败————————————————");
            if (listener != null)
                listener.onFail("压缩失败");
        }
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    L.e("开始压缩");
                }

                @Override
                public void onProgress(String message) {
                    L.e("FFMPEG__" + message);
                    if (listener != null)
                        listener.onProgress(message);
                }

                @Override
                public void onFailure(String message) {
                    L.e("压缩失败：" + message);
                    if (listener != null)
                        listener.onFail(message);
                }

                @Override
                public void onSuccess(String message) {
                    L.e("压缩成功：\n" + getOutPutFileDirPaths + "\n" + message);
                    if (listener != null)
                        listener.onSuccess(getOutPutFileDirPaths);
                }

                @Override
                public void onFinish() {
                    L.e("压缩完成");
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            L.e("FFmpegCommandAlreadyRunningException：" + e.getMessage());

        }
    }


    private static VideoCompressListener listener;


    public static void setListener(VideoCompressListener listener) {
        VideoCompressor.listener = listener;
    }
}
