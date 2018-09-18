package co.baselib.global;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import javax.net.ssl.SSLContext;

import co.baselib.BuildConfig;
import co.baselib.R;
import co.baselib.utils.ActivityPageManager;
import co.baselib.utils.CrashHandler;
import co.baselib.utils.L;
import co.baselib.utils.SSLContextUtil;

/**
 * Created by wupeitao on 15/6/15.
 */
public class AppController {


    public static Application mApplication;
    public static Context context;

    private static AppController mInstance;
    private static DisplayMetrics displayMetrics = null;

    /**
     * @return AppController的实例
     */
    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }
        return mInstance;
    }

    public void init(Application application, Context context) {
        mApplication = application;
        this.context = context;
        netInit();
        appInit();
        initUmAppkey();
        initXutils();
        initJLog();
        initImageLoader();
    }


    public String getResources(int res) {
        return mApplication.getResources().getText(R.string.error_not_found_server).toString();
    }


    /***
     * 程序初始化
     */
    public void appInit() {
        if (!AppConfig.ISZENGSHI) {
            L.isDebug = true;
        } else {
            L.isDebug = false;

        }
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
    }

    /***
     * 初始化nohttp
     */
    private void netInit() {
        com.yanzhenjie.nohttp.Logger.setDebug(AppConfig.ISZENGSHI);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        com.yanzhenjie.nohttp.Logger.setTag("NoHttp：");// 设置NoHttp打印Log的tag。
        InitializationConfig config;
        if (BuildConfig.is_certificate) {
            SSLContext sslContext = SSLContextUtil.getSSLContext();
            config = InitializationConfig.newBuilder(context)
                    // 全局连接服务器超时时间，单位毫秒，默认10s。
                    .connectionTimeout(60 * 1000)
                    // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                    .readTimeout(60 * 1000)
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                    .cacheStore(
                            // 如果不使用缓存，setEnable(false)禁用。
                            new DBCacheStore(context).setEnable(true)
                    )
                    // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                    .cookieStore(
                            // 如果不维护cookie，setEnable(false)禁用。
                            new DBCookieStore(context).setEnable(true)
                    )
                    // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                    .networkExecutor(new OkHttpNetworkExecutor())
                    // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
//                .addHeader()
                    // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
//                .addParam()
                    .sslSocketFactory(sslContext.getSocketFactory()) // 全局SSLSocketFactory。
//                .hostnameVerifier() // 全局HostnameVerifier。
                    .retry(3) // 全局重试次数，配置后每个请求失败都会重试x次。
                    .build();
        } else {
            config = InitializationConfig.newBuilder(context)
                    // 全局连接服务器超时时间，单位毫秒，默认10s。
                    .connectionTimeout(60 * 1000)
                    // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                    .readTimeout(60 * 1000)
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                    .cacheStore(
                            // 如果不使用缓存，setEnable(false)禁用。
                            new DBCacheStore(context).setEnable(true)
                    )
                    // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                    .cookieStore(
                            // 如果不维护cookie，setEnable(false)禁用。
                            new DBCookieStore(context).setEnable(true)
                    )
                    // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                    .networkExecutor(new OkHttpNetworkExecutor())
                    // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
//                .addHeader()
                    // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
//                .addParam()
//                .hostnameVerifier() // 全局HostnameVerifier。
                    .retry(3) // 全局重试次数，配置后每个请求失败都会重试x次。
                    .build();
        }

        NoHttp.initialize(config);
    }

    /***
     * umeng初始化
     */
    private void initUmAppkey() {
//        PlatformConfig.setWeixin(AppConfig.WEIXIN_APPID, AppConfig.WEIXIN_APPSECRET);
//        PlatformConfig.setQQZone(AppConfig.QQ_APPID, AppConfig.QQ_APPKEY);
//        PlatformConfig.setSinaWeibo(AppConfig.WEIBO_APPID, AppConfig.WEIBO_APPKEY, AppConfig.WEIBO_URL);

        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
    }

    /***
     * 初始化xutils
     */
    private void initXutils() {
//        x.Ext.init(mApplication);
//        x.Ext.setDebug(!AppConfig.ISZENGSHI); // 是否输出debug日志, 开启debug会影响性能.
    }


    /***
     * jlog初始化
     */
    private void initJLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("RYIT")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return !AppConfig.ISZENGSHI;
            }
        });

    }

    /***
     * 退出程序
     */
    public void exit() {
        ActivityPageManager.getInstance().exit(mApplication);
    }


    private void initImageLoader() {
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(false)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .cacheOnDisk(true)
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .defaultDisplayImageOptions(defaultOptions)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(context, AppConstants.APP_IMAGE)))
//                .diskCacheSize(100 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.LIFO)
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
//                .threadPoolSize(3)
//                .build();
//        ImageLoader.getInstance().init(config);
    }


    public float getScreenDensity() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(context.getResources().getDisplayMetrics());
        }
        return this.displayMetrics.density;
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(context.getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(context.getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }

    public int dp2px(float f) {
        return (int) (0.5F + f * getScreenDensity());
    }

    public int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return context.getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return context.getCacheDir().getAbsolutePath();
    }


    public Context getContext() {
        return context;
    }

    public Application getmApplication() {
        return mApplication;
    }


}
