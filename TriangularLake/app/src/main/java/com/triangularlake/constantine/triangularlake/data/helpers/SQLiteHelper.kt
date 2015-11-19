package com.triangularlake.constantine.triangularlake.data.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, SQLiteHelper.DATABASE_NAME, null, SQLiteHelper.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate()");
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade()");
    }

    companion object {
        @JvmField
        public val DATABASE_NAME: String = "triangular_lake_db";
        public val DATABASE_VERSION: Int = 1;
        private val TAG: String = SQLiteHelper.javaClass.simpleName;
    }
}

