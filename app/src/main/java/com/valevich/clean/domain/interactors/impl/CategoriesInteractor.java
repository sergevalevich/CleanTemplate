package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.ICategoriesManager;
import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;

import java.util.List;

abstract class CategoriesInteractor {

    ICategoriesManager categoriesManager;

    CategoriesInteractor(ICategoriesManager categoriesManager) {
        this.categoriesManager = categoriesManager;
    }
}
