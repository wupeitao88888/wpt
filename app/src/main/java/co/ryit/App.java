package co.ryit;

import android.app.Application;
import android.text.TextUtils;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


import cn.jpush.im.android.api.JMessageClient;
import co.baselib.global.AppController;
import co.baselib.global.IloomoConfig;
import co.jmessage.JmessageConfig;
import co.wpt.utils.UmengUtils;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        UmengUtils.setShareUtilsInit(this, "59150911b27b0a1ebf002350", true);
        IloomoConfig.init(this).setDebug(false);
        AppController.getInstance().init(this, this);
        JmessageConfig.init(this, true);
    }

    {
        PlatformConfig.setWeixin(BuildConfig.wxAPPID, BuildConfig.wxSecret);
        PlatformConfig.setQQZone(BuildConfig.qqAPPID, BuildConfig.qqKey);
        PlatformConfig.setSinaWeibo(BuildConfig.wbKEY, BuildConfig.wbSecret, BuildConfig.wbRedirectUrl);
    }
}
