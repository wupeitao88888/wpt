package co.ryit;

import android.app.Application;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import co.wpt.utils.UmengUtils;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        UmengUtils.setShareUtilsInit(this,"59150911b27b0a1ebf002350",true);
    }
    {
        PlatformConfig.setWeixin(co.baselib.BuildConfig.wxAPPID, co.baselib.BuildConfig.wxSecret);
        PlatformConfig.setQQZone(co.baselib.BuildConfig.qqAPPID, co.baselib.BuildConfig.qqKey);
        PlatformConfig.setSinaWeibo(co.baselib.BuildConfig.wbKEY, co.baselib.BuildConfig.wbSecret, co.baselib.BuildConfig.wbRedirectUrl);
    }


}
