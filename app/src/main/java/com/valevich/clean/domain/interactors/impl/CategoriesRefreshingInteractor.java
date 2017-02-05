package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.ICategoriesManager;
import com.valevich.clean.domain.interactors.ICategoriesRefreshingInteractor;
import com.valevich.clean.domain.model.Source;

import java.util.List;

import rx.Observable;

public class CategoriesRefreshingInteractor extends CategoriesInteractor implements ICategoriesRefreshingInteractor {

    public CategoriesRefreshingInteractor(ICategoriesManager categoriesManager) {
        super(categoriesManager);
    }

    @Override
    public Observable<List<Source>> refreshCategories() {
        return categoriesManager.refreshCategories();
    }
}
