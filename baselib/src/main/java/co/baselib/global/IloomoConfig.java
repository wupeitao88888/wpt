package co.baselib.global;

import android.content.Context;

import co.baselib.bean.IloomoSDKModel;
import co.baselib.bean.ResInit;

/**
 * Created by wupeitao on 2018/2/22.
 */

public class IloomoConfig {
    public static IloomoConfig iloomoConfig;

    public static IloomoConfig init(Context context) {
        if (iloomoConfig == null) {
            iloomoConfig = new IloomoConfig(context);
        }
        return iloomoConfig;
    }

    public final static int PHOTOS = 1230000;
    public int style;
    public Context context;

    public boolean isToastActivity = false;

    public IloomoConfig(Context context) {
        this.context = context;
    }

    /**
     * 设置SDK主题
     *
     * @param style
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /***
     * 设置配置文件
     * @return
     */
    public IloomoSDKModel getIloomoSDKModel() {
        return new IloomoSDKModel();
    }

    /***
     *设置是否在activity中显示,自定义toast,或者是系统的toast
     */
    public void setActivityToast(boolean isToastActivity) {
        this.isToastActivity = isToastActivity;
    }

    /***
     * 获取是否显示系统的Toast
     * @return
     */
    public boolean getActivityToast() {
        return isToastActivity;
    }

//
//    /**
//     * 跳转到播放本地视频的页面
//     *
//     * @param activity
//     * @param path     路径
//     */
//    public void showPlayVideo(Activity activity, String path) {
//        Intent intent = new Intent(activity, PictureVideoPlayActivity.class);
//        intent.putExtra("video_path", path);
//        activity.startActivity(intent);
//    }
//
//    /**
//     * 跳转到播放本地视频的页面
//     *
//     * @param context
//     * @param path    路径
//     */
//    public void showPlayVideo(Context context, String path) {
//        Intent intent = new Intent(context, PictureVideoPlayActivity.class);
//        intent.putExtra("video_path", path);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    public static final int SHOW_PREVIEW_PLAY_VIEW = 3000;
//    public static final int SHOW_PREVIEW_PLAY_VIEW_RESULTCODE = 3001;
//    public final static String VIDEO_PATH = "video_path";
//
//    /***
//     * 跳转到预览本地视频的页面
//     * @param activity
//     * @param path  路径
//     */
//    public void showPreViewPlayView(Activity activity, String path) {
//        Intent intent = new Intent(activity, PictureVideoPlayPreviewActivity.class);
//        intent.putExtra("video_path", path);
//        activity.startActivityForResult(intent, SHOW_PREVIEW_PLAY_VIEW);
//    }
//
//    /***
//     * 预览图片
//     * @param position 从第几个开始显示
//     * @param listimages 数据
//     */
//    public void showImagePreView(int position, List<String> listimages) {
//        Intent intent = new Intent(context, ShowImageActivity.class);
//        List<String> showlist = new ArrayList<String>();
//        showlist.addAll(listimages);
//        showlist.remove(showlist.size() - 1);
//        intent.putStringArrayListExtra("photos", (ArrayList<String>) showlist);
//        intent.putExtra("index", position);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    /***
//     * 预览图片
//     * @param position 从第几个开始显示
//     * @param listimages 数据
//     */
//    public void showImagePreViewforResult(Activity activity, int position, List<String> listimages, int requestCode) {
//        Intent intent = new Intent(context, ShowImageActivity.class);
//        intent.putStringArrayListExtra("photos", (ArrayList<String>) listimages);
//        intent.putExtra("index", position);
//        intent.putExtra("isOver", true);
//        activity.startActivityForResult(intent, requestCode);
//    }
//
//    /***
//     * 预览图片
//     * @param position 从第几个开始显示
//     * @param listimages 数据
//     */
//    public void showImagePreViewDeleteforResult(Activity activity, int position, List<String> listimages) {
//        Intent intent = new Intent(context, ShowImageActivity.class);
//        intent.putStringArrayListExtra("photos", (ArrayList<String>) listimages);
//        intent.putExtra("index", position);
//        intent.putExtra("isdelete", true);
//        activity.startActivity(intent);
//    }
//
//
//    /****
//     * 编辑视频
//     * @param activity
//     * @param path 路径
//     */
//    public void editVideo(Activity activity, String path) {
//        Intent intent = new Intent(activity, VideoEditActivity.class);
//        intent.putExtra("url", path);
//        activity.startActivity(intent);
//    }
//
//
//    /***
//     * 下载方法
//     * @param downloadurl
//     * @param path
//     * @param httpDownloadListener
//     */
//    public void downLoadFile(String downloadurl, String path, Callback.ProgressCallback<File> httpDownloadListener) {
//        xTools.DownLoadFile(downloadurl, path, httpDownloadListener);
//    }


    private ResInit resInit;

    public ResInit getResInit() {
        return resInit;
    }

    public void setResInit(ResInit resInit) {
        this.resInit = resInit;
    }
}
