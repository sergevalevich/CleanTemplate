package com.valevich.umora.domain.repository.impl;

import com.squareup.sqlbrite.BriteDatabase;
import com.valevich.umora.database.DatabaseHelper;
import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.database.model.SourceEntity;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.model.Source;
import com.valevich.umora.domain.repository.Repository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;

import java.util.List;

import rx.Observable;

public class SourcesRepository implements Repository<Source,SqlDelightSpecification<SourceEntity>> {

    private DatabaseHelper databaseHelper;

    private Repository<Category,SqlDelightSpecification<CategoryEntity>> categoriesRepo;

    private final SourceEntity.Insert_row sourceInsertStatement;

    public SourcesRepository(DatabaseHelper databaseHelper,
                             Repository<Category, SqlDelightSpecification<CategoryEntity>> categoriesRepository,
                             SourceEntity.Insert_row sourceInsertStatement) {
        this.databaseHelper = databaseHelper;
        this.categoriesRepo = categoriesRepository;
        this.sourceInsertStatement = sourceInsertStatement;
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
