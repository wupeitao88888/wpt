package co.baselib.db.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import co.baselib.BuildConfig;
import co.baselib.utils.ByFileUtils;


/**
 * Helper类，提供单例Helper
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = ByFileUtils.getSDPath();
    //Helper单例
    public DatabaseHelper(Context context,String name,int version) {
        super(context, DB_NAME+name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


}
