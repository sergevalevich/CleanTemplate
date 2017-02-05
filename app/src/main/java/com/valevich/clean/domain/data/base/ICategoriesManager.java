package com.valevich.clean.domain.data.base;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Source;

import java.util.List;

import rx.Observable;

public interface ICategoriesManager {
    Observable<List<Category>> getCategories();

    Observable<List<Source>> refreshCategories();
}
