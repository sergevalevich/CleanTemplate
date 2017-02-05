package com.valevich.clean.domain.data.base;

import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IStoriesManager {
    Observable<List<Story>> getStoriesByCategory(Category category, int count);

    Observable<List<Story>> refreshStoriesByCategory(Category category, int count);

    Observable<Boolean> update(Story story);

    Observable<List<Story>> getBookMarks();

    Observable<List<Story>> findStories(String filter);
}
