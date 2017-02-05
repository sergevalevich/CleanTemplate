package com.valevich.clean.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.database.model.SourceEntity;
import com.valevich.clean.database.model.StoryEntity;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "umorili.db";

    private static final int DATABASE_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(CategoryEntity.CREATE_TABLE);
            db.execSQL(StoryEntity.CREATE_TABLE);
            db.execSQL(SourceEntity.CREATE_TABLE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StoryEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SourceEntity.TABLE_NAME);
        onCreate(db);
    }
}