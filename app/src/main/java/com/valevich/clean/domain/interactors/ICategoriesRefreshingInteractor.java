package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;

import java.util.List;

import rx.Observable;


public interface ICategoriesRefreshingInteractor extends Interactor {
    Observable<List<Source>> refreshCategories();
}
