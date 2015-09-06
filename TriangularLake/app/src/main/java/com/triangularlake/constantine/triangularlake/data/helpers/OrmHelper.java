package com.triangularlake.constantine.triangularlake.data.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.dto.Side;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class OrmHelper extends OrmLiteSqliteOpenHelper implements ICommonOrmHelper {

    private final static String TAG = OrmHelper.class.getSimpleName();
    private static final int DB_FILES_COPY_BUFFER_SIZE = 1024;
    private static final String TRIANGULAR_LAKE_DB = "triangular_lake_db";

    // пути к БД в assets
    private String dbFolder;
    private String dbPath;
    private String dbAssetsPath;
    private Context context;

    private SparseArray<CommonDao> daos;

    private Class[] classes = {
            Sector.class,
            Region.class,
            Boulder.class,
            Photo.class,
            Problem.class,
            Side.class
    };

    public OrmHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        daos = new SparseArray<>();
        dbFolder = "/data/data/" + context.getPackageName() + "/databases/";
        dbPath = dbFolder + TRIANGULAR_LAKE_DB;
        dbAssetsPath = "db/" + TRIANGULAR_LAKE_DB;
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

    public void checkDatabase() {
        // TODO: добавить проверку на версию
        File fileDB = new File(dbPath);
        if (!fileDB.exists()) {
            initDBFromAssets();
        }
    }

    // TODO: сделать в AsyncTask и показывать прогрессбар
    // + добавить zip базы данных!!!
    // + можно выводить логотип пока загружаетя БД
    private void initDBFromAssets() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(context.getAssets().open(dbAssetsPath), DB_FILES_COPY_BUFFER_SIZE);
            File dbDir = new File(dbFolder);
            if (!dbDir.exists()) {
                dbDir.mkdir();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(dbPath), DB_FILES_COPY_BUFFER_SIZE);
            byte[] buffer = new byte[DB_FILES_COPY_BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
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
        } else if (classInstance.equals(Boulder.class)) {
            return getDaoByNum(Boulder.class, BOULDER_DAO_NUMBER);
        } else if (classInstance.equals(Photo.class)) {
            return getDaoByNum(Photo.class, PHOTO_DAO_NUMBER);
        } else if (classInstance.equals(Problem.class)) {
            return getDaoByNum(Problem.class, PROBLEM_DAO_NUMBER);
        } else if (classInstance.equals(Side.class)) {
            return getDaoByNum(Side.class, SIDE_DAO_NUMBER);
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
