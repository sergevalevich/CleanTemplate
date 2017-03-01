package com.valevich.umora.domain.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import com.valevich.umora.database.DatabaseHelper;
import com.valevich.umora.database.converters.DbCategoryConverter;
import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.repository.IRepository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;
import com.valevich.umora.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;

public class CategoriesRepository implements IRepository<Category,SqlDelightSpecification<CategoryEntity>> {

    private DatabaseHelper databaseHelper;

    private final CategoryEntity.Insert_row rowInsertStatement;

    private SchedulersTransformer schedulersTransformer;

    public CategoriesRepository(DatabaseHelper databaseHelper,
                                CategoryEntity.Insert_row rowInsertStatement,
                                SchedulersTransformer schedulersTransformer) {
        this.databaseHelper = databaseHelper;
        this.rowInsertStatement = rowInsertStatement;
        this.schedulersTransformer = schedulersTransformer;
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
                .compose(schedulersTransformer.applySchedulers());
    }
}
