package com.valevich.umora.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.umora.domain.repository.impl.CategoriesRepository;
import com.valevich.umora.domain.repository.impl.SourcesRepository;
import com.valevich.umora.domain.repository.specification.impl.AllCategoriesSqlDSpecification;
import com.valevich.umora.errors.NetworkUnavailableException;
import com.valevich.umora.injection.ApplicationContext;
import com.valevich.umora.network.UmoraApi;
import com.valevich.umora.network.converters.PayloadSourcesConverter;
import com.valevich.umora.network.utils.ConnectivityInspector;
import com.valevich.umora.presentation.presenters.base.BasePresenter;
import com.valevich.umora.presentation.ui.fragments.CategoriesFragment;
import com.valevich.umora.rx.utils.SchedulersTransformer;

import javax.inject.Inject;

import rx.Observable;


public class CategoriesPresenter extends BasePresenter<CategoriesFragment> {

    private static final int LOAD_CATEGORIES_TASK_ID = 0;
    private static final int REFRESH_SOURCES_TASK_ID = 1;

    @Inject
    CategoriesRepository categoriesRepository;

    @Inject
    SourcesRepository sourcesRepository;

    @Inject
    UmoraApi restApi;

    @Inject
    @ApplicationContext Context context;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                LOAD_CATEGORIES_TASK_ID,
                () -> categoriesRepository.get(AllCategoriesSqlDSpecification.create()),
                (CategoriesFragment::onCategories),
                CategoriesFragment::onError);

        restartableLatestCache(
                REFRESH_SOURCES_TASK_ID,
                () -> ConnectivityInspector.isNetworkAvailable(context)
                        ? restApi.getSources()
                        .map(PayloadSourcesConverter::getSourcesByPayload)
                        .doOnNext(sourcesRepository::add)
                        .compose(SchedulersTransformer.INSTANCE.applySchedulers())
                        : Observable.error(new NetworkUnavailableException()),
                ((f, s) -> f.onSourcesUpToDate()),
                CategoriesFragment::onError);
    }

    public void getAllCategories() {
        start(LOAD_CATEGORIES_TASK_ID);
    }

    public void refreshCategories() {
        start(REFRESH_SOURCES_TASK_ID);
    }
}
