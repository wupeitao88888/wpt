package co.baselib.update.utils;

/**
 * Created by wupeitao on 2017/10/26.
 */

public class HotUpdateUtils {
    private static boolean background = false;



    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean back) {
        background = back;
    }
}
