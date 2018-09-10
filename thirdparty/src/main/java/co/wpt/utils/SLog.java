package co.wpt.utils;


import android.text.TextUtils;
import android.util.Log;

public class SLog {

    private SLog() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "ShareUtils:";


    public static void i(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg)) {
                Log.i(TAG, msg);
            } else {
                Log.i(TAG, msg);
            }
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg))
                Log.d(TAG, msg);
            else
                Log.d(TAG, "");
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);// 打印剩余日志
        } else
            Log.e(TAG, "");
    }
}