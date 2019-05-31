package co.jmessage;

import android.content.Context;

import cn.jpush.im.android.api.JMessageClient;

public class JmessageConfig {
    /**
     * 项目初始化
     *
     * @param context
     * @param debug
     */
    public static void init(Context context, boolean debug) {
        JMessageClient.init(context);
        JMessageClient.setDebugMode(debug);
    }

    
}
