package co.baselib.utils;


import android.text.TextUtils;

import com.orhanobut.logger.Logger;


public class L {

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void i(String msg) {
        Logger.i(msg);

    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);

    }

    public static void json(String msg) {
        Logger.json(msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }

    public static void i(String tag, String msg) {
        Logger.i(msg);
    }

    public static void d(String tag, String msg) {
        Logger.d(msg);
    }

    public static void e(String tag, String msg) {
        Logger.e(msg);
    }

    public static void v(String tag, String msg) {

        Logger.v(msg);
    }
}