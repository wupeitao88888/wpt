package co.baselib.update.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.baselib.BuildConfig;
import co.baselib.model.MyOnCliclListener;
import co.baselib.update.model.UpdateEntity;
import co.baselib.utils.AppUtil;
import co.baselib.utils.L;
import co.baselib.utils.LCSharedPreferencesHelper;
import co.baselib.utils.PFileUtils;

import static android.os.Build.VERSION_CODES.M;


/**
 * Created by cretin on 2017/3/13.
 */

public class CretinAutoUpdateUtils {

    private static CretinAutoUpdateUtils cretinAutoUpdateUtils;

    //定义一个展示下载进度的进度条
    private static ProgressDialog progressDialog;

    private static Context mContext;

    //检查更新的url
    private static String checkUrl;

    //展示下载进度的方式 对话框模式 通知栏进度条模式
    private static int showType = Builder.TYPE_DIALOG;
    //是否展示忽略此版本的选项 默认开启
    private static boolean canIgnoreThisVersion = true;
    //app图标
    private static int iconRes;
    //appName
    private static String appName;
    //是否开启日志输出
    private static boolean showLog = true;

    //设置请求方式
    private static int requestMethod = Builder.METHOD_POST;

    //私有化构造方法
    private CretinAutoUpdateUtils() {
        lcSharedPreferencesHelper = new LCSharedPreferencesHelper(mContext, LCSharedPreferencesHelper.ILOOMO);
    }

//    /**
//     * 检查更新
//     */
//    public void check() {
//        if (TextUtils.isEmpty(checkUrl)) {
//            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
//        } else {
//            getCheckUpdate("1");
//        }
//    }
//
//    /**
//     * 检查更新——热更新
//     */
//    public void checkHot() {
//        if (TextUtils.isEmpty(checkUrl)) {
//            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
//        } else {
//            getCheckUpdate("2");
//        }
//    }

    /**
     * 初始化url
     *
     * @param url
     */
    public static void init(String url) {
        checkUrl = url;
    }

    /**
     * 初始化url
     *
     * @param builder
     */
    public static void init(Builder builder) {
        checkUrl = builder.baseUrl;
        showType = builder.showType;
        canIgnoreThisVersion = builder.canIgnoreThisVersion;
        iconRes = builder.iconRes;
        showLog = builder.showLog;
        requestMethod = builder.requestMethod;

    }

    LCSharedPreferencesHelper lcSharedPreferencesHelper;

    /**
     * getInstance()
     *
     * @param context
     * @return
     */
    public static CretinAutoUpdateUtils getInstance(Context context) {
        CretinAutoUpdateUtils.mContext = context;
        requestPermission(null);
        if (cretinAutoUpdateUtils == null) {
            cretinAutoUpdateUtils = new CretinAutoUpdateUtils();
        }
        return cretinAutoUpdateUtils;
    }


    /**
     * 取消广播的注册
     */
    public void destroy() {
        //不要忘了这一步

    }

    private boolean IS_CHECK = false;

    public boolean isCheck() {
        return IS_CHECK;
    }

    public void setCheck(boolean check) {
        IS_CHECK = check;
    }

    ;

    public void getCheckUpdate(UpdateEntity dataEntity) {
        IS_CHECK = true;
        /**
         * 判断更新方式
         */
        if ("1".equals(dataEntity.getType())) {
            if (dataEntity.isForceUpdate == 2) {
                //所有旧版本强制更新
                showUpdateDialog(dataEntity, true, false);
            } else if (dataEntity.isForceUpdate == 1) {
                //hasAffectCodes提及的版本强制更新
                if (dataEntity.versionCode > getVersionCode(mContext)) {
                    //有更新
                    String[] hasAffectCodes = dataEntity.hasAffectCodes.split("\\|");
                    if (Arrays.asList(hasAffectCodes).contains(getVersionCode(mContext) + "")) {
                        //被列入强制更新 不可忽略此版本
                        showUpdateDialog(dataEntity, true, false);
                    } else {
                        String dataVersion = dataEntity.versionName;
                        if (!TextUtils.isEmpty(dataVersion)) {
                            List listCodes = loadArray();
                            if (!listCodes.contains(dataVersion)) {
                                //没有设置为已忽略
                                showUpdateDialog(dataEntity, false, true);
                            } else {
                                IS_CHECK = false;
                            }
                        } else {
                            IS_CHECK = false;
                        }
                    }
                } else {
                    IS_CHECK = false;
                }
            } else if (dataEntity.isForceUpdate == 0) {
                L.e(dataEntity.versionCode + "++++++++++++++++" + getVersionCode(mContext));
                if (dataEntity.versionCode > getVersionCode(mContext)) {
                    showUpdateDialog(dataEntity, false, true);
                } else {
                    IS_CHECK = false;
                }
            }
        } else {

        }

    }


    public void getCheckUpdateHot(UpdateEntity dataEntity) {
        /**
         * 判断更新方式
         */
        if ("1".equals(dataEntity.getType())) {

        } else {
            //检测当前网络状态
//                    if (AppUtil.getCurrentNetType(mContext).equals("wifi")) {
            if (!getVersionName(mContext).equals(dataEntity.getVersionName())) {
                L.e("补丁版本不符合" + getVersionName(mContext) + "——————" + dataEntity.getVersionName());
                return;
            }
            if (dataEntity.getVersionCode() > lcSharedPreferencesHelper.getIntValue(lcSharedPreferencesHelper.DOWNLOAD_VERSION)) {
                L.e("准备下载补丁");
                updateLode(dataEntity);
            }
        }

    }


    private void updateLode(UpdateEntity data) {

        if (!TextUtils.isEmpty(data.downurl)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    String fileName = "";
                    fileName = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + data.getVersionCode() + "-vbug" + data.getVersionName() + ".apk";


                    L.e("UPDATE:APK___:" + fileName);
                    final File file = new File(fileName);
                    lcSharedPreferencesHelper.putInt(lcSharedPreferencesHelper.DOWNLOAD_VERSION, data.getVersionCode());
                    //如果不存在
                    if (!file.exists()) {

                        String fileNameTmp = "";

                        fileNameTmp = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + data.getVersionCode() + "-vbug" + data.getVersionName() + ".apk";

                        final File fileTmp = new File(fileNameTmp);

                        createFileAndDownloadHot(fileTmp, data.downurl);


                    } else {
                        L.e("——————————————————————————————文件已存在————————————————————————————");
                        if (onSuccessListener != null)
                            onSuccessListener.onSuccess(file.getAbsoluteFile().getAbsolutePath());
                    }
                } catch (Exception e) {
                    MobclickAgent.reportError(mContext, "自定义异常：IO异常热更新");
                }
            } else {
                MobclickAgent.reportError(mContext, "自定义异常：热更新：挂载SD卡异常");
            }
        } else

        {
            MobclickAgent.reportError(mContext, "自定义异常：下载路径为空");
        }
    }


    /**
     * 显示更新对话框
     *
     * @param data
     */
    private void showUpdateDialog(final UpdateEntity data, final boolean isForceUpdate, boolean showIgnore) {

        UpdateDialogUtils.getInstaces(mContext).showDialogUpdate(data.getVersionName(), data.getUpdateLog(), new MyOnCliclListener() {
            @Override
            public void onClick(View view) {
                if (isForceUpdate) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                IS_CHECK = false;
            }
        }, new MyOnCliclListener() {
            @Override
            public void onClick(View view) {
                IS_CHECK = false;
                startUpdate(data);
            }
        }, !isForceUpdate);
    }

    private static int PERMISSON_REQUEST_CODE = 2;

    @TargetApi(M)
    private static void requestPermission(final UpdateEntity data) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(mContext)
                        .setMessage("申请存储权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //读写权限
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
            }
        } else {
            if (data != null) {
                if (!TextUtils.isEmpty(data.downurl)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        try {
                            String fileName = "";
                            if (!TextUtils.isEmpty(data.getVersionName())) {
                                fileName = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + getPackgeName(mContext) + "-v" + data.getVersionName() + ".apk";
                            } else {
                                fileName = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + getPackgeName(mContext) + "-v" + getVersionName(mContext) + ".apk";
                            }

                            L.e("UPDATE:APK___:" + fileName);
                            final File file = new File(fileName);
                            //如果不存在
                            if (!file.exists()) {

                                String fileNameTmp = "";

                                if (!TextUtils.isEmpty(data.getVersionName())) {
                                    fileNameTmp = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + getPackgeName(mContext) + "-v" + data.getVersionName() + ".apk.tmp";
                                } else {
                                    fileNameTmp = PFileUtils.getAppPath() + BuildConfig.folderapk + "/" + getPackgeName(mContext) + "-v" + getVersionName(mContext) + ".apk.tmp";
                                }
                                final File fileTmp = new File(fileNameTmp);

                                //检测当前网络状态
                                if (!AppUtil.getCurrentNetType(mContext).equals("wifi")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setTitle("提示");
                                    builder.setMessage("当前处于非WIFI连接，是否继续？");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            createFileAndDownload(fileTmp, data.downurl);
                                        }
                                    });
                                    builder.setNegativeButton("取消", null);
                                    builder.show();
                                } else {
                                    createFileAndDownload(fileTmp, data.downurl);
                                }
                            } else {
                                installApkFile(mContext, file);
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, "下载失败！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "没有挂载的SD卡", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "下载路径为空", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //创建文件并下载文件
    private void createFileAndDownloadHot(File file, String downurl) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
//            L.e("下载开始:"+downurl);
//            xTools.DownLoadFile(downurl, file.getAbsolutePath(), new Callback.ProgressCallback<File>() {
//                @Override
//                public void onSuccess(File result) {
//                    L.e("下载成功：downPATH:" + result.getAbsoluteFile());
//                    if (onSuccessListener != null)
//                        onSuccessListener.onSuccess(result.getAbsoluteFile().getAbsolutePath());
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    L.e("下载失败");
//                    lcSharedPreferencesHelper.putInt(lcSharedPreferencesHelper.DOWNLOAD_VERSION, 0);
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//
//                @Override
//                public void onWaiting() {
//
//                }
//
//                @Override
//                public void onStarted() {
//                    L.e("下载开始");
//                }
//
//                @Override
//                public void onLoading(long total, long current, boolean isDownloading) {
//                    L.e("下载成功：downPATH:" + total + "current：" + current);
//                }
//            });


        } catch (Exception e) {
            L.e("...");
        }
    }

    private OnSuccessListener onSuccessListener;

    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }

    public interface OnSuccessListener {
        void onSuccess(String url);
    }

    //创建文件并下载文件
    private static void createFileAndDownload(File file, String downurl) {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(downurl);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        mContext.startActivity(intent);
        return;

//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        try {
//
//            //显示dialog
//            if (showType == Builder.TYPE_DIALOG) {
//                progressDialog = new ProgressDialog(mContext);
//                if (iconRes != 0) {
//                    progressDialog.setIcon(iconRes);
//                } else {
//                    progressDialog.setIcon(R.drawable.ic_launcher);
//                }
//                progressDialog.setTitle("正在更新...");
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
//                //进度最大值
//                progressDialog.setMax(100);
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//            }
//
////            String path = file.getAbsolutePath();
//
//
//            IloomoConfig.init(mContext).netGetFileDownLoadFile(TaskID.DOWNLOAD_FILE_HOT, downurl, file.getParent(), new HttpDownloadListener(mContext) {
//                @Override
//                public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
//                    super.onStart(what, isResume, beforeLength, headers, allCount);
//                }
//
//                @Override
//                public void onDownloadError(int what, Exception exception) {
//                    super.onDownloadError(what, exception);
//                    if (progressDialog.isShowing())
//                        progressDialog.cancel();
//                    ToastUtil.showShort(mContext, "下载失败！");
//                }
//
//                @Override
//                public void onProgress(int what, int progress, long fileCount, long speed) {
//                    super.onProgress(what, progress, fileCount, speed);
////                    double t = total;
////                    double c = current;
//                    progressDialog.setProgress(progress);
//                }
//
//                @Override
//                public void onFinish(int what, String filePath) {
//                    super.onFinish(what, filePath);
//                    L.e("下载成功：downPATH:" + filePath);
//                    if (progressDialog.isShowing())
//                        progressDialog.cancel();
//                    L.e("下载成功：downPATH:" + filePath);
//                    installApkFile(mContext, new File(filePath));
//
//                }
//
//                @Override
//                public void onCancel(int what) {
//                    super.onCancel(what);
//                }
//            });
////            xTools.DownLoadFile(downurl, path, new Callback.ProgressCallback<File>() {
////                @Override
////                public void onSuccess(File result) {
////                    if (progressDialog.isShowing())
////                        progressDialog.cancel();
////                    L.e("下载成功：downPATH:" + result.getAbsoluteFile());
////                    installApkFile(mContext, result.getAbsoluteFile());
////                }
////
////                @Override
////                public void onError(Throwable ex, boolean isOnCallback) {
////                    if (progressDialog.isShowing())
////                        progressDialog.cancel();
////                    ToastUtil.showShort(mContext, "下载失败！");
////                }
////
////                @Override
////                public void onCancelled(CancelledException cex) {
////
////                }
////
////                @Override
////                public void onFinished() {
////
////                }
////
////                @Override
////                public void onWaiting() {
////
////                }
////
////                @Override
////                public void onStarted() {
////
////                }
////
////                @Override
////                public void onLoading(long total, long current, boolean isDownloading) {
////                    double t = total;
////                    double c = current;
////                    progressDialog.setProgress((int) (100 * (c / t)));
////                }
////            });
//
//
//        } catch (Exception e) {
//            L.e("...");
//        }
    }

    /**
     * 开始更新操作
     */
    public void startUpdate(UpdateEntity data) {
        requestPermission(data);
    }


    /**
     * 安装app
     *
     * @param context
     * @param file
     */
    public static void installApkFile(Context context, File file) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent1.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent1, 0).size() > 0) {
            context.startActivity(intent1);
        }
    }

    /**
     * 获得apkPackgeName
     *
     * @param context
     * @return
     */
    public static String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    private static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    //建造者模式
    public static final class Builder {
        private String baseUrl;
        private int showType = TYPE_DIALOG;
        //是否显示忽略此版本 true 是 false 否
        private boolean canIgnoreThisVersion = true;
        //在通知栏显示进度
        public static final int TYPE_NITIFICATION = 1;
        //对话框显示进度
        public static final int TYPE_DIALOG = 2;
        //POST方法
        public static final int METHOD_POST = 3;
        //GET方法
        public static final int METHOD_GET = 4;
        //显示的app资源图
        private int iconRes;
        //显示的app名
        private String appName;
        //显示log日志
        private boolean showLog;
        //设置请求方式
        private int requestMethod = METHOD_POST;


        public final Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }


        public final Builder showLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public final Builder setRequestMethod(int requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public final Builder setShowType(int showType) {
            this.showType = showType;
            return this;
        }

        public final Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public final Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public final Builder setIgnoreThisVersion(boolean canIgnoreThisVersion) {
            this.canIgnoreThisVersion = canIgnoreThisVersion;
            return this;
        }

        public final Builder build() {
            return this;
        }

    }

    public boolean saveArray(List<String> list) {
        SharedPreferences sp = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    public List loadArray() {
        List<String> list = new ArrayList<>();
        SharedPreferences mSharedPreference1 = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for (int i = 0; i < size; i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return list;
    }
}
