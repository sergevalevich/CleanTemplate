package com.valevich.clean.repository;


import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface StoriesRepository {
    void add(Iterable<Story> items);

    void add(Story item);

    void update(Story item);

    Observable<List<Story>> getByCategory(Category category,int limit,int offset);

    Observable<List<Story>> getBookMarked(int limit,int offset);

    Observable<List<Story>> find(String filter,int count, int offset);

}
