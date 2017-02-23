package com.valevich.clean.domain.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.database.model.SourceEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.domain.repository.IRepository;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;

import java.util.List;

import rx.Observable;


public class SourcesRepository implements IRepository<Source,SqlDelightSpecification<SourceEntity>> {

    private DatabaseHelper databaseHelper;

    private IRepository<Category,SqlDelightSpecification<CategoryEntity>> categoriesRepo;

    private final SourceEntity.Insert_row sourceInsertStatement;

    public SourcesRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.sourceInsertStatement = new SourceEntity.Insert_row(databaseHelper.getWritableDatabase());
        this.categoriesRepo = new CategoriesRepository(this.databaseHelper);
    }

    @Override
    public void add(Iterable<Source> items) {
        BriteDatabase.Transaction transaction = databaseHelper.startTransaction();
        try {
            for (Source source : items) {
                add(source);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    @Override
    public void add(Source source) {
        sourceInsertStatement.bind(source.getSite());
        databaseHelper.insert(SourceEntity.TABLE_NAME, sourceInsertStatement.program);
        categoriesRepo.add(source.getCategories());
    }

    @Override
    public Observable<Source> update(Source source) {
        return null;
    }

    @Override
    public Observable<List<Source>> get(SqlDelightSpecification<SourceEntity> specification) {
        return null;
    }
}
