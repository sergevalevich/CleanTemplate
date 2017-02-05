package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;

import java.util.List;

import rx.Observable;


public interface ICategoriesLoadingInteractor extends Interactor {
    Observable<List<Category>> loadCategories();
}
