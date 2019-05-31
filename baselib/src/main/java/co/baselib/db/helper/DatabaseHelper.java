package co.baselib.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import co.baselib.global.IloomoConfig;
import co.baselib.utils.PFileUtils;


/**
 * Helper类，提供单例Helper
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context, (PFileUtils.getSDPath()+IloomoConfig.init(context).getDbName()), null, IloomoConfig.init(context).getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


}
