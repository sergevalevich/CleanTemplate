package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.DbOpenHelper;
import com.valevich.clean.database.model.CategoryEntity;
import com.valevich.clean.database.model.SourceEntity;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.domain.repository.IRepository;
import com.valevich.clean.domain.repository.impl.CategoriesRepository;
import com.valevich.clean.domain.repository.impl.SourcesRepository;
import com.valevich.clean.domain.repository.specification.SqlDelightSpecification;
import com.valevich.clean.domain.repository.specification.impl.AllCategoriesSqlDSpecification;
import com.valevich.clean.network.RestService;
import com.valevich.clean.network.converters.PayloadSourcesConverter;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.fragments.CategoriesFragment;
import com.valevich.clean.rx.utils.SchedulersTransformer;


public class CategoriesPresenter extends BasePresenter<CategoriesFragment> {

    private static final int LOAD_CATEGORIES_TASK_ID = 0;
    private static final int REFRESH_SOURCES_TASK_ID = 1;

    private IRepository<Category,SqlDelightSpecification<CategoryEntity>> categoriesRepository;
    private IRepository<Source,SqlDelightSpecification<SourceEntity>> sourcesRepository;
    private RestService restService;

    public CategoriesPresenter(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(new DbOpenHelper(context));
        sourcesRepository = new SourcesRepository(databaseHelper);
        categoriesRepository = new CategoriesRepository(databaseHelper);
        this.restService = new RestService();
    }

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
                () -> restService.getSources()
                        .map(PayloadSourcesConverter::getSourcesByPayload)
                        .doOnNext(sourcesRepository::add)
                        .compose(SchedulersTransformer.INSTANCE.applySchedulers()),
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
