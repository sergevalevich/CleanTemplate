package com.valevich.clean.domain.repository.impl;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.model.SourceEntity;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.errors.NetworkUnavailableException;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadSourcesConverter;
import com.valevich.clean.network.utils.ConnectivityInspector;
import com.valevich.clean.domain.repository.ICategoriesRepository;
import com.valevich.clean.domain.repository.ISourcesRepository;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;


public class SourcesRepository implements ISourcesRepository {

    private DatabaseHelper databaseHelper;
    private RestService restService;
    private Context context;

    private ICategoriesRepository categoriesRepo;

    private final SourceEntity.Insert_row sourceInsertStatement;

    public SourcesRepository(DatabaseHelper databaseHelper,RestService restService,Context context) {
        this.databaseHelper = databaseHelper;
        this.restService = restService;
        this.context = context;
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
    public Observable<List<Source>> get() {
        if (!ConnectivityInspector.isNetworkAvailable(context)) {
            return Observable.error(new NetworkUnavailableException());
        } else {
            return restService.getSources()
                    .map(PayloadSourcesConverter::getSourcesByPayload)
                    .doOnNext(this::add)
                    .compose(SchedulersTransformer.INSTANCE.applySchedulers());
        }
    }

}
