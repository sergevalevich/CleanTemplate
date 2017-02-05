package com.valevich.clean.domain.data.impl;

import android.content.Context;

import com.valevich.clean.database.DbHelper;
import com.valevich.clean.database.converters.DbCategoryConverter;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.domain.data.base.ICategoriesManager;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.errors.NetworkUnavailableException;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadSourcesConverter;
import com.valevich.clean.network.utils.ConnectivityInspector;
import com.valevich.clean.rx.utils.SchedulersTransformer;

import java.util.List;

import rx.Observable;

public class CategoriesManager implements ICategoriesManager {

    private DbHelper dbHelper;
    private RestService restService;
    private Context context;

    public CategoriesManager(DbHelper dbHelper, RestService restService, Context context) {
        this.dbHelper = dbHelper;
        this.restService = restService;
        this.context = context;
    }

    @Override
    public Observable<List<Category>> getCategories() {
        return dbHelper.getCategories(CategoryEntity.SELECT_ALL, new String[]{}, CategoryEntity.FACTORY.select_allMapper())
                .map(DbCategoryConverter::getCategoriesByDbEntity)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    @Override
    public Observable<List<Source>> refreshCategories() {
        return getFreshCategories()
                .doOnNext(this::cacheCategories)
                .compose(SchedulersTransformer.INSTANCE.applySchedulers());
    }

    private Observable<List<Source>> getFreshCategories() {
        return ConnectivityInspector.isNetworkAvailable(context)
                ? restService.getSources().map(PayloadSourcesConverter::getSourcesByPayload)
                : Observable.error(new NetworkUnavailableException());
    }

    private void cacheCategories(List<Source> sources) {
        dbHelper.insertSources(sources);
    }
}
