package com.triangularlake.constantine.triangularlake.data.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;

import java.sql.SQLException;

public class OrmHelper extends OrmLiteSqliteOpenHelper implements ICommonOrmHelper {

    private final static String TAG = OrmHelper.class.getSimpleName();

    private SparseArray<CommonDao> daos;

    private Class[] classes = {
            Sector.class,
            Region.class
    };

    public OrmHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        daos = new SparseArray<>();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.d(TAG, "onCreate() start");
        try {
            createAllTables(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database");
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate() done");
    }

    public void createAll() {
        try {
            createAllTables(getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            Log.d(TAG, "onUpgrade() start");
            dropAllTables(connectionSource);
            onCreate(sqLiteDatabase, connectionSource);
            Log.d(TAG, "onUpgrade() done");
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Создаем таблицы для перечисленных классов - classes.
     *
     * @param connectionSource
     * @throws SQLException
     */
    private void createAllTables(ConnectionSource connectionSource) throws SQLException {
        for (Class clazz : classes) {
            TableUtils.createTable(connectionSource, clazz);
        }
    }

    /**
     * Удаляем таблицы перечисленных классов - classes.
     *
     * @param connectionSource
     * @throws SQLException
     */
    private void dropAllTables(ConnectionSource connectionSource) throws SQLException {
        for (Class clazz : classes) {
            TableUtils.dropTable(connectionSource, clazz, true);
        }
    }

    /**
     * Возвращает Dao подключения к БД.
     *
     * @param classInstance
     * @return
     * @throws SQLException
     */
    public CommonDao getDaoByClass(Class<?> classInstance) throws SQLException {
        if (classInstance.equals(Sector.class)) {
            return getDaoByNum(Sector.class, SECTOR_DAO_NUMBER);
        } else if (classInstance.equals(Region.class)) {
            return getDaoByNum(Region.class, REGION_DAO_NUMBER);
        }
        return null;
    }

    /**
     * Возвращаем объект dao найденный по dto класса.
     *
     * @param className
     * @param daoNum
     * @return
     * @throws SQLException
     */
    private CommonDao getDaoByNum(Class<?> className, int daoNum) throws SQLException {
        CommonDao dao = daos.get(daoNum);
        if (dao == null) {
            dao = new CommonDao(getConnectionSource(), className);
            daos.put(daoNum, dao);
        }
        return dao;
    }
}
