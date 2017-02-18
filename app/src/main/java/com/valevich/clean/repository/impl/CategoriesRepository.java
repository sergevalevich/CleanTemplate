package com.valevich.clean.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.converters.DbCategoryConverter;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.repository.IRepository;
import com.valevich.clean.repository.specifications.SqlDelightSpecification;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;
import timber.log.Timber;


public class CategoriesRepository implements IRepository<Category,SqlDelightSpecification<CategoryEntity>> {

    private DatabaseHelper databaseHelper;

    private final CategoryEntity.Insert_row rowInsertStatement;

    public CategoriesRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.rowInsertStatement = new CategoryEntity.Insert_row(databaseHelper.getWritableDatabase());
    }

    @Override
    public void add(Iterable<Category> items) {
        BriteDatabase.Transaction transaction = databaseHelper.startTransaction();
        try {
            for (Category category : items) {
                add(category);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        Timber.d("Categories inserted");
    }

    @Override
    public void add(Category category) {
        rowInsertStatement.bind(
                category.getName(),
                category.getDescription(),
                category.getSite());
        databaseHelper.insert(CategoryEntity.TABLE_NAME, rowInsertStatement.program);
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public Observable<List<Category>> read(SqlDelightSpecification<CategoryEntity> specification) {
        return databaseHelper.get(CategoryEntity.TABLE_NAME, specification.getQuery(), specification.getArgs(), specification.getMapper())
                .map(DbCategoryConverter::getCategoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }
}
