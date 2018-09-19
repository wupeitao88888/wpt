package co.ryit;

import android.app.Application;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


import co.baselib.global.AppController;
import co.wpt.utils.UmengUtils;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        UmengUtils.setShareUtilsInit(this, "59150911b27b0a1ebf002350", true);
        AppController.getInstance().init(this, this);
    }

    {
        PlatformConfig.setWeixin(BuildConfig.wxAPPID, BuildConfig.wxSecret);
        PlatformConfig.setQQZone(BuildConfig.qqAPPID, BuildConfig.qqKey);
        PlatformConfig.setSinaWeibo(BuildConfig.wbKEY, BuildConfig.wbSecret, BuildConfig.wbRedirectUrl);
    }


}
