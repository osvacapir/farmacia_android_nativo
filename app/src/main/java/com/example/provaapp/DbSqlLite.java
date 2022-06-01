package com.example.provaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbSqlLite extends SQLiteOpenHelper {

    private static final String DBNAME = "capir_farm";
    private static final String TABLE = "artigos";
    private static final int VER = 1;

    public DbSqlLite(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE + "(" +
                "id integer primary key, " +
                "name_comer text, " +
                "name_cient text," +
                "data_valid date," +
                "data_fabri date," +
                "price double" +
                ")";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + TABLE + "";
        db.execSQL(query);
        onCreate(db);
    }

}
