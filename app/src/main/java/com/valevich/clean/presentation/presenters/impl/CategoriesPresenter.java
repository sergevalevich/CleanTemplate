package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.database.DbHelper;
import com.valevich.clean.database.DbOpenHelper;
import com.valevich.clean.domain.data.base.ICategoriesManager;
import com.valevich.clean.domain.data.impl.CategoriesManager;
import com.valevich.clean.domain.interactors.ICategoriesLoadingInteractor;
import com.valevich.clean.domain.interactors.ICategoriesRefreshingInteractor;
import com.valevich.clean.domain.interactors.impl.CategoriesLoadingInteractor;
import com.valevich.clean.domain.interactors.impl.CategoriesRefreshingInteractor;
import com.valevich.clean.domain.model.Source;
import com.valevich.clean.network.RestService;
import com.valevich.clean.presentation.presenters.base.BasePresenter;
import com.valevich.clean.presentation.ui.fragments.CategoriesFragment;

import timber.log.Timber;


public class CategoriesPresenter extends BasePresenter<CategoriesFragment> {

    private static final int LOAD_CATEGORIES_TASK_ID = 1;
    private static final int UPDATE_CATEGORIES_TASK_ID = 2;

    private ICategoriesLoadingInteractor loadingInteractor;
    private ICategoriesRefreshingInteractor refreshingInteractor;

    public CategoriesPresenter(Context context) {
        ICategoriesManager categoriesManager = new CategoriesManager(new DbHelper(new DbOpenHelper(context)), new RestService(), context);
        this.loadingInteractor = new CategoriesLoadingInteractor(categoriesManager);
        this.refreshingInteractor = new CategoriesRefreshingInteractor(categoriesManager);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(
                LOAD_CATEGORIES_TASK_ID,
                () -> loadingInteractor.loadCategories(),
                (CategoriesFragment::onCategories),
                CategoriesFragment::onError);

        restartableLatestCache(
                UPDATE_CATEGORIES_TASK_ID,
                () -> refreshingInteractor.refreshCategories(),
                ((f, sources) -> {
                    int count = 0;
                    for (Source source:sources) {
                        count += source.categories().size();
                    }
                    Timber.d("got %d categories",count);
                }),
                CategoriesFragment::onError);
    }

    public void loadCategories() {
        start(LOAD_CATEGORIES_TASK_ID);
    }

    public void refreshCategories() {
        start(UPDATE_CATEGORIES_TASK_ID);
    }
}
