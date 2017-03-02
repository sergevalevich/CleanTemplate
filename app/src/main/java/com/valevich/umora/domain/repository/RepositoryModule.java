package com.valevich.umora.domain.repository;


import com.valevich.umora.database.DatabaseHelper;
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
import com.valevich.umora.utils.SchedulersTransformer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    IRepository<Category, SqlDelightSpecification<CategoryEntity>> provideCategoriesRepo(DatabaseHelper databaseHelper,
                                                                                         CategoryEntity.Insert_row rowInsertStatement,
                                                                                         SchedulersTransformer schedulersTransformer) {
        return new CategoriesRepository(databaseHelper, rowInsertStatement,schedulersTransformer);
    }

    @Singleton
    @Provides
    IRepository<Source, SqlDelightSpecification<SourceEntity>> provideSourcesRepo(DatabaseHelper databaseHelper,
                                                                                  IRepository<Category, SqlDelightSpecification<CategoryEntity>>  categoriesRepository,
                                                                                  SourceEntity.Insert_row rowInsertStatement) {
        return new SourcesRepository(databaseHelper, categoriesRepository, rowInsertStatement);
    }

    @Singleton
    @Provides
    IRepository<Story, SqlDelightSpecification<StoryEntity>> provideStoriesRepo(DatabaseHelper databaseHelper,
                                                                                StoryEntity.Update_row rowUpdateStatement,
                                                                                StoryEntity.Insert_row rowInsertStatement,
                                                                                SchedulersTransformer schedulersTransformer) {
        return new StoriesRepository(databaseHelper, rowUpdateStatement, rowInsertStatement,schedulersTransformer);
    }
}
