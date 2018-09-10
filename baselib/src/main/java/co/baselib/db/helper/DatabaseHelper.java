package co.baselib.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import co.baselib.BuildConfig;
import co.baselib.utils.PFileUtils;


/**
 * Helper类，提供单例Helper
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = PFileUtils.getSDPath() + BuildConfig.dbName;
    //数据库名
    public static final int DB_VERSION = BuildConfig.dbVersion;
    //数据库版本

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


}
