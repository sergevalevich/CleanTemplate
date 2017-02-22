package com.valevich.clean.domain.repository;


import com.valevich.clean.domain.model.Category;

import java.util.List;

import rx.Observable;

public interface ICategoriesRepository {
    void add(Iterable<Category> items);

    void add(Category item);

    Observable<List<Category>> get();
}
