package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.database.DatabaseHelper;
import com.valevich.clean.database.DbOpenHelper;
import com.valevich.clean.network.RestService;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.fragments.CategoriesFragment;
import com.valevich.clean.domain.repository.ICategoriesRepository;
import com.valevich.clean.domain.repository.ISourcesRepository;
import com.valevich.clean.domain.repository.impl.CategoriesRepository;
import com.valevich.clean.domain.repository.impl.SourcesRepository;


public class CategoriesPresenter extends BasePresenter<CategoriesFragment> {

    private static final int LOAD_CATEGORIES_TASK_ID = 1;
    private static final int UPDATE_SOURCES_TASK_ID = 2;

    private ISourcesRepository sourcesRepository;
    private ICategoriesRepository categoriesRepository;

    public CategoriesPresenter(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(new DbOpenHelper(context));
        sourcesRepository = new SourcesRepository(databaseHelper, new RestService(), context);
        categoriesRepository = new CategoriesRepository(databaseHelper);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                LOAD_CATEGORIES_TASK_ID,
                () -> categoriesRepository.get(),
                (CategoriesFragment::onCategories),
                CategoriesFragment::onError);

        restartableLatestCache(
                UPDATE_SOURCES_TASK_ID,
                () -> sourcesRepository.get(),
                ((f, s) -> f.onSourcesUpToDate()),
                CategoriesFragment::onError);
    }

    public void getCategories() {
        start(LOAD_CATEGORIES_TASK_ID);
    }

    public void refreshCategories() {
        start(UPDATE_SOURCES_TASK_ID);
    }
}
