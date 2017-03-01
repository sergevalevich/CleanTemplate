package com.valevich.umora.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.database.model.SourceEntity;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class DbModule {
    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(DbOpenHelper openHelper, SqlBrite sqlBrite) {
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @Singleton
    DbOpenHelper provideOpenHelper(@ApplicationContext Context context) {
        return new DbOpenHelper(context);
    }

    @Provides
    @Singleton
    DatabaseHelper provideDbHelper(BriteDatabase database) {
        return new DatabaseHelper(database);
    }

    @Provides
    @Singleton
    SQLiteDatabase provideWritableDatabase(BriteDatabase database) {
        return database.getWritableDatabase();
    }

    @Provides
    @Singleton
    SourceEntity.Insert_row provideInsertSourceStatement(SQLiteDatabase sqLiteDatabase) {
        return new SourceEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    CategoryEntity.Insert_row provideInsertCategoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new CategoryEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    StoryEntity.Insert_row provideInsertStoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new StoryEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    StoryEntity.Update_row provideUpdateStoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new StoryEntity.Update_row(sqLiteDatabase);
    }
}
