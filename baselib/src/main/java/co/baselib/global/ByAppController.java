package co.baselib.global;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.OkHttpConnectFactory;


import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import co.baselib.R;
import co.baselib.service.ByPreLoadX5Service;
import co.baselib.utils.ByActivityPageManager;
import co.baselib.utils.ByCrashHandler;
import co.baselib.utils.BySSLContextUtil;

/**
 * Created by wupeitao on 15/6/15.
 */
public class ByAppController {


    public static Application mApplication;
    public static Context context;

    private static ByAppController mInstance;
    private static DisplayMetrics displayMetrics = null;

    /**
     * @return AppController的实例
     */
    public static synchronized ByAppController getInstance() {
        if (mInstance == null) {
            mInstance = new ByAppController();
        }
        return mInstance;
    }

    public void init(Application application) {
        mApplication = application;
        this.context = mApplication.getApplicationContext();
        netInit();
        appInit();
        initJLog();
        x5init();
    }


    public void x5init() {


        Intent intent = new Intent(context, ByPreLoadX5Service.class);
        context.startService(intent);

    }

    /***
     * 程序初始化
     */
    public void appInit() {
        ByCrashHandler crashHandler = ByCrashHandler.getInstance();
        crashHandler.init(context);
    }

    /***
     * 初始化nohttp
     */
    private void netInit() {
//        com.yanzhenjie.nohttp.Logger.setDebug(IloomoConfig.init(context).isDebug());// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
//        com.yanzhenjie.nohttp.Logger.setTag("NoHttp：");// 设置NoHttp打印Log的tag。
        KalleConfig config;

        if (ByConfig.init(context).isCertificate() && ByConfig.init(context).getInputStream() != null) {
            SSLContext sslContext = BySSLContextUtil.getSSLContext(ByConfig.init(context).getInputStream());

            config = KalleConfig.newBuilder()
                    // 全局连接服务器超时时间，单位毫秒，默认10s。
                    .connectionTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                    // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                    .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
//                    .cookieStore(DBCookieStore.newBuilder(context).build())
                    // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                    .connectFactory(OkHttpConnectFactory.newBuilder().build())
                    .sslSocketFactory(sslContext.getSocketFactory()) // 全局SSLSocketFactory。
                    .build();

        } else {
            config = KalleConfig.newBuilder()
                    // 全局连接服务器超时时间，单位毫秒，默认10s。
                    .connectionTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                    // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                    .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
//                    .cookieStore(DBCookieStore.newBuilder(context).build())
                    // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                    .connectFactory(OkHttpConnectFactory.newBuilder().build())
                    .build();
        }
        Kalle.setConfig(config);
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
                return ByConfig.init(context).isDebug();
            }
        });

    }

    /***
     * 退出程序
     */
    public void exit() {
        ByActivityPageManager.getInstance().exit(mApplication);
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
