package com.valevich.clean.domain.repository;


import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IStoriesRepository {
    void add(Iterable<Story> items);

    void add(Story item);

    Observable<Story> update(Story item);

    Observable<List<Story>> getByCategory(Category category,int limit,int offset);

    Observable<List<Story>> getByCategory(Category category,int limit);

    Observable<List<Story>> getBookMarked(int limit,int offset);

    Observable<List<Story>> find(String filter,int count, int offset);

}
