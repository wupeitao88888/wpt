package co.ryit.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import co.baselib.db.helper.DatabaseHelper;
import co.baselib.utils.L;
import co.ryit.db.bean.ThemeIndex;


/**
 * Helper类，提供单例Helper
 */
public class DataHelper extends DatabaseHelper {


    //数据库版本
    private static DataHelper instance;

    public DataHelper(Context context) {
        super(context);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        createTable(connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        dropTable(connectionSource);
        createTable(connectionSource);
    }

    /**
     * 双重加锁检查
     *
     * @param context 上下文
     * @return 单例
     */
    public static synchronized DataHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DataHelper.class) {
                if (instance == null) {
                    instance = new DataHelper(context);
                }
            }
        }
        return instance;
    }


    private void createTable(ConnectionSource connectionSource) {
        try {
            /***
             * 创建表
             */
            TableUtils.createTable(connectionSource, ThemeIndex.class);// //五个按钮
        } catch (SQLException e) {
            L.e("创建表异常");
        }
    }


    private void dropTable(ConnectionSource connectionSource) {
        try {
            /***
             *删除表
             */
            TableUtils.dropTable(connectionSource, ThemeIndex.class, true);// //五个按钮
        } catch (SQLException e) {
            L.e("删除表异常");
        }
    }

}
