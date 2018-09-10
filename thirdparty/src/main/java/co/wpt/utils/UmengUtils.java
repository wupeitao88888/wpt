package co.wpt.utils;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

public class UmengUtils {
    /***
     * 初始化umengsdk
     * @param context  上下文菜单
     * @param appid   umengappid
     * @param debug  是否打印log
     */
    public static void setShareUtilsInit(Context context, String appid, boolean debug) {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(context, appid, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        setDebug(debug);
    }

    /***
     * 是否打印日志
     * @param blean
     */
    public static void setDebug(boolean blean) {
        SLog.isDebug = blean;
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(blean);
    }

}
