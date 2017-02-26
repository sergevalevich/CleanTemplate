package com.valevich.umora.database;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqldelight.RowMapper;
import com.valevich.umora.errors.UpdateDeleteException;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class DatabaseHelper {
    private BriteDatabase db;

    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        db = sqlBrite.wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    public <T> Observable<List<T>> get(String table,String query, String[] args, RowMapper<T> mapper) {
        return db.createQuery(table, query, args).mapToList(mapper::map)
                //.delay(4, TimeUnit.SECONDS) // FIXME: 25.02.2017
                ;
    }

    public BriteDatabase.Transaction startTransaction() {
        return db.newTransaction();
    }

    public SQLiteDatabase getWritableDatabase() {
        return db.getWritableDatabase();
    }

    public void insert(String table, SQLiteStatement statement) {
        db.executeInsert(table, statement);
    }

    public void updateDelete(String table, SQLiteStatement statement) {
        int rows = db.executeUpdateDelete(table, statement);
        if (rows == 0) throw new UpdateDeleteException();
    }

}
