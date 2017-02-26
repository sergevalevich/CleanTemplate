package com.valevich.umora.database;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.valevich.umora.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class DbModule {
    @Provides
    @Singleton
    public BriteDatabase provideBriteDatabase(DbOpenHelper openHelper, SqlBrite sqlBrite) {
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    @Provides
    @Singleton
    public SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @Singleton
    public DbOpenHelper provideOpenHelper(@ApplicationContext Context context) {
        return new DbOpenHelper(context);
    }
}
