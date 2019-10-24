package co.baselib.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 * wpt
 * </pre>
 */
public class BySharedPreferencesHelper {
    public final static String IS_PUSH_INTO = "is_push_into";//是否推送进入 true 是
    public final static String ISCOMMENT = "iscomment";//是否弹出过评论对话框
    public final static String TAG = "SharedPreferencesHelper";
    public final static String ILOOMO = "RUIYANG";
    public final static String SHARED_PATH = "app_share";
    public final static String HISTORY = "HISTORY";
    public final static String ISLOGIN = "ISLOGIN";//是否已经登录
    public final static String DOWNLOAD_VERSION = "DOWNLOAD_VERSION";//版本跟新
    public final static String TIME_SYNC = "TimeSync";// 存储当前时间和服务器时间对比时间差
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Context mContext;

    private static BySharedPreferencesHelper sh;

    public static synchronized BySharedPreferencesHelper instance(
            Context context, String pName) {
        if (sh == null) {
            sh = new BySharedPreferencesHelper(context, pName);
        }
        return sh;
    }

    public BySharedPreferencesHelper(Context context, String pName) {
        this.mContext = context;
        sp = mContext.getSharedPreferences(pName, 0);
        editor = sp.edit();
    }

    public void putValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    public String getValue(String key) {
        return sp.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaults) {
        return sp.getBoolean(key, defaults);
    }

    public void moveValue(String key) {
        editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
