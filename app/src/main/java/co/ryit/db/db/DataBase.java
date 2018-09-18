package co.ryit.db.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import co.baselib.db.BaseDao;
import co.ryit.db.helper.DataHelper;


/**
 * BaseDao泛型实现类
 */
public class DataBase<T, Integer> extends BaseDao<T, Integer> {
    private Class<T> clazz;
    private Map<Class<T>, Dao<T, Integer>> mDaoMap = new HashMap<Class<T>, Dao<T, Integer>>();
    private DataHelper mDatabaseHelper;

    //缓存泛型Dao
    public DataBase(Context context, Class<T> clazz) {
        super(context);
        this.clazz = clazz;
        mDatabaseHelper = new DataHelper(context);
    }


    @Override
    public Dao<T, Integer> getDao() throws SQLException {
        Dao<T, Integer> dao = mDaoMap.get(clazz);
        if (null == dao) {
            dao = mDatabaseHelper.getDao(clazz);
            mDaoMap.put(clazz, dao);
        }
        return dao;
    }


}
