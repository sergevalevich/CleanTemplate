package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.ICategoriesManager;
import com.valevich.clean.domain.interactors.ICategoriesLoadingInteractor;
import com.valevich.clean.domain.model.Category;

import java.util.List;

import rx.Observable;

public class CategoriesLoadingInteractor extends CategoriesInteractor implements ICategoriesLoadingInteractor {

    public CategoriesLoadingInteractor(ICategoriesManager categoriesManager) {
        super(categoriesManager);
    }

    @Override
    public Observable<List<Category>> loadCategories() {
        return categoriesManager.getCategories();
    }
}
