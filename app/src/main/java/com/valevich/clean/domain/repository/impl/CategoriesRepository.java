package com.valevich.clean.domain.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.converters.DbCategoryConverter;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.repository.IRepository;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;


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
    public Observable<Category> update(Category category) {
        return null;
    }

    @Override
    public Observable<List<Category>> get(SqlDelightSpecification<CategoryEntity> specification) {
        SqlDelightStatement statement = specification.getStatement();
        return databaseHelper.get(CategoryEntity.TABLE_NAME, statement.statement, statement.args, CategoryEntity.FACTORY.select_allMapper())
                .map(DbCategoryConverter::getCategoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }
}
