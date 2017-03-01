package com.valevich.umora.presentation.presenters.impl;

import android.os.Bundle;

import com.valevich.umora.database.model.CategoryEntity;
import com.valevich.umora.database.model.SourceEntity;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.domain.model.Source;
import com.valevich.umora.domain.repository.IRepository;
import com.valevich.umora.domain.repository.specification.SqlDelightSpecification;
import com.valevich.umora.domain.repository.specification.impl.AllCategoriesSqlDSpecification;
import com.valevich.umora.errors.NetworkUnavailableException;
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
    IRepository<Category,SqlDelightSpecification<CategoryEntity>> categoriesRepository;

    @Inject
    IRepository<Source,SqlDelightSpecification<SourceEntity>> sourcesRepository;

    @Inject
    UmoraApi restApi;

    @Inject
    ConnectivityInspector connectivityInspector;

    @Inject
    SchedulersTransformer schedulersTransformer;

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
                () -> connectivityInspector.isNetworkAvailable()
                        ? restApi.getSources()
                        .map(PayloadSourcesConverter::getSourcesByPayload)
                        .doOnNext(sourcesRepository::add)
                        .compose(schedulersTransformer.applySchedulers())
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
