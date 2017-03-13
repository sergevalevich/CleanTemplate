package com.valevich.umora.domain.repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.valevich.umora.database.DatabaseHelper;
import com.valevich.umora.database.DbOpenHelper;
import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.database.model.SourceEntity;
import com.valevich.umora.database.model.StoryEntity;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.model.Source;
import com.valevich.umora.domain.model.Story;
import com.valevich.umora.domain.repository.impl.CategoriesRepository;
import com.valevich.umora.domain.repository.impl.SourcesRepository;
import com.valevich.umora.domain.repository.impl.StoriesRepository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;
import com.valevich.umora.injection.ApplicationContext;
import com.valevich.umora.utils.SchedulersTransformer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    static Repository<Category, SqlDelightSpecification<CategoryEntity>> provideCategoriesRepo(DatabaseHelper databaseHelper,
                                                                                        CategoryEntity.Insert_row rowInsertStatement,
                                                                                        SchedulersTransformer schedulersTransformer) {
        return new CategoriesRepository(databaseHelper, rowInsertStatement,schedulersTransformer);
    }

    @Singleton
    @Provides
    static Repository<Source, SqlDelightSpecification<SourceEntity>> provideSourcesRepo(DatabaseHelper databaseHelper,
                                                                                 Repository<Category, SqlDelightSpecification<CategoryEntity>> categoriesRepository,
                                                                                 SourceEntity.Insert_row rowInsertStatement) {
        return new SourcesRepository(databaseHelper, categoriesRepository, rowInsertStatement);
    }

    @Singleton
    @Provides
    static Repository<Story, SqlDelightSpecification<StoryEntity>> provideStoriesRepo(DatabaseHelper databaseHelper,
                                                                               StoryEntity.Update_row rowUpdateStatement,
                                                                               StoryEntity.Insert_row rowInsertStatement,
                                                                               SchedulersTransformer schedulersTransformer) {
        return new StoriesRepository(databaseHelper, rowUpdateStatement, rowInsertStatement,schedulersTransformer);
    }

    @Provides
    @Singleton
    static BriteDatabase provideBriteDatabase(DbOpenHelper openHelper, SqlBrite sqlBrite) {
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    @Provides
    @Singleton
    static SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @Singleton
    static DbOpenHelper provideOpenHelper(@ApplicationContext Context context) {
        return new DbOpenHelper(context);
    }

    @Provides
    @Singleton
    static DatabaseHelper provideDbHelper(BriteDatabase database) {
        return new DatabaseHelper(database);
    }

    @Provides
    @Singleton
    static SQLiteDatabase provideWritableDatabase(BriteDatabase database) {
        return database.getWritableDatabase();
    }

    @Provides
    @Singleton
    static SourceEntity.Insert_row provideInsertSourceStatement(SQLiteDatabase sqLiteDatabase) {
        return new SourceEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    static CategoryEntity.Insert_row provideInsertCategoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new CategoryEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    static StoryEntity.Insert_row provideInsertStoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new StoryEntity.Insert_row(sqLiteDatabase);
    }

    @Provides
    @Singleton
    static StoryEntity.Update_row provideUpdateStoryStatement(SQLiteDatabase sqLiteDatabase) {
        return new StoryEntity.Update_row(sqLiteDatabase);
    }
}
